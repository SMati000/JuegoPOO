import java.awt.event.KeyEvent;
import java.sql.*;

public class Configuraciones {
    //teclas por defecto
    static int arriba, abajo, izq, der, disparo, dispEspecial, pausa;
    ResultSet rs = null;
    PreparedStatement pstmt= null;
    Statement stmt;
    Connection conn = null;

    public Configuraciones(){
         //establece conexion con la base de datos
         
         try {
                  
             String url = "jdbc:sqlite:teclas.db";
                
             conn = DriverManager.getConnection(url); // Si no existe crea el archivo de la base de datos
            
             System.out.println("Conectado a  SQLite.");
                 
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         } finally {
             try {
                 if (conn != null) {
                     conn.close();
                 }
             } catch (SQLException ex) {
                 System.out.println(ex.getMessage());
             }
         }
    }

    //en este metodo me trae de la base de datos el codigo de la tecla elegida y me setea las por defecto
    public void selecTeclas(String TeclaSelect){

        try{
            String sql ="Select "+ TeclaSelect + " from Teclas";
            
            stmt = conn.createStatement();
            rs  = stmt.executeQuery(sql);
            
            while(rs.next()){
                //System.out.println(rs.getString("Defecto"));

                 
            }


         }catch (SQLException e) {
            System.out.println(e.getMessage());
     
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    }


}



