package com.residencial.dao;

import com.residencial.modelo.Pago;
import com.residencial.util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {
    
    public boolean crear(Pago pago) throws SQLException {
        String sql = "INSERT INTO pagos (id_usuario, tipo_servicio, monto, fecha_vencimiento, estado) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        
        pstmt.setInt(1, pago.getIdUsuario());
        pstmt.setString(2, pago.getTipoServicio());
        pstmt.setBigDecimal(3, pago.getMonto());
        pstmt.setDate(4, pago.getFechaVencimiento());
        pstmt.setString(5, pago.getEstado());
        
        int filasAfectadas = pstmt.executeUpdate();
        pstmt.close();
        
        return filasAfectadas > 0;
    }
    
    public List<Pago> obtenerPorUsuario(int idUsuario) throws SQLException {
        List<Pago> listaPagos = new ArrayList<>();
        String sql = "SELECT * FROM pagos WHERE id_usuario = ? ORDER BY fecha_pago DESC";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, idUsuario);
        
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Pago pago = new Pago();
            pago.setIdPago(rs.getInt("id_pago"));
            pago.setIdUsuario(rs.getInt("id_usuario"));
            pago.setTipoServicio(rs.getString("tipo_servicio"));
            pago.setMonto(rs.getBigDecimal("monto"));
            pago.setFechaPago(rs.getTimestamp("fecha_pago"));
            pago.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
            pago.setEstado(rs.getString("estado"));
            pago.setMetodoPago(rs.getString("metodo_pago"));
            
            listaPagos.add(pago);
        }
        
        rs.close();
        pstmt.close();
        return listaPagos;
    }
    
    public boolean marcarComoPagado(int idPago, String metodoPago) throws SQLException {
        String sql = "UPDATE pagos SET estado = 'PAGADO', metodo_pago = ?, fecha_pago = NOW() " +
                     "WHERE id_pago = ?";
        
        Connection con = ConexionBD.getConexion();
        PreparedStatement pstmt = con.prepareStatement(sql);
        
        pstmt.setString(1, metodoPago);
        pstmt.setInt(2, idPago);
        
        int filasAfectadas = pstmt.executeUpdate();
        pstmt.close();
        
        return filasAfectadas > 0;
    }
}

