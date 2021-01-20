package com.wat.tabularasa20;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.wat.tabularasa20.utilities.Preferences;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Set;

import static com.wat.tabularasa20.utilities.Preferences.*;

public class PreferencesTest {

    @Test
    public void saveCredentialsTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        LoginCredentials loginCredentials = Mockito.mock(LoginCredentials.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.saveCredentials(a, loginCredentials);
    }

    @Test
    public void readCredentialTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.readCredential(a);
    }

    @Test
    public void saveClientIDStringTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.saveClientID(a, "1");
    }

    @Test
    public void saveClientIDIntTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.saveClientID(a, 1);
    }

    @Test
    public void readClientIDTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.readClientID(a);
    }

    @Test
    public void saveAccountIDStringTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.saveAccountID(a, "1");
    }

    @Test
    public void saveAccountIDIntTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.saveAccountID(a, 1);
    }

    @Test
    public void readAccountIDTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.readAccountID(a);
    }

    @Test
    public void saveHelpStateTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.saveHelpState(a);
    }

    @Test
    public void readHelpStateTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.readHelpState(a);
    }

    @Test
    public void saveThemeTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.saveTheme(a, true);
    }

    @Test
    public void readThemeTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.readTheme(a);
    }


    @Test
    public void readChatInfoTest() {
        Preferences preferences = Mockito.mock(Preferences.class, Mockito.RETURNS_DEEP_STUBS);
        Activity a = Mockito.mock(Activity.class, Mockito.RETURNS_DEEP_STUBS);
        preferences.readChatInfo(a);
    }







}