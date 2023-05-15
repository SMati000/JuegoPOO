import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.entropyinteractive.*;

abstract public class Juego extends JGame {
    protected String nombre;
    protected String descripcion;

    public Juego(String nombre, String descripcion) {
        super(nombre, 800, 600);

        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    protected void setIcon(File icon) {
        try {
            this.getFrame().setIconImage(ImageIO.read(icon));
        } catch (IOException e) {
            System.out.println("Error estableciendo icono del juego");
        }
    }
   
    // Los metodos iniciar, finalizar, actualizar y dibujar los provee la superclase JGame.
}
