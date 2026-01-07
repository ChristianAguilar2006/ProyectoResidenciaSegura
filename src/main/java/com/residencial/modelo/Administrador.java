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
    
    public void crearUsuarioGUI(String nombre, String correo, String contrasena, String rol, String departamento, String bloque) throws Exception {
        Usuario nuevoUsuario = crearUsuario(nombre, correo, contrasena, rol, departamento, bloque);
        guardarUsuario(nuevoUsuario);
    }
    
    private Usuario crearUsuario(String nombre, String correo, String contrasena, String rol, String departamento, String bloque) {
        return new Usuario(nombre, correo, contrasena, rol, departamento, bloque);
    }
    
    private void guardarUsuario(Usuario usuario) throws Exception {
        if (!usuarioDAO.crear(usuario)) {
            throw new Exception("Error al crear el usuario");
        }
    }
    
    public void crearAvisoGUI(String titulo, String mensaje, String tipo) throws Exception {
        Aviso nuevoAviso = crearAviso(titulo, mensaje, tipo);
        guardarAviso(nuevoAviso);
    }
    
    private Aviso crearAviso(String titulo, String mensaje, String tipo) {
        return new Aviso(this.getIdUsuario(), titulo, mensaje, tipo);
    }
    
    private void guardarAviso(Aviso aviso) throws Exception {
        if (!avisoDAO.crear(aviso)) {
            throw new Exception("Error al crear el aviso");
        }
    }
    
    public List<Aviso> obtenerAvisos() throws Exception {
        return avisoDAO.obtenerActivos();
    }
    
    public void eliminarAvisoGUI(int idAviso) throws Exception {
        if (!avisoDAO.eliminar(idAviso)) {
            throw new Exception("Error al eliminar el aviso");
        }
    }
    
    public List<Usuario> obtenerUsuarios() throws Exception {
        return usuarioDAO.obtenerTodos();
    }
    
    @Override
    public void mostrarMenu() {
        System.out.println("\n--- MENÚ ADMINISTRADOR ---");
        System.out.println("Usuario: " + this.getNombre());
        System.out.println("1. Ver Perfil");
        System.out.println("2. Gestionar Usuarios");
        System.out.println("3. Crear Aviso");
        System.out.println("4. Ver Avisos");
        System.out.println("5. Ver Reportes");
        System.out.println("6. Cerrar Sesión");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
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
    
    private void gestionarUsuarios() {
        System.out.println("\n--- GESTIONAR USUARIOS ---");
        System.out.println("1. Crear Usuario");
        System.out.println("2. Volver");
        System.out.print("Seleccione: ");
        
        try {
            int op = scanner.nextInt();
            scanner.nextLine();
            if (op == 1) {
                crearUsuario();
            } else if (op == 2) {
            } else {
                System.out.println("Opcion invalida");
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Error: Debe ingresar un numero (1 o 2)");
        }
    }
}

