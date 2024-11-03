package com.pablo.recordatorio.medico.ui.flutterfragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.pablo.recordatorio.medico.R;

public class FlutterFullFragmentActivity extends AppCompatActivity {

    private static final String TAG = "FlutterFullFragmentActivity";
    public static final String EXTRA_FRAGMENT_CLASS_NAME = "EXTRA_FRAGMENT_CLASS_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_flutter_full_activity);
        Log.i(TAG, "Iniciando configuración del fragmento Flutter");
        // Asegura que la pantalla de carga sea visible inicialmente
        findViewById(R.id.loading_screen).setVisibility(View.VISIBLE);
        String fragmentClassName = getIntent().getStringExtra(EXTRA_FRAGMENT_CLASS_NAME);
        configurarFragmentoFlutter(fragmentClassName);
    }

    private void configurarFragmentoFlutter(String fragmentClassName) {

        try {
            Class<?> fragmentClass = Class.forName(fragmentClassName);
            Fragment flutterFragment = (Fragment) fragmentClass.getDeclaredMethod("newInstance").invoke(null);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flutter_fragment_container, flutterFragment);
            transaction.commit();

            // Usar un Handler para esperar y luego ocultar la pantalla de carga
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                findViewById(R.id.loading_screen).setVisibility(View.GONE);
                findViewById(R.id.flutter_fragment_container).setVisibility(View.VISIBLE);
                Log.i(TAG, "Fragmento de Flutter visible, pantalla de bienvenida oculta");
            }, 3000); // Ajusta el tiempo de retraso según sea necesario
            Log.i(TAG, "Transacción para el fragmento de Flutter confirmada");
        } catch (Exception e) {
            Log.e(TAG, "Error al instanciar el fragmento de Flutter: " + fragmentClassName, e);
        }
    }
}
