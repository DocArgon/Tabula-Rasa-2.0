package com.wat.tabularasa20;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.wat.tabularasa20.utilities.Network;
import com.wat.tabularasa20.utilities.Preferences;

import org.junit.Test;
import org.mockito.Mockito;

import static com.wat.tabularasa20.utilities.Preferences.*;


public class PreferencesTest {

    @Test
    public void saveCredentialsTest() {
        Preferences preferences = Mockito.mock(Preferences.class);
        Activity a = Mockito.mock(Activity.class);
        LoginCredentials loginCredentials = Mockito.mock(LoginCredentials.class);
//        SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        preferences.saveCredentials(a, loginCredentials);
    }

    @Test
    public void readCredentialTest() {
        Preferences preferences = Mockito.mock(Preferences.class);
        Activity a = Mockito.mock(Activity.class);
        preferences.readCredential(a);
    }

    @Test
    public void saveClientIDStringTest() {
        Preferences preferences = Mockito.mock(Preferences.class);
        Activity a = Mockito.mock(Activity.class);
        preferences.saveClientID(a, "1");
    }

    @Test
    public void saveClientIDIntTest() {
        Preferences preferences = Mockito.mock(Preferences.class);
        Activity a = Mockito.mock(Activity.class);
        preferences.saveClientID(a, 1);
    }

    @Test
    public void saveAccountIDStringTest() {
        Preferences preferences = Mockito.mock(Preferences.class);
        Activity a = Mockito.mock(Activity.class);
        preferences.saveClientID(a, "1");
    }

    @Test
    public void saveAccountIDIntTest() {
        Preferences preferences = Mockito.mock(Preferences.class);
        Activity a = Mockito.mock(Activity.class);
        preferences.saveClientID(a, 1);
    }

    @Test
    public void readAccountIDTest() {
        Preferences preferences = Mockito.mock(Preferences.class);
        Activity a = Mockito.mock(Activity.class);
        preferences.readCredential(a);
    }

}