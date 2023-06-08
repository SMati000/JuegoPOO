import javax.swing.*;
//import FXPlayer.Volume;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

class InterfacePrinc extends JFrame implements ActionListener{
    JLabel img1943;
    JLabel fondo = new JLabel();
    JMenuBar menu;
    JMenu menu1, menu2;
    JMenuItem item1, item2, item3;
    Configuraciones conf;
   

    public static void main(String[] args) {  
        new InterfacePrinc();
    } 

    public InterfacePrinc() {
        try {
            conf = new Configuraciones();
        } catch (SQLException e) {
            System.out.println("Error al crear el objeto de Configuraciones\n" + e.getMessage());
        }

        this.setPreferredSize(new Dimension(450, 550));
        this.setResizable(false);
        this.pack();

        fondo.setLayout(new GridBagLayout());
        fondo.setPreferredSize(new Dimension(450, 550));
        fondo.setIcon(new ImageIcon(getClass().getResource("/imagenes/fondoPueba2.png")));
        this.add(fondo);
        this.setComponentZOrder(fondo, 0);
        mostrarCatalogo();

        this.setVisible(true);
        this.repaint();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                    System.exit(0);
            };
        });
    }

    public void mostrarCatalogo() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = c.gridy = 0;
        
        c.insets = new Insets(10, 10, 10, 10);

        img1943 = new JLabel();
        img1943.setLayout(null);
    
        img1943.setIcon(new ImageIcon(getClass().getResource("/imagenes/1943.png")));
     
        img1943.setOpaque(true);

        img1943.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //se aprieta varias veces, fijarme como llamar al frame anterior del juego, con un metodo puede ser
        img1943.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JFrame frameJuego = new JFrame();
                frameJuego.setLayout(new GridBagLayout());           //no se con layout hacerlo para que quede bien
                Dimension dim1 = new Dimension(315,400);
                frameJuego.setPreferredSize(dim1);
                frameJuego.setLayout(new FlowLayout());
              
             
                JButton jugar =  new JButton("JUGAR");
                JButton ranking = new JButton("Ver Ranking");
                
                jugar.addActionListener(InterfacePrinc.this);
                ranking.addActionListener(InterfacePrinc.this);

                //menu visto en pantalla
                menu = new JMenuBar();


                menu1 = new JMenu("Configuraciones");
                menu2 = new JMenu("Ver");
                
                menu.add(menu1); 
                menu.add(menu2); 

                //item dentro de configuraciones
                item1 = new JMenuItem("Teclado");
                item1.addActionListener(InterfacePrinc.this);
                menu1.add(item1);

                item2 = new JMenuItem("Sonido");
                item2.addActionListener(InterfacePrinc.this);
                menu1.add(item2);

                item3 = new JMenuItem("Avion");     
                item3.addActionListener(InterfacePrinc.this);    
                menu1.add(item3);
 
                jugar.setSize(100,50);
                jugar.setLocation(100, 150);
                
  
                JPanel fondo= new JPanel();
                fondo.setLayout(new BorderLayout());
                JLabel pantallaJuego = new JLabel();
                fondo.add(pantallaJuego, BorderLayout.CENTER);
                pantallaJuego.setIcon(new ImageIcon(getClass().getResource("/imagenes/pantallaJuego.jpg")));
               
                pantallaJuego.add(jugar); 
                pantallaJuego.add(ranking);

                frameJuego.add(fondo);
                frameJuego.setJMenuBar(menu);               
                frameJuego.setResizable(false);

                jugar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if(evt.getSource() == jugar){
                            Juego juego = Juego1943.getInstance();
                            Thread t = new Thread() {
                                public void run() {
                                    juego.run(1.0 / 60.0);

                                }
                            };
                
                            t.start();
                        }
                    }
                });
                
                if(Configuraciones.sonidoBD.equals("CHASE")){
                    FXPlayer.CHASE.play();
                }
                if(Configuraciones.sonidoBD.equals("ROBO_COP")){
                    FXPlayer.ROBO_COP.play();
                } 
                if(Configuraciones.sonidoBD.equals("DRAMATIC")){
                    FXPlayer.DRAMATIC.play();
                } 
                if(Configuraciones.sonidoBD.equals("FIGHT")){
                    FXPlayer.FIGHT.play();
                } 

                frameJuego.setVisible(true);
                frameJuego.pack();
                
            }
            
        }); 
        
        
        fondo.add(img1943, c);
        //   c.ipadx = 120;
        // c.ipady = 180;
        String imgs[] = {"/imagenes/counter.png", "/imagenes/pes.png", "/imagenes/mario.png"};
        for(int j = 0; j <= 1; j++) {
            for(int i = j == 0 ? 2 : 1; i <= 2; i++) {
                c.gridx = (i-1);
                c.gridy = j;
                
                
                JLabel juegoGenerico = new JLabel();
                juegoGenerico.setLayout(null);

                juegoGenerico.setIcon(new ImageIcon(getClass().getResource(imgs[i+j*2-2])));

                juegoGenerico.setOpaque(true);

                juegoGenerico.setCursor(new Cursor(Cursor.HAND_CURSOR));

                fondo.add(juegoGenerico, c);
            }
        }
    }

    public void actionPerformed(ActionEvent evento){
               
        if (evento.getSource()==item1) {
            JFrame framConfTecla;
            JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7;
            Choice ch1, ch2, ch3, ch4, ch5, ch6, ch7;
            JButton defecto = new JButton("Defecto");
           // conf = new Configuraciones();

            String opciones[] = {"a","b", "c", "d" ,"e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        
            framConfTecla = new JFrame("Configuracion de teclas");

            framConfTecla.setLayout(new GridLayout(8,8));
            framConfTecla.setPreferredSize(new Dimension(300,400));

            lb1 = new JLabel("Hacia adelante: ");

            ch1 = new Choice();
            ch1.add("↑"); //muestra un cuadrado, cambiarlo
            for (int i = 0; i < opciones.length; i++) {
                ch1.add(opciones[i]);
            }

            framConfTecla.add(lb1);
            framConfTecla.add(ch1);
            
            ch1.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent ie){
                    conf.selecTeclas(ch1.getSelectedItem(), 1, KeyEvent.getExtendedKeyCodeForChar(ch1.getSelectedItem().charAt(0)));                   
                }
            });



            lb2 = new JLabel("Hacia abajo: ");
            ch2 = new Choice();
            ch2.add("↓");                                   //muestra un cuadrado, cambiarlo
            for (int i = 0; i < opciones.length; i++) {
                ch2.add(opciones[i]);
            }

            //Captura el elemento del choice
            ch2.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent ie){
                    conf.selecTeclas(ch2.getSelectedItem(), 2, KeyEvent.getExtendedKeyCodeForChar(ch2.getSelectedItem().charAt(0)));
                }
            });            

            framConfTecla.add(lb2);
            framConfTecla.add(ch2);
            
            lb3 = new JLabel("Derecha: ");
            ch3 = new Choice();
            ch3.add("→");                                   //muestra un cuadrado, cambiarlo
            for (int i = 0; i < opciones.length; i++) {
                ch3.add(opciones[i]);
            }
            framConfTecla.add(lb3);
            framConfTecla.add(ch3);

            ch3.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent ie){
                    conf.selecTeclas(ch3.getSelectedItem(), 3, KeyEvent.getExtendedKeyCodeForChar(ch3.getSelectedItem().charAt(0)));
                }
            });


            lb4 = new JLabel("Izquierda: ");
            ch4 = new Choice();
            ch4.add("←");                                   //muestra un cuadrado, cambiarlo
            for (int i = 0; i < opciones.length; i++) {
                ch4.add(opciones[i]);
            }
            framConfTecla.add(lb4);
            framConfTecla.add(ch4);
           
            ch4.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent ie){
                    conf.selecTeclas(ch4.getSelectedItem(), 4, KeyEvent.getExtendedKeyCodeForChar(ch4.getSelectedItem().charAt(0)));
                }
            });
           
            lb5 = new JLabel("Disparar: ");
            ch5 = new Choice();
            ch5.add("x");                                   
            for (int i = 0; i < opciones.length; i++) {
                ch5.add(opciones[i]);
            }
            framConfTecla.add(lb5);
            framConfTecla.add(ch5);

            ch5.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent ie){
                    conf.selecTeclas(ch5.getSelectedItem(), 5, KeyEvent.getExtendedKeyCodeForChar(ch5.getSelectedItem().charAt(0)));
                }
            });


            lb6 = new JLabel("Ataques especiales: ");
            ch6 = new Choice();
            ch6.add("z");                                   
            for (int i = 0; i < opciones.length; i++) {
                ch6.add(opciones[i]);
            }
            framConfTecla.add(lb6);
            framConfTecla.add(ch6);

            ch6.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent ie){
                    conf.selecTeclas(ch6.getSelectedItem(), 6, KeyEvent.getExtendedKeyCodeForChar(ch6.getSelectedItem().charAt(0)));
                }
            });

            lb7 = new JLabel("Pausa: ");
            ch7 = new Choice();
            ch7.addItem("Barra espacio");                                   
            for (int i = 0; i < opciones.length; i++) {
                ch7.add(opciones[i]);
            }
            framConfTecla.add(lb7);
            framConfTecla.add(ch7);
            
            ch7.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent ie){
                    conf.selecTeclas(ch7.getSelectedItem(), 7, KeyEvent.getExtendedKeyCodeForChar(ch7.getSelectedItem().charAt(0)));
                }
            });

            framConfTecla.add(defecto);
            defecto.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(evt.getSource() == defecto){
                        conf.setDefecto("Defecto");
                    }
                }
            });

            framConfTecla.setVisible(true);
            framConfTecla.pack();

        }
      //ver como poner arriba sonido por defect, y abajo cambiar y donde se acepta la url
        if(evento.getSource()== item2){
            FXPlayer.init();
            FXPlayer.volume = FXPlayer.Volume.LOW;
  
            JFrame framConfSonido = new JFrame();
            framConfSonido.setLayout(new FlowLayout());
            framConfSonido.setPreferredSize(new Dimension(200,100));
           
            Choice sonidos = new Choice();
            sonidos.addItem("CHASE");
            sonidos.addItem("ROBO_COP");
            sonidos.addItem("DRAMATIC");
            sonidos.addItem("FIGHT");

            sonidos.select(Configuraciones.sonidoBD);

            //para cambiar la musica seleccionada
            sonidos.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent ie){
                    conf.setNuevoSonido(sonidos.getSelectedItem());
                }
            });

            Checkbox activo = new Checkbox("Sonido");
            activo.setState(true);
            activo.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if(activo.getState()){
                        FXPlayer.volume = FXPlayer.Volume.MUTE; 
                       // FXPlayer.CHASE.stop();
                    }
                }
            });

            //checkbox para sonidos de tiros, efectos especiales, etc.
            Checkbox efectos = new Checkbox("Efectos");
            efectos.setState(true);
           
            JButton defecto = new JButton("Defecto");    
            
            defecto.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    conf.resetearSonido();
                    sonidos.select(Configuraciones.sonidoBD);
                }
            });

            framConfSonido.add(sonidos);
            framConfSonido.add(defecto);
            framConfSonido.add(activo);
            framConfSonido.add(efectos);

            framConfSonido.setVisible(true);
            framConfSonido.pack();

        }

    }
     
}
