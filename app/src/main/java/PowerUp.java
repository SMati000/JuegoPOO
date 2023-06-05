import java.awt.Point;
import java.io.IOException;

public abstract class PowerUp extends Bonus {
    protected int vidaBonus;

    public PowerUp(Bonus bonus) throws IOException {
        super(bonus.nombre, "", (Point)bonus.getPosicion().clone());
        setGrafico(bonus.grafico);
    }
    @Override
    public void AsignarBonus() {}
    
    public PowerUp(String grafico, Point posicion) throws IOException {
        super("bonus", grafico, posicion);
    }
    @Override
    public void update() {}

    protected int Energia;
}
