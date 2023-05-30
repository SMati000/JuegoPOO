import java.awt.Point;
import java.io.IOException;

public class Barco extends Enemigo {

    public Barco(String nombre, String grafico, Point posicion) throws IOException {
        super(nombre, grafico, posicion);
        this.rangoDeteccion = 800;
    }

    public Barco(Enemigo enemigo) throws IOException {
        super(enemigo);
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad < 4 ? velocidad : 4;
    }

}
