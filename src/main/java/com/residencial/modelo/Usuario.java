package com.residencial.modelo;

public class Usuario {
    
    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;
    private String departamento;
    private String bloque;
    private String telefono;
    
    public Usuario() {
    }
    
    public Usuario(String nombre, String correo, String contrasena, String rol, 
                   String departamento, String bloque) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.departamento = departamento;
        this.bloque = bloque;
    }
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getContrasena() {
        return contrasena;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public String getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    public String getBloque() {
        return bloque;
    }
    
    public void setBloque(String bloque) {
        this.bloque = bloque;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

