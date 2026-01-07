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
import com.residencial.modelo.Aviso;

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
        configurarTablaPagos();
        configurarTablaPedidos();
        configurarTablaAvisos();
    }
    
    private void configurarTablaPagos() {
        if (tablaPagos == null) return;
        
        tablaPagos.getColumns().clear();
        tablaPagos.getColumns().add(crearColumna("ID", "idPago"));
        tablaPagos.getColumns().add(crearColumna("Tipo", "tipoServicio"));
        tablaPagos.getColumns().add(crearColumna("Monto", "monto"));
        tablaPagos.getColumns().add(crearColumna("Estado", "estado"));
    }
    
    private void configurarTablaPedidos() {
        if (tablaPedidos == null) return;
        
        tablaPedidos.getColumns().clear();
        tablaPedidos.getColumns().add(crearColumna("ID", "idPedido"));
        tablaPedidos.getColumns().add(crearColumna("Descripción", "descripcion"));
        tablaPedidos.getColumns().add(crearColumna("Estado", "estado"));
    }
    
    private void configurarTablaAvisos() {
        if (tablaAvisos == null) return;
        
        tablaAvisos.getColumns().clear();
        tablaAvisos.getColumns().add(crearColumna("Título", "titulo"));
        tablaAvisos.getColumns().add(crearColumna("Tipo", "tipo"));
        tablaAvisos.getColumns().add(crearColumna("Fecha", "fechaPublicacion"));
    }
    
    private TableColumn crearColumna(String nombre, String propiedad) {
        TableColumn columna = new TableColumn(nombre);
        columna.setCellValueFactory(new PropertyValueFactory<>(propiedad));
        return columna;
    }
    
    private void cargarDatosPerfil() {
        if (lblNombre != null) lblNombre.setText(residente.getNombre());
        if (lblCorreo != null) lblCorreo.setText(residente.getCorreo());
        if (lblDepartamento != null) lblDepartamento.setText(residente.getDepartamento());
        if (lblBloque != null) lblBloque.setText(residente.getBloque());
    }
    
    @FXML
    private void pagarServicio() {
        String tipo = txtTipoServicio.getText().trim();
        String monto = txtMonto.getText().trim();
        String fecha = txtFechaVencimiento.getText().trim();
        
        if (!validarCamposPago(tipo, monto, fecha)) {
            return;
        }
        
        try {
            residente.pagarServicioGUI(tipo, monto, fecha);
            DialogosUtil.mostrarExito("Pago registrado correctamente");
            limpiarCamposPago();
            cargarPagos();
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    private boolean validarCamposPago(String tipo, String monto, String fecha) {
        if (tipo.isEmpty() || monto.isEmpty() || fecha.isEmpty()) {
            DialogosUtil.mostrarError("Complete todos los campos");
            return false;
        }
        
        if (!esTipoServicioValido(tipo)) {
            DialogosUtil.mostrarError("Tipo inválido. Use: LUZ, AGUA, ALICUOTA, GAS u OTRO");
            return false;
        }
        
        if (!esNumeroValido(monto)) {
            DialogosUtil.mostrarError("El monto debe ser un número válido");
            return false;
        }
        
        if (!esFechaValida(fecha)) {
            DialogosUtil.mostrarError("Formato de fecha inválido. Use: YYYY-MM-DD");
            return false;
        }
        
        return true;
    }
    
    private boolean esTipoServicioValido(String tipo) {
        return tipo.matches("LUZ|AGUA|ALICUOTA|GAS|OTRO");
    }
    
    private boolean esNumeroValido(String numero) {
        try {
            Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean esFechaValida(String fecha) {
        return fecha.matches("\\d{4}-\\d{2}-\\d{2}");
    }
    
    @FXML
    private void crearPedido() {
        String descripcion = txtDescripcionPedido.getText().trim();
        String tipo = txtTipoPedido.getText().trim();
        
        if (!validarCamposPedido(descripcion, tipo)) {
            return;
        }
        
        try {
            residente.crearPedidoGUI(descripcion, tipo);
            DialogosUtil.mostrarExito("Pedido creado correctamente");
            limpiarCamposPedido();
            cargarPedidos();
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    private boolean validarCamposPedido(String descripcion, String tipo) {
        if (descripcion.isEmpty() || tipo.isEmpty()) {
            DialogosUtil.mostrarError("Complete todos los campos");
            return false;
        }
        
        if (!esTipoPedidoValido(tipo)) {
            DialogosUtil.mostrarError("Tipo inválido. Use: ENCARGO, SERVICIO, PRODUCTO u OTRO");
            return false;
        }
        
        return true;
    }
    
    private boolean esTipoPedidoValido(String tipo) {
        return tipo.matches("ENCARGO|SERVICIO|PRODUCTO|OTRO");
    }
    
    @FXML
    private void activarEmergencia() {
        String tipo = txtTipoEmergencia.getText().trim();
        String descripcion = txtDescripcionEmergencia.getText().trim();
        String ubicacion = txtUbicacionEmergencia.getText().trim();
        String prioridad = txtPrioridad.getText().trim();
        
        if (!validarCamposEmergencia(tipo, descripcion, ubicacion, prioridad)) {
            return;
        }
        
        try {
            residente.activarEmergenciaGUI(tipo, descripcion, ubicacion, prioridad);
            DialogosUtil.mostrarExito("Emergencia activada correctamente");
            limpiarCamposEmergencia();
        } catch (Exception e) {
            DialogosUtil.mostrarError("Error: " + e.getMessage());
        }
    }
    
    private boolean validarCamposEmergencia(String tipo, String descripcion, String ubicacion, String prioridad) {
        if (tipo.isEmpty() || descripcion.isEmpty() || ubicacion.isEmpty() || prioridad.isEmpty()) {
            DialogosUtil.mostrarError("Complete todos los campos");
            return false;
        }
        
        if (!esTipoEmergenciaValido(tipo)) {
            DialogosUtil.mostrarError("Tipo de emergencia inválido");
            return false;
        }
        
        if (!esPrioridadValida(prioridad)) {
            DialogosUtil.mostrarError("Prioridad inválida. Use: BAJA, MEDIA, ALTA o CRITICA");
            return false;
        }
        
        return true;
    }
    
    private boolean esTipoEmergenciaValido(String tipo) {
        return tipo.matches("MEDICA|INCENDIO|ROBO|ACCIDENTE|FALLA_ELECTRICA|FALLA_PLOMERIA|OTRA");
    }
    
    private boolean esPrioridadValida(String prioridad) {
        return prioridad.matches("BAJA|MEDIA|ALTA|CRITICA");
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
    private void refrescarPagos() {
        cargarPagos();
        DialogosUtil.mostrarExito("Pagos actualizados");
    }
    
    @FXML
    private void refrescarPedidos() {
        cargarPedidos();
        DialogosUtil.mostrarExito("Pedidos actualizados");
    }
    
    @FXML
    private void refrescarAvisos() {
        cargarAvisos();
        DialogosUtil.mostrarExito("Avisos actualizados");
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

