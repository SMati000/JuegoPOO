import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

public abstract class Bonus extends ObjetoGrafico{
    private int tiempo;                 //tiempo que dura en en el avion
    private boolean visible;
    
    public Bonus(String nombre, String grafico, Point posicion) throws IOException {
        super(nombre, grafico, posicion);
    }

    public void draw(Graphics2D g) {
        g.drawImage(grafico, posicion.x, posicion.y, null);
    }   

    public abstract void AsignarBonus();
}
