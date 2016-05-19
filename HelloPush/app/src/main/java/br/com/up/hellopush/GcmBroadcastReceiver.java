package br.com.up.hellopush;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    private static final String TAG = "gcm";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "GcmBroadcastReceiver.onReceive: " + intent.getExtras());
        // Informa que o "GcmIntentService" vai tratar essa intent.
        ComponentName comp = new ComponentName(context.getPackageName(), GcmIntentService.class.getName());
        // Inicia o service, mas deixa o device "acordado" enquanto estÃ¡ executando.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}