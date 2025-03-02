package com.server.databases.mongodb.controllers.media.diffusers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.databases.mongodb.models.media.diffusers.DiffusersMediaModel;
import com.server.databases.mongodb.services.MainService;
import com.server.databases.mongodb.services.media.diffusers.DiffusersMediaService;

@RestController
@RequestMapping("/api/mongodb/media/diffusers")
@CrossOrigin("*")
public class DiffusersMediaController {

  @Autowired
  private DiffusersMediaService mediaService;
  @Autowired
  private MainService mainService;
  @Autowired
  private MongoTemplate mongoTemplate;

  @PostMapping("/create")
  public ResponseEntity<Object> create(@RequestBody String requestBodyString){
    try {
      ResponseEntity<Object> response = mediaService.createOne(requestBodyString);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't add new product to database !");
    }
  }

  @PatchMapping("/update")
  public ResponseEntity<Object> updateOneById(@RequestBody String requestBodyString){
    try {
      ResponseEntity<Object> response = mainService.updateNewOneById(requestBodyString, DiffusersMediaModel.class);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Unable to update this product at database !");
    }
  }

  @PutMapping("/push")
  public ResponseEntity<Object> pushNewOneToArrayById(@RequestBody String requestBodyString){
    try {
      ResponseEntity<Object> response = mainService.pushNewOneToArrayById(requestBodyString, DiffusersMediaModel.class);

      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Unable to update this product at database !");
    }
  }

  @GetMapping("/get")
  public ResponseEntity<Object> findAllById(@RequestParam(name = "id", required = false) List<String> id){
    try {
      if(id == null || id.isEmpty()){
        ResponseEntity<Object> response = mainService.findAll(DiffusersMediaModel.class);

        return response;
      } else {
        List<DiffusersMediaModel> foundMedia = mainService.findAllById("id", id, DiffusersMediaModel.class);

        ResponseEntity<Object> response = mainService.getAsResponseEntity(foundMedia);
  
        return response;
      }
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Internal server error: " + error.getMessage());
    }
  }

  @GetMapping("/delete")
  public ResponseEntity<Object> deleteAllById(@RequestParam(name = "id", required = true) List<String> id){
    try {
      ResponseEntity<Object> response =  mainService.deleteAllById(id, DiffusersMediaModel.class);
      
      return response;
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't delete product by ID from database !");
    }
  }

  @GetMapping("/clear-col")
  public ResponseEntity<String> clearCollection(){
    try {
      mongoTemplate.remove(new Query(), DiffusersMediaModel.class);

      return ResponseEntity.ok("All products have been removed from database !");
    } catch(Exception error){
      error.printStackTrace();
      System.out.println(error.getMessage());
      return ResponseEntity.internalServerError().body("Can't delete products from database !");
    }
  }
  
}


