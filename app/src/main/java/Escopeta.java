import java.awt.Point;
import java.io.IOException;

public class Escopeta extends ArmadeFuego{
       
    public Escopeta(Bonus bonus) throws IOException {
        super(bonus);
        
    }

    public Escopeta(String filename, Point posicion) throws IOException {
        super(filename, posicion);
    }

    public Escopeta(Point posicion) throws IOException {
        super("pow.png", posicion);
  
    }

    @Override
    public void AsignarBonus(AvionAmigo amigo) {
    
    }
}
