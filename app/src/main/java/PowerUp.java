import java.awt.Point;
import java.io.IOException;

public abstract class PowerUp extends Bonus {
    
    public PowerUp(Bonus bonus) throws IOException {
        super(bonus.nombre, "", (Point)bonus.getPosicion().clone());
        setGrafico(bonus.grafico);
    }

    
    
    public PowerUp(String grafico, Point posicion) throws IOException {
        super("bonus", grafico, posicion);
    }
    @Override
    public void update() {}

  
}
