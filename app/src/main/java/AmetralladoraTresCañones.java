import java.awt.Point;
import java.io.IOException;

public class AmetralladoraTresCañones extends ArmadeFuego {
    public AmetralladoraTresCañones(Bonus bonus) throws IOException {
        super(bonus);
        
    }
   
    public AmetralladoraTresCañones(String filename, Point posicion) throws IOException {
        super(filename, posicion);
        
    }
    

    public AmetralladoraTresCañones(Point posicion) throws IOException {
        super("pow.png", posicion);
      
    }

    @Override
    public void AsignarBonus(AvionAmigo avion) {
        System.out.println("Ametralladora asignada");
    }


}
