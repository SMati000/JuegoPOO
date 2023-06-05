import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.entropyinteractive.Keyboard;

public class Juego1943 extends Juego {
    private static final Juego instance = new Juego1943();

    private BufferedImage gameOver;
    private Mapa mapa;
    private Camara cam;

    private VehiculoMilitar avionAmigo;
    private final double desplazamiento = 180.0;

    private int misionAsignada;
    private Mision mision;
    private int animacionEnCurso;

    Keyboard keyboard;

    private Juego1943(){
        super("1943", "Juego 1943");
        setIcon(new File(getClass().getResource("/imagenes/1943ico.png").getPath()));
        
        try {
            gameOver = ImageIO.read(getClass().getResource("imagenes/gameover.jpg"));

            mapa = new Mapa("fondo1943Aire.jpg", "fondo1943Tierra.jpg");
            mapa.setTransicion("fondo1943Transicion.jpg");

            cam = new Camara(120, 0, 0);
            avionAmigo = new AvionAmigo(new Point(400, 300));
            animacionEnCurso = 0;
        } catch (IOException e) {
            System.out.println("No se pudo crear avion amigo");
        }
        
    }

    @Override
    public void gameStartup() {
        keyboard = this.getKeyboard();

        cam.setRegionVisible(800, 600);

        try {
            Enemigo e1 = new AvionEnemigo("avionEnemigo1.png", new Point(0, 0), avionAmigo.getPosicion());
            // e1.setGraficosDoblar("avionEnemigo1Izq.png", "avionEnemigo1Der.png");
    
            Enemigo e2 = new AvionEnemigo("avionEnemigo2.png", new Point(0, 0), avionAmigo.getPosicion());
            // e2.setGraficosDoblar("avionEnemigo2Izq.png", "avionEnemigo2Der.png");
    
            Enemigo e3 = new Barco("barco1.png", new Point(0, 0), avionAmigo.getPosicion());
            for(Arma a : e3.getArmas()) {
                a.seguir(false);
                a.setAngulo(180);
            }
            
            Enemigo jefe = new Barco("jefe1.png", new Point(0, 0), avionAmigo.getPosicion());
            jefe.setResistencia(100/50);
            for(Arma a : jefe.getArmas()) {
                a.setTiros(2);
                a.setAnguloMaximo(170);
            }

            misionAsignada = 1;

            Enemigo enemigos[];
            switch(misionAsignada) {
                case 1:
                    enemigos = new Enemigo[]{e1, e2, e3};

                    mision = new Mision.MisionBuilder((AvionAmigo)avionAmigo, enemigos, jefe)
                    .setTiempo(40)
                    .setDificultad(Mision.DIFICULTAD.FACIL)
                    .build();
                    break;
                case 2:
                    enemigos = new Enemigo[]{e1, e2};

                    mision = new Mision.MisionBuilder((AvionAmigo)avionAmigo, enemigos, null)
                    .setTiempo(60*7)
                    .setDificultad(Mision.DIFICULTAD.DIFICIL)
                    .build();
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gameShutdown() {
        
    }
    
    @Override 
    public void gameUpdate(double delta) {

        if (keyboard.isKeyPressed(Configuraciones.arriba)) {
            if(this.getViewPort().contains(avionAmigo.getX(), avionAmigo.getY()  - avionAmigo.grafico.getHeight(), 
                avionAmigo.grafico.getWidth(), avionAmigo.grafico.getHeight())) {
                avionAmigo.setY(avionAmigo.getY() - (int)(desplazamiento * delta));
            } else {
                avionAmigo.setY(avionAmigo.getY() - (int)(120 * delta * 0.5));
            }
        }
        
        if (keyboard.isKeyPressed(Configuraciones.abajo)) {
            if(this.getViewPort().contains(avionAmigo.getX(), avionAmigo.getY() + (int)(desplazamiento * delta), 
                avionAmigo.grafico.getWidth(), avionAmigo.grafico.getHeight())) {
                avionAmigo.setY(avionAmigo.getY() + (int)(desplazamiento * delta));
            } else {
                avionAmigo.setY(avionAmigo.getY() - (int)(cam.getDesplazamiento() * delta * 0.5));
            }
        }

        if(!keyboard.isKeyPressed(Configuraciones.arriba) && !keyboard.isKeyPressed(Configuraciones.abajo)) {
            avionAmigo.setY(avionAmigo.getY() - (int)(cam.getDesplazamiento() * delta * 0.5));
        }

        if (keyboard.isKeyPressed(Configuraciones.izq)) {
            avionAmigo.setX(avionAmigo.getX() - (int)(desplazamiento * delta));

            if(!(animacionEnCurso > 0 && animacionEnCurso < 180))
                ((AvionAmigo)avionAmigo).setIcon(AvionAmigo.Iconos.IZQ);
        }

        if (keyboard.isKeyPressed(Configuraciones.der)) {
            avionAmigo.setX(avionAmigo.getX() + (int)(desplazamiento * delta));
            
            if(!(animacionEnCurso > 0 && animacionEnCurso < 180))
                ((AvionAmigo)avionAmigo).setIcon(AvionAmigo.Iconos.DER);
        }
        
        if(!keyboard.isKeyPressed(Configuraciones.izq) && !keyboard.isKeyPressed(Configuraciones.der)) {
            if(!(animacionEnCurso > 0 && animacionEnCurso < 180))
                ((AvionAmigo)avionAmigo).setIcon(AvionAmigo.Iconos.COMUN);
        }
        
        if(mision.getEstado() == Mision.ESTADO.TIERRA && animacionEnCurso < 180) {
            animacion();
        }

        cam.avanzar((AvionAmigo)avionAmigo, delta);
        mision.update();
    }

    private void animacion() {
        if(animacionEnCurso == 0 || animacionEnCurso == 120)
            ((AvionAmigo)avionAmigo).setIcon(AvionAmigo.Iconos.BAJANDO1);
        else if(animacionEnCurso == 60)
            ((AvionAmigo)avionAmigo).setIcon(AvionAmigo.Iconos.BAJANDO2);
        else if(animacionEnCurso == 179)
            ((AvionAmigo)avionAmigo).setIcon(AvionAmigo.Iconos.COMUN);

        if(animacionEnCurso == 60 || animacionEnCurso == 175)
            mapa.cambiarMapa();

        animacionEnCurso++;
    }

    @Override
    public void gameDraw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(mision.getEstado() == Mision.ESTADO.FIN || avionAmigo.getEnergia() <= 0) {
            g.drawImage(gameOver, 0, 0, null);
            gameShutdown();
            return;
        }

        g.translate(cam.getX(),cam.getY());
    
        mapa.setPosition((int)cam.getX(), (int)cam.getY());
        mapa.draw(g);
        
        mision.draw(g, (int)(mapa.getY()-(cam.getY()*2)+60));
        
        g.translate(-cam.getX(),-cam.getY());
    }

    public Rectangle getViewPort() {
        return new Rectangle(new Point((int)cam.getX(), -(int)cam.getY()),
            new Dimension(this.getWidth(), this.getHeight()));
    }

    public static Juego getInstance() {
        return instance;
    }
}

class Camara {
    private double desplazamiento;
    private double x,y;

	private double resX, resY;

    public Camara(double desplazamiento, double x,double y) {
    	this.desplazamiento=desplazamiento;
    	this.x=x;
    	this.y=y;
    }

	public void avanzar(AvionAmigo avion, double delta){
		this.y += desplazamiento * delta * 0.5;
        // System.out.println(y);
    }

	public void setViewPort(double x,double y){
		setRegionVisible(x,y);
	}

	public void setRegionVisible(double x,double y){
		resX=x;
		resY=y;
	}

    public void setX(double x){
    	this.x=x;
    }

     public void setY(double y){
    	this.y=y;
    }

    public double getDesplazamiento(){
    	return this.desplazamiento;
    }

    public double getX(){
    	return this.x;
    }

     public double getY(){
    	return this.y;
    }

    public double getTamanoX(){
    	return this.resX;
    }

     public double getTamanoY(){
    	return this.resY;
    }
}