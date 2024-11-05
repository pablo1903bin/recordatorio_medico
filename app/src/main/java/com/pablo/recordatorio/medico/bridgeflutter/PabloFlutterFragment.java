package com.pablo.recordatorio.medico.bridgeflutter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import io.flutter.embedding.android.FlutterFragment;
import io.flutter.embedding.android.RenderMode;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class PabloFlutterFragment extends FlutterFragment {
    private PabloChannel canal;

    public static Fragment newInstance(Class<? extends FlutterFragment> clase, String ruta) {
        Log.d("PabloFlutterFragment_newInstannce()", "Se esta construyendo una nueva instancia del motor de flutter para mostrar mi fragment...");
        return new NewEngineFragmentBuilder(clase)
                .renderMode(RenderMode.texture)      // Configura el modo de renderizado a `texture` para integrarlo en Android.
                .dartEntrypoint("appModuleFlutter") // Punto de entrada para el código Dart, especifica el módulo.
                .initialRoute(ruta)                  // Ruta inicial que carga Flutter.
                .build();
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        GeneratedPluginRegistrant.registerWith(flutterEngine);

        // Configura el canal de comunicación entre Android y Flutter
        canal = new PabloChannel();                  // Crea una instancia de `PabloChannel`.
        // Asegúrate de que obtenga el Activity en un contexto general sin casting
        Activity activity = getActivity();
        if (activity != null) {
            canal.configuraCanal(flutterEngine, activity);
        } else {
            Log.e("PabloFlutterFragment", "Activity es null en configureFlutterEngine");
        }
    }

    /**
     * Método para crear la vista del fragmento sin encabezado nativo adicional.
     * Este método llama a `super.onCreateView()` para obtener la vista Flutter y luego la devuelve.
     * No agrega ninguna vista extra a la vista Flutter.
     *
     * @param inflater  Inflador que convierte los layouts XML en vistas.
     * @param container Contenedor principal donde se añadirá la vista de Flutter.
     * @param savedInstanceState Estado de instancia guardado, si existe.
     * @return La vista de Flutter.
     */
    public View createWithOutUserData(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
