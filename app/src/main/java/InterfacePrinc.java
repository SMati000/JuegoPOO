import javax.swing.*;
import java.awt.*;
import java.awt.event.*;




class InterfacePrinc extends JFrame implements ActionListener{
    JPanel panel, panel2, panel3, panel4;
    JButton b1, b2, b3, b4;
    JMenuItem item1,item2, item3;
    JMenuBar menu;
    JMenu menu1, menu2;
    JLabel fondo = new JLabel();

    public static void main(String[] args) {  
        new InterfacePrinc();
    } 

    public InterfacePrinc() {
        this.setPreferredSize(new Dimension(450, 550));
        this.setResizable(false);
        this.pack();
        
        fondo.setLayout(null);
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
/*
    public void paint(Graphics g){
        Dimension dimension = this.getSize();
        ImageIcon icon = new ImageIcon(getClass().getResource("/src/main/resource/imagenes/Fondo.jpg"));
        g.drawImage(icon.getImage(), 0, 0, 1080, 720, null);
        
        panelFondo.setOpaque(false);
        super.paintComponents(g);
    }
*/
    public void mostrarCatalogo(){
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBounds(50,50,150,200);
        panel.setBackground(Color.gray);
        panel.add(new JLabel("1943"), BorderLayout.NORTH);
        b1 = new JButton("Iniciar");
        panel.add(b1, BorderLayout.SOUTH);
      

        panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.setBounds(210,50,150,200);
        panel2.setBackground(Color.gray);
        panel2.add(new JLabel("Juego 2"), BorderLayout.NORTH);                  //ver bien layout de frame, repiten borderLayout.NORTH
        b2 = new JButton("Iniciar");
        panel2.add(b2, BorderLayout.SOUTH);


        panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        panel3.setBounds(50,270,150,200);
        panel3.setBackground(Color.gray);
        panel3.add(new JLabel("Juego 3"), BorderLayout.NORTH);
        b3 = new JButton("Iniciar");
        panel3.add(b3, BorderLayout.SOUTH);


        panel4 = new JPanel();
        panel4.setLayout(new BorderLayout());
        panel4.setBounds(210,270,150,200);
        panel4.setBackground(Color.gray);
        panel4.add(new JLabel("Juego 4"), BorderLayout.NORTH);
        b4 = new JButton("Iniciar");
        panel4.add(b4, BorderLayout.SOUTH);

        


        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        

        fondo.add(panel);
        fondo.add(panel2);
        fondo.add(panel3);
        fondo.add(panel4);

        setVisible(true);
        pack();


        
        WindowAdapter l=new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                    System.exit(0);
            };
    
        };
        this.addWindowListener(l);
        //Agrega el objeto para que se muestre por la ventana.

        Dimension d = new Dimension(450,550);
        this.setPreferredSize(d);
       
        //Redimensiona la ventana a su tama�o natural
        this.setVisible(true);
        this.pack();

    }

    public void actionPerformed(ActionEvent evento){
        JPanel panelJuego = new JPanel();
        JFrame frame = new JFrame();
        
        
        if (evento.getSource()==item1) {
            JFrame framConfTecla;
            JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7;
            Choice ch1, ch2, ch3, ch4, ch5, ch6, ch7;

            Configuraciones conf = new Configuraciones();

            
            framConfTecla = new JFrame("Configuracion de teclas");
            framConfTecla.setLayout(new GridLayout(7,7));
            Dimension d1 = new Dimension(300,400);
            framConfTecla.setPreferredSize(d1);

            lb1 = new JLabel("Hacia adelante: ");
            ch1 = new Choice();
            String opciones[] = {"a","b", "c", "d" ,"e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
            ch1.addItem("↑");                                   //muestra un cuadrado, cambiarlo
            for (int i = 0; i < opciones.length; i++) {
                ch1.add(opciones[i]);
            }
            framConfTecla.add(lb1);
            framConfTecla.add(ch1);

            // ch7.addKeyListener(new KeyAdapter() {

            //     @Override
            //     public void keyReleased(KeyEvent e) {
            //        // System.out.println(KeyEvent.getKeyText(e.getExtendedKeyCode()) + " - " + e.getExtendedKeyCode());
            //         conf.guardarEnBD(e.getExtendedKeyCode(), KeyEvent.getKeyText(e.getExtendedKeyCode()));
            //         ch7.setText(KeyEvent.getKeyText(e.getExtendedKeyCode()));
            //         // habria q hacer que se guarde en la DB
            //     }
            // });
            
       
            ch1.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent ie){
                    conf.selecTeclas(ch1.getSelectedItem(), 1, KeyEvent.getExtendedKeyCodeForChar(ch1.getSelectedItem().charAt(0)));
                }
            });



            lb2 = new JLabel("Hacia abajo: ");
            ch2 = new Choice();
            ch2.addItem("↓");                                   //muestra un cuadrado, cambiarlo
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
            ch3.addItem("→");                                   //muestra un cuadrado, cambiarlo
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
            ch4.addItem("←");                                   //muestra un cuadrado, cambiarlo
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
            ch5.addItem("x");                                   
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
            ch6.addItem("z");                                   
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
            Dimension d1 = new Dimension(300,400);
            framConfSonido.setPreferredSize(d1);
           
           
            JLabel defecto= new JLabel("Sonido por defecto");                   //nombre de la pista

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
        
            JButton jugar =  new JButton("JUGAR");
            GridBagConstraints resBotonJugar = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 
                                                                weightx, weighty, anchor, fill,in, ipadx, ipady);

                        
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
            menu1.add(item1);
            // item1.addActionListener(this);
            item1.addActionListener(this);
            item2 = new JMenuItem("Sonido");
            menu1.add(item2);
            item2.addActionListener(this);
            item3 = new JMenuItem("Avion");         
            menu1.add(item3);
            item3.addActionListener(this);
       
            frameJuego.setJMenuBar(menu);
            
            jugar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(evt.getSource() == jugar){
                        Juego juego = new Juego1943();
                        Thread t = new Thread() {
                            public void run() {
                                //juego.run(1.0 / 60.0);
                            }
                        };
            
                        t.start();
                        
                    }
                }
            });



           

            frameJuego.setVisible(true);
            frameJuego.pack();
            



            
        } else if(evento.getActionCommand() == b2.getActionCommand()) {
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
