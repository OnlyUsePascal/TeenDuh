package com.example.teenduh.stores.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;

public class BaseAuthClientStore {
    private static FirebaseAuth firebaseAuth;
    private static PhoneAuthCredential credential;

    BaseAuthClientStore() {}

    public static void setCredential(PhoneAuthCredential credential) {
        BaseAuthClientStore.credential = credential;
    }

    public static PhoneAuthCredential getCredential() {
        return credential;
    }

    public static FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

}
