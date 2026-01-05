package com.residencial.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Clase para gestionar la conexión con la base de datos MySQL
 * Utiliza el patrón Singleton para mantener una única instancia de conexión
 * 
 * @author Sistema Residencial
 * @version 1.0
 */
public class Conexion {
    
    // Configuración de la base de datos (usa ConfiguracionBD)
    private static final String URL = ConfiguracionBD.obtenerURL();
    private static final String USUARIO = ConfiguracionBD.USUARIO;
    private static final String CONTRASEÑA = ConfiguracionBD.CONTRASEÑA;
    
    // Instancia única de la conexión (Singleton)
    private static Connection conexion = null;
    
    // Driver de MySQL
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * Constructor privado para evitar instanciación directa (Singleton)
     */
    private Conexion() {
    }
    
    /**
     * Obtiene la conexión a la base de datos
     * Si no existe, crea una nueva conexión
     * 
     * @return Connection objeto de conexión a la base de datos
     * @throws SQLException si ocurre un error al conectar
     */
    public static Connection conectar() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                // Cargar el driver de MySQL
                Class.forName(DRIVER);
                
                // Establecer la conexión
                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
                
                System.out.println("✅ Conexión a la base de datos establecida correctamente");
                
            } catch (ClassNotFoundException e) {
                System.err.println("❌ Error: No se encontró el driver de MySQL");
                System.err.println("   Asegúrate de tener el archivo mysql-connector-java.jar en el classpath");
                throw new SQLException("Driver no encontrado: " + e.getMessage());
                
            } catch (SQLException e) {
                System.err.println("❌ Error al conectar con la base de datos:");
                System.err.println("   " + e.getMessage());
                throw e;
            }
        }
        return conexion;
    }
    
    /**
     * Cierra la conexión a la base de datos
     */
    public static void desconectar() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    System.out.println("✅ Conexión cerrada correctamente");
                }
            } catch (SQLException e) {
                System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    /**
     * Verifica si la conexión está activa
     * 
     * @return true si la conexión está activa, false en caso contrario
     */
    public static boolean estaConectado() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Ejecuta una consulta SELECT y retorna un ResultSet
     * 
     * @param sql consulta SQL a ejecutar
     * @return ResultSet con los resultados de la consulta
     * @throws SQLException si ocurre un error al ejecutar la consulta
     */
    public static ResultSet ejecutarConsulta(String sql) throws SQLException {
        Connection conn = conectar();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }
    
    /**
     * Ejecuta una consulta INSERT, UPDATE o DELETE
     * 
     * @param sql consulta SQL a ejecutar
     * @return número de filas afectadas
     * @throws SQLException si ocurre un error al ejecutar la consulta
     */
    public static int ejecutarActualizacion(String sql) throws SQLException {
        Connection conn = conectar();
        Statement stmt = conn.createStatement();
        return stmt.executeUpdate(sql);
    }
    
    /**
     * Prueba la conexión a la base de datos
     * 
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public static boolean probarConexion() {
        try {
            Connection conn = conectar();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Prueba de conexión exitosa");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error en la prueba de conexión: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Obtiene información de la base de datos
     */
    public static void mostrarInfoConexion() {
        try {
            Connection conn = conectar();
            if (conn != null) {
                System.out.println("\n📊 Información de la conexión:");
                System.out.println("   URL: " + URL);
                System.out.println("   Usuario: " + USUARIO);
                System.out.println("   Base de datos: resident_god");
                System.out.println("   Estado: " + (conn.isClosed() ? "Cerrada" : "Abierta"));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener información: " + e.getMessage());
        }
    }
}

