import java.awt.Point;
import java.io.IOException;


public class Pow extends PowerUp {
      
    public Pow(Bonus bonus) throws IOException {
        super(bonus);
        this.vidaBonus = 100;
    }

    public Pow(String filename, Point posicion) throws IOException {
        super(filename, posicion);
        this.vidaBonus = 100;
    }

    public Pow(Point posicion) throws IOException {
        super("pow.png", posicion);
        this.vidaBonus = 100;
    }
    
}
