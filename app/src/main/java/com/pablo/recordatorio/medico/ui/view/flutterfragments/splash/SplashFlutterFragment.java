package com.pablo.recordatorio.medico.ui.view.flutterfragments.splash;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.pablo.recordatorio.medico.bridgeflutter.PabloFlutterFragment;

/*
*SplashFlutterFragment sirve para mostrar una pantalla de splash de Flutter en una aplicación Android nativa,
* configurando el fragmento para iniciar la interfaz de Flutter en la ruta "/splash".
*/
public class SplashFlutterFragment extends PabloFlutterFragment {

    public static Fragment newInstance() {
        return PabloFlutterFragment.newInstance(SplashFlutterFragment.class, "/splash");
    }

    // Este método se ejecuta cuando se crea la vista del fragmento
    // Utiliza `createWithOutUserData` para crear la vista sin datos de usuario adicionales
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createWithOutUserData(inflater, container, savedInstanceState);
    }


}
