import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

public abstract class VehiculoMilitar extends ObjetoGrafico{

    public VehiculoMilitar(String nombre, String grafico, Point posicion) throws IOException {
        super(nombre, grafico, posicion);
    }

    public void setX(int x) {
        if(x > 0 && x < Juego1943.getInstance().getWidth()-grafico.getWidth())
            this.posicion.x = x;
    }

    public abstract Municion disparar();
    public abstract void destruir();

    public void draw(Graphics2D g) {
        g.drawImage(grafico, posicion.x, posicion.y, null);
    }
}