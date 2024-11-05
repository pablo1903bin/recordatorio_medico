package com.pablo.recordatorio.medico.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.pablo.recordatorio.medico.MainActivity;
import com.pablo.recordatorio.medico.utils.NotificationScheduler;
import com.pablo.recordatorio.medico.R;
import com.pablo.recordatorio.medico.data.model.Recordatorio.Recordatorio;
import com.pablo.recordatorio.medico.data.model.Recordatorio.RecordatorioResponse;
import com.pablo.recordatorio.medico.databinding.FragmentHomeBinding;
import com.pablo.recordatorio.medico.network.RecordatorioApiClient;
import com.pablo.recordatorio.medico.ui.flutterfragments.FlutterFullFragmentActivity;
import com.pablo.recordatorio.medico.ui.flutterfragments.alarma.AlarmaFlutterFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView textReloj;
    private Handler relojHandler = new Handler();
    private Runnable actualizarRelojRunnable;
    private Calendar calendar;
    private ProgressBar progressBar;
    private FragmentHomeBinding binding;
    private TextView textRecordatorios;
    private RecyclerView recyclerViewAlertas;
    private RecordatorioAdapter recordatorioAdapter;
    private List<Recordatorio> recordatorios = new ArrayList<>(); // Inicializa una lista vacía

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Configura el ViewModel
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Configura el RecyclerView
        recyclerViewAlertas = root.findViewById(R.id.recyclerView_alertas);
        recyclerViewAlertas.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configura el adaptador con una lista vacía al principio
        recordatorioAdapter = new RecordatorioAdapter(recordatorios);
        recyclerViewAlertas.setAdapter(recordatorioAdapter);

        // Observa los datos de recordatorios en el ViewModel
        homeViewModel.getRecordatorios().observe(getViewLifecycleOwner(), recordatorioResponse -> {
            if (recordatorioResponse != null) {
                // Actualiza la lista de recordatorios cuando los datos cambian
                recordatorios.clear();
                recordatorios.addAll(recordatorioResponse.getData()); // Suponiendo que getData() da la lista de Recordatorio
                recordatorioAdapter.notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
            }
        });

        // Observa el estado de carga para mostrar u ocultar el ProgressBar
        progressBar = root.findViewById(R.id.progressBar);
        homeViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        // Llama a fetchRecordatorios para cargar los datos desde la API
        homeViewModel.fetchRecordatorios("16"); // Puedes cambiar el ID por uno válido
        // Inicializar el objeto Calendar
        calendar = Calendar.getInstance();

        // Enlazar el FloatingActionButton
        FloatingActionButton fab = root.findViewById(R.id.fab);
        // Configurar el evento onClick para el FloatingActionButton
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                // Obtener la hora actual y añadir un minuto
                calendar.add(Calendar.MINUTE, 1);

                NotificationScheduler.programarNotificacion(getContext(), calendar, "Paracetamol");

                // Mostrar mensaje con la hora en que se programará la notificación
                String formattedTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.getTime());

                // Acción a realizar cuando se hace clic en el FAB
                Toast.makeText(getContext(), "Recordatorio programado para las: " + formattedTime, Toast.LENGTH_SHORT).show();
                // Llamar a otros métodos o ejecutar acciones según sea necesario
            }
        });
        // Botón de navegación 'Inicio'
        ImageView btnInicio = root.findViewById(R.id.imageView78);
        btnInicio.setOnClickListener(v -> {
            // Aquí puedes agregar la acción para 'Inicio', si es la misma activity puedes omitir este botón
        });

        // Botón de navegación 'Wallet' que navega a FlutterFullFragmentActivity
        ImageView btnWallet = root.findViewById(R.id.imageView87);
        btnWallet.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FlutterFullFragmentActivity.class);
            intent.putExtra(FlutterFullFragmentActivity.EXTRA_FRAGMENT_CLASS_NAME, "com.pablo.recordatorio.medico.ui.flutterfragments.alarma.AlarmaFlutterFragment");
            startActivity(intent);
        });

        // Botón de navegación 'Profile'
        ImageView btnProfile = root.findViewById(R.id.imageView8);
        btnProfile.setOnClickListener(v -> {
            // Agrega la navegación a la actividad o funcionalidad deseada
        });

        // Botón de navegación 'Configs'
        ImageView btnConfigs = root.findViewById(R.id.imageView557);
        btnConfigs.setOnClickListener(v -> {
            // Agrega la navegación a la actividad o funcionalidad deseada
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List<Recordatorio> obtenerDatosFalsos() {
        List<Recordatorio> recordatorios = new ArrayList<>();
        recordatorios.add(new Recordatorio(1, 1, "Paracetamol", "Para dolor de cabeza", "500mg",
                "Oral", 8, "horas", "2024-11-05", "08:00", "5 días", "activo", "2024-11-04"));

        recordatorios.add(new Recordatorio(2, 1, "Ibuprofeno", "Para inflamación", "200mg",
                "Oral", 6, "horas", "2024-11-05", "09:00", "7 días", "activo", "2024-11-04"));

        recordatorios.add(new Recordatorio(3, 1, "Amoxicilina", "Antibiótico para infecciones", "500mg",
                "Oral", 12, "horas", "2024-11-06", "10:00", "10 días", "activo", "2024-11-05"));

        recordatorios.add(new Recordatorio(4, 1, "Loratadina", "Para alergias", "10mg",
                "Oral", 24, "horas", "2024-11-07", "08:00", "30 días", "activo", "2024-11-06"));

        recordatorios.add(new Recordatorio(5, 1, "Metformina", "Para control de diabetes", "850mg",
                "Oral", 12, "horas", "2024-11-08", "07:00", "indefinido", "activo", "2024-11-07"));

        recordatorios.add(new Recordatorio(6, 1, "Omeprazol", "Protector gástrico", "20mg",
                "Oral", 24, "horas", "2024-11-09", "06:00", "15 días", "activo", "2024-11-08"));

        recordatorios.add(new Recordatorio(7, 1, "Enalapril", "Para la presión arterial alta", "10mg",
                "Oral", 12, "horas", "2024-11-10", "07:30", "indefinido", "activo", "2024-11-09"));

        recordatorios.add(new Recordatorio(8, 1, "Aspirina", "Para prevenir coágulos", "100mg",
                "Oral", 24, "horas", "2024-11-11", "08:00", "indefinido", "activo", "2024-11-10"));

        recordatorios.add(new Recordatorio(9, 1, "Alprazolam", "Para la ansiedad", "0.5mg",
                "Oral", 8, "horas", "2024-11-12", "14:00", "15 días", "activo", "2024-11-11"));

        recordatorios.add(new Recordatorio(10, 1, "Furosemida", "Diurético", "40mg",
                "Oral", 12, "horas", "2024-11-13", "09:00", "7 días", "activo", "2024-11-12"));

        return recordatorios;
    }

    private void realizarLlamadaApi() {
        Log.i("Llamda","Llamando a mi API...");
        RecordatorioApiClient.RecordatorioService service = RecordatorioApiClient.getInstance().getRecordatorioService();

        Call<RecordatorioResponse> call = service.getRecordatorios("16");
        call.enqueue(new Callback<RecordatorioResponse>() {
            @Override
            public void onResponse(Call<RecordatorioResponse> call, Response<RecordatorioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RecordatorioResponse recordatorioResponse = response.body();
                    Log.d("API_RESPONSE", "Respuesta completa: " + recordatorioResponse.toString());

                    // Itera sobre cada recordatorio en data y muestra sus detalles en el log
                    for (Recordatorio recordatorio : recordatorioResponse.getData()) {
                        Log.d("API_RESPONSE", "Detalle Recordatorio: " + recordatorio.toString());
                    }
                } else {
                    Log.e("API_RESPONSE", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RecordatorioResponse> call, Throwable t) {
                Log.e("API_RESPONSE", "Fallo en la llamada a la API", t);
            }
        });
    }


    private void mostrarFlutterFullFragmentActivity() {
        Log.i("mostrarFlutterFullFragmentActivity","Cargar el FlutterFullFragmentActivity....");
        Intent intent = new Intent(getContext(), FlutterFullFragmentActivity.class);
        intent.putExtra(FlutterFullFragmentActivity.EXTRA_FRAGMENT_CLASS_NAME, AlarmaFlutterFragment.class.getName());
        startActivity(intent);
    }
    private void mostrarDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    // Configurar la fecha seleccionada en el Calendar
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Mostrar el TimePicker después de seleccionar la fecha
                    mostrarTimePicker();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void mostrarTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (view, hourOfDay, minute) -> {
                    // Configurar la hora seleccionada en el Calendar
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);

                    // Programar la notificación con la fecha y hora seleccionadas
                    programarNotificacion();
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void programarNotificacion() {
        // Formatear la fecha y hora seleccionadas para mostrarlas en un mensaje
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String fechaHora = dateFormat.format(calendar.getTime());

        // Llamar al método para programar la notificación
        NotificationScheduler.programarNotificacion(getContext(), calendar, "Cimitril");

        // Mostrar un mensaje de confirmación
        Toast.makeText(getContext(), "Notificación programada para: " + fechaHora, Toast.LENGTH_LONG).show();
    }

    // Método para actualizar la hora en el TextView
    private void actualizarHora() {
        // Obtiene la hora actual y la formatea
        String horaActual = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        textReloj.setText(horaActual); // Muestra la hora en el TextView
    }
}