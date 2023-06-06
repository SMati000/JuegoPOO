import java.awt.Point;
import java.io.IOException;

public abstract class ArmadeFuego extends Bonus {

    public ArmadeFuego(Bonus bonus) throws IOException {
        super(bonus.nombre, "", (Point)bonus.getPosicion().clone());
        setGrafico(bonus.grafico);
    }

    
    public ArmadeFuego(String grafico, Point posicion) throws IOException {
        super("Arma de fuego", grafico, posicion);
    }
   
    @Override
    public void update() {
        
    }




    
}
