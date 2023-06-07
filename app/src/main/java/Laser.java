import java.awt.Point;
import java.io.IOException;

public class Laser extends ArmadeFuego {

    public Laser(Bonus bonus) throws IOException {
        super(bonus);        
    }

    public Laser(String filename, Point posicion) throws IOException {
        super(filename, posicion);
    }

    public Laser(Point posicion) throws IOException {
        super("pow.png", posicion);
        
    }

    @Override
    public void AsignarBonus(AvionAmigo amigo) {
       
    }
    
}
