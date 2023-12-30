package com.example.teenduh.stores.auth;

import com.google.firebase.auth.FirebaseUser;

public class UserStore {
    private static FirebaseUser user;

    UserStore() {}

    public static void setUser(FirebaseUser user) {
        UserStore.user = user;
    }

    public static FirebaseUser getUser() {
        return user;
    }
}
