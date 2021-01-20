package com.wat.tabularasa20.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import com.wat.tabularasa20.R;

public class ActivityUtil {

    public static void changeTheme (@NonNull Context context) {
        context.setTheme(Preferences.readTheme(context) ? R.style.AppTheme : R.style.AppTheme_DifferentBG);
    }

    public static void refreshActivity (@NonNull Activity activity) {
        new CountDownTimer(3000, 3000) {
            @Override public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                activity.startActivity(new Intent(activity, activity.getClass()));
                activity.overridePendingTransition(0, 0);
                activity.finish();
            }
        }.start();
    }
}
