
package Reportes;
import Modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
public class Graficod {
    public static void Graficard(String fecha){
        Connection con;
        Conexion cn= new Conexion();
        PreparedStatement ps;
        ResultSet rs;
    try{
        String sql ="SELECT totalfin from caja where fecha=?";
        con=cn.getConexion();
        ps= con.prepareStatement(sql);
        ps.setString(1, fecha);
        rs=ps.executeQuery();
        DefaultPieDataset  dataset = new DefaultPieDataset();
        while(rs.next()){
            
            dataset.setValue(rs.getString("totalfin"), rs.getDouble("totalfin"));
        }
        JFreeChart jf= ChartFactory.createPieChart("Reporte de Ganancia", dataset);
        ChartFrame f= new  ChartFrame("Total de Ganancia",jf);
        f.setSize(1000,500);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }catch (SQLException e){
        System.out.println(e.toString());
    }
    }
}
