import java.awt.Point;
import java.io.IOException;

public class EstrellaNinja extends PowerUp {
    public EstrellaNinja(Bonus bonus) throws IOException {
        super(bonus);
        this.vidaBonus = 150;
    }

    public EstrellaNinja(String filename, Point posicion) throws IOException {
        super(filename, posicion);
        this.vidaBonus = 150;
    }

    public EstrellaNinja(Point posicion) throws IOException {
        super("estrellaNinja.png", posicion);
        this.vidaBonus = 150;
    }
}
