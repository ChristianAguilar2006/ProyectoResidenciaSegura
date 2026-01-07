package com.residencial.gui.controladores;

import com.residencial.gui.util.DialogosUtil;
import com.residencial.gui.util.Navegacion;
import com.residencial.modelo.Guardia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class GuardiaController implements Initializable {
    
    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    
    @FXML private TextField txtIdEmergencia;
    @FXML private TextField txtNuevoEstado;
    
    @FXML private TableView tablaEmergencias;
    
    private Guardia guardia;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTablas();
    }
    
    public void setGuardia(Guardia guardia) {
        this.guardia = guardia;
        cargarDatosPerfil();
        cargarEmergencias();
    }
    
    private void configurarTablas() {
        if (tablaEmergencias != null) {
            TableColumn colId = new TableColumn("ID");
            colId.setCellValueFactory(new PropertyValueFactory<>("idEmergencia"));
            TableColumn colTipo = new TableColumn("Tipo");
            colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            TableColumn colPrioridad = new TableColumn("Prioridad");
            colPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridad"));
            TableColumn colEstado = new TableColumn("Estado");
            colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            TableColumn colUbicacion = new TableColumn("Ubicación");
            colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
            tablaEmergencias.getColumns().addAll(colId, colTipo, colPrioridad, colEstado, colUbicacion);
        }
    }
    
    private void cargarDatosPerfil() {
        if (lblNombre != null) lblNombre.setText(guardia.getNombre());
        if (lblCorreo != null) lblCorreo.setText(guardia.getCorreo());
    }
    
    @FXML
    private void atenderEmergencia() {
        try {
            String idStr = txtIdEmergencia.getText().trim();
            String nuevoEstado = txtNuevoEstado.getText().trim();
            
            if (idStr.isEmpty() || nuevoEstado.isEmpty()) {
                DialogosUtil.mostrarError("Complete todos los campos");
                return;
            }
            
            int idEmergencia = Integer.parseInt(idStr);
            guardia.atenderEmergenciaGUI(idEmergencia, nuevoEstado);
            DialogosUtil.mostrarExito("Emergencia actualizada correctamente");
            limpiarCamposEmergencia();
            cargarEmergencias();
        } catch (NumberFormatException e) {
            DialogosUtil.mostrarError("El ID debe ser un número válido");
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    @FXML
    private void refrescarEmergencias() {
        cargarEmergencias();
    }
    
    @FXML
    private void cerrarSesion() {
        if (DialogosUtil.mostrarConfirmacion("Cerrar Sesión", "¿Desea cerrar sesión?")) {
            lblNombre.getScene().getWindow().hide();
            Navegacion.abrirVentanaLogin();
        }
    }
    
    private void cargarEmergencias() {
        if (tablaEmergencias != null) {
            try {
                ObservableList emergencias = FXCollections.observableArrayList(guardia.obtenerEmergenciasActivas());
                tablaEmergencias.setItems(emergencias);
            } catch (Exception e) {
                DialogosUtil.mostrarError("Error al cargar emergencias: " + e.getMessage());
            }
        }
    }
    
    private void limpiarCamposEmergencia() {
        txtIdEmergencia.clear();
        txtNuevoEstado.clear();
    }
}

