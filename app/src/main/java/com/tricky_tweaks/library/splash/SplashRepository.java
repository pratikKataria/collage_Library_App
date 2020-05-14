package com.tricky_tweaks.library.splash;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tricky_tweaks.library.model.Student;
import com.tricky_tweaks.library.utils.LogMessage;

import static com.tricky_tweaks.library.utils.Constants.IConstants.STUDENTS;

public class SplashRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private Student student = new Student();
    private FirebaseFirestore studentReference = FirebaseFirestore.getInstance();
    private CollectionReference studentCollectionReference = studentReference.collection(STUDENTS);

    interface FirebaseAsyncValueListener {
        void data(Object value);
    }

    MutableLiveData<Student> checkIfUserIsAuthenticatedInFirebase() {
        MutableLiveData<Student> isStudentAuthenticatedInFirebaseMutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            student.isAuthenticated = false;
            isStudentAuthenticatedInFirebaseMutableLiveData.setValue(student);
        } else {
            student.setUid(firebaseUser.getUid());
            student.isAuthenticated = true;
            isStudentAuthenticatedInFirebaseMutableLiveData.setValue(student);
        }
        return isStudentAuthenticatedInFirebaseMutableLiveData;
    }

    void checkEnrollmentNumberInFirebaseStore(String uid, FirebaseAsyncValueListener firebaseAsyncValueListener) {
        studentCollectionReference.document(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    LogMessage.eMess("task success full ");
                    String enrollmentNumber = (String) documentSnapshot.get("enrollmentNo");
                    if (enrollmentNumber != null && !enrollmentNumber.isEmpty()) {
                        firebaseAsyncValueListener.data(true);
                    } else {
                        firebaseAsyncValueListener.data(false);
                    }
                }
            }
        });
    }

}
