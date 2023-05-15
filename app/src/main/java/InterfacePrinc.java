import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

class InterfacePrinc extends JFrame implements ActionListener{
    JPanel panel, panelFondo = new JPanel();
    JButton b1, bGenerico;
    JMenuBar menu;
    JMenu menu1, menu2;
    JMenuItem item1,item2, item3;

    public static void main(String[] args) {  
        new InterfacePrinc();
    } 

    public InterfacePrinc() {
        this.setPreferredSize(new Dimension(450, 550));
        this.setLayout(new GridBagLayout());
        
        mostrarCatalogo();

        this.setVisible(true);
        this.pack();  
        
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
        
        this.add(panel, c);

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
                
                this.add(juegoGenerico, c);
            }
        }

        this.add(panelFondo);
    }

    public void actionPerformed(ActionEvent evento){
        JPanel panelJuego = new JPanel();
        JFrame frame = new JFrame();
        
        if (evento.getSource()==item1) {
            JFrame framConfTecla;
            JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7;
            Choice ch1, ch2, ch3, ch4, ch5, ch6;
            
            TextField ch7 = new TextField();
            // ch7.setEditable(false);
            // ch7.setEnabled(false);

            Configuraciones conf = new Configuraciones();

            String opciones[] = {"a","b", "c", "d" ,"e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        
            framConfTecla = new JFrame("Configuracion de teclas");

            framConfTecla.setLayout(new GridLayout(7,7));
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
                   System.out.println(KeyEvent.getExtendedKeyCodeForChar(ch1.getSelectedItem().toCharArray()[0]) + "");
                    System.out.println(KeyEvent.getKeyText(KeyEvent.getExtendedKeyCodeForChar(ch1.getSelectedItem().toCharArray()[0])));
                    // conf.selecTeclas(KeyEvent.getExtendedKeyCodeForChar(ch1.getSelectedItem().toCharArray()[0]) + "");
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
                    conf.selecTeclas(ch2.getSelectedItem());
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
                    conf.selecTeclas(ch3.getSelectedItem());
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
                    conf.selecTeclas(ch4.getSelectedItem());
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
                    conf.selecTeclas(ch5.getSelectedItem());
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
                    conf.selecTeclas(ch6.getSelectedItem());
                }
            });

            lb7 = new JLabel("Pausa: ");
            // ch7 = new Choice();
            // ch7.addItem("Barra espacio");                                   
            // for (int i = 0; i < opciones.length; i++) {
            //     ch7.add(opciones[i]);
            // }
            framConfTecla.add(lb7);
            framConfTecla.add(ch7);

            ch7.addKeyListener(new KeyAdapter() {

                @Override
                public void keyReleased(KeyEvent e) {
                   // System.out.println(KeyEvent.getKeyText(e.getExtendedKeyCode()) + " - " + e.getExtendedKeyCode());
                    conf.guardarEnBD(e.getExtendedKeyCode(), KeyEvent.getKeyText(e.getExtendedKeyCode()));
                    ch7.setText(KeyEvent.getKeyText(e.getExtendedKeyCode()));
                    // habria q hacer que se guarde en la DB
                }
            });
            

/*
            ch7.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent ie){
                    conf.selecTeclas(ch7.getSelectedItem());
                }
            });
*/
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