package com.residencial.util;

public class ConfiguracionBD {
    
    public static final String HOST = "localhost";
    public static final String PUERTO = "3306";
    public static final String BASE_DATOS = "resident_god";
    public static final String USUARIO = "root";
    public static final String CONTRASEÑA = "";
    
    public static String obtenerURL() {
        return "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + BASE_DATOS + 
               "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    }
    
    public static void mostrarConfiguracion() {
        System.out.println("\nConfiguracion de Base de Datos:");
        System.out.println("   Host: " + HOST);
        System.out.println("   Puerto: " + PUERTO);
        System.out.println("   Base de datos: " + BASE_DATOS);
        System.out.println("   Usuario: " + USUARIO);
        System.out.println("   Contrasena: " + (CONTRASEÑA.isEmpty() ? "(vacia)" : "***"));
    }
}

