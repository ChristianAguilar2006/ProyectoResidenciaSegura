package com.residencial.test;

import com.residencial.util.ConexionBD;
import java.sql.Connection;

public class TestConexion {
    
    public static void main(String[] args) {
        try (Connection con = ConexionBD.getConexion()) {
            System.out.println("Conexion exitosa a la base de datos");
        } catch (Exception e) {
            System.out.println("Error de conexion");
            e.printStackTrace();
        }
    }
}
