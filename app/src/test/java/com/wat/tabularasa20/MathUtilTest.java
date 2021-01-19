package com.wat.tabularasa20;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.wat.tabularasa20.utilities.MathUtil;
import org.junit.Test;
import org.mockito.Mockito;
import java.security.MessageDigest;
import java.util.Base64;

public class MathUtilTest {

    @Test
    public void toBase64Test() {
        MathUtil mathUtil = Mockito.mock(MathUtil.class);
        Bitmap btmp = Mockito.mock(Bitmap.class);
        Base64 b64 = Mockito.mock(Base64.class);
//        ByteArrayOutputStream baos = Mockito.mock(ByteArrayOutputStream.class);

        mathUtil.toBase64(btmp);
    }

    @Test
    public void fromBase64Test() {
        MathUtil mathUtil = Mockito.mock(MathUtil.class);
        Base64 b64 = Mockito.mock(Base64.class);
        BitmapFactory bmpfc = Mockito.mock(BitmapFactory.class);
        mathUtil.fromBase64("test");
    }

    @Test
    public void shaTest() {
        MathUtil mathUtil = Mockito.mock(MathUtil.class);
        MessageDigest mssgdg = Mockito.mock(MessageDigest.class);
        String outcome = "test";
        mathUtil.sha(outcome);
    }

}