package com.tricky_tweaks.library.auth;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tricky_tweaks.library.interfaces.FirebaseState;
import com.tricky_tweaks.library.model.Student;
import com.tricky_tweaks.library.utils.Constants;
import com.tricky_tweaks.library.utils.LogMessage;

import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.FAILED;
import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.LOADING;
import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.SUCCESS;

class AuthRepository implements FirebaseState {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore ref = FirebaseFirestore.getInstance();
    private CollectionReference studentRef = ref.collection(Constants.IConstants.STUDENTS);

    MutableLiveData<Student> firebaseSignupWithEmail(String emailCredentials, String passwordCredentials) {
        MutableLiveData<Student> authenticatedStudentMutableLiveData = new MutableLiveData<>();
        state(LOADING);
        firebaseAuth.createUserWithEmailAndPassword(emailCredentials, passwordCredentials)
                .addOnSuccessListener(authResult -> {
                    boolean isNewUser = authResult.getAdditionalUserInfo().isNewUser();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        String uid = firebaseUser.getUid();
                        String name = firebaseUser.getDisplayName();
                        String email = firebaseUser.getEmail();
                        Student student = new Student(name, email, uid);
                        student.isNew = isNewUser;
                        authenticatedStudentMutableLiveData.setValue(student);
                        state(SUCCESS);
                    }
                })
                .addOnFailureListener(e -> {
                    state(FAILED);
                    LogMessage.eMess(e.getMessage());
                });
        return authenticatedStudentMutableLiveData;
    }

    MutableLiveData<Student> firebaseSigninWithEmail(String emailCredentials, String passwordCredentials) {
        MutableLiveData<Student> authenticatedStudentMutableLiveData = new MutableLiveData<>();
        state(LOADING);
        firebaseAuth.signInWithEmailAndPassword(emailCredentials, passwordCredentials)
                .addOnSuccessListener(authResult -> {
                    boolean isNewUser = authResult.getAdditionalUserInfo().isNewUser();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        String uid = firebaseUser.getUid();
                        String name = firebaseUser.getDisplayName();
                        String email = firebaseUser.getEmail();
                        Student student = new Student(name, email, uid);
                        student.isNew = isNewUser;
                        student.isAuthenticated = true;
                        authenticatedStudentMutableLiveData.setValue(student);
                        state(SUCCESS);
                    }
                }).addOnFailureListener(e -> state(FAILED));
        return authenticatedStudentMutableLiveData;
    }

    MutableLiveData<Student> createStudentInFirestoreIfNotExist(Student authenticatedStudent) {
        MutableLiveData<Student> newStudentMutableLiveData = new MutableLiveData<>();
        DocumentReference uidRef = studentRef.document(authenticatedStudent.getUid());
        uidRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot snapshot = task.getResult();
                if (!snapshot.exists()) {
                    uidRef.set(authenticatedStudent).addOnSuccessListener(taskSuccess -> {
                        authenticatedStudent.isCreated = true;
                        newStudentMutableLiveData.setValue(authenticatedStudent);
                    }).addOnFailureListener(taskFailed -> {
                        LogMessage.eMess(taskFailed.getMessage());
                    });
                }
            }
        });
        return newStudentMutableLiveData;
    }

    @Override
    public void state(int iFirebaseState) {

    }
}
