import java.awt.Point;
import java.io.IOException;

public class SuperShell extends PowerUp {
    public SuperShell(Point posicion) throws IOException {
        super("Super Shell", "superShell.png", posicion);   
    }

    public SuperShell(String filename, Point posicion) throws IOException {
        super("Super Shell", filename, posicion);
    }

    public SuperShell(SuperShell bonus) throws IOException {
        super(bonus);
    }

    @Override
    public void AsignarBonus(AvionAmigo avion) {
       System.out.println("se asigno super");
    }

    public void destruir() {}

    @Override
    public SuperShell clone() {
        SuperShell temp = null;

        try {
            temp = new SuperShell(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }
}
