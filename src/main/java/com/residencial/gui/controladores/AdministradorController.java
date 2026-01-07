package com.residencial.gui.controladores;

import com.residencial.gui.util.DialogosUtil;
import com.residencial.gui.util.Navegacion;
import com.residencial.modelo.Administrador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AdministradorController implements Initializable {
    
    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtCorreoUsuario;
    @FXML private TextField txtContrasenaUsuario;
    @FXML private TextField txtRolUsuario;
    @FXML private TextField txtDepartamentoUsuario;
    @FXML private TextField txtBloqueUsuario;
    
    @FXML private TextField txtTituloAviso;
    @FXML private TextArea txtMensajeAviso;
    @FXML private TextField txtTipoAviso;
    
    @FXML private TableView tablaAvisos;
    @FXML private TableView tablaUsuarios;
    
    private Administrador administrador;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTablas();
    }
    
    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
        cargarDatosPerfil();
        cargarAvisos();
    }
    
    private void configurarTablas() {
        if (tablaAvisos != null) {
            TableColumn colTitulo = new TableColumn("Título");
            colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            TableColumn colTipo = new TableColumn("Tipo");
            colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            TableColumn colFecha = new TableColumn("Fecha");
            colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaPublicacion"));
            tablaAvisos.getColumns().addAll(colTitulo, colTipo, colFecha);
        }
    }
    
    private void cargarDatosPerfil() {
        if (lblNombre != null) lblNombre.setText(administrador.getNombre());
        if (lblCorreo != null) lblCorreo.setText(administrador.getCorreo());
    }
    
    @FXML
    private void crearUsuario() {
        try {
            String nombre = txtNombreUsuario.getText().trim();
            String correo = txtCorreoUsuario.getText().trim();
            String contrasena = txtContrasenaUsuario.getText().trim();
            String rol = txtRolUsuario.getText().trim();
            String departamento = txtDepartamentoUsuario.getText().trim();
            String bloque = txtBloqueUsuario.getText().trim();
            
            if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol.isEmpty()) {
                DialogosUtil.mostrarError("Complete todos los campos obligatorios");
                return;
            }
            
            administrador.crearUsuarioGUI(nombre, correo, contrasena, rol, departamento, bloque);
            DialogosUtil.mostrarExito("Usuario creado correctamente");
            limpiarCamposUsuario();
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    @FXML
    private void crearAviso() {
        try {
            String titulo = txtTituloAviso.getText().trim();
            String mensaje = txtMensajeAviso.getText().trim();
            String tipo = txtTipoAviso.getText().trim();
            
            if (titulo.isEmpty() || mensaje.isEmpty() || tipo.isEmpty()) {
                DialogosUtil.mostrarError("Complete todos los campos");
                return;
            }
            
            administrador.crearAvisoGUI(titulo, mensaje, tipo);
            DialogosUtil.mostrarExito("Aviso creado correctamente");
            limpiarCamposAviso();
            cargarAvisos();
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    @FXML
    private void verReportes() {
        DialogosUtil.mostrarAlerta("Reportes", "Funcionalidad en desarrollo", Alert.AlertType.INFORMATION);
    }
    
    @FXML
    private void cerrarSesion() {
        if (DialogosUtil.mostrarConfirmacion("Cerrar Sesión", "¿Desea cerrar sesión?")) {
            lblNombre.getScene().getWindow().hide();
            Navegacion.abrirVentanaLogin();
        }
    }
    
    private void cargarAvisos() {
        if (tablaAvisos != null) {
            try {
                ObservableList avisos = FXCollections.observableArrayList(administrador.obtenerAvisos());
                tablaAvisos.setItems(avisos);
            } catch (Exception e) {
                DialogosUtil.mostrarError("Error al cargar avisos: " + e.getMessage());
            }
        }
    }
    
    private void limpiarCamposUsuario() {
        txtNombreUsuario.clear();
        txtCorreoUsuario.clear();
        txtContrasenaUsuario.clear();
        txtRolUsuario.clear();
        txtDepartamentoUsuario.clear();
        txtBloqueUsuario.clear();
    }
    
    private void limpiarCamposAviso() {
        txtTituloAviso.clear();
        txtMensajeAviso.clear();
        txtTipoAviso.clear();
    }
}

