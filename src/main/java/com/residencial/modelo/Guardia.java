package com.residencial.modelo;

import com.residencial.dao.EmergenciaDAO;
import com.residencial.interfaces.IMenuGuardia;
import java.util.List;
import java.util.Scanner;

public class Guardia extends Usuario implements IMenuGuardia {
    
    private EmergenciaDAO emergenciaDAO = new EmergenciaDAO();
    private Scanner scanner = new Scanner(System.in);
    
    public Guardia() {
        super();
    }
    
    public Guardia(String nombre, String correo, String contrasena) {
        super(nombre, correo, contrasena, "GUARDIA", "SEGURIDAD", "ENTRADA");
    }
    
    public void verEmergenciasActivas() {
        System.out.println("\n--- EMERGENCIAS ACTIVAS ---");
        try {
            List<Emergencia> emergencias = emergenciaDAO.obtenerActivas();
            
            if (emergencias.isEmpty()) {
                System.out.println("No hay emergencias activas");
            } else {
                for (Emergencia e : emergencias) {
                    System.out.println("\nID: " + e.getIdEmergencia() + 
                                     " | Tipo: " + e.getTipo() + 
                                     " | Prioridad: " + e.getPrioridad());
                    System.out.println("Descripcion: " + e.getDescripcion());
                    System.out.println("Ubicacion: " + e.getUbicacion());
                    System.out.println("Estado: " + e.getEstado());
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public void atenderEmergencia() {
        System.out.println("\n--- ATENDER EMERGENCIA ---");
        System.out.print("ID de la emergencia: ");
        int idEmergencia;
        try {
            idEmergencia = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Error: Debe ingresar un numero valido");
            return;
        }
        
        System.out.print("Nuevo estado (EN_ATENCION, RESUELTA): ");
        String nuevoEstado = scanner.nextLine().toUpperCase();
        
        try {
            if (emergenciaDAO.actualizarEstado(idEmergencia, nuevoEstado, this.getIdUsuario())) {
                System.out.println("\nEmergencia actualizada correctamente");
            } else {
                System.out.println("\nError al actualizar la emergencia");
            }
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENU GUARDIA ---");
        System.out.println("Usuario: " + this.getNombre());
        System.out.println("1. Ver Perfil");
        System.out.println("2. Ver Emergencias Activas");
        System.out.println("3. Atender Emergencia");
        System.out.println("4. Cerrar Sesion");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opcion: ");
    }
    
    @Override
    public void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                verPerfil();
                break;
            case 2:
                verEmergenciasActivas();
                break;
            case 3:
                atenderEmergencia();
                break;
            case 4:
                break;
            case 5:
                System.out.println("\nHasta luego!");
                System.exit(0);
                break;
            default:
                System.out.println("Opcion invalida");
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
        System.out.println("Telefono: " + (this.getTelefono() != null ? this.getTelefono() : "No registrado"));
    }
    
}

