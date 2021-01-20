package com.wat.tabularasa20.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MathUtil {

    public static String sha(@NonNull String str) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            digest.update(str.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte n : messageDigest) {
                StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & n));
                while (h.length() < 2)
                    h.insert(0, "0");
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ignore) {
            return null;
        }
    }

    public static String bitmapToBase64(@NonNull Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
    }

    public static Bitmap bitmapFromBase64(String base64) {
        byte[] data = Base64.decode(base64, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    public static String toBase64 (@NonNull String msg) {
        return Base64.encodeToString(msg.getBytes(), Base64.NO_WRAP);
    }

    public static String fromBase64 (String base64) {
        return new String(Base64.decode(base64, Base64.NO_WRAP));
    }
}
