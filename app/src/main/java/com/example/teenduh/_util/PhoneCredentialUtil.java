package com.example.teenduh._util;

import com.google.firebase.auth.PhoneAuthProvider;

import java.io.Serializable;

public class PhoneCredentialUtil implements Serializable {
    private String phoneNumber;
    private String verificationID;
    private PhoneAuthProvider.ForceResendingToken resendingToken;

    public PhoneCredentialUtil() {}

    public PhoneCredentialUtil(String verificationID, PhoneAuthProvider.ForceResendingToken resendingToken, String phoneNumber) {
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
