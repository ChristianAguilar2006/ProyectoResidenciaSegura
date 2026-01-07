package com.residencial.dao;

import com.residencial.modelo.Pedido;
import com.residencial.util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    
    public boolean crear(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos (id_usuario, descripcion, tipo_pedido, estado, costo) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, pedido.getIdUsuario());
        pstmt.setString(2, pedido.getDescripcion());
        pstmt.setString(3, pedido.getTipoPedido());
        pstmt.setString(4, pedido.getEstado());
        pstmt.setBigDecimal(5, pedido.getCosto());
        
        int filasAfectadas = pstmt.executeUpdate();
        pstmt.close();
        
        return filasAfectadas > 0;
    }
    
    public List<Pedido> obtenerPorUsuario(int idUsuario) throws SQLException {
        List<Pedido> listaPedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos WHERE id_usuario = ? ORDER BY fecha_solicitud DESC";
        
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement consulta = conexion.prepareStatement(sql);
        consulta.setInt(1, idUsuario);
        
        ResultSet resultados = consulta.executeQuery();
        while (resultados.next()) {
            Pedido pedido = new Pedido();
            pedido.setIdPedido(resultados.getInt("id_pedido"));
            pedido.setIdUsuario(resultados.getInt("id_usuario"));
            pedido.setDescripcion(resultados.getString("descripcion"));
            pedido.setTipoPedido(resultados.getString("tipo_pedido"));
            pedido.setFechaSolicitud(resultados.getTimestamp("fecha_solicitud"));
            pedido.setFechaEntregaEstimada(resultados.getDate("fecha_entrega_estimada"));
            pedido.setEstado(resultados.getString("estado"));
            pedido.setCosto(resultados.getBigDecimal("costo"));
            listaPedidos.add(pedido);
        }
        
        resultados.close();
        consulta.close();
        return listaPedidos;
    }
    
    public boolean actualizarEstado(int idPedido, String nuevoEstado) throws SQLException {
        String sql = "UPDATE pedidos SET estado = ? WHERE id_pedido = ?";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        
        pstmt.setString(1, nuevoEstado);
        pstmt.setInt(2, idPedido);
        
        int filasAfectadas = pstmt.executeUpdate();
        pstmt.close();
        
        return filasAfectadas > 0;
    }
}

