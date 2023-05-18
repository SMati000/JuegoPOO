import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class InterfacePrinc extends JFrame implements ActionListener{
    JPanel panel;
    JLabel fondo = new JLabel();
    JButton b1, bGenerico;
    JMenuBar menu;
    JMenu menu1, menu2;
    JMenuItem item1, item2, item3;
    Configuraciones conf = new Configuraciones();;

    public static void main(String[] args) {  
        new InterfacePrinc();
    } 

    public InterfacePrinc() {
        this.setPreferredSize(new Dimension(450, 550));
        this.setResizable(false);
        this.pack();
        
        fondo.setLayout(new GridBagLayout());
        fondo.setIcon(new ImageIcon(getClass().getResource("/imagenes/fondo.jpg")));
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
        c.ipadx = 120;
        c.ipady = 180;
        c.insets = new Insets(10, 10, 10, 10);

        panel = new JPanel(new BorderLayout());

        panel.setBackground(Color.gray);        
        panel.add(new JLabel("1943"), BorderLayout.NORTH);
        
        b1 = new JButton("Iniciar");
        panel.add(b1, BorderLayout.SOUTH);
        b1.addActionListener(this);
        
        fondo.add(panel, c);

        for(int j = 0; j <= 1; j++) {
            for(int i = j == 0 ? 2 : 1; i <= 2; i++) {
                c.gridx = (i-1);
                c.gridy = j;
                
                JPanel juegoGenerico = new JPanel(new BorderLayout());

                juegoGenerico.setBackground(Color.gray);
                juegoGenerico.add(new JLabel("Juego " + (i+j*2)), BorderLayout.NORTH);
                
                bGenerico = new JButton("Iniciar");
                juegoGenerico.add(bGenerico, BorderLayout.SOUTH);
                bGenerico.addActionListener(this);
                
                fondo.add(juegoGenerico, c);
            }
        }
    }

    public void actionPerformed(ActionEvent evento){
        JPanel panelJuego = new JPanel();
        JFrame frame = new JFrame();
        
        if (evento.getSource()==item1) {
            JFrame framConfTecla;
            JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7;
            Choice ch1, ch2, ch3, ch4, ch5, ch6, ch7;

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





            framConfTecla.setVisible(true);
            framConfTecla.pack();

        }
                                                                        //ver como poner arriba sonido por defect, y abajo cambiar y donde se acepta la url
        if(evento.getSource()== item2){
            JFrame framConfSonido = new JFrame();
            framConfSonido.setLayout(new GridLayout(2,1));
            framConfSonido.setPreferredSize(new Dimension(300,400));
           
            JLabel defecto= new JLabel("Sonido por defecto");      //nombre de la pista
            JLabel nuevoPista = new JLabel("Cambiar musica:");    //como se pone nueva musica en juego??
            JTextField url = new JTextField("Url nueva musica");

            framConfSonido.add(defecto);
            framConfSonido.add(nuevoPista);
            framConfSonido.add(url);

            framConfSonido.setVisible(true);
            framConfSonido.pack();

        }

        //ACOMODAR CON EL GRIDBAG PARA QUE SE ACOMODE
        if(evento.getSource() == b1){
            
            JFrame frameJuego = new JFrame();
            frameJuego.setLayout(new GridBagLayout());           //no se con layout hacerlo para que quede bien
            Dimension dim1 = new Dimension(300,300);
            frameJuego.setPreferredSize(dim1);
            
            Insets in = new Insets(5,5,5,5);
            int gridx = 0;
            int gridy = 200;
            int gridwidth = 10;
            int gridheight = 10;
            double weightx = 1.0;
            double weighty = 0.5;
            int anchor = GridBagConstraints.CENTER; 
            int fill = GridBagConstraints.NONE;
        
            int ipadx = 10;
            int ipady = 10;
        
            GridBagConstraints resBotonJugar = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 
                                                                weightx, weighty, anchor, fill,in, ipadx, ipady);

            JButton jugar =  new JButton("JUGAR");
            jugar.addActionListener(this);
            frameJuego.add(jugar, resBotonJugar);

            //menu visto en pantalla
            menu = new JMenuBar();

            
            menu1 = new JMenu("Configuraciones");
            menu2 = new JMenu("Ver");
            
            menu.add(menu1); 
            menu.add(menu2); 

            //item dentro de configuraciones
            item1 = new JMenuItem("Teclado");
            item1.addActionListener(this);
            menu1.add(item1);

            item2 = new JMenuItem("Sonido");
            item2.addActionListener(this);
            menu1.add(item2);

            item3 = new JMenuItem("Avion");     
            item3.addActionListener(this);    
            menu1.add(item3);
       
            frameJuego.setJMenuBar(menu);
            
            jugar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(evt.getSource() == jugar){
                        Juego juego = new Juego1943();
                        Thread t = new Thread() {
                            public void run() {
                                juego.run(1.0 / 60.0);

                            }
                        };
            
                        t.start();
                    }
                }
            });

            frameJuego.setVisible(true);
            frameJuego.pack();
            
        } else if(evento.getActionCommand() == bGenerico.getActionCommand()) {
            panelJuego.add(new JLabel("Cargando..."));
            panelJuego.setBackground(Color.gray);
            Dimension dim = new Dimension(100,100);
            frame.setPreferredSize(dim);
            frame.add(panelJuego);
            frame.setVisible(true);
            frame.pack();
        }
    }
}
