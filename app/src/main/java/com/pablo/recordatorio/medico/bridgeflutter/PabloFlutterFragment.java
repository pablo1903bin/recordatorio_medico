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

    // Instancia de PabloChannel para la comunicación entre Flutter y Android
    private PabloChannel canal;

    /**
     * Método estático para crear una nueva instancia de `PabloFlutterFragment`.
     * Recibe una clase (`clase`) y una ruta específica (`ruta`) para inicializar el fragmento.
     * Aquí se utiliza `NewEngineFragmentBuilder` para crear un nuevo motor y asociarlo
     * con la ruta inicial y el punto de entrada de Flutter.
     *
     * @param clase La clase de fragmento que se va a crear (heredando de FlutterFragment).
     * @param ruta  La ruta inicial que cargará Flutter cuando se inicie este fragmento.
     * @return Un nuevo fragmento con el motor configurado.
     */
    public static Fragment newInstance(Class<? extends FlutterFragment> clase, String ruta) {
        Log.d("PabloFlutterFragment_newInstannce()", "Se esta construyendo una nueva instancia del motor de flutter para mostrar mi fragment...");
        return new NewEngineFragmentBuilder(clase)
                .renderMode(RenderMode.texture)      // Configura el modo de renderizado a `texture` para integrarlo en Android.
                .dartEntrypoint("appModuleFlutter") // Punto de entrada para el código Dart, especifica el módulo.
                .initialRoute(ruta)                  // Ruta inicial que carga Flutter.
                .build();
    }

    /**
     * Método `configureFlutterEngine` que se llama para configurar el `FlutterEngine`.
     * Registra los plugins de Flutter y configura el canal de comunicación con `PabloChannel`.
     * Este método se llama cada vez que el motor de Flutter necesita configurarse para una instancia del fragmento.
     *
     * @param flutterEngine El motor de Flutter que se va a configurar.
     */
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
