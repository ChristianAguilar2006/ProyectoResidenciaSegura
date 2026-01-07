package com.residencial.gui.controladores;

import com.residencial.gui.util.DialogosUtil;
import com.residencial.gui.util.Navegacion;
import com.residencial.modelo.Residente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ResidenteController implements Initializable {
    
    @FXML private Label lblNombre;
    @FXML private Label lblCorreo;
    @FXML private Label lblDepartamento;
    @FXML private Label lblBloque;
    
    @FXML private TextField txtTipoServicio;
    @FXML private TextField txtMonto;
    @FXML private TextField txtFechaVencimiento;
    
    @FXML private TextArea txtDescripcionPedido;
    @FXML private TextField txtTipoPedido;
    
    @FXML private TextField txtTipoEmergencia;
    @FXML private TextArea txtDescripcionEmergencia;
    @FXML private TextField txtUbicacionEmergencia;
    @FXML private TextField txtPrioridad;
    
    @FXML private TableView tablaPagos;
    @FXML private TableView tablaPedidos;
    @FXML private TableView tablaAvisos;
    
    private Residente residente;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTablas();
    }
    
    public void setResidente(Residente residente) {
        this.residente = residente;
        cargarDatosPerfil();
        cargarPagos();
        cargarPedidos();
        cargarAvisos();
    }
    
    private void configurarTablas() {
        if (tablaPagos != null) {
            TableColumn colIdPago = new TableColumn("ID");
            colIdPago.setCellValueFactory(new PropertyValueFactory<>("idPago"));
            TableColumn colTipo = new TableColumn("Tipo");
            colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoServicio"));
            TableColumn colMonto = new TableColumn("Monto");
            colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
            TableColumn colEstado = new TableColumn("Estado");
            colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            tablaPagos.getColumns().addAll(colIdPago, colTipo, colMonto, colEstado);
        }
        
        if (tablaPedidos != null) {
            TableColumn colIdPedido = new TableColumn("ID");
            colIdPedido.setCellValueFactory(new PropertyValueFactory<>("idPedido"));
            TableColumn colDescripcion = new TableColumn("Descripción");
            colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            TableColumn colEstado = new TableColumn("Estado");
            colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            tablaPedidos.getColumns().addAll(colIdPedido, colDescripcion, colEstado);
        }
        
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
        if (lblNombre != null) lblNombre.setText(residente.getNombre());
        if (lblCorreo != null) lblCorreo.setText(residente.getCorreo());
        if (lblDepartamento != null) lblDepartamento.setText(residente.getDepartamento());
        if (lblBloque != null) lblBloque.setText(residente.getBloque());
    }
    
    @FXML
    private void pagarServicio() {
        try {
            String tipo = txtTipoServicio.getText().trim();
            String montoStr = txtMonto.getText().trim();
            String fecha = txtFechaVencimiento.getText().trim();
            
            if (tipo.isEmpty() || montoStr.isEmpty() || fecha.isEmpty()) {
                DialogosUtil.mostrarError("Complete todos los campos");
                return;
            }
            
            residente.pagarServicioGUI(tipo, montoStr, fecha);
            DialogosUtil.mostrarExito("Pago registrado correctamente");
            limpiarCamposPago();
            cargarPagos();
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    @FXML
    private void crearPedido() {
        try {
            String descripcion = txtDescripcionPedido.getText().trim();
            String tipo = txtTipoPedido.getText().trim();
            
            if (descripcion.isEmpty() || tipo.isEmpty()) {
                DialogosUtil.mostrarError("Complete todos los campos");
                return;
            }
            
            residente.crearPedidoGUI(descripcion, tipo);
            DialogosUtil.mostrarExito("Pedido creado correctamente");
            limpiarCamposPedido();
            cargarPedidos();
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    @FXML
    private void activarEmergencia() {
        try {
            String tipo = txtTipoEmergencia.getText().trim();
            String descripcion = txtDescripcionEmergencia.getText().trim();
            String ubicacion = txtUbicacionEmergencia.getText().trim();
            String prioridad = txtPrioridad.getText().trim();
            
            if (tipo.isEmpty() || descripcion.isEmpty() || ubicacion.isEmpty() || prioridad.isEmpty()) {
                DialogosUtil.mostrarError("Complete todos los campos");
                return;
            }
            
            residente.activarEmergenciaGUI(tipo, descripcion, ubicacion, prioridad);
            DialogosUtil.mostrarExito("Emergencia activada correctamente");
            limpiarCamposEmergencia();
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    @FXML
    private void actualizarPerfil() {
        try {
            String nombre = lblNombre.getText();
            String telefono = "";
            
            TextInputDialog dialog = new TextInputDialog(telefono);
            dialog.setTitle("Actualizar Perfil");
            dialog.setHeaderText("Actualizar Teléfono");
            dialog.setContentText("Nuevo teléfono:");
            
            String nuevoTelefono = dialog.showAndWait().orElse("");
            if (!nuevoTelefono.isEmpty()) {
                residente.actualizarPerfilGUI(nombre, nuevoTelefono);
                DialogosUtil.mostrarExito("Perfil actualizado correctamente");
                cargarDatosPerfil();
            }
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    @FXML
    private void cerrarSesion() {
        if (DialogosUtil.mostrarConfirmacion("Cerrar Sesión", "¿Desea cerrar sesión?")) {
            lblNombre.getScene().getWindow().hide();
            Navegacion.abrirVentanaLogin();
        }
    }
    
    private void cargarPagos() {
        if (tablaPagos != null) {
            try {
                ObservableList pagos = FXCollections.observableArrayList(residente.obtenerPagos());
                tablaPagos.setItems(pagos);
            } catch (Exception e) {
                DialogosUtil.mostrarError("Error al cargar pagos: " + e.getMessage());
            }
        }
    }
    
    private void cargarPedidos() {
        if (tablaPedidos != null) {
            try {
                ObservableList pedidos = FXCollections.observableArrayList(residente.obtenerPedidos());
                tablaPedidos.setItems(pedidos);
            } catch (Exception e) {
                DialogosUtil.mostrarError("Error al cargar pedidos: " + e.getMessage());
            }
        }
    }
    
    private void cargarAvisos() {
        if (tablaAvisos != null) {
            try {
                ObservableList avisos = FXCollections.observableArrayList(residente.obtenerAvisos());
                tablaAvisos.setItems(avisos);
            } catch (Exception e) {
                DialogosUtil.mostrarError("Error al cargar avisos: " + e.getMessage());
            }
        }
    }
    
    private void limpiarCamposPago() {
        txtTipoServicio.clear();
        txtMonto.clear();
        txtFechaVencimiento.clear();
    }
    
    private void limpiarCamposPedido() {
        txtDescripcionPedido.clear();
        txtTipoPedido.clear();
    }
    
    private void limpiarCamposEmergencia() {
        txtTipoEmergencia.clear();
        txtDescripcionEmergencia.clear();
        txtUbicacionEmergencia.clear();
        txtPrioridad.clear();
    }
}

