
package Modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CajaDAO {
     Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarCaja(Cajas caja) {
        String sql = "INSERT INTO caja (inicio,totalfin,fecha)VALUES (?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setFloat(1, caja.getInicio());
            ps.setFloat(2,caja.getTotalfin());
            ps.setString(3,caja.getFecha());
            ps.execute();
            return true;

        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;

        }
    }

    public List ListarCaja() {
        List<Cajas> Listacaja = new ArrayList();
        String sql = "SELECT * FROM caja";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cajas ca = new Cajas();
                ca.setId(rs.getInt("id"));
                ca.setTotalfin(rs.getFloat("totalfin"));
                ca.setFecha(rs.getString("fecha"));
                Listacaja.add(ca);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());

        }
        return Listacaja;
    }
 public boolean EliminarGanancia(int id) {
        String sql = "DELETE FROM caja WHERE id=?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

  
}
