package com.pablo.recordatorio.medico.ui.home;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.recordatorio.medico.R;
import com.pablo.recordatorio.medico.data.model.Recordatorio.Recordatorio;

import java.util.List;


public class RecordatorioAdapter extends RecyclerView.Adapter<RecordatorioAdapter.RecordatorioViewHolder> {

    // Lista de recordatorios que se va a mostrar en el RecyclerView
    private List<Recordatorio> recordatorios;

    // Constructor que recibe la lista de recordatorios y la asigna al adaptador
    public RecordatorioAdapter(List<Recordatorio> recordatorios) {
        this.recordatorios = recordatorios;
    }

    // Método que infla el layout de cada item (elemento) del RecyclerView
    @NonNull
    @Override
    public RecordatorioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el layout para cada item de la lista desde item_recordatorio.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recordatorio, parent, false);
        return new RecordatorioViewHolder(view); // Retornamos una nueva instancia del ViewHolder
    }

    // Método que enlaza los datos de cada recordatorio con los elementos visuales (TextViews) del ViewHolder
    @Override
    public void onBindViewHolder(@NonNull RecordatorioViewHolder holder, int position) {
        // Obtenemos el recordatorio correspondiente a la posición actual
        Recordatorio recordatorio = recordatorios.get(position);

        // Asignamos los valores de los campos del objeto Recordatorio a los TextViews en el ViewHolder
        holder.nombreMedicamento.setText(recordatorio.getNombreMedicamento());
        holder.descripcion.setText(recordatorio.getDescripcion());
        holder.dosisMetodo.setText(recordatorio.getDosis() + " - " + recordatorio.getMetodoAdministracion());
        holder.frecuenciaHora.setText(recordatorio.getFrecuenciaUnidades() + " " +
                recordatorio.getFrecuenciaIntervalo() + " a las " + recordatorio.getHoraInicio());
        holder.duracion.setText("Duración: " + recordatorio.getDuracionTratamiento());
    }

    // Método que retorna la cantidad de elementos en la lista de recordatorios
    @Override
    public int getItemCount() {
        return recordatorios.size();
    }

    // Clase interna que representa el ViewHolder del RecyclerView. Aquí se inicializan los elementos visuales.
    static class RecordatorioViewHolder extends RecyclerView.ViewHolder {
        // Declaración de los TextViews que mostrarán los datos del Recordatorio
        TextView nombreMedicamento, descripcion, dosisMetodo, frecuenciaHora, duracion;

        // Constructor del ViewHolder, donde se asignan los TextViews utilizando los IDs definidos en item_recordatorio.xml
        public RecordatorioViewHolder(View itemView) {
            super(itemView);
            nombreMedicamento = itemView.findViewById(R.id.text_nombre_medicamento);
            descripcion = itemView.findViewById(R.id.text_descripcion);
            dosisMetodo = itemView.findViewById(R.id.text_dosis_metodo);
            frecuenciaHora = itemView.findViewById(R.id.text_frecuencia_hora);
            duracion = itemView.findViewById(R.id.text_duracion);
        }
    }
}