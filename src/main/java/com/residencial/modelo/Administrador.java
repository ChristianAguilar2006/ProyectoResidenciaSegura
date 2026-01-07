package com.residencial.modelo;

import com.residencial.dao.*;
import com.residencial.interfaces.IMenuAdministrador;
import java.util.List;
import java.util.Scanner;

public class Administrador extends Usuario implements IMenuAdministrador {
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private AvisoDAO avisoDAO = new AvisoDAO();
    private Scanner scanner = new Scanner(System.in);
    
    public Administrador() {
        super();
    }
    
    public Administrador(String nombre, String correo, String contrasena) {
        super(nombre, correo, contrasena, "ADMINISTRADOR", "ADMIN", "ADMIN");
    }
    
    public void crearUsuario() {
        System.out.println("\n--- CREAR USUARIO ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        
        System.out.print("Contrasena: ");
        String contrasena = scanner.nextLine();
        
        System.out.print("Rol (RESIDENTE, ADMINISTRADOR, GUARDIA): ");
        String rol = scanner.nextLine().toUpperCase();
        
        System.out.print("Departamento: ");
        String departamento = scanner.nextLine();
        
        System.out.print("Bloque: ");
        String bloque = scanner.nextLine();
        
        Usuario usuario = new Usuario(nombre, correo, contrasena, rol, departamento, bloque);
        
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
        System.out.print("Titulo: ");
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
        System.out.println("Aqui se mostrarian reportes de pagos, pedidos, emergencias, etc.");
    }
    
    
    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENU ADMINISTRADOR ---");
        System.out.println("Usuario: " + this.getNombre());
        System.out.println("1. Ver Perfil");
        System.out.println("2. Gestionar Usuarios");
        System.out.println("3. Crear Aviso");
        System.out.println("4. Ver Avisos");
        System.out.println("5. Ver Reportes");
        System.out.println("6. Cerrar Sesion");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opcion: ");
    }
    
    @Override
    public void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                verPerfil();
                break;
            case 2:
                gestionarUsuarios();
                break;
            case 3:
                crearAviso();
                break;
            case 4:
                verAvisos();
                break;
            case 5:
                verReportes();
                break;
            case 6:
                break;
            case 7:
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
    
    private void gestionarUsuarios() {
        System.out.println("\n--- GESTIONAR USUARIOS ---");
        System.out.println("1. Crear Usuario");
        System.out.println("2. Volver");
        System.out.print("Seleccione: ");
        
        try {
            int op = scanner.nextInt();
            scanner.nextLine();
            switch (op) {
                case 1:
                    crearUsuario();
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Error: Debe ingresar un numero (1 o 2)");
        }
    }
}

