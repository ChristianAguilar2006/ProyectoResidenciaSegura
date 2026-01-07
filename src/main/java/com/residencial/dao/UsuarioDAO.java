package com.residencial.dao;

import com.residencial.modelo.Usuario;
import com.residencial.util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    public Usuario login(String correo, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND contraseña = ? AND activo = TRUE";
        
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement consulta = conexion.prepareStatement(sql);
        
        consulta.setString(1, correo);
        consulta.setString(2, contrasena);
        
        ResultSet resultados = consulta.executeQuery();
        if (resultados.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(resultados.getInt("id_usuario"));
            usuario.setNombre(resultados.getString("nombre"));
            usuario.setCorreo(resultados.getString("correo"));
            usuario.setRol(resultados.getString("rol"));
            usuario.setDepartamento(resultados.getString("departamento"));
            usuario.setBloque(resultados.getString("bloque"));
            usuario.setTelefono(resultados.getString("telefono"));
            resultados.close();
            consulta.close();
            return usuario;
        }
        
        resultados.close();
        consulta.close();
        return null;
    }
    
    public boolean crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, correo, contraseña, rol, departamento, bloque, telefono) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        
        pstmt.setString(1, usuario.getNombre());
        pstmt.setString(2, usuario.getCorreo());
        pstmt.setString(3, usuario.getContrasena());
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
    
    public List<Usuario> obtenerTodos() throws SQLException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE activo = TRUE ORDER BY nombre";
        
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement consulta = conexion.prepareStatement(sql);
        ResultSet resultados = consulta.executeQuery();
        
        while (resultados.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(resultados.getInt("id_usuario"));
            usuario.setNombre(resultados.getString("nombre"));
            usuario.setCorreo(resultados.getString("correo"));
            usuario.setRol(resultados.getString("rol"));
            usuario.setDepartamento(resultados.getString("departamento"));
            usuario.setBloque(resultados.getString("bloque"));
            usuario.setTelefono(resultados.getString("telefono"));
            listaUsuarios.add(usuario);
        }
        
        resultados.close();
        consulta.close();
        return listaUsuarios;
    }
}

