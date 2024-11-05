package com.pablo.recordatorio.medico;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.pablo.recordatorio.medico.databinding.ActivityMainBinding;
import com.pablo.recordatorio.medico.utils.NotificationScheduler;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar permisos de notificaciones para Android TIRAMISU en adelante
        verificarPermisos();

        // Configurar binding para inflar el layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtener el medicamento desde el Intent, si está presente
        String medicamento = getIntent().getStringExtra("medicamento");
        if (medicamento != null) {
            Log.d("MainActivity", "Medicamento a tomar: " + medicamento);
            Snackbar.make(binding.getRoot(), "Es hora de tomar " + medicamento, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú de opciones en la barra de acciones (AppBar) si es necesario.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void verificarPermisos(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }
}
