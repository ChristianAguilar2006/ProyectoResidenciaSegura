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
import com.residencial.modelo.Aviso;
import com.residencial.modelo.Usuario;

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
        cargarUsuarios();
    }
    
    private void configurarTablas() {
        configurarTablaAvisos();
        configurarTablaUsuarios();
    }
    
    private void configurarTablaAvisos() {
        if (tablaAvisos == null) return;
        
        tablaAvisos.getColumns().clear();
        tablaAvisos.getColumns().add(crearColumna("ID", "idAviso"));
        tablaAvisos.getColumns().add(crearColumna("Título", "titulo"));
        tablaAvisos.getColumns().add(crearColumna("Tipo", "tipo"));
        tablaAvisos.getColumns().add(crearColumna("Fecha", "fechaPublicacion"));
    }
    
    private void configurarTablaUsuarios() {
        if (tablaUsuarios == null) return;
        
        tablaUsuarios.getColumns().clear();
        tablaUsuarios.getColumns().add(crearColumna("ID", "idUsuario"));
        tablaUsuarios.getColumns().add(crearColumna("Nombre", "nombre"));
        tablaUsuarios.getColumns().add(crearColumna("Correo", "correo"));
        tablaUsuarios.getColumns().add(crearColumna("Rol", "rol"));
        tablaUsuarios.getColumns().add(crearColumna("Departamento", "departamento"));
        tablaUsuarios.getColumns().add(crearColumna("Bloque", "bloque"));
    }
    
    private TableColumn crearColumna(String nombre, String propiedad) {
        TableColumn columna = new TableColumn(nombre);
        columna.setCellValueFactory(new PropertyValueFactory<>(propiedad));
        return columna;
    }
    
    private void cargarDatosPerfil() {
        if (lblNombre != null) lblNombre.setText(administrador.getNombre());
        if (lblCorreo != null) lblCorreo.setText(administrador.getCorreo());
    }
    
    @FXML
    private void crearUsuario() {
        String nombre = txtNombreUsuario.getText().trim();
        String correo = txtCorreoUsuario.getText().trim();
        String contrasena = txtContrasenaUsuario.getText().trim();
        String rol = txtRolUsuario.getText().trim();
        String departamento = txtDepartamentoUsuario.getText().trim();
        String bloque = txtBloqueUsuario.getText().trim();
        
        if (!validarCamposUsuario(nombre, correo, contrasena, rol)) {
            return;
        }
        
        try {
            administrador.crearUsuarioGUI(nombre, correo, contrasena, rol, departamento, bloque);
            DialogosUtil.mostrarExito("Usuario creado correctamente");
            limpiarCamposUsuario();
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    private boolean validarCamposUsuario(String nombre, String correo, String contrasena, String rol) {
        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol.isEmpty()) {
            DialogosUtil.mostrarError("Complete todos los campos obligatorios");
            return false;
        }
        
        if (!esRolValido(rol)) {
            DialogosUtil.mostrarError("Rol inválido. Use: RESIDENTE, ADMINISTRADOR o GUARDIA");
            return false;
        }
        
        if (!esCorreoValido(correo)) {
            DialogosUtil.mostrarError("Formato de correo inválido");
            return false;
        }
        
        return true;
    }
    
    private boolean esRolValido(String rol) {
        return rol.matches("RESIDENTE|ADMINISTRADOR|GUARDIA");
    }
    
    private boolean esCorreoValido(String correo) {
        return correo.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    @FXML
    private void crearAviso() {
        String titulo = txtTituloAviso.getText().trim();
        String mensaje = txtMensajeAviso.getText().trim();
        String tipo = txtTipoAviso.getText().trim();
        
        if (!validarCamposAviso(titulo, mensaje, tipo)) {
            return;
        }
        
        try {
            administrador.crearAvisoGUI(titulo, mensaje, tipo);
            DialogosUtil.mostrarExito("Aviso creado correctamente");
            limpiarCamposAviso();
            cargarAvisos();
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    private boolean validarCamposAviso(String titulo, String mensaje, String tipo) {
        if (titulo.isEmpty() || mensaje.isEmpty() || tipo.isEmpty()) {
            DialogosUtil.mostrarError("Complete todos los campos");
            return false;
        }
        
        if (!esTipoAvisoValido(tipo)) {
            DialogosUtil.mostrarError("Tipo inválido. Use: INFORMATIVO, URGENTE, MANTENIMIENTO, EVENTO u OTRO");
            return false;
        }
        
        return true;
    }
    
    private boolean esTipoAvisoValido(String tipo) {
        return tipo.matches("INFORMATIVO|URGENTE|MANTENIMIENTO|EVENTO|OTRO");
    }
    
    @FXML
    private void eliminarAviso() {
        Aviso avisoSeleccionado = obtenerAvisoSeleccionado();
        if (avisoSeleccionado == null) {
            DialogosUtil.mostrarError("Seleccione un aviso para eliminar");
            return;
        }
        
        String mensajeConfirmacion = "¿Está seguro de eliminar el aviso: " + avisoSeleccionado.getTitulo() + "?";
        if (!DialogosUtil.mostrarConfirmacion("Eliminar Aviso", mensajeConfirmacion)) {
            return;
        }
        
        try {
            administrador.eliminarAvisoGUI(avisoSeleccionado.getIdAviso());
            DialogosUtil.mostrarExito("Aviso eliminado correctamente");
            cargarAvisos();
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    @FXML
    private void verDetalleAviso() {
        Aviso avisoSeleccionado = obtenerAvisoSeleccionado();
        if (avisoSeleccionado == null) {
            DialogosUtil.mostrarError("Seleccione un aviso para ver los detalles");
            return;
        }
        
        String detalle = crearDetalleAviso(avisoSeleccionado);
        DialogosUtil.mostrarAlerta("Detalle del Aviso", detalle, Alert.AlertType.INFORMATION);
    }
    
    private Aviso obtenerAvisoSeleccionado() {
        return (Aviso) tablaAvisos.getSelectionModel().getSelectedItem();
    }
    
    private String crearDetalleAviso(Aviso aviso) {
        return "Título: " + aviso.getTitulo() + "\n\n" +
               "Mensaje: " + aviso.getMensaje() + "\n\n" +
               "Tipo: " + aviso.getTipo() + "\n" +
               "Fecha: " + aviso.getFechaPublicacion();
    }
    
    @FXML
    private void refrescarAvisos() {
        cargarAvisos();
        DialogosUtil.mostrarExito("Avisos actualizados");
    }
    
    @FXML
    private void refrescarUsuarios() {
        cargarUsuarios();
        DialogosUtil.mostrarExito("Usuarios actualizados");
    }
    
    @FXML
    private void verReportes() {
        try {
            int totalUsuarios = administrador.obtenerUsuarios().size();
            int totalAvisos = administrador.obtenerAvisos().size();
            String reporte = crearTextoReporte(totalUsuarios, totalAvisos);
            DialogosUtil.mostrarAlerta("Reportes", reporte, Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error al generar reportes: " + e.getMessage());
        }
    }
    
    private String crearTextoReporte(int totalUsuarios, int totalAvisos) {
        return "REPORTES DEL SISTEMA\n\n" +
               "Total de Usuarios Activos: " + totalUsuarios + "\n" +
               "Total de Avisos Activos: " + totalAvisos + "\n\n" +
               "Funcionalidad completa en desarrollo...";
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
    
    private void cargarUsuarios() {
        if (tablaUsuarios != null) {
            try {
                ObservableList usuarios = FXCollections.observableArrayList(administrador.obtenerUsuarios());
                tablaUsuarios.setItems(usuarios);
            } catch (Exception e) {
                DialogosUtil.mostrarError("Error al cargar usuarios: " + e.getMessage());
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

