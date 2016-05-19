package br.com.up.hellopush;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {
    private static final String TAG = "gcm";

    public GcmIntentService() {
        super(br.up.demos.hellopush.Constants.PROJECT_NUMBER);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        Log.i(TAG, "GcmIntentService.onHandleIntent: " + extras);
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // Verifica o tipo da mensagem
        String messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty()) {
            // O extras.isEmpty() precisa ser chamado para ler o bundle
            // Verifica o tipo da mensagem, no futuro podemos ter mais tipos
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                // Erro
                onError(extras);
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // Mensagem do tipo normal. Faz a leitura do parÃ¢metro "msg"
                // enviado pelo servidor
                onMessage(extras);
            }
        }
        // Libera o wake lock, que foi bloqueado pela classe
        // "GcmBroadcastReceiver".
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void onError(Bundle extras) {
        Log.d(TAG, "Erro: " + extras.toString());
    }

    private void onMessage(Bundle extras) {
        // Por enquanto vamos apenas logar a mensagem no LogCat e fazer um hello
        // world
        String msg = extras.getString("msg");
        Log.d(TAG, msg);

        // Se o app esta aberto, podemos passar a mensagem direto ao
        // receiver
        if (ActivityStackUtils.isMyApplicationTaskOnTop(this)) {
            // Dispara uma Intent para o receiver configurado na Activity
            Intent intent = new Intent("RECEIVER_QUE_VAI_RECEBER_ESTA_MSG");
            intent.putExtra("msg", msg);
            sendBroadcast(intent);
        } else {
            // Se o app estiver fechado, a melhor maneira de se comunicar
            // com o usuÃ¡rio Ã© com uma notificaÃ§Ã£o
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("msg", msg);
            NotificationUtil.create(this, "Nova mensagem", intent);
        }
    }
}