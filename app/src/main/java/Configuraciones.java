import java.sql.*;

public class Configuraciones {
    //teclas por defecto
    static int arriba, abajo, izq, der, disparo, dispEspecial, pausa;
    static String sonidoBD;
    ResultSet rs = null;
    PreparedStatement pstmt= null;
    Statement stmt;
    Connection conn = null;
    private static final String nombre_base = "./app/Teclas.db";
   // String sonidoBD;
    public Configuraciones(){
        //establece conexion con la base de datos
       setDefecto("Usuario");
       //setDefectoSonido();
    }
    //agregar String que devuelva la letra
    //en este metodo me trae de la base de datos el codigo de la tecla elegida y me setea las por defecto
    public void selecTeclas(String TeclaSelect, int nroChoice, int codLetra){

        try{
            String sql ="Select Usuario from Teclado where Letra = '" + TeclaSelect + "';";
                  
            stmt = conn.createStatement();
            rs  = stmt.executeQuery(sql);
            int valorAux = rs.getInt("Usuario");
            
        
        if( valorAux == 0){ 
            int valor = codLetra;
            System.out.println("valor: "+valor);

            if(true){
               switch(nroChoice){
                    case 1:
                        arriba = valor;
                        actTeclas("Arriba", valor, TeclaSelect);
                        break;
                    case 2:
                        abajo = valor;
                        actTeclas("Abajo", valor, TeclaSelect);
                        break;
                    case 3:
                        der = valor;
                        actTeclas("Derecha", valor, TeclaSelect);
                        break;
                    case 4:
                        izq = valor;
                        actTeclas("Izquierda", valor, TeclaSelect);
                        break;
                    case 5:
                        disparo = valor;
                        actTeclas("Disparo", valor, TeclaSelect);
                        break;
                    case 6:
                        dispEspecial = valor;
                        actTeclas("DispEspecial", valor, TeclaSelect);
                        break;
                    case 7:
                        pausa = valor;
                        actTeclas("Pausa", valor, TeclaSelect);
                        break;
                }
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
    //actualiza las teclas en la base de datos
    public void actTeclas(String tipoLetra, int nuevoValor, String TeclaSelect ){

        try{
            String sql ="Select Defecto from Teclado where Accion = '" + tipoLetra + "';";
            stmt = conn.createStatement();
            rs  = stmt.executeQuery(sql);
            
            String sqlAct = "UPDATE Teclado SET Defecto=?,  Usuario=? , Letra=?, Accion=? WHERE Accion =?";

            PreparedStatement pstmt = conn.prepareStatement(sqlAct);
            pstmt.setInt(1, rs.getInt("Defecto"));  //defecto
            pstmt.setInt(2, nuevoValor); //usuario
            pstmt.setString(3, TeclaSelect);  //letra 
            pstmt.setString(4, tipoLetra);  //accion
            pstmt.setString(5, tipoLetra);  //condicion where
            pstmt.executeUpdate();
            
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


  //setea por defecto cada vez que se inicie el juego
    public void setDefecto(String campoBD){
        try {
            String url = "jdbc:sqlite:"+nombre_base;
            
            conn = DriverManager.getConnection(url); // Si no existe crea el archivo de la base de datos
            stmt = conn.createStatement();

            String teclas[] = {"Arriba", "Abajo", "Izquierda", "Derecha", "Disparo", "DispEspecial", "Pausa"};
            int teclasDefecto[]= {arriba, abajo, izq, der, disparo, dispEspecial, pausa};
            
            for (int i = 0; i < teclas.length; i++) {
                String sql ="Select "+campoBD+" from Teclado where Accion = '" + teclas[i] + "';";
                rs  = stmt.executeQuery(sql);
                teclasDefecto[i] = rs.getInt(campoBD);
            }
            arriba = teclasDefecto[0];
            abajo = teclasDefecto[1];
            izq = teclasDefecto[2];
            der = teclasDefecto[3];
            disparo = teclasDefecto[4];
            dispEspecial = teclasDefecto[5];
            pausa = teclasDefecto[6];
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }
   //Para setear sonido por defecto
    public String setDefectoSonido(){
        try { 
            String url = "jdbc:sqlite:"+nombre_base;
            FXPlayer.init();
            FXPlayer.volume = FXPlayer.Volume.LOW;
           
            if(sonidoBD == null) {
            
                conn = DriverManager.getConnection(url); // Si no existe crea el archivo de la base de datos
                stmt = conn.createStatement();
                String sql ="Select Defecto from Musica where Defecto is not null;";
                rs  = stmt.executeQuery(sql);
                sonidoBD = rs.getString("Defecto");            
            
            }
            return sonidoBD;
            

        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return " ";

        }
    }
   
//la base de datos queda ocupada con el setDefectoSonidos, probar poniendo sonidoDB como static
    public void setNuevoSonido(String nuevoSon){
        try{

           
            String sqlAct = "UPDATE Musica SET Defecto=?,  Usuario=?, Disponibles=? WHERE Defecto ='CHASE';";
            PreparedStatement pstmt1 = conn.prepareStatement(sqlAct);
            pstmt1.setString(1, "CHASE");  //defecto
            pstmt1.setString(2, nuevoSon);  //usuario
            pstmt1.setString(3, "CHASE");  //Disponibles
            pstmt1.executeUpdate();
            

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
 /*  
   //guarda las nuevas teclas que selecciona el usuario
     private boolean guardarEnBD(int codigo, String letra, int nroChoice){
         try{
             String sql = "INSERT INTO Teclado(Defecto,Usuario,Letra, Accion) VALUES(?,?,?,?)";
             pstmt = conn.prepareStatement(sql);
             pstmt.setInt(1, codigo);  //defecto
             pstmt.setInt(2, codigo);      //usuario
             pstmt.setString(3, letra);     //letra
             pstmt.setString(4, "null");      //accion
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

    public String setTecla(String accion){
        try{
            String sql ="Select Letra from Teclado where Accion = '"+accion+ "';";
            System.out.println(sql);
            stmt = conn.createStatement();
            rs  = stmt.executeQuery(sql);

            String nuevaLetra = rs.getString("Letra");
            return nuevaLetra;

        }catch (SQLException e) {
            System.out.println(e.getMessage());
            return " ";
        }
        

    }
   */ 
    
}

