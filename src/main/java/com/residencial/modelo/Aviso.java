package com.residencial.modelo;

import java.sql.Date;
import java.sql.Timestamp;

public class Aviso {
    
    private int idAviso;
    private int idAdministrador;
    private String titulo;
    private String mensaje;
    private String tipo;
    private Timestamp fechaPublicacion;
    private Date fechaExpiracion;
    private boolean activo;
    
    public Aviso() {
        this.tipo = "INFORMATIVO";
        this.activo = true;
    }
    
    public Aviso(int idAdministrador, String titulo, String mensaje, String tipo) {
        this.idAdministrador = idAdministrador;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.activo = true;
    }
    
    // Getters y Setters
    public int getIdAviso() {
        return idAviso;
    }
    
    public void setIdAviso(int idAviso) {
        this.idAviso = idAviso;
    }
    
    public int getIdAdministrador() {
        return idAdministrador;
    }
    
    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public Timestamp getFechaPublicacion() {
        return fechaPublicacion;
    }
    
    public void setFechaPublicacion(Timestamp fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    
    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }
    
    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}

