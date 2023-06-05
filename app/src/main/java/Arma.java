import java.awt.Point;
import java.awt.Rectangle;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.IOException;

public class Arma extends ObjetoGrafico {
    private final Point objetivo;

    private int frecuenciaDisparo, velocidadGiro;
    private boolean seguir;
    private int cantTiros;
    private double anguloMax;

    private double angulo;
    
    public Arma(Point posicion, Point objetivo) throws IOException {
        super("Arma", null, posicion);

        this.objetivo = objetivo;
        this.posPrevia = (Point) this.objetivo.clone();

        this.velocidadGiro = 6;
        this.angulo = 0;

        this.frecuenciaDisparo = 15; // cada 15 frames
        this.seguir = false;
        this.cantTiros = 1;
        this.anguloMax = 90;
    }

    public Arma(Arma arma) throws IOException {
        super("Arma", null, arma.posicion);

        this.objetivo = arma.objetivo;
        this.posPrevia = (Point) arma.objetivo.clone();

        this.velocidadGiro = arma.velocidadGiro;
        this.angulo = arma.angulo;

        this.frecuenciaDisparo = arma.frecuenciaDisparo; // cada 15 frames
        this.seguir = arma.seguir;
        this.cantTiros = arma.cantTiros;
        this.anguloMax = arma.anguloMax;
        grafico = arma.grafico;
    }

    public void setGrafico(String filename) {
        try {
            grafico = ImageIO.read(getClass().getResource("imagenes/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void seguir(boolean seguir) {
        this.seguir = seguir;
    }

    public void setTiros(int cantidad) {
        this.cantTiros = cantidad > 0 ? cantidad : 1;
    }

    public void setFrecuenciaDisparos(int frecuenciaDisparo) {
        this.frecuenciaDisparo = frecuenciaDisparo;
    }

    public void setAnguloMaximo(double anguloMax) {
        if(anguloMax >= 0 && anguloMax <= 180)
            this.anguloMax = anguloMax;
    }

    public void setAngulo(double angulo) {
        this.angulo = Math.toRadians(angulo);
    }

    private void rotar() {
        double y = this.posicion.getLocation().y - posPrevia.y;
        double x = this.posicion.getLocation().x - posPrevia.x - 20;

        if(Math.abs(x) > 10) {
            double tempAng = -(Math.abs(x)/x) * Math.atan(-Math.abs(x/y));

            if(x > 0 && y > 0) {
                tempAng = Math.PI - Math.abs(tempAng); 
            } else if(x < 0 && y > 0) {
                tempAng += Math.PI;
                tempAng = -tempAng; 
            }

            // if(Math.toDegrees(tempAng) < anguloMax /* && y < -10 */) {
                // angulo = tempAng;
                angulo = Math.abs(Math.toDegrees(tempAng)) < anguloMax ? tempAng : angulo;
            // }
        }
    }

    private int contadorFrecDisp = 0;
    public Municion disparar() {
        try {
            if(contadorFrecDisp == this.frecuenciaDisparo) {
                contadorFrecDisp = 0;

                angulo = seguir ? angulo : 0;

                Municion muni = new Municion("municion1.png", this.posicion.getLocation(), angulo);
                muni.setTiros(cantTiros);

                return muni;
            }

            contadorFrecDisp++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update() {}

    private Point posPrevia;
    private int contadorVelGiro = 0;
    public void update(Point posicion) {
        this.posicion = (Point) posicion.clone();

        if(seguir) {           // 60 fps
            if(contadorVelGiro == 60/this.velocidadGiro) {
                posPrevia = (Point) this.objetivo.clone();
                contadorVelGiro = 0;
            }
            
            this.rotar();
            contadorVelGiro++;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        Graphics2D g1 = (Graphics2D)g.create();

        int x = this.posicion.x;
        int y = this.posicion.y;

        if(this.grafico != null) {
            g1.rotate(angulo, x+this.grafico.getWidth()/2, y+this.grafico.getHeight()/2);
        } else {
            g1.rotate(angulo, x, y);
        }

        g1.drawImage(grafico, x, y, null);
    }
}
