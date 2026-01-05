package com.residencial.modelo;

import com.residencial.dao.*;
import java.util.List;
import java.util.Scanner;

public class Administrador extends Usuario {
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private AvisoDAO avisoDAO = new AvisoDAO();
    private Scanner scanner = new Scanner(System.in);
    
    public Administrador() {
        super();
    }
    
    public Administrador(String nombre, String correo, String contraseña) {
        super(nombre, correo, contraseña, "ADMINISTRADOR", "ADMIN", "ADMIN");
    }
    
    public void crearUsuario() {
        System.out.println("\n--- CREAR USUARIO ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();
        
        System.out.print("Rol (RESIDENTE, ADMINISTRADOR, GUARDIA): ");
        String rol = scanner.nextLine().toUpperCase();
        
        System.out.print("Departamento: ");
        String departamento = scanner.nextLine();
        
        System.out.print("Bloque: ");
        String bloque = scanner.nextLine();
        
        Usuario usuario = new Usuario(nombre, correo, contraseña, rol, departamento, bloque);
        
        try {
            if (usuarioDAO.crear(usuario)) {
                System.out.println("\nUsuario creado correctamente");
            } else {
                System.out.println("\nError al crear el usuario");
            }
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    public void crearAviso() {
        System.out.println("\n--- CREAR AVISO ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        
        System.out.print("Mensaje: ");
        String mensaje = scanner.nextLine();
        
        System.out.print("Tipo (INFORMATIVO, URGENTE, MANTENIMIENTO, EVENTO, OTRO): ");
        String tipo = scanner.nextLine().toUpperCase();
        
        Aviso aviso = new Aviso(this.getIdUsuario(), titulo, mensaje, tipo);
        
        try {
            if (avisoDAO.crear(aviso)) {
                System.out.println("\nAviso creado correctamente");
            } else {
                System.out.println("\nError al crear el aviso");
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
    
    public void verReportes() {
        System.out.println("\n--- REPORTES ---");
        System.out.println("Funcionalidad en desarrollo");
        System.out.println("Aquí se mostrarían reportes de pagos, pedidos, emergencias, etc.");
    }
}

