import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

public abstract class VehiculoMilitar extends ObjetoGrafico implements Disparable {
    protected double energia, resistencia;

    public VehiculoMilitar(String nombre, String grafico, Point posicion) throws IOException {
        super(nombre, grafico, posicion);
        this.energia = 100;
        this.resistencia = this.energia/10; // energia / cuantos disparos resiste
    }

    public void setX(int x) {
        if(x > 0 && x < Juego1943.getInstance().getWidth()-grafico.getWidth())
            this.posicion.x = x;
    }

    public void setResistencia(double resistencia) {
        this.resistencia = resistencia;
    }

    public double getResistencia() {
        return this.resistencia;
    }

    public void llenarEnergia() {
        energia = 100;
    }

    public void modificarEnergia(double deltaE) {
        if((energia + deltaE) <= 100)
            energia += deltaE;

        if(energia <= 0)
            this.destruir();
    }

    public double getEnergia() {
        return energia;
    }

    public void draw(Graphics2D g) {
        g.drawImage(grafico, posicion.x, posicion.y, null);
    }
}