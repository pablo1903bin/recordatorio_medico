package com.pablo.recordatorio.medico.ui.view.flutterfragments.bloqueo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.pablo.recordatorio.medico.bridgeflutter.PabloFlutterFragment;

public class BloqueoFlutterFragment extends PabloFlutterFragment {

  public static Fragment newInstance(){
   return PabloFlutterFragment.newInstance(BloqueoFlutterFragment.class, "/mmovil/cuenta/bloqueo");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createWithOutUserData(inflater, container, savedInstanceState);
  }
}
