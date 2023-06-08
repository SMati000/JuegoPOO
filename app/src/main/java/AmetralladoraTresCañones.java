import java.awt.Point;
import java.io.IOException;

public class AmetralladoraTresCañones extends ArmadeFuego {
    public AmetralladoraTresCañones(Point posicion) throws IOException {
        super("pow.png", posicion);
    }
   
    public AmetralladoraTresCañones(String filename, Point posicion) throws IOException {
        super(filename, posicion);
    }
    
    public AmetralladoraTresCañones(AmetralladoraTresCañones bonus) throws IOException {
        super(bonus);
    }

    @Override
    public void AsignarBonus(AvionAmigo avion) {
        System.out.println("Ametralladora asignada");
    }

    @Override
    public AmetralladoraTresCañones clone() {
        AmetralladoraTresCañones temp = null;

        try {
            temp = new AmetralladoraTresCañones(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }
}
