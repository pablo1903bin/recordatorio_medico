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
    private List<Recordatorio> recordatorioList;

    public RecordatorioAdapter(List<Recordatorio> recordatorioList) {
        this.recordatorioList = recordatorioList;
    }

    @NonNull
    @Override
    public RecordatorioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recordatorio, parent, false);
        return new RecordatorioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordatorioViewHolder holder, int position) {
        Recordatorio recordatorio = recordatorioList.get(position);
        holder.nombreMedicamento.setText(recordatorio.getNombreMedicamento());
        holder.descripcion.setText(recordatorio.getDescripcion());
        holder.dosis.setText(recordatorio.getDosis());
    }

    @Override
    public int getItemCount() {
        return recordatorioList.size();
    }

    public void setRecordatorioList(List<Recordatorio> recordatorioList) {
        this.recordatorioList = recordatorioList;
        notifyDataSetChanged();
    }

    public static class RecordatorioViewHolder extends RecyclerView.ViewHolder {
        TextView nombreMedicamento, descripcion, dosis;

        public RecordatorioViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreMedicamento = itemView.findViewById(R.id.text_nombre_medicamento);
            descripcion = itemView.findViewById(R.id.text_descripcion);
            dosis = itemView.findViewById(R.id.text_dosis);
        }
    }
}
