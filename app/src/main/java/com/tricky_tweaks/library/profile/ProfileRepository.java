package com.tricky_tweaks.library.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.tricky_tweaks.library.utils.FirebaseState;

import java.util.HashMap;
import java.util.Map;

import static com.tricky_tweaks.library.utils.Constants.IConstants.STUDENTS;

public class ProfileRepository implements FirebaseState {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestoreReference = FirebaseFirestore.getInstance();
    private CollectionReference studentColReference = firebaseFirestoreReference.collection(STUDENTS);

    void firebaseUpdateEnrollmentNumber(String enrollmentNumber) {
        loading(true);
        DocumentReference studentDocumentReference = studentColReference.document(firebaseAuth.getUid() + "");
        Map<String, String> updateEnrollmentNumber = new HashMap<>();
        updateEnrollmentNumber.put("enrollmentNo", enrollmentNumber);
        studentDocumentReference.set(updateEnrollmentNumber, SetOptions.merge())
                .addOnSuccessListener(result -> {
                    loading(false);
                    success(true);
                }).addOnFailureListener(resultFailed -> {
                    loading(false);
                    success(false);
                    error(resultFailed.getMessage());
        });
    }

    @Override
    public void loading(boolean isLoading) {

    }

    @Override
    public void error(String message) {

    }

    @Override
    public void success(boolean isSuccessful) {

    }
}
