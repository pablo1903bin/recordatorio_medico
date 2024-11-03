package com.pablo.recordatorio.medico.ui.view.flutterfragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.pablo.recordatorio.medico.bridgeflutter.PabloFlutterFragment;

public class HomeFlutterFragment extends PabloFlutterFragment {

    // Método para crear una instancia de este fragmento con la ruta de la pantalla de Flutter que quieres cargar
    public static Fragment newInstance() {
        // Aquí defines la ruta de Flutter, por ejemplo "/bloqueo", que corresponde a la pantalla que deseas mostrar
        return PabloFlutterFragment.newInstance(HomeFlutterFragment.class, "/inicio");
    }

    // Este método se ejecuta cuando se crea la vista del fragmento
    // Utiliza `createWithOutUserData` para crear la vista sin datos de usuario adicionales
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createWithOutUserData(inflater, container, savedInstanceState);
    }
}
