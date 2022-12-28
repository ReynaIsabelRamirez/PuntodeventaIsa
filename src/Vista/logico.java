/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class logico {

    public DefaultTableModel buscarFecha(String buscar) {

        int contador = 1; // Dedicado para acomular en número de registros que hay en la tabla

        String[] nombresColumnas = {"  ID  ", "VENDEDOR", "TOTAL", "FECHA"};//Indica el nombre de las columnas en la tabla

        String[] registros = new String[4];

        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

        String sql = "SELECT * FROM ventas WHERE fecha LIKE '%" + buscar + "%'";

        Connection con = null;
        Conexion cn = new Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = cn.getConexion();

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                //registros[0] = Integer.toString(contador);

                registros[0] = rs.getString("id");

                registros[1] = rs.getString("vendedor");

                registros[2] = rs.getString("total");

                registros[3] = rs.getString("fecha");

                modelo.addRow(registros);

                contador++;

            }
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al conectar. " + e.getMessage());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        return modelo;
    }

    public DefaultTableModel buscarFechaCaja(String buscar) {

        int contador = 1; // Dedicado para acomular en número de registros que hay en la tabla

        String[] nombresColumnas = {"  ID  ", "GANANCIA", "FECHA"};//Indica el nombre de las columnas en la tabla

        String[] registros = new String[3];

        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

        String sql = "SELECT * FROM caja WHERE fecha LIKE '%" + buscar + "%'";

        Connection con = null;
        Conexion cn = new Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = cn.getConexion();

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                //registros[0] = Integer.toString(contador);

                registros[0] = rs.getString("id");

                registros[1] = rs.getString("totalfin");

                registros[2] = rs.getString("fecha");

                modelo.addRow(registros);

                contador++;

            }
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al conectar. " + e.getMessage());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        return modelo;
    }

    public DefaultTableModel buscarProducto(String buscar) {

        int contador = 1; // Dedicado para acomular en número de registros que hay en la tabla

        String[] nombresColumnas = {"  ID  ", "CÓDIGO", "DESCRIPCIÓN", "PROVEEDOR", "CATEGORÍA", "STOCK", "PRECIO VENTA"};//Indica el nombre de las columnas en la tabla

        String[] registros = new String[7];

        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

        String sql = "SELECT * FROM productos WHERE nombre LIKE '%" + buscar + "%'";

        Connection con = null;
        Conexion cn = new Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = cn.getConexion();

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                //registros[0] = Integer.toString(contador);

                registros[0] = rs.getString("id");

                registros[1] = rs.getString("codigo");

                registros[2] = rs.getString("nombre");
                registros[3] = rs.getString("proveedor");
                registros[4] = rs.getString("categoria");
                registros[5] = rs.getString("stock");
                registros[6] = rs.getString("precioventa");

                modelo.addRow(registros);

                contador++;

            }
        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Error al conectar. " + e.getMessage());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (ps != null) {
                    ps.close();
                }

                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        return modelo;
    }
}
