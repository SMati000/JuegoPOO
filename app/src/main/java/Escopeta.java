import java.awt.Point;
import java.io.IOException;

public class Escopeta extends ArmadeFuego {
    public Escopeta(Point posicion) throws IOException {
        super("pow.png", posicion);
    }

    public Escopeta(String filename, Point posicion) throws IOException {
        super(filename, posicion);
    }
       
    public Escopeta(Escopeta bonus) throws IOException {
        super(bonus);
    }

    @Override
    public void AsignarBonus(AvionAmigo amigo) {
    
    }

    @Override
    public Escopeta clone() {
        Escopeta temp = null;

        try {
            temp = new Escopeta(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }
}
