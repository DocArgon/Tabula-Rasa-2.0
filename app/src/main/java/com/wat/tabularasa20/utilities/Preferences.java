package com.wat.tabularasa20.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;

/**
 * Klasa do zarządzania ustawieniami użytkownika
 */
public class Preferences {

    /**
     * Struktura przechowująca dane logowania
     */
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

    /**
     * Motoda zapisująca dane logowania
     * @param credentials obiekt klasy <code>LoginCredentials</code>
     * @see LoginCredentials
     */
    public static void saveCredentials (Context context, LoginCredentials credentials) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", credentials.login);
        editor.putString("pass", credentials.password);
        editor.apply();
    }

    /**
     * Metoda odczytująca zachowane dane logowania
     * @return obiekt klasy <code>LoginCredentials</code>
     * @see LoginCredentials
     */
    public static LoginCredentials readCredential (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        String login = sharedPreferences.getString("login", "");
        String pass = sharedPreferences.getString("pass", "");
        if (login.isEmpty() || pass.isEmpty())
            return null;
        return new LoginCredentials(login, pass);
    }

    /**
     * Metoda zapisująca identyfikator użytkownika
     * @param uid identyfikator użytkownika jako <code>String</code>
     */
    public static void saveUID (Context context, String uid) {
        saveUID(context, Integer.parseInt(uid));
    }

    /**
     * Metoda zapisująca identyfikator użytkownika
     * @param uid identyfikator użytkownika jako <code>int</code>
     */
    public static void saveUID (Context context, int uid) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("uid", uid);
        editor.apply();
    }

    /**
     * Metoda odczytująca zachowany identyfikator użytkownika
     * @return identyfikator użytkownika
     */
    public static int readUID (Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserID", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("uid", -1);
    }
}
