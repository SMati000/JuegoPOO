import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemigo extends VehiculoMilitar {
    protected Arma arma;

    protected int velocidad, rangoDeteccion;

    protected BufferedImage comun, izq, der;

    protected final Point objetivo;

    public Enemigo(String grafico, Point posicion, Point objetivo) throws IOException {
        super("enemigo", grafico, posicion);
        this.comun = ImageIO.read(Enemigo.class.getResource("imagenes/" + grafico));

        this.objetivo = objetivo;
        this.velocidad = 5;
        this.rangoDeteccion = 400;

        arma = new Arma(this.getPosicion(), objetivo, "armaBarco1.png");
    }

    public Enemigo(Enemigo enemigo) throws IOException {
        super(enemigo.nombre, "", (Point)enemigo.getPosicion().clone());
        
        this.setGrafico(enemigo.grafico);
        this.comun = enemigo.grafico;
        this.izq = enemigo.izq;
        this.der = enemigo.der;

        this.objetivo = enemigo.objetivo;
        this.velocidad = 5;
        this.rangoDeteccion = 400;

        arma = new Arma(this.getPosicion(), objetivo, "armaBarco1.png");
    }

    public void setGraficosDoblar(String izq, String der) {
        try {
            this.izq = ImageIO.read(VehiculoMilitar.class.getResource("imagenes/" + izq));
            this.der = ImageIO.read(VehiculoMilitar.class.getResource("imagenes/" + der));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad < 10 ? velocidad : 10;
    }

    public void avanzar() {
        posicion.y += velocidad;
    }

    public boolean objetivoEnRadar() {
        int distancia = objetivo.y - this.posicion.y;

        return distancia > 0 && distancia <= rangoDeteccion;
    }

    @Override
    public Municion disparar() {
        if(this.objetivoEnRadar())
            return arma.disparar();
         
        return null;
    }

    @Override
    public void destruir() {}

    @Override
    public void update() {
        this.avanzar();
        arma.update();

        if(this.objetivoEnRadar()) {
            this.setVelocidad(3);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(grafico, posicion.x, posicion.y, null);
        arma.draw(g);
    }
}
