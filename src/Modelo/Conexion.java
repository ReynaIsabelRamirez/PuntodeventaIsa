
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class Conexion {
    Connection con;
    public Connection getConexion(){
        try{
            String myBD="jdbc:mysql://localhost:3306/proyectocel?serverTimezone=UTC";
            con=DriverManager.getConnection(myBD,"root","");
            return con;
            
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.toString());
            
        }  
        return null; 
    }
    
}
