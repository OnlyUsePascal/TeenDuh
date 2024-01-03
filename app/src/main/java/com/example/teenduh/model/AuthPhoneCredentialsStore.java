package com.example.teenduh.model;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.Serializable;

public class AuthPhoneCredentialsStore implements Serializable {
    private String phoneNumber;
    private String verificationID;
    private PhoneAuthProvider.ForceResendingToken resendingToken;

    public AuthPhoneCredentialsStore() {}

    public AuthPhoneCredentialsStore(String verificationID, PhoneAuthProvider.ForceResendingToken resendingToken, String phoneNumber) {
        this.verificationID = verificationID;
        this.resendingToken = resendingToken;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getVerificationID() {
        return verificationID;
    }

    public PhoneAuthProvider.ForceResendingToken getResendingToken() {
        return resendingToken;
    }
}
