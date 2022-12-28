package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarCategorias(Categoria cat) {
        String sql = "INSERT INTO categoria (clave,nombre)VALUES (?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, cat.getClave());
            ps.setString(2, cat.getNombre());

            ps.execute();
            return true;

        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;

        }
    }

    public List ListarCategoria() {
        List<Categoria> Listacat = new ArrayList();
        String sql = "SELECT * FROM categoria";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Categoria cat = new Categoria();
                cat.setId(rs.getInt("id"));
                cat.setClave(rs.getString("clave"));
                cat.setNombre(rs.getString("nombre"));
                Listacat.add(cat);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());

        }
        return Listacat;
    }

    public boolean EliminarCategoria(int id) {
        String sql = "DELETE FROM categoria WHERE id=?";
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

    public boolean ModificarCategoria(Categoria cat) {
        String sql = "UPDATE categoria SET clave=?, nombre=?  WHERE id=?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, cat.getClave());
            ps.setString(2, cat.getNombre());
            ps.setInt(3, cat.getId());
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
