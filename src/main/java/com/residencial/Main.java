package com.residencial;

import com.residencial.dao.UsuarioDAO;
import com.residencial.interfaces.*;
import com.residencial.modelo.*;
import java.util.Scanner;

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static Usuario usuarioActual = null;
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  SISTEMA DE GESTIÓN RESIDENCIAL");
        System.out.println("========================================\n");
        
        boolean salir = false;
        
        while (!salir) {
            if (usuarioActual == null) {
                mostrarMenuPrincipal();
            } else {
                mostrarMenuUsuario();
            }
            
            int opcion = 0;
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Error: Debe ingresar un numero valido");
                continue;
            }
            
            if (usuarioActual == null) {
                switch (opcion) {
                    case 1:
                        iniciarSesion();
                        break;
                    case 2:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } else {
                String rol = usuarioActual.getRol();
                
                if (rol.equals("RESIDENTE")) {
                    Residente residente = convertirAResidente(usuarioActual);
                    IMenuResidente menu = residente;
                    menu.procesarOpcion(opcion);
                    if (opcion == 9) {
                        cerrarSesion();
                    }
                } else if (rol.equals("ADMINISTRADOR")) {
                    Administrador admin = convertirAAdministrador(usuarioActual);
                    IMenuAdministrador menu = admin;
                    menu.procesarOpcion(opcion);
                    if (opcion == 6) {
                        cerrarSesion();
                    }
                } else if (rol.equals("GUARDIA")) {
                    Guardia guardia = convertirAGuardia(usuarioActual);
                    IMenuGuardia menu = guardia;
                    menu.procesarOpcion(opcion);
                    if (opcion == 4) {
                        cerrarSesion();
                    }
                }
            }
        }
        
        System.out.println("\n¡Hasta luego!");
        scanner.close();
    }
    
    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Iniciar Sesión");
        System.out.println("2. Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    private static void mostrarMenuUsuario() {
        String rol = usuarioActual.getRol();
        
        if (rol.equals("RESIDENTE")) {
            Residente residente = convertirAResidente(usuarioActual);
            IMenuResidente menu = residente;
            menu.mostrarMenu();
        } else if (rol.equals("ADMINISTRADOR")) {
            Administrador admin = convertirAAdministrador(usuarioActual);
            IMenuAdministrador menu = admin;
            menu.mostrarMenu();
        } else if (rol.equals("GUARDIA")) {
            Guardia guardia = convertirAGuardia(usuarioActual);
            IMenuGuardia menu = guardia;
            menu.mostrarMenu();
        }
    }
    
    private static void iniciarSesion() {
        System.out.println("\n--- INICIAR SESIÓN ---");
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();
        
        try {
            usuarioActual = usuarioDAO.login(correo, contraseña);
            if (usuarioActual != null) {
                System.out.println("\nSesion iniciada correctamente");
                System.out.println("Bienvenido, " + usuarioActual.getNombre());
            } else {
                System.out.println("\nCredenciales incorrectas");
            }
        } catch (Exception e) {
            System.out.println("\nError al iniciar sesion: " + e.getMessage());
        }
    }
    
    private static void cerrarSesion() {
        usuarioActual = null;
        System.out.println("\nSesion cerrada");
    }
    
    private static Residente convertirAResidente(Usuario usuario) {
        Residente residente = new Residente();
        residente.setIdUsuario(usuario.getIdUsuario());
        residente.setNombre(usuario.getNombre());
        residente.setCorreo(usuario.getCorreo());
        residente.setRol(usuario.getRol());
        residente.setDepartamento(usuario.getDepartamento());
        residente.setBloque(usuario.getBloque());
        residente.setTelefono(usuario.getTelefono());
        return residente;
    }
    
    private static Administrador convertirAAdministrador(Usuario usuario) {
        Administrador admin = new Administrador();
        admin.setIdUsuario(usuario.getIdUsuario());
        admin.setNombre(usuario.getNombre());
        admin.setCorreo(usuario.getCorreo());
        admin.setRol(usuario.getRol());
        admin.setDepartamento(usuario.getDepartamento());
        admin.setBloque(usuario.getBloque());
        admin.setTelefono(usuario.getTelefono());
        return admin;
    }
    
    private static Guardia convertirAGuardia(Usuario usuario) {
        Guardia guardia = new Guardia();
        guardia.setIdUsuario(usuario.getIdUsuario());
        guardia.setNombre(usuario.getNombre());
        guardia.setCorreo(usuario.getCorreo());
        guardia.setRol(usuario.getRol());
        guardia.setDepartamento(usuario.getDepartamento());
        guardia.setBloque(usuario.getBloque());
        guardia.setTelefono(usuario.getTelefono());
        return guardia;
    }
    
}

