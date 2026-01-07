package com.residencial.gui.util;

import com.residencial.modelo.Administrador;
import com.residencial.modelo.Guardia;
import com.residencial.modelo.Residente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navegacion {
    
    private static final int ANCHO_VENTANA_PRINCIPAL = 900;
    private static final int ALTO_VENTANA_PRINCIPAL = 700;
    private static final int ANCHO_VENTANA_LOGIN = 600;
    private static final int ALTO_VENTANA_LOGIN = 400;
    
    public static void abrirVentanaResidente(Residente residente) {
        try {
            FXMLLoader loader = new FXMLLoader(Navegacion.class.getResource("/fxml/Residente.fxml"));
            Parent root = loader.load();
            
            com.residencial.gui.controladores.ResidenteController controller = loader.getController();
            controller.setResidente(residente);
            
            mostrarVentana(root, "Menú Residente - " + residente.getNombre(), 
                          ANCHO_VENTANA_PRINCIPAL, ALTO_VENTANA_PRINCIPAL, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void abrirVentanaAdministrador(Administrador admin) {
        try {
            FXMLLoader loader = new FXMLLoader(Navegacion.class.getResource("/fxml/Administrador.fxml"));
            Parent root = loader.load();
            
            com.residencial.gui.controladores.AdministradorController controller = loader.getController();
            controller.setAdministrador(admin);
            
            mostrarVentana(root, "Menú Administrador - " + admin.getNombre(), 
                          ANCHO_VENTANA_PRINCIPAL, ALTO_VENTANA_PRINCIPAL, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void abrirVentanaGuardia(Guardia guardia) {
        try {
            FXMLLoader loader = new FXMLLoader(Navegacion.class.getResource("/fxml/Guardia.fxml"));
            Parent root = loader.load();
            
            com.residencial.gui.controladores.GuardiaController controller = loader.getController();
            controller.setGuardia(guardia);
            
            mostrarVentana(root, "Menú Guardia - " + guardia.getNombre(), 
                          ANCHO_VENTANA_PRINCIPAL, ALTO_VENTANA_PRINCIPAL, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void abrirVentanaLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(Navegacion.class.getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            
            mostrarVentana(root, "Sistema de Gestión Residencial", 
                          ANCHO_VENTANA_LOGIN, ALTO_VENTANA_LOGIN, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void mostrarVentana(Parent root, String titulo, int ancho, int alto, boolean redimensionable) {
        Stage nuevaVentana = new Stage();
        nuevaVentana.setTitle(titulo);
        nuevaVentana.setScene(new Scene(root, ancho, alto));
        nuevaVentana.setResizable(redimensionable);
        nuevaVentana.show();
    }
}
