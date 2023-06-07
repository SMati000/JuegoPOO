import java.awt.Point;
import java.io.IOException;

public class Auto extends PowerUp {
    public Auto(Bonus bonus) throws IOException {
        super(bonus);
      
    }

    public Auto(String filename, Point posicion) throws IOException {
        super(filename, posicion);
        
    }

    public Auto(Point posicion) throws IOException {
        super("auto.png", posicion);
      
    }
    public void AsignarBonus(AvionAmigo avion) {
        System.out.println("auto asignado");
    }
    
}
