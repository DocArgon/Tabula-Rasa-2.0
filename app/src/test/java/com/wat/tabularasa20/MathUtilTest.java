package com.wat.tabularasa20;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.wat.tabularasa20.utilities.MathUtil;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import java.security.MessageDigest;
import java.util.Base64;

public class MathUtilTest {

    @Test
    public void shaTest() {
        MathUtil mathUtil = Mockito.mock(MathUtil.class);
        String parameter = "test";
        mathUtil.sha(parameter);
    }

//    @Test
//    public void toBase64Test() {
//        MathUtil mathUtil = Mockito.mock(MathUtil.class, Mockito.RETURNS_DEEP_STUBS);
//        Bitmap btmp = Mockito.mock(Bitmap.class, Mockito.RETURNS_DEEP_STUBS);
//        Base64 b64 = Mockito.mock(Base64.class, Mockito.RETURNS_SELF);
//        mathUtil.bitmapToBase64(btmp);
//    }
//
//    @Test
//    public void fromBase64Test() {
//        MathUtil mathUtil = Mockito.mock(MathUtil.class);
//        Base64 b64 = Mockito.mock(Base64.class);
//        BitmapFactory bmpfc = Mockito.mock(BitmapFactory.class);
//        String parameter = "test";
//        mathUtil.bitmapFromBase64(parameter);
//    }
//
//    @Test
//    public void bitmapFromBase64Test() {
//        MathUtil mathUtil = Mockito.mock(MathUtil.class);
//        String string = "test";
//        mathUtil.bitmapFromBase64(string);
//    }

}