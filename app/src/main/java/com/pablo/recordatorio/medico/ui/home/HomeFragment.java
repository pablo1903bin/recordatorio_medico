package com.pablo.recordatorio.medico.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.Locale;

import androidx.lifecycle.ViewModelProvider;

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
    private Button buttonProgramarNotificacion;
    private TextView textReloj;
    private Handler relojHandler = new Handler();
    private Runnable actualizarRelojRunnable;
    private Calendar calendar;
    private ProgressBar progressBar;
    private FragmentHomeBinding binding;
    private TextView textRecordatorios;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Configura el ViewModel
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializar el objeto Calendar
        calendar = Calendar.getInstance();

        // Inicializar los elementos de la UI
        textReloj = root.findViewById(R.id.text_reloj);
        textRecordatorios = root.findViewById(R.id.text_recordatorios);
        progressBar = root.findViewById(R.id.progressBar);

        // Observa el LiveData de recordatorios y actualiza la interfaz
        homeViewModel.getRecordatorios().observe(getViewLifecycleOwner(), recordatorioResponse -> {
            if (recordatorioResponse != null) {
                // Muestra los datos en el TextView
                textRecordatorios.setText(recordatorioResponse.toString());
            } else {
                textRecordatorios.setText("No se encontraron recordatorios.");
            }
        });

        // Observa el estado de carga para mostrar u ocultar el ProgressBar
        homeViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        // Configura el botón para iniciar la llamada a la API al hacer clic
        buttonProgramarNotificacion = root.findViewById(R.id.api_recordatorio);
        buttonProgramarNotificacion.setOnClickListener(v -> {
            Log.i("btn_flutter_fragment","Llamada a API desde botón...");
            homeViewModel.fetchRecordatorios("16");
        });

        // Botón de llamada a la API directa
        buttonProgramarNotificacion = root.findViewById(R.id.btn_flutter_fragment);
        buttonProgramarNotificacion.setOnClickListener(v -> mostrarFlutterFullFragmentActivity());

        // Botón para programar notificación
        buttonProgramarNotificacion = root.findViewById(R.id.button_programar_notificacion);
        buttonProgramarNotificacion.setOnClickListener(v -> mostrarDatePicker());

        // Configuración del reloj en tiempo real
        actualizarRelojRunnable = () -> {
            actualizarHora();
            relojHandler.postDelayed(actualizarRelojRunnable, 1000); // Actualiza cada segundo
        };
        relojHandler.post(actualizarRelojRunnable);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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