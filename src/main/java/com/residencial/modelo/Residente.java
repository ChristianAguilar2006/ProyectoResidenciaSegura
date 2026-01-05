package com.residencial.modelo;

import com.residencial.dao.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Residente extends Usuario {
    
    private PagoDAO pagoDAO = new PagoDAO();
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private EmergenciaDAO emergenciaDAO = new EmergenciaDAO();
    private AvisoDAO avisoDAO = new AvisoDAO();
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
            System.out.println("❌ Error: " + e.getMessage());
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
            System.out.println("❌ Error: " + e.getMessage());
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
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}

