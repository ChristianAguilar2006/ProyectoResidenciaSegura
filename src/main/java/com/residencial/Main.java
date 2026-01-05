package com.residencial;

import com.residencial.dao.UsuarioDAO;
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
                    menuResidente(opcion, residente);
                } else if (rol.equals("ADMINISTRADOR")) {
                    Administrador admin = convertirAAdministrador(usuarioActual);
                    menuAdministrador(opcion, admin);
                } else if (rol.equals("GUARDIA")) {
                    Guardia guardia = convertirAGuardia(usuarioActual);
                    menuGuardia(opcion, guardia);
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
            mostrarMenuResidente();
        } else if (rol.equals("ADMINISTRADOR")) {
            mostrarMenuAdministrador();
        } else if (rol.equals("GUARDIA")) {
            mostrarMenuGuardia();
        }
    }
    
    private static void mostrarMenuResidente() {
        System.out.println("\n--- MENÚ RESIDENTE ---");
        System.out.println("Usuario: " + usuarioActual.getNombre());
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
    
    private static void mostrarMenuAdministrador() {
        System.out.println("\n--- MENÚ ADMINISTRADOR ---");
        System.out.println("Usuario: " + usuarioActual.getNombre());
        System.out.println("1. Ver Perfil");
        System.out.println("2. Gestionar Usuarios");
        System.out.println("3. Crear Aviso");
        System.out.println("4. Ver Avisos");
        System.out.println("5. Ver Reportes");
        System.out.println("6. Cerrar Sesión");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    private static void mostrarMenuGuardia() {
        System.out.println("\n--- MENÚ GUARDIA ---");
        System.out.println("Usuario: " + usuarioActual.getNombre());
        System.out.println("1. Ver Perfil");
        System.out.println("2. Ver Emergencias Activas");
        System.out.println("3. Atender Emergencia");
        System.out.println("4. Cerrar Sesión");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
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
    
    private static void verPerfil() {
        System.out.println("\n--- MI PERFIL ---");
        System.out.println("ID: " + usuarioActual.getIdUsuario());
        System.out.println("Nombre: " + usuarioActual.getNombre());
        System.out.println("Correo: " + usuarioActual.getCorreo());
        System.out.println("Rol: " + usuarioActual.getRol());
        System.out.println("Departamento: " + usuarioActual.getDepartamento());
        System.out.println("Bloque: " + usuarioActual.getBloque());
        System.out.println("Teléfono: " + (usuarioActual.getTelefono() != null ? usuarioActual.getTelefono() : "No registrado"));
    }
    
    private static void actualizarPerfil() {
        System.out.println("\n--- ACTUALIZAR PERFIL ---");
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = scanner.nextLine();
        
        if (!nombre.isEmpty()) {
            usuarioActual.setNombre(nombre);
        }
        
        System.out.print("Nuevo telefono (Enter para mantener): ");
        String telefono = scanner.nextLine();
        if (!telefono.isEmpty()) {
            usuarioActual.setTelefono(telefono);
        }
        
        try {
            boolean exito = usuarioDAO.actualizar(usuarioActual);
            
            if (exito) {
                System.out.println("\nPerfil actualizado correctamente");
            } else {
                System.out.println("\nError al actualizar el perfil");
            }
        } catch (Exception e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    private static void cerrarSesion() {
        usuarioActual = null;
        System.out.println("\nSesion cerrada");
    }
    
    private static void gestionarUsuarios(Administrador admin) {
        System.out.println("\n--- GESTIONAR USUARIOS ---");
        System.out.println("1. Crear Usuario");
        System.out.println("2. Volver");
        System.out.print("Seleccione: ");
        
        try {
            int op = scanner.nextInt();
            scanner.nextLine();
            if (op == 1) {
                admin.crearUsuario();
            } else if (op == 2) {
            } else {
                System.out.println("Opcion invalida");
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Error: Debe ingresar un numero (1 o 2)");
        }
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
    
    
    private static void menuResidente(int opcion, Residente residente) {
        switch (opcion) {
            case 1:
                verPerfil();
                break;
            case 2:
                actualizarPerfil();
                break;
            case 3:
                residente.pagarServicio();
                break;
            case 4:
                residente.verHistorialPagos();
                break;
            case 5:
                residente.crearPedido();
                break;
            case 6:
                residente.verEstadoPedidos();
                break;
            case 7:
                residente.activarEmergencia();
                break;
            case 8:
                residente.verAvisos();
                break;
            case 9:
                cerrarSesion();
                break;
            case 10:
                System.out.println("\n¡Hasta luego!");
                System.exit(0);
                break;
            default:
                System.out.println("Opción inválida");
        }
    }
    
    
    private static void menuAdministrador(int opcion, Administrador admin) {
        switch (opcion) {
            case 1:
                verPerfil();
                break;
            case 2:
                gestionarUsuarios(admin);
                break;
            case 3:
                admin.crearAviso();
                break;
            case 4:
                admin.verAvisos();
                break;
            case 5:
                admin.verReportes();
                break;
            case 6:
                cerrarSesion();
                break;
            case 7:
                System.out.println("\n¡Hasta luego!");
                System.exit(0);
                break;
            default:
                System.out.println("Opción inválida");
        }
    }
    
    
    private static void menuGuardia(int opcion, Guardia guardia) {
        switch (opcion) {
            case 1:
                verPerfil();
                break;
            case 2:
                guardia.verEmergenciasActivas();
                break;
            case 3:
                guardia.atenderEmergencia();
                break;
            case 4:
                cerrarSesion();
                break;
            case 5:
                System.out.println("\n¡Hasta luego!");
                System.exit(0);
                break;
            default:
                System.out.println("Opción inválida");
        }
    }
}

