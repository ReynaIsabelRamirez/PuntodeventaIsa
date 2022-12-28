package Modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class IngresoDAO {
     Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    
    public Ingreso log(String usuario, String clave){
        Ingreso l = new Ingreso();
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND clave = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clave);
            rs= ps.executeQuery();
            if (rs.next()) {
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setUsuario(rs.getString("usuario"));
                l.setClave(rs.getString("clave"));
                l.setRol(rs.getString("rol"));
                
                
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }
    
    public boolean Registrar(Ingreso reg){
        String sql = "INSERT INTO usuarios (nombre, usuario, clave,rol) VALUES (?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, reg.getNombre());
            ps.setString(2, reg.getUsuario());
            ps.setString(3, reg.getClave());
             ps.setString(4, reg.getRol());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
}
