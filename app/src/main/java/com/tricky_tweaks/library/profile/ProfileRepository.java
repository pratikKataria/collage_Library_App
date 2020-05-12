package com.tricky_tweaks.library.profile;

import androidx.lifecycle.MutableLiveData;
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

    MutableLiveData<Boolean> firebaseUpdateEnrollmentNumber(String enrollmentNumber) {
        loading(true);
        MutableLiveData<Boolean> updatedSuccessfully = new MutableLiveData<>();
        DocumentReference studentDocumentReference = studentColReference.document(firebaseAuth.getUid() + "");
        Map<String, String> updateEnrollmentNumber = new HashMap<>();
        updateEnrollmentNumber.put("enrollmentNo", enrollmentNumber);
        studentDocumentReference.set(updateEnrollmentNumber, SetOptions.merge())
                .addOnSuccessListener(result -> {
                    loading(false);
                    updatedSuccessfully.setValue(true);
                }).addOnFailureListener(resultFailed -> {
                    loading(false);
                    error(resultFailed.getMessage());
                    updatedSuccessfully.setValue(false);
        });
        return updatedSuccessfully;
    }

    @Override
    public void loading(boolean isLoading) {

    }

    @Override
    public void error(String message) {

    }
}
