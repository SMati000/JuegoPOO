import java.awt.Point;
import java.io.IOException;

public class Refuerzos extends Bonus {
    public Refuerzos(Bonus bonus) throws IOException {
        super("Refuerzo", "refuerzo.png", new Point(0,0));
    }

    public Refuerzos(String filename, Point posicion) throws IOException {
        super("refuerzo", filename, posicion);
        
    }

    public Refuerzos(Point posicion) throws IOException {
        super("refuerzo", "refuerzo.png", posicion);
    }

    @Override
    public void AsignarBonus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'AsignarBonus'");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}


