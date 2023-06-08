import java.awt.Point;
import java.io.IOException;

public class Auto extends PowerUp {
    public Auto(Point posicion) throws IOException {
        super("auto.png", posicion);
    }

    public Auto(String filename, Point posicion) throws IOException {
        super(filename, posicion);
    }

    public Auto(Auto bonus) throws IOException {
        super(bonus);
    }

    public void AsignarBonus(AvionAmigo avion) {
        System.out.println("auto asignado");
    }

    @Override
    public Auto clone() {
        Auto temp = null;

        try {
            temp = new Auto(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }
}
