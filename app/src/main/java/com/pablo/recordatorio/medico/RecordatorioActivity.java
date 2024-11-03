package com.pablo.recordatorio.medico;

import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class RecordatorioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configura la actividad para ser de pantalla completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_recordatorio);

        // Opcional: Puedes recibir datos adicionales del Intent para mostrar el medicamento
        String medicamento = getIntent().getStringExtra("medicamento");

        // Aquí puedes configurar la vista, mostrar el medicamento y cualquier otro detalle
    }
}
