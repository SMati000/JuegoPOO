import java.awt.Point;
import java.io.IOException;

public class EstrellaNinja extends PowerUp {
    public EstrellaNinja(Point posicion) throws IOException {
        super("estrellaNinja.png", posicion);
    }

    public EstrellaNinja(String filename, Point posicion) throws IOException {
        super(filename, posicion);
    }

    public EstrellaNinja(EstrellaNinja bonus) throws IOException {
        super(bonus);
    }

    @Override
    public void AsignarBonus(AvionAmigo avion) {
        avion.modificarEnergia(99);
    }

    @Override
    public EstrellaNinja clone() {
        EstrellaNinja temp = null;

        try {
            temp = new EstrellaNinja(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }
}
