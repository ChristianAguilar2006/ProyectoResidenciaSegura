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
        
        if (correo.isEmpty() || contrasena.isEmpty()) {
            mostrarAlerta("Error", "Por favor complete todos los campos", Alert.AlertType.ERROR);
            return;
        }
        
        try {
            Usuario usuario = usuarioDAO.login(correo, contrasena);
            
            if (usuario != null) {
                String rol = usuario.getRol();
                
                if (rol.equals("RESIDENTE")) {
                    Residente residente = convertirAResidente(usuario);
                    Navegacion.abrirVentanaResidente(residente);
                } else if (rol.equals("ADMINISTRADOR")) {
                    Administrador admin = convertirAAdministrador(usuario);
                    Navegacion.abrirVentanaAdministrador(admin);
                } else if (rol.equals("GUARDIA")) {
                    Guardia guardia = convertirAGuardia(usuario);
                    Navegacion.abrirVentanaGuardia(guardia);
                }
                
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "Credenciales incorrectas", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al iniciar sesión: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void salir() {
        System.exit(0);
    }
    
    private Residente convertirAResidente(Usuario usuario) {
        Residente residente = new Residente();
        residente.setIdUsuario(usuario.getIdUsuario());
        residente.setNombre(usuario.getNombre());
        residente.setCorreo(usuario.getCorreo());
        residente.setRol(usuario.getRol());
        residente.setDepartamento(usuario.getDepartamento());
        residente.setBloque(usuario.getBloque());
        residente.setTelefono(usuario.getTelefono());
        return residente;
    }
    
    private Administrador convertirAAdministrador(Usuario usuario) {
        Administrador admin = new Administrador();
        admin.setIdUsuario(usuario.getIdUsuario());
        admin.setNombre(usuario.getNombre());
        admin.setCorreo(usuario.getCorreo());
        admin.setRol(usuario.getRol());
        admin.setDepartamento(usuario.getDepartamento());
        admin.setBloque(usuario.getBloque());
        admin.setTelefono(usuario.getTelefono());
        return admin;
    }
    
    private Guardia convertirAGuardia(Usuario usuario) {
        Guardia guardia = new Guardia();
        guardia.setIdUsuario(usuario.getIdUsuario());
        guardia.setNombre(usuario.getNombre());
        guardia.setCorreo(usuario.getCorreo());
        guardia.setRol(usuario.getRol());
        guardia.setDepartamento(usuario.getDepartamento());
        guardia.setBloque(usuario.getBloque());
        guardia.setTelefono(usuario.getTelefono());
        return guardia;
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

