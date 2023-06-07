import java.awt.Point;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

public class Arma extends ObjetoGrafico {
    private Point objetivo;

    private int frecuenciaDisparo, velocidadGiro;
    private boolean seguir;
    private int cantTiros;
    private double anguloMax;

    private double angulo;
    
    public Arma(Point posicion) throws IOException {
        super("Arma", null, posicion);

        this.velocidadGiro = 6;
        this.angulo = 0;

        this.frecuenciaDisparo = 25; // cada 25 frames
        this.seguir = false;
        this.cantTiros = 1;
        this.anguloMax = 90;
    }

    public void setObjetivo(Point objetivo) {
        this.objetivo = objetivo;
        this.posPrevia = (Point) this.objetivo.clone();
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
        this.grafico = arma.grafico;
    }

    public void setPosicion(Point posicion) {
        this.posicion = (Point) posicion.clone();
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

                // angulo = seguir ? angulo : 0;

                Point tempPos = new Point(this.posicion.x, this.posicion.y);

                if(grafico != null) {
                    tempPos.y -= this.grafico.getHeight()/2;
                }

                Municion muni = new Municion(
                    "municion1.png", 
                    tempPos, 
                    angulo
                );
                
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

        if(grafico != null) {
            this.posicion.x += this.grafico.getWidth()/2;
            this.posicion.y += this.grafico.getHeight()/2;
        }

        if(seguir) {           // 60 fps
            if(contadorVelGiro == 60/this.velocidadGiro) {
                
                if(this.objetivo != null) {
                    posPrevia = (Point) this.objetivo.clone();
                }

                contadorVelGiro = 0;
            }
            
            if(posPrevia != null) {
                this.rotar();
            }
            
            contadorVelGiro++;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        Graphics2D g1 = (Graphics2D)g.create();

        g1.rotate(angulo, posicion.x, posicion.y);
        
        if(this.grafico != null) {
            g1.drawImage(grafico, posicion.x - this.grafico.getWidth()/2, posicion.y-this.grafico.getHeight()/2, null);
        } else {
            g1.drawImage(grafico, posicion.x, posicion.y, null);
        }
    }
}