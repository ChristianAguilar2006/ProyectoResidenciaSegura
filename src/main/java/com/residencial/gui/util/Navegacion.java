package com.residencial.gui.util;

import com.residencial.modelo.Administrador;
import com.residencial.modelo.Guardia;
import com.residencial.modelo.Residente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navegacion {
    
    public static void abrirVentanaResidente(Residente residente) {
        try {
            FXMLLoader loader = new FXMLLoader(Navegacion.class.getResource("/fxml/Residente.fxml"));
            Parent root = loader.load();
            
            com.residencial.gui.controladores.ResidenteController controller = loader.getController();
            controller.setResidente(residente);
            
            Stage stage = new Stage();
            stage.setTitle("Menú Residente - " + residente.getNombre());
            stage.setScene(new Scene(root, 900, 700));
            stage.setResizable(true);
            stage.show();
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
            
            Stage stage = new Stage();
            stage.setTitle("Menú Administrador - " + admin.getNombre());
            stage.setScene(new Scene(root, 900, 700));
            stage.setResizable(true);
            stage.show();
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
            
            Stage stage = new Stage();
            stage.setTitle("Menú Guardia - " + guardia.getNombre());
            stage.setScene(new Scene(root, 900, 700));
            stage.setResizable(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void abrirVentanaLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(Navegacion.class.getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setTitle("Sistema de Gestión Residencial");
            stage.setScene(new Scene(root, 600, 400));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

