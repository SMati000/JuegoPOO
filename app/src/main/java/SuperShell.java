import java.awt.Point;
import java.io.IOException;

public class SuperShell extends PowerUp{
    public SuperShell(Bonus bonus) throws IOException {
        super(bonus);
       
    }

    public SuperShell(String filename, Point posicion) throws IOException {
        super(filename, posicion);
        
    }

    public SuperShell(Point posicion) throws IOException {
        super("superShell.png", posicion);
        
    }

    @Override
    public void AsignarBonus(AvionAmigo avion) {
       System.out.println("se asigno super");
    }
}
