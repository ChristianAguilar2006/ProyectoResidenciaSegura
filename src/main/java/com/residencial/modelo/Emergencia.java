package com.residencial.modelo;

import java.sql.Timestamp;

public class Emergencia {
    
    private int idEmergencia;
    private int idUsuario;
    private String tipo;
    private String descripcion;
    private String ubicacion;
    private Timestamp fechaReporte;
    private String estado;
    private String prioridad;
    private Integer idGuardiaAtendiendo;
    private Timestamp fechaResolucion;
    private String observaciones;
    
    public Emergencia() {
        this.estado = "ACTIVA";
        this.prioridad = "MEDIA";
    }
    
    public Emergencia(int idUsuario, String tipo, String descripcion, String ubicacion) {
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.estado = "ACTIVA";
        this.prioridad = "MEDIA";
    }
    
    // Getters y Setters
    public int getIdEmergencia() {
        return idEmergencia;
    }
    
    public void setIdEmergencia(int idEmergencia) {
        this.idEmergencia = idEmergencia;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public Timestamp getFechaReporte() {
        return fechaReporte;
    }
    
    public void setFechaReporte(Timestamp fechaReporte) {
        this.fechaReporte = fechaReporte;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getPrioridad() {
        return prioridad;
    }
    
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
    
    public Integer getIdGuardiaAtendiendo() {
        return idGuardiaAtendiendo;
    }
    
    public void setIdGuardiaAtendiendo(Integer idGuardiaAtendiendo) {
        this.idGuardiaAtendiendo = idGuardiaAtendiendo;
    }
    
    public Timestamp getFechaResolucion() {
        return fechaResolucion;
    }
    
    public void setFechaResolucion(Timestamp fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

