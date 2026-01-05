package com.residencial.modelo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Pedido {
    
    private int idPedido;
    private int idUsuario;
    private String descripcion;
    private String tipoPedido;
    private Timestamp fechaSolicitud;
    private Date fechaEntregaEstimada;
    private String estado;
    private BigDecimal costo;
    private String observaciones;
    
    public Pedido() {
    }
    
    public Pedido(int idUsuario, String descripcion, String tipoPedido) {
        this.idUsuario = idUsuario;
        this.descripcion = descripcion;
        this.tipoPedido = tipoPedido;
        this.estado = "PENDIENTE";
        this.costo = BigDecimal.ZERO;
    }
    
    // Getters y Setters
    public int getIdPedido() {
        return idPedido;
    }
    
    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getTipoPedido() {
        return tipoPedido;
    }
    
    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }
    
    public Timestamp getFechaSolicitud() {
        return fechaSolicitud;
    }
    
    public void setFechaSolicitud(Timestamp fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    
    public Date getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }
    
    public void setFechaEntregaEstimada(Date fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public BigDecimal getCosto() {
        return costo;
    }
    
    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

