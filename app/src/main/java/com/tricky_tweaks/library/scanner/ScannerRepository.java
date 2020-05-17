package com.tricky_tweaks.library.scanner;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.tricky_tweaks.library.interfaces.FirebaseState;
import com.tricky_tweaks.library.interfaces.ListHeader;
import com.tricky_tweaks.library.model.LibraryEntryModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.tricky_tweaks.library.utils.Constants.IConstants.LIBRARY_ENTRY;
import static com.tricky_tweaks.library.utils.Constants.IConstants.STUDENTS_ONLINE;
import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.FAILED;
import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.LOADING;
import static com.tricky_tweaks.library.utils.Constants.IFirebaseState.SUCCESS;

public class ScannerRepository implements FirebaseState {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference libraryEntriesCollectionReference = firebaseFirestore.collection(LIBRARY_ENTRY);
    private CollectionReference studentOnlineInLibraryCollectionReference = firebaseFirestore.collection(STUDENTS_ONLINE);

    public MutableLiveData<ArrayList<ListHeader>> getScannedListFromFirebaseFake() {
        MutableLiveData<ArrayList<ListHeader>> listOfStudent = new MutableLiveData<>();

        ArrayList<ListHeader> list = new ArrayList<>();
        LibraryEntryModel student = new LibraryEntryModel("Pratik\nKatariya", "0832CS171121 ", "10:20\nAM", "24-2-2020", new Date());
        list.add(student);
        student = new LibraryEntryModel("Pratik\nKatariya", "0832CS171121 ", "10:20\nAM", "24-2-2020", new Date());
        list.add(student);
        student = new LibraryEntryModel("Pratik\nKatariya", "0832CS171121 ", "10:20\nAM", "22-2-2020", new Date());
        list.add(student);
        student = new LibraryEntryModel("Pratik\nKatariya", "0832CS171121 ", "10:20\nAM", "21-3-2020", new Date());
        list.add(student);
        student = new LibraryEntryModel("Pratik\nKatariya", "0832CS171121 ", "10:20\nAM", "1-4-2020" , new Date());
        list.add(student);
        student = new LibraryEntryModel("Pratik\nKatariya", "0832CS171121 ", "10:20\nAM", "5-4-2020" , new Date());
        list.add(student);
        student = new LibraryEntryModel("Pratik\nKatariya", "0832CS171121 ", "10:20\nAM", "7-5-2020" , new Date());
        list.add(student);
        student = new LibraryEntryModel("Pratik\nKatariya", "0832CS171121 ", "10:20\nAM", "6-5-2020" , new Date());
        list.add(student);
        student = new LibraryEntryModel("Pratik\nKatariya", "0832CS171121 ", "10:20\nAM", "8-5-2020" , new Date());
        list.add(student);
        student = new LibraryEntryModel("Pratik\nKatariya", "0832CS171121 ", "10:20\nAM", "9-6-2020" , new Date());
        list.add(student);
        student = new LibraryEntryModel("Pratik\nKatariya", "0832CS171121 ", "10:20\nAM", "2-10-2020", new Date());
        list.add(student);
        student = new LibraryEntryModel("Pratik\nKatariya", "0832CS171121 ", "10:20\nAM", "2-12-2020", new Date());
        list.add(student);

        listOfStudent.setValue(list);
        return listOfStudent;
    }

    void updateScannedValueWhenEnterInFirestoreDatabase(LibraryEntryModel libraryEntryModel) {

        state(LOADING);

        WriteBatch writeBatch = firebaseFirestore.batch();

        DocumentReference libraryEntriesDocumentReference = libraryEntriesCollectionReference.document(FirebaseAuth.getInstance().getUid() + "");
        String libraryEntriesDocumentReferenceKeyString = getSaltString();
        Map<String, Object> keyNode = new HashMap<>();
        Map<String, Object> nestedLibraryEntries = new HashMap<>();
        nestedLibraryEntries.put(libraryEntriesDocumentReferenceKeyString, nestedLibraryEntries);
        keyNode.put(libraryEntriesDocumentReferenceKeyString, libraryEntryModel);

        writeBatch.update(libraryEntriesDocumentReference, keyNode);

        DocumentReference onlineStudentDocument = studentOnlineInLibraryCollectionReference.document(FirebaseAuth.getInstance().getUid() + "");
        Map<String, String> onlineStudentDataModel = new HashMap<>();
        onlineStudentDataModel.put("enterTime", libraryEntryModel.getEnterTime());
        onlineStudentDataModel.put("uid", FirebaseAuth.getInstance().getUid());
        onlineStudentDataModel.put("collectionRefID", libraryEntriesDocumentReferenceKeyString);

        writeBatch.set(onlineStudentDocument, onlineStudentDataModel);

        writeBatch.commit().addOnSuccessListener(success -> {
            state(SUCCESS);
        }).addOnFailureListener(failed -> {
            state(FAILED);
        });
    }

    void updateScannedValueWhenExistInFirestoreDatabase(String exitTimeString) {
        state(LOADING);
        DocumentReference fetchValueFromOnlineStudentDocumentReference = studentOnlineInLibraryCollectionReference.document(FirebaseAuth.getInstance().getUid() + "");
        fetchValueFromOnlineStudentDocumentReference.get().addOnCompleteListener(snapshotTask -> {
            if (snapshotTask.isSuccessful()) {
                DocumentSnapshot onlineStudentSnapshot = snapshotTask.getResult();
                if (onlineStudentSnapshot != null && onlineStudentSnapshot.exists() && onlineStudentSnapshot.contains("collectionRefID")) {
                    String libraryEntriesCollectionID = (String) onlineStudentSnapshot.get("collectionRefID");

                    DocumentReference reference = libraryEntriesCollectionReference.document(FirebaseAuth.getInstance().getUid() + "");
                    Map<String, Object> rootNode = new HashMap<>();
                    Map<String, String> existTimeDataModel = new HashMap<>();
                    existTimeDataModel.put("exitTime", exitTimeString);
                    rootNode.put(libraryEntriesCollectionID, existTimeDataModel);
                    reference.set(rootNode, SetOptions.merge()).addOnSuccessListener(task -> {
                        state(SUCCESS);
                    }
                    ).addOnFailureListener(failed -> {
                        state(FAILED);
                    });
                }
            }
        }).addOnFailureListener(taskFailed -> {

        });
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    @Override
    public void state(int iFirebaseState) {

    }
}
