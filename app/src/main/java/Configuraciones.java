import java.sql.*;

public class Configuraciones {
    //teclas por defecto
    static String arriba, abajo, izq, der, disparo, dispEspecial, pausa;

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
    public void selecTeclas(String TeclaSelect, int nroChoice, int codLetra){

        try{
            String sql ="Select Usuario from Teclado where Letra = '" + TeclaSelect + "';";
                  
            stmt = conn.createStatement();
            rs  = stmt.executeQuery(sql);

            String valor = rs.getString("Usuario");
            boolean guardado = false;

            if(valor == null) {
                guardado = guardarEnBD(codLetra, TeclaSelect, nroChoice);
                valor = codLetra + "";
            }
            
            if(guardado) {
                switch(nroChoice){
                    case 1:
                        arriba = valor;
                        break;
                    case 2:
                        abajo = valor;
                        break;
                    case 3:
                        der = valor;
                        break;
                    case 4:
                        izq = valor;
                        break;
                    case 5:
                        disparo = valor;
                        break;
                    case 6:
                        dispEspecial = valor;
                        break;
                    case 7:
                        pausa = valor;
                        break;
                }
            }

        }catch (SQLException e) {
            System.out.println(e.getMessage());
            //  } finally {
            //     try {
            //         if (conn != null) {
            //            conn.close();
            //            System.out.println("Closed Connection");
            //         }
            //     } catch (SQLException ex) {
            //         System.out.println(ex.getMessage());
            //     }
        }
    }

  
    private boolean guardarEnBD(int codigo, String letra, int nroChoice){
        try{
            String sql = "INSERT INTO Teclado(Defecto,Usuario,Letra) VALUES(?,?,?)";
            pstmt = conn.prepareStatement(sql);
    
            pstmt.setInt(1, codigo);  
            pstmt.setInt(2, codigo);    
            pstmt.setString(3, letra);
            pstmt.executeUpdate();

            return true;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return false;
        }
    }
}



