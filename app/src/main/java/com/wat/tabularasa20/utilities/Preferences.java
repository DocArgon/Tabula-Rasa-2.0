package com.wat.tabularasa20.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;

public class Preferences {

    public static class LoginCredentials {
        public String login, password;
        public LoginCredentials(String login, String password) {
            this.login = login;
            this.password = password;
        }
        public LoginCredentials(Editable login, Editable password) {
            this(login.toString(), password.toString());
        }
    }

    public static void saveCredentials (Context context, LoginCredentials credentials) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", credentials.login);
        editor.putString("pass", credentials.password);
        editor.apply();
    }

    public static LoginCredentials readCredential (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        String login = sharedPreferences.getString("login", "");
        String pass = sharedPreferences.getString("pass", "");
        if (login.isEmpty() || pass.isEmpty())
            return null;
        return new LoginCredentials(login, pass);
    }

    public static void saveUID (Context context, String uid) {
        saveUID(context, Integer.parseInt(uid));
    }

    public static void saveUID (Context context, int uid) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("uid", uid);
        editor.apply();
    }

    public static int readUID (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserID", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("uid", -1);
    }
}
