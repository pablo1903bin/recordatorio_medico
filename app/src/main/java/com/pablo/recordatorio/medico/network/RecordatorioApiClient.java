package com.pablo.recordatorio.medico.network;
import com.pablo.recordatorio.medico.data.model.Recordatorio.RecordatorioResponse;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecordatorioApiClient {


    private static RecordatorioApiClient instanciaUnica;

    // Interfaz de servicio para las solicitudes de la API
    public interface RecordatorioService {
        @GET("recordatorio/usuario/{idUser}")
        Call<RecordatorioResponse> getRecordatorios(@Path("idUser") String idUser);
    }

    // Instancia de Retrofit para las solicitudes
    private final Retrofit retrofit;

    // Constructor privado del Singleton
    private RecordatorioApiClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://45.33.13.164:8080/gateway/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // Método para obtener la instancia única de RecordatorioApiClient
    public static synchronized RecordatorioApiClient getInstance() {
        if (instanciaUnica == null) {
            instanciaUnica = new RecordatorioApiClient();
        }
        return instanciaUnica;
    }

    // Método para obtener el servicio RecordatorioService
    public RecordatorioService getRecordatorioService() {
        return retrofit.create(RecordatorioService.class);
    }
}