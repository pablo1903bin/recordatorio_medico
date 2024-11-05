package com.pablo.recordatorio.medico.data.model.Recordatorio;

public class Recordatorio {
    private int id;
    private int user;
    private String nombreMedicamento;
    private String descripcion;
    private String dosis;
    private String metodoAdministracion;
    private int frecuenciaUnidades;
    private String frecuenciaIntervalo;
    private String fechaInicio;
    private String horaInicio;
    private String duracionTratamiento;
    private String estado;
    private String fechaCreacion;

    public Recordatorio(int id, int user, String nombreMedicamento, String descripcion, String dosis,
                        String metodoAdministracion, int frecuenciaUnidades, String frecuenciaIntervalo,
                        String fechaInicio, String horaInicio, String duracionTratamiento, String estado,
                        String fechaCreacion) {
        this.id = id;
        this.user = user;
        this.nombreMedicamento = nombreMedicamento;
        this.descripcion = descripcion;
        this.dosis = dosis;
        this.metodoAdministracion = metodoAdministracion;
        this.frecuenciaUnidades = frecuenciaUnidades;
        this.frecuenciaIntervalo = frecuenciaIntervalo;
        this.fechaInicio = fechaInicio;
        this.horaInicio = horaInicio;
        this.duracionTratamiento = duracionTratamiento;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getMetodoAdministracion() {
        return metodoAdministracion;
    }

    public void setMetodoAdministracion(String metodoAdministracion) {
        this.metodoAdministracion = metodoAdministracion;
    }

    public int getFrecuenciaUnidades() {
        return frecuenciaUnidades;
    }

    public void setFrecuenciaUnidades(int frecuenciaUnidades) {
        this.frecuenciaUnidades = frecuenciaUnidades;
    }

    public String getFrecuenciaIntervalo() {
        return frecuenciaIntervalo;
    }

    public void setFrecuenciaIntervalo(String frecuenciaIntervalo) {
        this.frecuenciaIntervalo = frecuenciaIntervalo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getDuracionTratamiento() {
        return duracionTratamiento;
    }

    public void setDuracionTratamiento(String duracionTratamiento) {
        this.duracionTratamiento = duracionTratamiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Recordatorio{" +
                "descripcion='" + descripcion + '\'' +
                ", id=" + id +
                ", user=" + user +
                ", nombreMedicamento='" + nombreMedicamento + '\'' +
                ", dosis='" + dosis + '\'' +
                ", metodoAdministracion='" + metodoAdministracion + '\'' +
                ", frecuenciaUnidades=" + frecuenciaUnidades +
                ", frecuenciaIntervalo='" + frecuenciaIntervalo + '\'' +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", duracionTratamiento='" + duracionTratamiento + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                '}';
    }
}

