package com.wat.tabularasa20;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.wat.tabularasa20.utilities.Network;
import com.wat.tabularasa20.utilities.Uploader;
import android.view.Menu;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class NetworkTest {
    String jsonBroken1 = "\\\"data\\\" : \"[ ]\"";
    String jsonBroken2 = "\\\"{\\\"data\\\" : \"[ ]\"}\\\"";
    @Test
    public void repairJsonTest() {
        Network network = Mockito.mock(Network.class);
        network.repairJson(jsonBroken1);
        network.repairJson(jsonBroken2);
    }

    @Test
    public void isDeviceConnectedTest() {
        Network network = Mockito.mock(Network.class);
        Activity a = Mockito.mock(Activity.class);
        network.isDeviceConnected(a);
    }

}