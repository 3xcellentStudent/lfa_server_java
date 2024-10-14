package com.server.services.firebase;

import java.util.List;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.server.heplers.FirebaseHelper;

// @Service
public class FirebaseService {
  private Firestore firestore = FirestoreClient.getFirestore();

  public ResponseEntity<JSONObject> readDocument(String collName, String docName){
    DocumentReference document = firestore.collection(collName).document(docName);
    ApiFuture<DocumentSnapshot> data = document.get();
    try {
      DocumentSnapshot snapshot = data.get();
      if(snapshot.exists()) return FirebaseHelper.resEntityJSON(200, snapshot.getData());
      else return FirebaseHelper.resEntityJSON(204, "Document not found!");
    } catch(Exception err){return FirebaseHelper.resEntityJSON(500, err);}
  }

  public ResponseEntity<JSONObject> readCollection(String collName){
    CollectionReference collection = firestore.collection(collName);
    ApiFuture<QuerySnapshot> snapshot = collection.get();
    try {
      List<QueryDocumentSnapshot> documents = snapshot.get().getDocuments();
      return FirebaseHelper.resEntityJSON(201, documents);
    } catch(Exception err){return FirebaseHelper.resEntityJSON(500, err);}
  }

  public int postDocument(String collName, Object data){
    CollectionReference collection = firestore.collection(collName);
    ApiFuture<DocumentReference> document = collection.add(data);
    try {
      if(document.isDone()) return 200;
      else return 400;
    } catch(Exception err){return 500;}
  }
}

// try {
//   if(document.isDone()) return FirebaseHelper.convertToJson(200, "Document added to collection");
//   else return FirebaseHelper.convertToJson(500, "Document creation failed");
// } catch (Exception err) {
//   return FirebaseHelper.convertToJson(500, err);
// }