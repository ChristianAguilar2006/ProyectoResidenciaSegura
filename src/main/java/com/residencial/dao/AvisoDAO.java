package com.residencial.dao;

import com.residencial.modelo.Aviso;
import com.residencial.util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AvisoDAO {
    
    public boolean crear(Aviso aviso) throws SQLException {
        String sql = "INSERT INTO avisos (id_administrador, titulo, mensaje, tipo, activo) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, aviso.getIdAdministrador());
        pstmt.setString(2, aviso.getTitulo());
        pstmt.setString(3, aviso.getMensaje());
        pstmt.setString(4, aviso.getTipo());
        pstmt.setBoolean(5, aviso.isActivo());
        
        int filasAfectadas = pstmt.executeUpdate();
        pstmt.close();
        
        return filasAfectadas > 0;
    }
    
    public List<Aviso> obtenerActivos() throws SQLException {
        List<Aviso> listaAvisos = new ArrayList<>();
        String sql = "SELECT * FROM avisos WHERE activo = TRUE ORDER BY fecha_publicacion DESC";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Aviso aviso = new Aviso();
            aviso.setIdAviso(rs.getInt("id_aviso"));
            aviso.setIdAdministrador(rs.getInt("id_administrador"));
            aviso.setTitulo(rs.getString("titulo"));
            aviso.setMensaje(rs.getString("mensaje"));
            aviso.setTipo(rs.getString("tipo"));
            aviso.setFechaPublicacion(rs.getTimestamp("fecha_publicacion"));
            aviso.setFechaExpiracion(rs.getDate("fecha_expiracion"));
            aviso.setActivo(rs.getBoolean("activo"));
            
            listaAvisos.add(aviso);
        }
        
        rs.close();
        pstmt.close();
        return listaAvisos;
    }
    
    public boolean eliminar(int idAviso) throws SQLException {
        String sql = "UPDATE avisos SET activo = FALSE WHERE id_aviso = ?";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        
        pstmt.setInt(1, idAviso);
        
        int filasAfectadas = pstmt.executeUpdate();
        pstmt.close();
        
        return filasAfectadas > 0;
    }
}

