package Modelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
public class ProductosDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    public boolean RegistrarProductos(Productos pro) {
        String sql = "INSERT INTO productos (codigo,nombre,proveedor,categoria,stock,precioventa)VALUES (?,?,?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, pro.getCodigo());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getProveedor());
            ps.setString(4, pro.getCategoria());
            ps.setInt(5, pro.getStock());
            ps.setDouble(6, pro.getPrecioventa());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
     public void ConsularProveedor(JComboBox proveedor) {
        String sql = "SELECT nombre FROM proveedor";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                proveedor.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void ConsularCategoria(JComboBox categoria) {
        String sql = "SELECT nombre FROM categoria";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                categoria.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public List ListarProductos() {
        List<Productos> Listapro = new ArrayList();
        String sql = "SELECT * FROM productos";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Productos pro = new Productos();
                pro.setId(rs.getInt("id"));
                pro.setCodigo(rs.getString("codigo"));
                pro.setNombre(rs.getString("nombre"));
                pro.setProveedor(rs.getString("proveedor"));
                pro.setCategoria(rs.getString("categoria"));
                pro.setStock(rs.getInt("stock"));
                pro.setPrecioventa(rs.getDouble("precioventa"));
                Listapro.add(pro);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return Listapro;
    }
    public boolean EliminarProductos(int id){
       String sql = "DELETE FROM productos WHERE id = ?";
       try {
           ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException ex) {
               System.out.println(ex.toString());
           }
       }
   }
     public boolean ModificarProductos(Productos pro){
       String sql = "UPDATE productos SET codigo=?, nombre=?, proveedor=?,categoria=? ,stock=?, precioventa=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);
           ps.setString(1, pro.getCodigo());
           ps.setString(2, pro.getNombre());
           ps.setString(3, pro.getProveedor());
           ps.setString(4, pro.getCategoria());
           ps.setInt(5, pro.getStock());
           ps.setDouble(6, pro.getPrecioventa());
           ps.setInt(7, pro.getId());
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException e) {
               System.out.println(e.toString());
           }
       }
   }
     
     public Productos BuscarPro(String cod){
         Productos producto = new Productos();
         String sql= "SELECT *FROM productos WHERE codigo =?";
         
         try{
             con= cn.getConexion();
             ps =con.prepareStatement(sql);
             ps.setString(1, cod);
             rs= ps.executeQuery();
             if(rs.next()){
                 producto.setNombre(rs.getNString("nombre"));
                 producto.setPrecioventa(rs.getDouble("precioventa"));
                 producto.setStock(rs.getInt("stock"));      
             }
         }catch (SQLException e){
             System.out.println(e.toString());
         }
         return producto;
     }
     public Config BuscarDatos(){
        Config conf = new Config();
        String sql = "SELECT * FROM config";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                conf.setId(rs.getInt("id"));
                conf.setRuc(rs.getInt("ruc"));
                conf.setNombre(rs.getString("nombre"));
                conf.setTelefono(rs.getInt("telefono"));
                conf.setDireccion(rs.getString("direccion"));
                conf.setMensaje(rs.getString("mensaje"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conf;
    }
     
      public boolean ModificarDatos(Config conf){
       String sql = "UPDATE config SET ruc=?, nombre=?,telefono=?,direccion=? ,mensaje=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);
           ps.setInt(1,conf.getRuc());
           ps.setString(2, conf.getNombre());
           ps.setInt(3,conf.getTelefono());
           ps.setString(4,conf.getDireccion());
           ps.setString(5, conf.getMensaje());
           ps.setInt(6,conf.getId());
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException e) {
               System.out.println(e.toString());
           }
       }
   }
     
      
}

  
