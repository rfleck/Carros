package br.com.up.hellopush;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

public class GCM {
    private static final String TAG = "gcm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";

    // Preferencias para salvar o registration id
    private static SharedPreferences getGCMPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("livroandroid_gcm", Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    // Retorna o registration id salvo nas preferencias
    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId == null || registrationId.trim().length() == 0) {
            // Nao registrado ainda
            return "";
        }
        return registrationId;
    }

    // Salva o registration id nas preferencias
    public static void saveRegistrationId(Context context, String registrationId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, registrationId);
        editor.commit();
    }

    // Utiliza a classe GoogleCloudMessaging para registrar no serviÃ§o do GCM
    public static String registrar(Context context, String projectNumber) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        try {
            Log.d(TAG, ">> GCM.registrar(): " + projectNumber);
            String registrationId = gcm.register(projectNumber);
            Log.d(TAG, "<< GCM.registrar() OK, registration id: " + registrationId);
            return registrationId;
        } catch (IOException e) {
            Log.e(TAG, "<< GCM.registrar() ERRO: " + e.getMessage(), e);
        }
        return null;
    }

    // Utiliza a classe GoogleCloudMessaging para cancelar o registro no serviÃ§o
    // do GCM
    public static void cancelar(Context context) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        try {
            gcm.unregister();
            saveRegistrationId(context, null);
            Log.d(TAG, "GCM cancelado com sucesso.");
        } catch (IOException e) {
            Log.e(TAG, "GCM erro ao desregistrar: " + e.getMessage(), e);
        }
    }

    // Verifica se estÃ¡ registrado no serviÃ§o do GCM
    public static boolean isAtivo(Context context) {
        String registrationId = getRegistrationId(context);
        if (registrationId == null || registrationId.trim().length() == 0) {
            return false;
        }
        return true;
    }
}