import java.awt.Point;
import java.io.IOException;

public class Refuerzos extends Bonus {
    public Refuerzos(Point posicion) throws IOException {
        super("refuerzo", "refuerzo.png", posicion);
    }

    public Refuerzos(String filename, Point posicion) throws IOException {
        super("refuerzo", filename, posicion);
    }
    
    public Refuerzos(Refuerzos bonus) throws IOException {
        super("Refuerzo", "refuerzo.png", new Point(0,0));
    }

    @Override
    public void AsignarBonus(AvionAmigo avionAmigo) {
        System.out.println("refuerzos asignados");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Refuerzos clone() {
        Refuerzos temp = null;

        try {
            temp = new Refuerzos(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }
}


