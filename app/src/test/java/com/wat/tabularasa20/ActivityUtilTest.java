package com.wat.tabularasa20;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;

import com.wat.tabularasa20.utilities.ActivityUtil;
import com.wat.tabularasa20.utilities.Network;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class ActivityUtilTest {

    @Test
    public void changeThemeTest() {
        ActivityUtil actutil = Mockito.mock(ActivityUtil.class, Mockito.RETURNS_DEEP_STUBS);
        Context context = Mockito.mock(Context.class, Mockito.RETURNS_DEEP_STUBS);
        actutil.changeTheme(context);
    }
}