package com.pablo.recordatorio.medico;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class NotificationScheduler {

    public static void programarNotificacion(Context context, Calendar calendar, String medicamento) {
        Log.i("[NotificationScheduler ]", "programar una nueva alarma-..... ");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("medicamento", medicamento);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Verificar si el tiempo en el calendario está en el pasado
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            // Si está en el pasado, añade un día
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Programar la alarma
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }

}
