package com.pablo.recordatorio.medico.ui.flutterfragments.alarma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.pablo.recordatorio.medico.bridgeflutter.PabloFlutterFragment;
import com.pablo.recordatorio.medico.ui.flutterfragments.bloqueo.BloqueoFlutterFragment;


public class AlarmaFlutterFragment extends PabloFlutterFragment {

    public static Fragment newInstance(){
        return PabloFlutterFragment.newInstance(AlarmaFlutterFragment.class, "/programamed");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createWithOutUserData(inflater, container, savedInstanceState);
    }
}
