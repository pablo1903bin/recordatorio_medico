package com.pablo.recordatorio.medico.utils;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.pablo.recordatorio.medico.MainActivity;
import com.pablo.recordatorio.medico.R;

public class NotificationReceiver extends BroadcastReceiver {
    // ID del canal de notificación, se usará para identificar el canal de notificación de medicamentos
    public static final String CHANNEL_ID = "canal_01";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Obtener el nombre del medicamento de los datos del Intent
        String medicamento = intent.getStringExtra("medicamento");

        // Crear un Intent para abrir MainActivity al hacer clic en la notificación
        Intent notificationIntent = new Intent(context, MainActivity.class);
        // Pasar el nombre del medicamento al Intent para usarlo en MainActivity
        notificationIntent.putExtra("medicamento", medicamento);

        // Crear un PendingIntent para abrir MainActivity cuando se hace clic en la notificación
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Configurar el sonido de la notificación: el tono de alarma predeterminado del dispositivo
        Uri alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.alarmloud);

        // Crear el canal de notificación (requerido en Android 8.0 y versiones superiores)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Obtener el gestor de notificaciones y registrar el canal para que esté disponible en el sistema
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.deleteNotificationChannel(CHANNEL_ID);

            // Definir el canal de notificación con un nombre, descripción y nivel de importancia
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,                     // ID del canal
                    "Recordatorio de Medicina",     // Nombre del canal, visible para el usuario
                    NotificationManager.IMPORTANCE_HIGH // Importancia para notificaciones importantes
            );

            channel.setDescription("Recordatorio para tomar medicina");// Descripción del canal (opcional, visible para el usuario)
            channel.setSound(alarmSound, null); // Establecer el sonido para el canal de notificacion
            channel.enableVibration(true);// Activar la vibración en el canal de notificación
            channel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationManager.createNotificationChannel(channel);
        }

        // Construir la notificación usando NotificationCompat.Builder para garantizar compatibilidad con versiones anteriores de Android
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.mi_app_icon)          // Ícono pequeño de la notificación
                .setContentTitle("Recordatorio de Medicina")    // Título de la notificación
                .setContentText("Es hora de tomar: " + medicamento) // Texto de la notificación, incluye el medicamento
                .setPriority(NotificationCompat.PRIORITY_HIGH)  // Prioridad alta para que sea visible en la pantalla
                .setContentIntent(pendingIntent)                // Define la acción al tocar la notificación
                .setAutoCancel(true)                            // Ocultar la notificación al hacer clic
                .setSound(alarmSound)                           // Establecer el sonido de alarma
                .setVibrate(new long[]{0, 1000, 500, 1000});    // Configurar el patrón de vibración

        // Obtener el NotificationManager del sistema para poder enviar la notificación
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Enviar la notificación con un ID (en este caso, 1)
        notificationManager.notify(1, builder.build());
    }
}

