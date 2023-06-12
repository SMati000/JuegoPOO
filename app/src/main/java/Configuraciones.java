import java.sql.*;
import java.util.ArrayList;

public class Configuraciones {
    //teclas por defecto
    public static int arriba, abajo, izq, der, disparo, dispEspecial, pausa;
    public static String sonidoBD;

    private ResultSet rs = null;
    private PreparedStatement pstmt= null;
    private Statement stmt;
    private Connection conn = null;
    
    private static final String nombre_base = "./app/Teclas.db";

   // String sonidoBD;
    public Configuraciones() throws SQLException{
        //establece conexion con la base de datos
        String url = "jdbc:sqlite:" + nombre_base;
        conn = DriverManager.getConnection(url);
        
        stmt = conn.createStatement();

        setDefecto("Usuario");

        FXPlayer.init();
        FXPlayer.volume = FXPlayer.Volume.LOW;
        cargarSonido();
    }

    public void Ranking(String nombre, int score){
        try{    
            String sql = "Insert into Ranking(Nombre, Score) VALUES (?,?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, nombre);
            pstmt.setInt(2, score);
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

    public ArrayList<String[]> verRanking(){
        try{    
                //se obtiene la cantiada para usarla en el for que se muestra el ranking
                // ver si se necesita
            ArrayList<String[]> rank = new ArrayList<>();  
            String sql = "Select * from Ranking order by score desc;";
            String nombre;
            int score;
            int i=0;
            
            rs  = stmt.executeQuery(sql);
            while(rs.next()) {
                String[] contenedor = new String[2];
                
                nombre = rs.getString("Nombre");
                score = rs.getInt("Score");

                contenedor[i] = nombre;
                contenedor[i+1] = String.valueOf(score) ;
                

                rank.add(contenedor);
        }

            return rank;

        }catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return null;
        }
    }

    //agregar String que devuelva la letra
    //en este metodo me trae de la base de datos el codigo de la tecla elegida y me setea las por defecto
    public void selecTeclas(String TeclaSelect, int nroChoice, int codLetra){

        try{
            String sql ="Select Usuario from Teclado where Letra = '" + TeclaSelect + "';";
                  
            rs  = stmt.executeQuery(sql);
            int valorAux = rs.getInt("Usuario");
            
        
        if(valorAux == 0){ 
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

    public void setDefecto(String campoBD){
        try {
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
    public void cargarSonido(){
        try {
            sonidoBD = getFromMusica("Usuario");
        } catch (SQLException e) {
            System.out.println("Error al cargar el sonido");
            e.printStackTrace();
        }
    }

    public void resetearSonido() {
        try {
            sonidoBD = getFromMusica("Defecto");
            updateMusica("Usuario", sonidoBD);
        } catch (SQLException e) {
            System.out.println("Error al resetear el sonido");
            e.printStackTrace();
        }
    }

    //la base de datos queda ocupada con el setDefectoSonidos, probar poniendo sonidoDB como static
    public void setNuevoSonido(String nuevoSon){
        try {
            updateMusica("Usuario", nuevoSon);
            sonidoBD = getFromMusica("Usuario");
        } catch (SQLException e) {
            System.out.println("Error al setear el sonido");
            e.printStackTrace();
        }
    }
   
    private String getFromMusica(String campo) throws SQLException {
        String sql ="Select " + campo + " from Musica where Defecto is not null;";
        rs  = stmt.executeQuery(sql);
        return rs.getString(campo);
    }

    private void updateMusica(String campo, String valor) throws SQLException {
        String sqlAct = "UPDATE Musica SET " + campo + "=? WHERE Defecto is not null;";

        PreparedStatement pstmt1 = conn.prepareStatement(sqlAct);

        pstmt1.setString(1, valor);
        pstmt1.executeUpdate();
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

