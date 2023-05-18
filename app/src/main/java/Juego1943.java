import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import com.entropyinteractive.Keyboard;

public class Juego1943 extends Juego {
    private VehiculoMilitar avionAmigo;
    Keyboard keyboard;
    private final double dezplazamiento = 150.0;

    public Juego1943(){
        super("1943", "Juego 1943");
        setIcon(new File(getClass().getResource("/imagenes/1943ico.png").getPath()));
        keyboard = this.getKeyboard();
    }

    @Override
    public void gameStartup() {
        try {
            avionAmigo = new AvionAmigo();
            avionAmigo.setPosicion(new Point(400, 300));
        } catch (IOException e) {
            System.out.println("No se pudo crear avion amigo.");
        }
    }

    @Override
    public void gameShutdown() {
        
    }
/*
    @Override
    public void gameUpdate(double delta) {
        if (keyboard.isKeyPressed(Integer.parseInt(Configuraciones.arriba))) {
            avionAmigo.setY(avionAmigo.getY() - (int)(dezplazamiento * delta));
        }
          
        if (keyboard.isKeyPressed(Integer.parseInt(Configuraciones.abajo))) {
            avionAmigo.setY(avionAmigo.getY() + (int)(dezplazamiento * delta));
        }

        if (keyboard.isKeyPressed(Integer.parseInt(Configuraciones.izq))) {
            avionAmigo.setX(avionAmigo.getX() - (int)(dezplazamiento * delta));
        }

        if (keyboard.isKeyPressed(Integer.parseInt(Configuraciones.der))) {
            avionAmigo.setX(avionAmigo.getX() + (int)(dezplazamiento * delta));
        }
      
        if(keyboard.isKeyPressed(Integer.parseInt(Configuraciones.der))){
            JFrame pausado = new JFrame();
            //crear un frame o un panel para que cuando aprete pausa aparezca en pantalla con el boton reanudar
        }

    }
*/
    public void gameUpdate(double delta) {
        if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
            avionAmigo.setY(avionAmigo.getY() - (int)(dezplazamiento * delta));
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
            avionAmigo.setY(avionAmigo.getY() + (int)(dezplazamiento * delta));
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
            avionAmigo.setX(avionAmigo.getX() - (int)(dezplazamiento * delta));
        }

        if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
            avionAmigo.setX(avionAmigo.getX() + (int)(dezplazamiento * delta));
        }
    }

    @Override
    public void gameDraw(Graphics2D g) {
        avionAmigo.draw(g);
    }
}
