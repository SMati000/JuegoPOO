import com.entropyinteractive.*;

abstract public class Juego extends JGame {
    protected String nombre;
    protected String descripcion;

    public Juego(String nombre, String descripcion) {
        super(nombre, 800, 600);

        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Los metodos iniciar, finalizar, actualizar y dibujar los provee la superclase JGame.
}
