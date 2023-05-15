import java.awt.event.KeyEvent;
import java.sql.*;

public class Configuraciones {
    //teclas por defecto
    static int arriba, abajo, izq, der, disparo, dispEspecial, pausa;
    ResultSet rs = null;
    PreparedStatement pstmt= null;
    Statement stmt;
    Connection conn = null;
    private static final String nombre_base = "./app/Teclas.db";
    public Configuraciones(){
        //establece conexion con la base de datos
        try {
                  
            String url = "jdbc:sqlite:"+nombre_base;
                
            conn = DriverManager.getConnection(url); // Si no existe crea el archivo de la base de datos
            
            System.out.println("Conectado a  SQLite.");
                 
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
    }

    //en este metodo me trae de la base de datos el codigo de la tecla elegida y me setea las por defecto
    public void selecTeclas(String TeclaSelect){

        try{
            String sql ="Select Usuario from Teclas where codigo = " + TeclaSelect + ";";
            // System.out.println(sql);
            
            stmt = conn.createStatement();
            rs  = stmt.executeQuery(sql);
            
            while(rs.next()){
                System.out.println(rs.getString("Usuario"));
            }

         }catch (SQLException e) {
            System.out.println(e.getMessage());
         } finally {
            try {
                if (conn != null) {
                   conn.close();
                   System.out.println("Closed Connection");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void guardarEnBD(int codigo, String letra){
        String sql = "INSERT INTO Teclado(Defecto,Usuario,Letra) VALUES(Defecto,codigo,letra)";


    }
    
}



