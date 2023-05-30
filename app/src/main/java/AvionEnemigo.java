import java.awt.Point;
import java.io.IOException;

public class AvionEnemigo extends Enemigo {

    public AvionEnemigo(String nombre, String filename, Point posicion) throws IOException {
        super(nombre, filename, posicion);
    }

    public AvionEnemigo(Enemigo enemigo) throws IOException {
        super(enemigo);
    }

    public void seguirHorizontalmente(Point objetivo) {
        if(objetivo.x - this.posicion.x > 20) { // a la izq del objetivo
            this.posicion.x = this.posicion.x + 1;
            this.grafico = this.der;
        } else if(objetivo.x - this.posicion.x < -20) { // a la der del objetivo
            this.posicion.x = this.posicion.x - 1;
            this.grafico = this.izq;
        } 
        
        if(!this.objetivoEnRadar(objetivo.y - 5)) { 
            this.grafico = this.comun;
        }
    }
}
