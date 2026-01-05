package com.residencial.modelo;

import com.residencial.dao.EmergenciaDAO;
import java.util.List;
import java.util.Scanner;

public class Guardia extends Usuario {
    
    private EmergenciaDAO emergenciaDAO = new EmergenciaDAO();
    private Scanner scanner = new Scanner(System.in);
    
    public Guardia() {
        super();
    }
    
    public Guardia(String nombre, String correo, String contraseña) {
        super(nombre, correo, contraseña, "GUARDIA", "SEGURIDAD", "ENTRADA");
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
                    System.out.println("Descripción: " + e.getDescripcion());
                    System.out.println("Ubicación: " + e.getUbicacion());
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
}

