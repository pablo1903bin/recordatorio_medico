package com.pablo.recordatorio.medico.bridgeflutter;

import android.app.Activity;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pablo.recordatorio.medico.R;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;

import com.pablo.recordatorio.medico.utils.NotificationScheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class PabloChannel {

    // Nombre del canal utilizado para la comunicación entre Flutter y Android
    private static final String CHANNEL = "pablo_app/channel";
    private MethodChannel canal; // Variable para el canal de comunicación

    /**
     *
     *
     * Configurar el canal para la comunicación Flutter-Android.
     * Este método crea un canal de comunicación a través del cual
     * Flutter puede enviar mensajes a Android y viceversa.
     *
     * @param flutterEngine El motor de Flutter utilizado en la aplicación.
     * @param activity La actividad principal de la aplicación.
     */
    public void configuraCanal(FlutterEngine flutterEngine, Activity activity) {
        canal = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL);

        canal.setMethodCallHandler((call, result) -> {
            // Obtener el nombre del método llamado desde Flutter.
            String method = call.method;
            Log.i("com.pablo.myapp", "Flutter CALL.method: " + method); // Registrar el método llamado para depuración.

            // Verificar qué método fue llamado y responder en consecuencia.
            switch (method) {
                case "crearAlarma":
                    // Obtén el `Map<String, Object>` desde Flutter
                    Map<String, Object> alarmaMap = call.argument("alarma");
                    if (alarmaMap != null) {

                        String label = (String) alarmaMap.get("label");
                        String dateTimeString = (String) alarmaMap.get("dateTime");
                        boolean check = (boolean) alarmaMap.get("check");
                        String when = (String) alarmaMap.get("when");

                        // Verifica si el valor es un `Long` y conviértelo a `int` si es necesario
                        int id;
                        if (alarmaMap.get("id") instanceof Long) {
                            id = ((Long) alarmaMap.get("id")).intValue();
                        } else {
                            id = (int) alarmaMap.get("id");
                        }

                        int milliseconds;
                        if (alarmaMap.get("milliseconds") instanceof Long) {
                            milliseconds = ((Long) alarmaMap.get("milliseconds")).intValue();
                        } else {
                            milliseconds = (int) alarmaMap.get("milliseconds");
                        }

                        Log.i("com.pablo.myapp", "Valor de 'alarma' recibido: " + dateTimeString);

                        Calendar calendar = convertirStringACalendar(dateTimeString);
                        NotificationScheduler.programarNotificacion(activity, calendar, label != null ? label : "Medicamento");
                        showToast(activity, "Programando alarma con mensaje: " + dateTimeString);
                        result.success(null);

                    } else {
                        result.error("INVALID_ARGUMENT", "El mensaje es nulo o vacío", null);
                    }
                    break;

                case "mostrarToast":
                    String mensaje = call.argument("message");
                    if (mensaje != null) {
                        showToast(activity, mensaje);
                        result.success(null); // Devuelve un éxito a Flutter
                    } else {
                        result.error("INVALID_ARGUMENT", "El mensaje es nulo o vacío", null);
                    }
                    break;
                case "getBatteryLevel": // Obtener el nivel de batería del dispositivo.
                    getBatteryLevel(activity, result);
                    break;
                case "getToken": // Obtener un token (simulado).
                    getToken(result);
                    break;
                case "getOSVersion": // Obtener la versión del sistema operativo.
                    getOSVersion(result);
                    break;
                default: // Si el método no está implementado, devolver una respuesta de "no implementado".
                    result.notImplemented();
                    break;
            }
        });
    }

    public Calendar convertirStringACalendar(String dateTimeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        if (dateTimeString == null || dateTimeString.isEmpty()) {
            Log.e("com.pablo.myapp", "El valor de dateTimeString es nulo o vacío");
            return calendar; // Devuelve el calendario actual si dateTimeString es nulo
        }

        try {
            Date date = sdf.parse(dateTimeString);
            if (date != null) {
                calendar.setTime(date);
            }
        } catch (ParseException e) {
            Log.e("com.pablo.myapp", "Error al convertir fecha: " + e.getMessage());
        }
        return calendar;
    }

    private void showToast(Context context, String message) {
        Log.i("PabloChannel", "Mensaje recibido: " + message);

        if (message == null || message.isEmpty()) {
            Log.e("PabloChannel", "El mensaje es nulo o está vacío.");
            return;
        }

        // Infla el diseño personalizado del Toast
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        // Configura el mensaje en el TextView del diseño personalizado
        TextView textView = layout.findViewById(R.id.text);
        textView.setText(message);

        // Configura el icono si es necesario
        ImageView icon = layout.findViewById(R.id.icon);
        icon.setImageResource(R.drawable.ic_custom_icon); // Usa el icono que desees

        // Crea y muestra el Toast personalizado
        Toast toast = new Toast(context.getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout); // Establece la vista personalizada
        toast.show();
    }


    /**
     * Obtener el nivel de batería del dispositivo.
     * Este método usa el BatteryManager para recuperar el nivel de batería actual.
     *
     * @param activity La actividad principal que proporciona el contexto para el BatteryManager.
     * @param result El resultado que se devuelve a Flutter.
     */
    private void getBatteryLevel(Activity activity, MethodChannel.Result result) {
        // Obtener el BatteryManager del sistema.
        BatteryManager batteryManager = (BatteryManager) activity.getSystemService(Context.BATTERY_SERVICE);
        if (batteryManager != null) {
            // Obtener el nivel de batería como un porcentaje.
            int batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            if (batteryLevel != -1) {
                result.success(batteryLevel); // Enviar el nivel de batería a Flutter si es válido.
            } else {
                result.error("UNAVAILABLE", "Battery level not available.", null); // Error si el nivel no está disponible.
            }
        } else {
            result.error("UNAVAILABLE", "BatteryManager not available.", null); // Error si el BatteryManager no está disponible.
        }
    }

    /**
     * Obtener un token simulado.
     * Este método simula la obtención de un token, pero puede ser reemplazado con lógica real.
     *
     * @param result El resultado que se devuelve a Flutter.
     */
    private void getToken(MethodChannel.Result result) {
        Log.i("com.pablo.myapp", "Obteniendo token desde Android"); // Registrar la operación para depuración.
        String tokenSimulado = "Token12345";
        result.success(tokenSimulado);
    }

    /**
     * Obtener la versión del sistema operativo.
     * Este método recupera la versión actual del sistema operativo Android.
     *
     * @param result El resultado que se devuelve a Flutter.
     */
    private void getOSVersion(MethodChannel.Result result) {
        // Obtener la versión del sistema operativo.
        String osVersion = "Android " + Build.VERSION.RELEASE;
        Log.i("com.pablo.myapp", "Versión del sistema operativo: " + osVersion); // Registrar la versión para depuración.
        result.success(osVersion); // Enviar la versión del sistema operativo a Flutter.
    }

    /**
     * Enviar un mensaje desde Android a Flutter.
     * Este método permite que Android invoque un método en Flutter.
     *
     * @param mensaje El mensaje que se enviará a Flutter.
     */
    public void notificarCambioEnFlutter(String mensaje) {
        if (canal != null) {
            canal.invokeMethod("notificarCambio", mensaje); // Invocar el método en Flutter con el mensaje proporcionado.
        }
    }
}
