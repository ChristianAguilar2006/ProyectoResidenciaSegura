package com.residencial.dao;

import com.residencial.modelo.Emergencia;
import com.residencial.util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmergenciaDAO {
    
    public boolean crear(Emergencia emergencia) throws SQLException {
        String sql = "INSERT INTO emergencias (id_usuario, tipo, descripcion, ubicacion, prioridad, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, emergencia.getIdUsuario());
        pstmt.setString(2, emergencia.getTipo());
        pstmt.setString(3, emergencia.getDescripcion());
        pstmt.setString(4, emergencia.getUbicacion());
        pstmt.setString(5, emergencia.getPrioridad());
        pstmt.setString(6, emergencia.getEstado());
        
        int filasAfectadas = pstmt.executeUpdate();
        pstmt.close();
        
        return filasAfectadas > 0;
    }
    
    public List<Emergencia> obtenerActivas() throws SQLException {
        List<Emergencia> listaEmergencias = new ArrayList<>();
        String sql = "SELECT * FROM emergencias WHERE estado IN ('ACTIVA', 'EN_ATENCION') " +
                     "ORDER BY fecha_reporte DESC";
        
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement consulta = conexion.prepareStatement(sql);
        ResultSet resultados = consulta.executeQuery();
        
        while (resultados.next()) {
            Emergencia emergencia = new Emergencia();
            emergencia.setIdEmergencia(resultados.getInt("id_emergencia"));
            emergencia.setIdUsuario(resultados.getInt("id_usuario"));
            emergencia.setTipo(resultados.getString("tipo"));
            emergencia.setDescripcion(resultados.getString("descripcion"));
            emergencia.setUbicacion(resultados.getString("ubicacion"));
            emergencia.setFechaReporte(resultados.getTimestamp("fecha_reporte"));
            emergencia.setEstado(resultados.getString("estado"));
            emergencia.setPrioridad(resultados.getString("prioridad"));
            listaEmergencias.add(emergencia);
        }
        
        resultados.close();
        consulta.close();
        return listaEmergencias;
    }
    
    public boolean actualizarEstado(int idEmergencia, String nuevoEstado, Integer idGuardia) throws SQLException {
        String sql = "UPDATE emergencias SET estado = ?, id_guardia_atendiendo = ? " +
                     "WHERE id_emergencia = ?";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        
        pstmt.setString(1, nuevoEstado);
        if (idGuardia != null) {
            pstmt.setInt(2, idGuardia);
        } else {
            pstmt.setNull(2, java.sql.Types.INTEGER);
        }
        pstmt.setInt(3, idEmergencia);
        
        int filasAfectadas = pstmt.executeUpdate();
        pstmt.close();
        
        return filasAfectadas > 0;
    }
}

