package com.pablo.recordatorio.medico;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.pablo.recordatorio.medico.databinding.ActivityMainBinding;

import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    static final String CHANNEL_ID = "recordatorio_medicina_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //permisos de la App -->Notificaciones
        verificarPermisos();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                // Obtener la hora actual y añadir un minuto
                calendar.add(Calendar.MINUTE, 1);

                NotificationScheduler.programarNotificacion(MainActivity.this, calendar, "Paracetamol");

                // Mostrar mensaje con la hora en que se programará la notificación
                String formattedTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.getTime());
                Snackbar.make(view, "Recordatorio programado para las " + formattedTime, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String medicamento = getIntent().getStringExtra("medicamento");
        if (medicamento != null) {
            Log.d("MainActivity", "Medicamento a tomar: " + medicamento);
            Snackbar.make(binding.getRoot(), "Es hora de tomar " + medicamento, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Define los destinos principales de navegación. Cada ID representa una sección del menú (home, gallery, slideshow)
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer) // Vincula el DrawerLayout para que pueda abrirse/cerrarse con el AppBar
                .build();

        // Controlador de navegación que gestiona los cambios de fragmento dentro del layout nav_host_fragment_content_main
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        // Vincula el AppBar con el controlador de navegación para que se actualice el título automáticamente
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        // Configura el NavigationView para que funcione con el NavController, permitiendo la navegación al hacer clic en los elementos del menú
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú (options menu) en la barra de acciones (AppBar). Agrega los elementos del menú especificados en R.menu.main
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Método que se llama cuando el usuario hace clic en el botón de navegación "arriba" en la barra de acciones
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private void verificarPermisos(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    };

}