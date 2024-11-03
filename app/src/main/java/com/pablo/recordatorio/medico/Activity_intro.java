package com.pablo.recordatorio.medico;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.pablo.recordatorio.medico.ui.view.flutterfragments.splash.SplashFlutterFragment;

public class Activity_intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_intro);
        // Configuración para que no se dibuje en la barra de estado
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        // Configura la barra de estado en modo transparente para ver el fondo
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        // Si el fondo de la barra de estado es claro, usa un estilo de texto oscuro
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Botón para lanzar el FlutterFragment
        findViewById(R.id.btn_inicio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("launchFlutterButton", "Llamar al método para mostrar el fragmento de SplashFlutter");
                mostrarInicio();
            }
        });
    }


    private void mostrarInicio() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}