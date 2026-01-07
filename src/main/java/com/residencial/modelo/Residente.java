package com.residencial.modelo;

import com.residencial.dao.*;
import com.residencial.interfaces.IMenuResidente;
import com.residencial.dao.UsuarioDAO;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Residente extends Usuario implements IMenuResidente {
    
    private PagoDAO pagoDAO = new PagoDAO();
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private EmergenciaDAO emergenciaDAO = new EmergenciaDAO();
    private AvisoDAO avisoDAO = new AvisoDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Scanner scanner = new Scanner(System.in);
    
    public Residente() {
        super();
    }
    
    public Residente(String nombre, String correo, String contraseña, 
                    String departamento, String bloque) {
        super(nombre, correo, contraseña, "RESIDENTE", departamento, bloque);
    }
    
    public void pagarServicio() {
        System.out.println("\n--- PAGAR SERVICIO ---");
        System.out.println("Tipos de servicio: LUZ, AGUA, ALICUOTA, GAS, OTRO");
        System.out.print("Tipo de servicio: ");
        String tipo = scanner.nextLine().toUpperCase();
        
        System.out.print("Monto: ");
        BigDecimal monto;
        try {
            monto = scanner.nextBigDecimal();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Error: Debe ingresar un numero valido");
            return;
        }
        
        System.out.print("Fecha de vencimiento (YYYY-MM-DD): ");
        String fechaStr = scanner.nextLine();
        Date fechaVencimiento;
        try {
            fechaVencimiento = Date.valueOf(fechaStr);
        } catch (Exception e) {
            System.out.println("Error: Formato de fecha invalido. Use YYYY-MM-DD");
            return;
        }
        
        Pago pago = new Pago(this.getIdUsuario(), tipo, monto, fechaVencimiento);
        
        try {
            if (pagoDAO.crear(pago)) {
                System.out.println("\nPago registrado correctamente");
            } else {
                System.out.println("\nError al registrar el pago");
            }
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    public void verHistorialPagos() {
        System.out.println("\n--- MIS PAGOS ---");
        try {
            List<Pago> pagos = pagoDAO.obtenerPorUsuario(this.getIdUsuario());
            
            if (pagos.isEmpty()) {
                System.out.println("No tienes pagos registrados");
            } else {
                for (Pago pago : pagos) {
                    System.out.println("ID: " + pago.getIdPago() + 
                                     " | Tipo: " + pago.getTipoServicio() + 
                                     " | Monto: $" + pago.getMonto() + 
                                     " | Estado: " + pago.getEstado());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void crearPedido() {
        System.out.println("\n--- CREAR PEDIDO ---");
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        
        System.out.print("Tipo (ENCARGO, SERVICIO, PRODUCTO, OTRO): ");
        String tipo = scanner.nextLine().toUpperCase();
        
        Pedido pedido = new Pedido(this.getIdUsuario(), descripcion, tipo);
        
        try {
            if (pedidoDAO.crear(pedido)) {
                System.out.println("\nPedido creado correctamente");
            } else {
                System.out.println("\nError al crear el pedido");
            }
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    public void verEstadoPedidos() {
        System.out.println("\n--- MIS PEDIDOS ---");
        try {
            List<Pedido> pedidos = pedidoDAO.obtenerPorUsuario(this.getIdUsuario());
            
            if (pedidos.isEmpty()) {
                System.out.println("No tienes pedidos registrados");
            } else {
                for (Pedido pedido : pedidos) {
                    System.out.println("ID: " + pedido.getIdPedido() + 
                                     " | Descripción: " + pedido.getDescripcion() + 
                                     " | Estado: " + pedido.getEstado());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void activarEmergencia() {
        System.out.println("\n--- ACTIVAR EMERGENCIA ---");
        System.out.print("Tipo (MEDICA, INCENDIO, ROBO, ACCIDENTE, FALLA_ELECTRICA, FALLA_PLOMERIA, OTRA): ");
        String tipo = scanner.nextLine().toUpperCase();
        
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        
        System.out.print("Ubicación: ");
        String ubicacion = scanner.nextLine();
        
        System.out.print("Prioridad (BAJA, MEDIA, ALTA, CRITICA): ");
        String prioridad = scanner.nextLine().toUpperCase();
        
        Emergencia emergencia = new Emergencia(this.getIdUsuario(), tipo, descripcion, ubicacion);
        emergencia.setPrioridad(prioridad);
        
        try {
            if (emergenciaDAO.crear(emergencia)) {
                System.out.println("\nEmergencia activada correctamente");
            } else {
                System.out.println("\nError al activar la emergencia");
            }
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    public void verAvisos() {
        System.out.println("\n--- AVISOS ---");
        try {
            List<Aviso> avisos = avisoDAO.obtenerActivos();
            
            if (avisos.isEmpty()) {
                System.out.println("No hay avisos disponibles");
            } else {
                for (Aviso aviso : avisos) {
                    System.out.println("\n[" + aviso.getTipo() + "] " + aviso.getTitulo());
                    System.out.println(aviso.getMensaje());
                    System.out.println("Fecha: " + aviso.getFechaPublicacion());
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ RESIDENTE ---");
        System.out.println("Usuario: " + this.getNombre());
        System.out.println("1. Ver Perfil");
        System.out.println("2. Actualizar Perfil");
        System.out.println("3. Pagar Servicio");
        System.out.println("4. Ver Mis Pagos");
        System.out.println("5. Crear Pedido");
        System.out.println("6. Ver Mis Pedidos");
        System.out.println("7. Activar Emergencia");
        System.out.println("8. Ver Avisos");
        System.out.println("9. Cerrar Sesión");
        System.out.println("10. Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    @Override
    public void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                verPerfil();
                break;
            case 2:
                actualizarPerfil();
                break;
            case 3:
                pagarServicio();
                break;
            case 4:
                verHistorialPagos();
                break;
            case 5:
                crearPedido();
                break;
            case 6:
                verEstadoPedidos();
                break;
            case 7:
                activarEmergencia();
                break;
            case 8:
                verAvisos();
                break;
            case 9:
                break;
            case 10:
                System.out.println("\n¡Hasta luego!");
                System.exit(0);
                break;
            default:
                System.out.println("Opción inválida");
        }
    }
    
    private void verPerfil() {
        System.out.println("\n--- MI PERFIL ---");
        System.out.println("ID: " + this.getIdUsuario());
        System.out.println("Nombre: " + this.getNombre());
        System.out.println("Correo: " + this.getCorreo());
        System.out.println("Rol: " + this.getRol());
        System.out.println("Departamento: " + this.getDepartamento());
        System.out.println("Bloque: " + this.getBloque());
        System.out.println("Teléfono: " + (this.getTelefono() != null ? this.getTelefono() : "No registrado"));
    }
    
    private void actualizarPerfil() {
        System.out.println("\n--- ACTUALIZAR PERFIL ---");
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = scanner.nextLine();
        
        if (!nombre.isEmpty()) {
            this.setNombre(nombre);
        }
        
        System.out.print("Nuevo telefono (Enter para mantener): ");
        String telefono = scanner.nextLine();
        if (!telefono.isEmpty()) {
            this.setTelefono(telefono);
        }
        
        try {
            boolean exito = usuarioDAO.actualizar(this);
            
            if (exito) {
                System.out.println("\nPerfil actualizado correctamente");
            } else {
                System.out.println("\nError al actualizar el perfil");
            }
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    public void pagarServicioGUI(String tipo, String montoTexto, String fechaTexto) throws Exception {
        BigDecimal monto = convertirAMonto(montoTexto);
        Date fechaVencimiento = convertirAFecha(fechaTexto);
        Pago nuevoPago = crearPago(tipo, monto, fechaVencimiento);
        guardarPago(nuevoPago);
    }
    
    private BigDecimal convertirAMonto(String montoTexto) {
        return new BigDecimal(montoTexto);
    }
    
    private Date convertirAFecha(String fechaTexto) {
        return Date.valueOf(fechaTexto);
    }
    
    private Pago crearPago(String tipo, BigDecimal monto, Date fechaVencimiento) {
        return new Pago(this.getIdUsuario(), tipo, monto, fechaVencimiento);
    }
    
    private void guardarPago(Pago pago) throws Exception {
        if (!pagoDAO.crear(pago)) {
            throw new Exception("Error al registrar el pago");
        }
    }
    
    public void crearPedidoGUI(String descripcion, String tipo) throws Exception {
        Pedido nuevoPedido = crearPedido(descripcion, tipo);
        guardarPedido(nuevoPedido);
    }
    
    private Pedido crearPedido(String descripcion, String tipo) {
        return new Pedido(this.getIdUsuario(), descripcion, tipo);
    }
    
    private void guardarPedido(Pedido pedido) throws Exception {
        if (!pedidoDAO.crear(pedido)) {
            throw new Exception("Error al crear el pedido");
        }
    }
    
    public void activarEmergenciaGUI(String tipo, String descripcion, String ubicacion, String prioridad) throws Exception {
        Emergencia nuevaEmergencia = crearEmergencia(tipo, descripcion, ubicacion, prioridad);
        guardarEmergencia(nuevaEmergencia);
    }
    
    private Emergencia crearEmergencia(String tipo, String descripcion, String ubicacion, String prioridad) {
        Emergencia emergencia = new Emergencia(this.getIdUsuario(), tipo, descripcion, ubicacion);
        emergencia.setPrioridad(prioridad);
        return emergencia;
    }
    
    private void guardarEmergencia(Emergencia emergencia) throws Exception {
        if (!emergenciaDAO.crear(emergencia)) {
            throw new Exception("Error al activar la emergencia");
        }
    }
    
    public void actualizarPerfilGUI(String nombre, String telefono) throws Exception {
        actualizarDatosPersonales(nombre, telefono);
        guardarCambiosPerfil();
    }
    
    private void actualizarDatosPersonales(String nombre, String telefono) {
        this.setNombre(nombre);
        this.setTelefono(telefono);
    }
    
    private void guardarCambiosPerfil() throws Exception {
        if (!usuarioDAO.actualizar(this)) {
            throw new Exception("Error al actualizar el perfil");
        }
    }
    
    public List<Pago> obtenerPagos() throws Exception {
        return pagoDAO.obtenerPorUsuario(this.getIdUsuario());
    }
    
    public List<Pedido> obtenerPedidos() throws Exception {
        return pedidoDAO.obtenerPorUsuario(this.getIdUsuario());
    }
    
    public List<Aviso> obtenerAvisos() throws Exception {
        return avisoDAO.obtenerActivos();
    }
}

