package com.server.databases.redis.service;

import org.json.JSONObject;

import com.server.api.helpers.paypal.CreateResponse;
import com.server.payments.paypal.services.PayPalAuth;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisService {

  private static JedisPoolConfig buildPoolConfig(){
    final JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(100000);
    poolConfig.setMaxIdle(128);
    poolConfig.setMinIdle(16);
    poolConfig.setTestOnBorrow(true);
    poolConfig.setTestOnReturn(true);
    poolConfig.setTestWhileIdle(true);
    poolConfig.setNumTestsPerEvictionRun(3);
    poolConfig.setBlockWhenExhausted(true);
    return poolConfig;
  }

  JedisPool jedisPool = new JedisPool(buildPoolConfig(), "localhost", 6379, 4000);
  
  private void saveToken(String key, String value){
    System.out.println("Saving token in Redis...");
    try {
      Jedis jedis = jedisPool.getResource();
      String response = jedis.set(key, value);
      jedis.close();
      System.out.println("Token saved successfully with status code: " + response + " !");
      return;
    } catch(Exception error){
      System.out.println("Something went wrong while saving the token !");
      System.err.println(error.getMessage());
      error.printStackTrace();
      return;
    }
  }

  public JSONObject getToken(String key){
    System.out.println("Getting a token from Redis...");
    try {
      Jedis jedis = jedisPool.getResource();
      String authDataString = jedis.get(key);
      jedis.close();
      if(authDataString != null){
        JSONObject jsonObject = new JSONObject(authDataString);
        String accessToken = jsonObject.getString("access_token");
        Long expiresIn = jsonObject.getLong("expires_in");
        if(expiresIn > System.currentTimeMillis()){
          System.out.println("Token received from Redis !");
          return CreateResponse.toJSON(200, accessToken);
        } else {
          JSONObject newAuthData = requestAuthToken();
          return newAuthData;
        }
      } else {
        JSONObject response = requestAuthToken();
        return response;
      }
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return CreateResponse.toJSON(500, "Token requesting error !");
    }
  }

  private JSONObject requestAuthToken(){
    System.out.println("Token has expired. Requesting a new one...");
    String authDataString = new PayPalAuth().authorization();

    if(authDataString.contains("access_token")){
      JSONObject authDataJSON = new JSONObject(authDataString);
      String accessToken = authDataJSON.getString("access_token");
      int expiresIn = authDataJSON.getInt("expires_in");
      authDataJSON.put("expires_in", expiresIn + System.currentTimeMillis());
      System.out.println("The token was received !");
      saveToken("PayPalToken", authDataJSON.toString());
      return CreateResponse.toJSON(200, accessToken);
    } else {
      System.out.println("Something went wrong with token receiving !");
      return CreateResponse.toJSON(401, "Error PayPal authorization, try again !");
    }
  }

}