package com.pablo.recordatorio.medico.data.model.Recordatorio;

import java.util.List;

public class RecordatorioResponse {
    private String codigo;
    private String mensaje;
    private List<Recordatorio> data;

    public RecordatorioResponse() {
    }

    public RecordatorioResponse(String codigo, String mensaje, List<Recordatorio> data) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.data = data;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Recordatorio> getData() {
        return data;
    }

    public void setData(List<Recordatorio> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RecordatorioResponse{" +
                "codigo='" + codigo + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", data=" + data +
                '}';
    }
}
