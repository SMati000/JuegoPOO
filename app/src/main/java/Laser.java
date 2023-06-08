import java.awt.Point;
import java.io.IOException;

public class Laser extends ArmadeFuego {
    public Laser(Point posicion) throws IOException {
        super("pow.png", posicion);
    }

    public Laser(String filename, Point posicion) throws IOException {
        super(filename, posicion);
    }

    public Laser(Laser bonus) throws IOException {
        super(bonus);        
    }

    @Override
    public void AsignarBonus(AvionAmigo amigo) {
       
    }

    @Override
    public Laser clone() {
        Laser temp = null;

        try {
            temp = new Laser(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }
}
