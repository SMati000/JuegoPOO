import java.awt.Point;
import java.io.IOException;

public class EstrellaNinja extends PowerUp {
    public EstrellaNinja(Bonus bonus) throws IOException {
        super(bonus);
    }

    public EstrellaNinja(String filename, Point posicion) throws IOException {
        super(filename, posicion);
    
    }

    public EstrellaNinja(Point posicion) throws IOException {
        super("estrellaNinja.png", posicion);
      
    }

    @Override
    public void AsignarBonus(AvionAmigo avion) {
        avion.modificarEnergia(99);
    }
}
