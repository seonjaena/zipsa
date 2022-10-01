package com.project.zipsa.repository;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.project.zipsa.dto.test;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {

    public void test(String id, String title, String body, String token) {
        Firestore db = FirestoreClient.getFirestore();
        test t = new test(id, title, body, token);
        db.collection("zipsa-alarm").document("test").set(t);
    }


}
