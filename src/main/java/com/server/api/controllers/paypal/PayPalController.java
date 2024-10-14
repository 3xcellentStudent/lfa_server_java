package com.server.api.controllers.paypal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.api.helpers.paypal.CreateResponse;
import com.server.api.helpers.paypal.PayPalRoutes;
import com.server.api.services.paypal.PayPalCaptureOrder;
import com.server.api.services.paypal.PayPalCreateOrder;
import com.services.pdf.PDFService;
import com.services.pdf.helpers.json.CaptureResponseObject;
import com.services.redis.service.RedisService;

import org.springframework.http.ResponseEntity;

import org.json.JSONObject;

@RestController
@RequestMapping("/paypal")
@CrossOrigin(origins = "*")
public class PayPalController {
  public static final String HASH_KEY = "PayPalToken";
  private RedisService redis = new RedisService();

  @PostMapping(value = PayPalRoutes.ENDPOINT_CREATE_ORDER)
  public ResponseEntity<String> createOrder(@RequestBody String body){
    try {
      JSONObject jsonData = redis.getToken(HASH_KEY);
      if(jsonData.getInt("status") == 200){
        String accessToken = jsonData.getString("data");
        int status = jsonData.getInt("status");

        if(status >= 400){return CreateResponse.toEntity(404, accessToken);} 
        else {return PayPalCreateOrder.createOrder(accessToken);}
      } else {
        String message = "Got error while requesting token in Redis !";
        return CreateResponse.toEntity(500, message);
      }
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return CreateResponse.toEntity(500, "Server error !");
    }
  }

  @PostMapping(value = PayPalRoutes.ENDPOINT_CAPTURE_ORDER)
  public ResponseEntity<String> captureOrder(@RequestParam String orderId){
    String sysMessage = String.format("Capturing order with orderId: %s...", orderId);
    System.out.println(sysMessage);
    try {
      JSONObject jsonRedisData = redis.getToken(HASH_KEY);
      if(jsonRedisData.getInt("status") == 200){
        String accessToken = jsonRedisData.getString("data");
        int status = jsonRedisData.getInt("status");
        if(status >= 400){return CreateResponse.toEntity(404, jsonRedisData);}
        else {
          String orderDetails = PayPalCaptureOrder.getOrderDetails(orderId, accessToken);
          String orderStatus = new JSONObject(orderDetails).getString("status");
          if(orderStatus.contains("APPROVED")){
            JSONObject response = PayPalCaptureOrder.captureOrder(orderId, accessToken);
            System.out.println("Captured response status: " + response.getString("status"));
            return PayPalCaptureOrder.isCapturedOrder(response);
          } else {
            return CreateResponse.toEntity(404, "You are canceled this order !");
          }
        }
      } else {
        System.out.println("Server error. Something went wrong at \"PayPalController.java\" !");
        return CreateResponse.toEntity(404, "Can't find token at database !");
      }
    } catch(Exception error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return CreateResponse.toEntity(500, "Server error !");
    }
  }

  @PostMapping("/create-pdf")
  public void createPdf(@RequestBody String body){
    System.out.println(body);
    
    JSONObject jsonBody = new JSONObject(body);
    // String message = jsonBody.getString("data");
    
    // new PDFService().create(message);
    new PDFService().create(new CaptureResponseObject(jsonBody));
  }




  // @GetMapping()
  // public ResponseEntity<String> detailsOrder(){
  //   try {
  //     JSONObject jsonData = redis.getToken(HASH_KEY);
  //     if(jsonData.getInt("status") == 200){
  //       String accessToken = jsonData.getString("data");
  //       int status = jsonData.getInt("status");
  //       if(status >= 400){return ResponseEntity.status(404).body(accessToken);}
  //       else {
  //         return PayPalCaptureOrder.captureOrder(orderId, accessToken);
  //       }
  //     } else {
  //       System.out.println("Server error. Something went wrong at \"PayPalController.java\" !");
  //       return ResponseEntity.status(500).body(jsonData.toString());
  //     }
  //   } catch(Exception error){
  //     System.err.println(error.getMessage());
  //     error.printStackTrace();
  //     return ResponseEntity.status(500).body(error.getMessage());
  //   }
  // }

  // @GetMapping(value = PayPalRoutes.ENDPOINT_PAYMENT_CANCEL)
  // public void cancelOrder(@RequestParam String id){
  //   // final String cancelURLString = PayPalRoutes.PAYPAL_CAPTURE_ORDER + String.format("/%s/capture", id);
  //   System.out.println("Cancel route is working !!!!");
  // }

  // @PostMapping(value = PayPalRoutes.ENDPOINT_CAPTURE_ORDER)
  // public ResponseEntity<String> captureOrder(@RequestBody String body) {
  //     File filePath = new File("C:\\Users\\alina\\Desktop\\AndriiJob");
  //     try {
  //         File file = File.createTempFile("file-", ".txt", filePath);
  //         String pathToFile = file.getAbsolutePath();
  //         System.out.println("Created file with name: " + file.getName() + ", and path: " + pathToFile);
  //         return ResponseEntity.ok("File \"" + file.getName() + "\" was created !");
  //     } catch (IOException error) {
  //         System.err.println(error);
  //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong while creating file !");
  //     }
  // }
}
