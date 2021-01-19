package com.wat.tabularasa20.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import androidx.annotation.NonNull;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

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
        public LoginCredentials(@NonNull Editable login, @NonNull Editable password) {
            this(login.toString(), password.toString());
        }
    }

    /**
     * Struktura przechowująca dane czatu
     */
    public static class ChatInfo implements Comparable {
        public int withID, aboutID, msgCount;
        public ChatInfo(int withID, int aboutID, int msgCount) {
            this.withID = withID;
            this.aboutID = aboutID;
            this.msgCount = msgCount;
        }
        public ChatInfo(String withID, String aboutID, String msgCount) {
            this(Integer.parseInt(withID), Integer.parseInt(aboutID), Integer.parseInt(msgCount));
        }
        public ChatInfo(@NonNull String row) {
            this(row.split("-")[0], row.split("-")[1], row.split("-")[2]);
        }
        @NonNull
        @Override
        public String toString() {
            return "" + withID + "-" + aboutID + "-" + msgCount;
        }
        @Override
        public int compareTo(@NonNull Object o) {
            return toString().compareTo(o.toString());
        }
    }

    /**
     * Motoda zapisująca dane logowania
     * @param credentials obiekt klasy <code>LoginCredentials</code>
     * @see LoginCredentials
     */
    public static void saveCredentials (@NonNull Context context, @NonNull LoginCredentials credentials) {
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
    public static LoginCredentials readCredential (@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        String login = sharedPreferences.getString("login", "");
        String pass = sharedPreferences.getString("pass", "");
        if (login == null || pass == null || login.isEmpty() || pass.isEmpty())
            return null;
        return new LoginCredentials(login, pass);
    }

    /**
     * Metoda zapisująca identyfikator klienta
     * @param uid identyfikator użytkownika jako <code>String</code>
     */
    public static void saveClientID (Context context, String uid) {
        saveClientID(context, Integer.parseInt(uid));
    }

    /**
     * Metoda zapisująca identyfikator klienta
     * @param uid identyfikator użytkownika jako <code>int</code>
     */
    public static void saveClientID (@NonNull Context context, int uid) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("client_id", uid);
        editor.apply();
    }

    /**
     * Metoda odczytująca zachowany identyfikator klienta
     * @return identyfikator użytkownika
     */
    public static int readClientID (@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserID", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("client_id", -1);
    }

    /**
     * Metoda zapisująca identyfikator konta
     * @param uid identyfikator użytkownika jako <code>String</code>
     */
    public static void saveAccountID (Context context, String uid) {
        saveAccountID(context, Integer.parseInt(uid));
    }

    /**
     * Metoda zapisująca identyfikator konta
     * @param uid identyfikator użytkownika jako <code>int</code>
     */
    public static void saveAccountID (@NonNull Context context, int uid) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("account_id", uid);
        editor.apply();
    }

    /**
     * Metoda odczytująca zachowany identyfikator konta
     * @return identyfikator użytkownika
     */
    public static int readAccountID (@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserID", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("account_id", -1);
    }

    public static void saveChatInfo (@NonNull Context context, @NonNull Set<ChatInfo> infos) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ChatData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        TreeSet<String> ts = new TreeSet<>();
        for (ChatInfo ci : infos)
            ts.add(ci.toString());
        editor.putStringSet("chats", ts);
        editor.apply();
    }

    @NonNull
    public static Set<ChatInfo> readChatInfo(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ChatData", Context.MODE_PRIVATE);
        TreeSet<ChatInfo> ts = new TreeSet<>();
        for (String s : Objects.requireNonNull(sharedPreferences.getStringSet("chats", null)))
            ts.add(new ChatInfo(s));
        return ts;
    }
}
