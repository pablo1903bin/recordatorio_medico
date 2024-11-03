package com.pablo.recordatorio.medico.utils;
import android.app.NotificationManager;
import android.content.Context;
import android.app.NotificationChannel;
import android.os.Build;

public class NotificationHelper {

    static final String CHANNEL_ID = "canal_rec_med_01";
    static final String CHANNEL_NAME = "Recordatorio de Medicina";
    static final String CHANNEL_DESCRIPTION = "Canal para recordatorios de medicamentos";

    // Método para crear el canal de notificación
    public static void createNotificationChannel(Context context) {
        // Solo se requiere en Android Oreo (API 26) o superior para registrar el canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            // Crea el canal de notificación con ID, nombre y nivel de importancia
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription(CHANNEL_DESCRIPTION);

            // Registra el canal en el sistema usando NotificationManager
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}

