package com.residencial.dao;

import com.residencial.modelo.Usuario;
import com.residencial.util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    
    public Usuario login(String correo, String contraseña) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ? AND activo = TRUE";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        
        pstmt.setString(1, correo);
        pstmt.setString(2, contraseña);
        
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setCorreo(rs.getString("correo"));
            usuario.setRol(rs.getString("rol"));
            usuario.setDepartamento(rs.getString("departamento"));
            usuario.setBloque(rs.getString("bloque"));
            usuario.setTelefono(rs.getString("telefono"));
            
            rs.close();
            pstmt.close();
            return usuario;
        }
        
        rs.close();
        pstmt.close();
        return null;
    }
    
    public boolean crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, correo, contraseña, rol, departamento, bloque, telefono) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        
        pstmt.setString(1, usuario.getNombre());
        pstmt.setString(2, usuario.getCorreo());
        pstmt.setString(3, usuario.getContraseña());
        pstmt.setString(4, usuario.getRol());
        pstmt.setString(5, usuario.getDepartamento());
        pstmt.setString(6, usuario.getBloque());
        pstmt.setString(7, usuario.getTelefono());
        
        int filasAfectadas = pstmt.executeUpdate();
        pstmt.close();
        
        return filasAfectadas > 0;
    }
    
    public boolean actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, telefono = ?, departamento = ?, bloque = ? " +
                     "WHERE id_usuario = ?";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        
        pstmt.setString(1, usuario.getNombre());
        pstmt.setString(2, usuario.getTelefono());
        pstmt.setString(3, usuario.getDepartamento());
        pstmt.setString(4, usuario.getBloque());
        pstmt.setInt(5, usuario.getIdUsuario());
        
        int filasAfectadas = pstmt.executeUpdate();
        pstmt.close();
        
        return filasAfectadas > 0;
    }
}

