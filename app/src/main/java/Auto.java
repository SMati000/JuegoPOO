import java.awt.Point;
import java.io.IOException;

public class Auto extends PowerUp {
    public Auto(Bonus bonus) throws IOException {
        super(bonus);
        this.vidaBonus = 130;
    }

    public Auto(String filename, Point posicion) throws IOException {
        super(filename, posicion);
        this.vidaBonus = 130;
    }

    public Auto(Point posicion) throws IOException {
        super("auto.png", posicion);
        this.vidaBonus = 130;
    }
    
    
}
