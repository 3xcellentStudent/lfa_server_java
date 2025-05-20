package com.server.paypal.services;

import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// import com.server.api.helpers.paypal.CreateResponse;
// import com.server.api.helpers.paypal.PayPalRoutes;
// import com.server.api.services.threads.PayPalThreads;

@Service
public class PayPalCaptureOrder {

	// public static JSONObject captureOrder(String orderId, String token){
  //   String route = PayPalRoutes.PAYPAL_CHECKOUT_ORDERS + String.format("/%s/capture", orderId);
  //   try {
  //     URL url = new URI(route).toURL();
  //     HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
  //     httpConn.setRequestMethod("POST");
  
  //     httpConn.setRequestProperty("Content-Type", "application/json");
  //     httpConn.setRequestProperty("Authorization", "Bearer " + token);
  
  //     InputStream responseStream = httpConn.getResponseCode() / 100 == 2
  //         ? httpConn.getInputStream()
  //         : httpConn.getErrorStream();
  //     Scanner scanner = new Scanner(responseStream).useDelimiter("\\A");
  //     String response = scanner.hasNext() ? scanner.next() : "";
  //     scanner.close();
  //     return new JSONObject(response);
  //   } catch(Exception error){
  //     System.err.println("Something went wrong while capturing order with error: " + 
  //     error.getMessage());
  //     error.printStackTrace();
  //     return null;
  //   }
	// }

  // public static ResponseEntity<String> isCapturedOrder(JSONObject data){
  //   // JSONObject jsonResponse = new JSONObject(data);
  //   boolean isCompleted = data.getString("status")
  //   .equals("COMPLETED");
    
  //   if(isCompleted){
  //     try {
  //       System.out.println(Thread.currentThread().getName());

  //       Thread thread = new Thread(new PayPalThreads(data));
  //       thread.start();
  //       ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
  //       long[] threadIds = threadMXBean.getAllThreadIds();

  //       System.out.println("Active Threads:");
  //       for (long id : threadIds) {
  //           ThreadInfo threadInfo = threadMXBean.getThreadInfo(id);
  //           System.out.println("Thread Name: " + threadInfo.getThreadName() + ", State: " + threadInfo.getThreadState());
  //       }

  //       // Даём потоку поработать 1 секунду (до выполнения запроса)
  //       Thread.sleep(1000);

  //       // Прерываем поток (если запрос ещё не был выполнен)
  //       System.out.println("Main thread is interrupting the worker thread");
  //       thread.interrupt();

  //       // Основной поток может продолжать работу без ожидания завершения рабочего потока
  //       System.out.println("Main thread continues without waiting for the worker thread to finish");
  //     } catch(Exception error){
  //     }

  //     ResponseEntity<String> response = CreateResponse.toEntity(200, data);
  //     System.out.println("Order was captured successfully !");
  //     return response;
  //   } else {
  //     String message = "Something went wrong while capturing order";
  //     System.out.println(message + " !");
  //     return CreateResponse.toEntity(400, message + ", try again !");
  //   }
  // }

	// public static String getOrderDetails(String orderId, String token){
  //   // String route = PayPalRoutes.PAYPAL_DETAILS_ORDER + String.format("/%s", orderId);
  //   String route = PayPalRoutes.PAYPAL_CHECKOUT_ORDERS +  "/" + orderId;

  //   try {
  //     URL url = new URI(route).toURL();
  //     HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

  //     httpConn.setRequestMethod("GET");
  //     httpConn.setRequestProperty("Authorization", "Bearer " + token);
  
  //     InputStream responseStream = httpConn.getResponseCode() / 100 == 2
  //         ? httpConn.getInputStream()
  //         : httpConn.getErrorStream();
  //     Scanner scanner = new Scanner(responseStream).useDelimiter("\\A");
  //     String response = scanner.hasNext() ? scanner.next() : "";
  //     scanner.close();
  //     return response;
  //   } catch(Exception error){
  //     System.err.println("Something went wrong while requesting order details with error: " + 
  //     error.getMessage());
  //     error.printStackTrace();
  //     return null;
  //   }
	// }
  
}
