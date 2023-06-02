import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Arma extends ObjetoGrafico {
    private final Point objetivo;
    private int frecuencia, velocidad;
    private double angulo=0;
    // private Municion municion;

    public Arma(Point posicion, Point objetivo) throws IOException {
        super("Arma", null, posicion);

        this.objetivo = objetivo;
        this.posicion = posicion; // el constructor del super() inicializa el parametro por valor, se necesita por referencia

        this.frecuencia = 8;
        this.velocidad = 1;
        // this.municion = new Municion("municion1.png");
    }

    public Arma(Point posicion, Point objetivo, String filename) throws IOException {
        this(posicion, objetivo);
        grafico = ImageIO.read(getClass().getResource("imagenes/" + filename));
    }

    private void rotar() {
        double y = posicion.y - objetivo.y;
        double x = posicion.x - objetivo.x - 20;

        if(Math.abs(Math.toDegrees(angulo)) < 85 && y < -10 && Math.abs(x) > 10) {
            angulo = -(Math.abs(x)/x) * Math.atan(-Math.abs(x/y));
        }
    }

    private int contador = 0;
    public Municion disparar() {
        try {
            if(contador == 10) {
                contador = 0;
                return new Municion("municion1.png", posicion, angulo);
            }

            contador++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
        // municion.avanzar(this.posicion, angulo);
    }

    public void setVelocidad(int velocidad) {
        if(velocidad > 1)
            this.velocidad = velocidad;
    }

    @Override
    public void update() {
        rotar();
    }

    @Override
    public void draw(Graphics2D g) {
        Graphics2D g1 = (Graphics2D)g.create();

        g1.rotate(angulo, posicion.x+12, posicion.y);
        g1.drawImage(grafico, posicion.x+12, posicion.y, null);
    }
}
