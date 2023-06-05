import java.awt.Point;
import java.io.IOException;

public class SuperShell extends PowerUp{
    public SuperShell(Bonus bonus) throws IOException {
        super(bonus);
        this.vidaBonus = 120;
    }

    public SuperShell(String filename, Point posicion) throws IOException {
        super(filename, posicion);
        this.vidaBonus = 120;
    }

    public SuperShell(Point posicion) throws IOException {
        super("superShell.png", posicion);
        this.vidaBonus = 120;
    }
}
