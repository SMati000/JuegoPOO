import java.awt.Point;
import java.io.IOException;


public class Pow extends PowerUp {
    public Pow(Point posicion) throws IOException {
        super("pow.png", posicion);
    }

    public Pow(String filename, Point posicion) throws IOException {
        super(filename, posicion);
    }
      
    public Pow(Pow bonus) throws IOException {
        super(bonus);
    }

    @Override
    public void AsignarBonus(AvionAmigo avion) {
        avion.modificarEnergia(30);
    }

    @Override
    public Pow clone() {
        Pow temp = null;

        try {
            temp = new Pow(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }
}
