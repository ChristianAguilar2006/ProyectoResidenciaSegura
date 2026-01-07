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
import com.residencial.modelo.Emergencia;

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
        if (tablaEmergencias == null) return;
        
        tablaEmergencias.getColumns().clear();
        tablaEmergencias.getColumns().add(crearColumna("ID", "idEmergencia"));
        tablaEmergencias.getColumns().add(crearColumna("Tipo", "tipo"));
        tablaEmergencias.getColumns().add(crearColumna("Prioridad", "prioridad"));
        tablaEmergencias.getColumns().add(crearColumna("Estado", "estado"));
        tablaEmergencias.getColumns().add(crearColumna("Ubicación", "ubicacion"));
    }
    
    private TableColumn crearColumna(String nombre, String propiedad) {
        TableColumn columna = new TableColumn(nombre);
        columna.setCellValueFactory(new PropertyValueFactory<>(propiedad));
        return columna;
    }
    
    private void cargarDatosPerfil() {
        if (lblNombre != null) lblNombre.setText(guardia.getNombre());
        if (lblCorreo != null) lblCorreo.setText(guardia.getCorreo());
    }
    
    @FXML
    private void atenderEmergencia() {
        String idTexto = txtIdEmergencia.getText().trim();
        String nuevoEstado = txtNuevoEstado.getText().trim();
        
        if (!validarCamposAtenderEmergencia(idTexto, nuevoEstado)) {
            return;
        }
        
        try {
            int idEmergencia = Integer.parseInt(idTexto);
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
    
    private boolean validarCamposAtenderEmergencia(String idTexto, String nuevoEstado) {
        if (idTexto.isEmpty() || nuevoEstado.isEmpty()) {
            DialogosUtil.mostrarError("Complete todos los campos");
            return false;
        }
        
        if (!esEstadoEmergenciaValido(nuevoEstado)) {
            DialogosUtil.mostrarError("Estado inválido. Use: EN_ATENCION o RESUELTA");
            return false;
        }
        
        return true;
    }
    
    private boolean esEstadoEmergenciaValido(String estado) {
        return estado.matches("EN_ATENCION|RESUELTA");
    }
    
    @FXML
    private void verDetalleEmergencia() {
        Emergencia emergenciaSeleccionada = obtenerEmergenciaSeleccionada();
        if (emergenciaSeleccionada == null) {
            DialogosUtil.mostrarError("Seleccione una emergencia para ver los detalles");
            return;
        }
        
        String detalle = crearDetalleEmergencia(emergenciaSeleccionada);
        DialogosUtil.mostrarAlerta("Detalle de la Emergencia", detalle, Alert.AlertType.INFORMATION);
    }
    
    private Emergencia obtenerEmergenciaSeleccionada() {
        return (Emergencia) tablaEmergencias.getSelectionModel().getSelectedItem();
    }
    
    private String crearDetalleEmergencia(Emergencia emergencia) {
        return "ID: " + emergencia.getIdEmergencia() + "\n\n" +
               "Tipo: " + emergencia.getTipo() + "\n" +
               "Prioridad: " + emergencia.getPrioridad() + "\n" +
               "Estado: " + emergencia.getEstado() + "\n" +
               "Ubicación: " + emergencia.getUbicacion() + "\n\n" +
               "Descripción:\n" + emergencia.getDescripcion();
    }
    
    @FXML
    private void refrescarEmergencias() {
        cargarEmergencias();
        DialogosUtil.mostrarExito("Emergencias actualizadas");
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

