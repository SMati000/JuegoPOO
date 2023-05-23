import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import com.entropyinteractive.Keyboard;

public class Juego1943 extends Juego {
    private static final Juego instance = new Juego1943();
    private Mapa mapa;
    private Camara cam;
    private VehiculoMilitar avionAmigo;
    Keyboard keyboard;
    private final double dezplazamiento = 120.0;

    private Juego1943(){
        super("1943", "Juego 1943");
        setIcon(new File(getClass().getResource("/imagenes/1943ico.png").getPath()));
        
        try {
            mapa = new Mapa("/imagenes/fondo1943.jpg");
            cam = new Camara(0, 0);
            avionAmigo = new AvionAmigo();
        } catch (IOException e) {
            System.out.println("No se pudo crear avion amigo");
        }
        
    }

    @Override
    public void gameStartup() {
        keyboard = this.getKeyboard();

        avionAmigo.setPosicion(new Point(400, 300));
        cam.setRegionVisible(800, 600);
    }

    @Override
    public void gameShutdown() {
        
    }

    @Override
    public void gameUpdate(double delta) {
        if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            avionAmigo.setY(avionAmigo.getY() - (int)(dezplazamiento * delta));
        }
        
        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            avionAmigo.setY(avionAmigo.getY() + (int)(dezplazamiento * delta));
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            avionAmigo.setX(avionAmigo.getX() - (int)(dezplazamiento * delta));
            ((AvionAmigo)avionAmigo).setIcon(AvionAmigo.Iconos.IZQ);
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            avionAmigo.setX(avionAmigo.getX() + (int)(dezplazamiento * delta));
            ((AvionAmigo)avionAmigo).setIcon(AvionAmigo.Iconos.DER);
        }

        if(!keyboard.isKeyPressed(KeyEvent.VK_UP) && !keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            avionAmigo.setY(avionAmigo.getY() - (int)(dezplazamiento * delta * 0.5));
        }
        
        if(!keyboard.isKeyPressed(KeyEvent.VK_LEFT) && !keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            ((AvionAmigo)avionAmigo).setIcon(AvionAmigo.Iconos.COMUN);
        }

        cam.seguirPersonaje((AvionAmigo)avionAmigo);
    }

    @Override
    public void gameDraw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.translate(cam.getX(),cam.getY());
    
        // System.out.println("Translation: " + cam.getX() + " - " + cam.getY());
        mapa.setPosition((int)cam.getX(), (int)cam.getY());
        mapa.display(g);
        avionAmigo.draw(g);

        // g.translate(-cam.getX(),-cam.getY());
    }

    public static Juego getInstance() {
        return instance;
    }
}

class Camara {
    private double x,y;

	private double resX, resY;

    public Camara(double x,double y) {
    	this.x=x;
    	this.y=y;
    }

	public void seguirPersonaje(AvionAmigo avion){
		this.y = -avion.getY()+resY/2;
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

    public double getX(){
    	return this.x;
    }

     public double getY(){
    	return this.y;
    }
}