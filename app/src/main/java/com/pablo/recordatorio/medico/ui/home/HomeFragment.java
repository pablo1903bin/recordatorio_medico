package com.pablo.recordatorio.medico.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.pablo.recordatorio.medico.NotificationScheduler;
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
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textReloj = root.findViewById(R.id.text_reloj);
        calendar = Calendar.getInstance();

        buttonProgramarNotificacion = root.findViewById(R.id.btn_flutter_fragment);


        buttonProgramarNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("btn_flutter_fragment","Ir a flutter fragment....");
                mostrarFlutterFullFragmentActivity();
            }
        });

        buttonProgramarNotificacion = root.findViewById(R.id.Llamada);
        buttonProgramarNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              realizarLlamadaApi();  // Llama al método que hace la llamada a la API
            }
        });

        buttonProgramarNotificacion = root.findViewById(R.id.button_programar_notificacion);
        buttonProgramarNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker();
            }
        });
        // Configura el Runnable para actualizar la hora cada segundo
        actualizarRelojRunnable = new Runnable() {
            @Override
            public void run() {
                actualizarHora();
                relojHandler.postDelayed(this, 1000); // Actualiza cada segundo
            }
        };

        // Inicia la actualización del reloj
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