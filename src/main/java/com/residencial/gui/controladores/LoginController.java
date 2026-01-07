package com.residencial.gui.controladores;

import com.residencial.dao.UsuarioDAO;
import com.residencial.gui.util.Navegacion;
import com.residencial.modelo.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    
    @FXML
    private TextField txtCorreo;
    
    @FXML
    private PasswordField txtContrasena;
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    @FXML
    private void iniciarSesion() {
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText();
        
        if (camposVacios(correo, contrasena)) {
            mostrarAlerta("Error", "Por favor complete todos los campos", Alert.AlertType.ERROR);
            return;
        }
        
        try {
            Usuario usuario = usuarioDAO.login(correo, contrasena);
            
            if (usuario == null) {
                mostrarAlerta("Error", "Credenciales incorrectas", Alert.AlertType.ERROR);
                return;
            }
            
            abrirVentanaSegunRol(usuario);
            cerrarVentana();
            
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al iniciar sesión: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private boolean camposVacios(String correo, String contrasena) {
        return correo.isEmpty() || contrasena.isEmpty();
    }
    
    private void abrirVentanaSegunRol(Usuario usuario) {
        String rol = usuario.getRol();
        
        if (rol.equals("RESIDENTE")) {
            Residente residente = crearResidenteDesdeUsuario(usuario);
            Navegacion.abrirVentanaResidente(residente);
        } else if (rol.equals("ADMINISTRADOR")) {
            Administrador admin = crearAdministradorDesdeUsuario(usuario);
            Navegacion.abrirVentanaAdministrador(admin);
        } else if (rol.equals("GUARDIA")) {
            Guardia guardia = crearGuardiaDesdeUsuario(usuario);
            Navegacion.abrirVentanaGuardia(guardia);
        }
    }
    
    @FXML
    private void salir() {
        System.exit(0);
    }
    
    private Residente crearResidenteDesdeUsuario(Usuario usuario) {
        Residente residente = new Residente();
        copiarDatosUsuario(usuario, residente);
        return residente;
    }
    
    private Administrador crearAdministradorDesdeUsuario(Usuario usuario) {
        Administrador admin = new Administrador();
        copiarDatosUsuario(usuario, admin);
        return admin;
    }
    
    private Guardia crearGuardiaDesdeUsuario(Usuario usuario) {
        Guardia guardia = new Guardia();
        copiarDatosUsuario(usuario, guardia);
        return guardia;
    }
    
    private void copiarDatosUsuario(Usuario origen, Usuario destino) {
        destino.setIdUsuario(origen.getIdUsuario());
        destino.setNombre(origen.getNombre());
        destino.setCorreo(origen.getCorreo());
        destino.setRol(origen.getRol());
        destino.setDepartamento(origen.getDepartamento());
        destino.setBloque(origen.getBloque());
        destino.setTelefono(origen.getTelefono());
    }
    
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void cerrarVentana() {
        txtCorreo.getScene().getWindow().hide();
    }
}

