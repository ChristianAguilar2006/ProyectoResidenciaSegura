package com.residencial.modelo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Pago {
    
    private int idPago;
    private int idUsuario;
    private String tipoServicio;
    private BigDecimal monto;
    private Timestamp fechaPago;
    private Date fechaVencimiento;
    private String estado;
    private String metodoPago;
    private String comprobante;
    
    public Pago() {
    }
    
    public Pago(int idUsuario, String tipoServicio, BigDecimal monto, 
                Date fechaVencimiento) {
        this.idUsuario = idUsuario;
        this.tipoServicio = tipoServicio;
        this.monto = monto;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = "PENDIENTE";
    }
    
    // Getters y Setters
    public int getIdPago() {
        return idPago;
    }
    
    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getTipoServicio() {
        return tipoServicio;
    }
    
    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
    
    public BigDecimal getMonto() {
        return monto;
    }
    
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
    public Timestamp getFechaPago() {
        return fechaPago;
    }
    
    public void setFechaPago(Timestamp fechaPago) {
        this.fechaPago = fechaPago;
    }
    
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }
    
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    
    public String getComprobante() {
        return comprobante;
    }
    
    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }
}

