package com.pablo.recordatorio.medico.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pablo.recordatorio.medico.data.model.Recordatorio.RecordatorioResponse;
import com.pablo.recordatorio.medico.network.RecordatorioApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    private final MutableLiveData<RecordatorioResponse> recordatorios = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esta es mi pantalla de home");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<RecordatorioResponse> getRecordatorios() {
        return recordatorios;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void fetchRecordatorios(String userId) {
        isLoading.setValue(true);
        RecordatorioApiClient.RecordatorioService service = RecordatorioApiClient.getInstance().getRecordatorioService();

        Call<RecordatorioResponse> call = service.getRecordatorios(userId);
        call.enqueue(new Callback<RecordatorioResponse>() {
            @Override
            public void onResponse(Call<RecordatorioResponse> call, Response<RecordatorioResponse> response) {
                isLoading.setValue(false); // Indica que la carga terminó
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("Response Data Rec", response.body().toString());
                    recordatorios.setValue(response.body()); // Actualiza los datos
                } else {
                    // Maneja el caso de error en la respuesta (puedes agregar un LiveData de errores si lo deseas)
                    mText.setValue("Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RecordatorioResponse> call, Throwable t) {
                isLoading.setValue(false); // Indica que la carga terminó
                mText.setValue("Fallo en la llamada a la API: " + t.getMessage()); // Manejo de errores
            }
        });
    }
}