// {
//   "titleContent": {
//     "productLogo": "https://example.com",
//     "descriptionVideo": "//vitruvi.ca/cdn/shop/files/preview_images/f7e3669e31214c2784b8397c480a6987.thumbnail.0000000000_1500x.jpg?v=1719510183"
//   },
//   "images": [
//     [
//       [
//         {"media": "(max-width: 480px)", "src": "https://static.wixstatic.com/media/24fdcf_5ffb83f114b240a881eb09ae019bbfaf~mv2.png/v1/fill/w_336,h_420,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/24fdcf_5ffb83f114b240a881eb09ae019bbfaf~mv2.png"},
//         {"media": "(max-width: 768px)", "src": "https://www.simplelighting.co.uk/media/webp_image/catalog/product/cache/716cb71e4a7f1a549f7cc6339f6037ad/t/b/tbar-led-lifestyle-compressed.effectsresult-scene-4.webp"},
//         {"media": "", "src": "https://vitruvi.ca/cdn/shop/files/pdp_stone-diffuser_front_white_gallery_1_v9_image_36a28caa-4ba6-48d9-aa51-39a0d0867af5.png?v=1719457697&width=713"}
//       ],
//       [
//         {"media": "(max-width: 480px)", "src": "https://static.wixstatic.com/media/24fdcf_5ffb83f114b240a881eb09ae019bbfaf~mv2.png/v1/fill/w_336,h_420,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/24fdcf_5ffb83f114b240a881eb09ae019bbfaf~mv2.png"},
//         {"media": "(max-width: 768px)", "src": "https://www.simplelighting.co.uk/media/webp_image/catalog/product/cache/716cb71e4a7f1a549f7cc6339f6037ad/t/b/tbar-led-lifestyle-compressed.effectsresult-scene-4.webp"},
//         {"media": "", "src": "https://vitruvi.ca/cdn/shop/files/pdp_stone-diffuser_front_charcoal_gallery_1_v9_image_a067dcd6-ad3c-4cdc-ae86-2284973f5822.png?v=1719457697&width=713"}
//       ],
//       [
//         {"media": "(max-width: 480px)", "src": "https://static.wixstatic.com/media/24fdcf_5ffb83f114b240a881eb09ae019bbfaf~mv2.png/v1/fill/w_336,h_420,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/24fdcf_5ffb83f114b240a881eb09ae019bbfaf~mv2.png"},
//         {"media": "(max-width: 768px)", "src": "https://www.simplelighting.co.uk/media/webp_image/catalog/product/cache/716cb71e4a7f1a549f7cc6339f6037ad/t/b/tbar-led-lifestyle-compressed.effectsresult-scene-4.webp"},
//         {"media": "", "src": "https://vitruvi.ca/cdn/shop/files/pdp_stone-diffuser_front_terracotta_gallery_1_v9_image_9e9dcb8f-d542-46e6-85e9-2c27e595547d.png?v=1719457697&width=713"}
//       ],
//       [
//         {"media": "(max-width: 480px)", "src": "https://static.wixstatic.com/media/24fdcf_5ffb83f114b240a881eb09ae019bbfaf~mv2.png/v1/fill/w_336,h_420,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/24fdcf_5ffb83f114b240a881eb09ae019bbfaf~mv2.png"},
//         {"media": "(max-width: 768px)", "src": "https://www.simplelighting.co.uk/media/webp_image/catalog/product/cache/716cb71e4a7f1a549f7cc6339f6037ad/t/b/tbar-led-lifestyle-compressed.effectsresult-scene-4.webp"},
//         {"media": "", "src": "https://vitruvi.ca/cdn/shop/files/pdp_stone-diffuser_front_charcoal_gallery_1_v9_image_a067dcd6-ad3c-4cdc-ae86-2284973f5822.png?v=1719457697&width=713"}
//       ],
//       [
//         {"media": "(max-width: 480px)", "src": "https://static.wixstatic.com/media/24fdcf_5ffb83f114b240a881eb09ae019bbfaf~mv2.png/v1/fill/w_336,h_420,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/24fdcf_5ffb83f114b240a881eb09ae019bbfaf~mv2.png"},
//         {"media": "(max-width: 768px)", "src": "https://www.simplelighting.co.uk/media/webp_image/catalog/product/cache/716cb71e4a7f1a549f7cc6339f6037ad/t/b/tbar-led-lifestyle-compressed.effectsresult-scene-4.webp"},
//         {"media": "", "src": "https://vitruvi.ca/cdn/shop/files/pdp_stone-diffuser_front_sea_gallery_1_v9_image_843aee0e-353f-441c-b6f8-26c956b28d57.png?v=1719457697&width=713"}
//       ],
//       [
//         {"media": "(max-width: 480px)", "src": "https://static.wixstatic.com/media/24fdcf_5ffb83f114b240a881eb09ae019bbfaf~mv2.png/v1/fill/w_336,h_420,al_c,q_85,usm_0.66_1.00_0.01,enc_auto/24fdcf_5ffb83f114b240a881eb09ae019bbfaf~mv2.png"},
//         {"media": "(max-width: 768px)", "src": "https://www.simplelighting.co.uk/media/webp_image/catalog/product/cache/716cb71e4a7f1a549f7cc6339f6037ad/t/b/tbar-led-lifestyle-compressed.effectsresult-scene-4.webp"},
//         {"media": "", "src": "https://vitruvi.ca/cdn/shop/files/pdp_stone-diffuser_front_Suede_gallery_1_v9_image_80ffad4d-2678-49ce-962e-17615e85b620.png?v=1719457697&width=713"}
//       ]
//     ]
//   ],
//   "videos": [
    
//   ]
// }
