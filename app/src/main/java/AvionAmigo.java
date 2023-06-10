import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AvionAmigo extends VehiculoMilitar {
    Jugador1943 jugador;
    
    public static enum Iconos {
        COMUN("avionAmigo.png"), IZQ("avionAmigoIzq.png"), DER("avionAmigoDer.png"),
        BAJANDO1("avionAmigoBajando1.png"), BAJANDO2("avionAmigoBajando2.png");

        private final String filename;
        private Iconos(String filename) {
            this.filename = filename;
        }
    }

    private Iconos icono;
    private double angulo;
    private boolean esquivando;

    public AvionAmigo(Point posicion) throws IOException {
        super("Avion Amigo", "avionAmigo.png", posicion);
        this.jugador = ((Juego1943)Juego1943.getInstance()).getJugador();  //esta instancia es para poder pasar el puntaje al jugador
        this.resistencia = this.energia/50;
        this.icono = Iconos.COMUN;
        this.angulo = 0;
        this.esquivando = false;

        this.arma.seguir(false);
        this.arma.setAngulo(180);
        this.arma.setFrecuenciaDisparos(5);
        this.arma.setTiros(2, new double[]{0, 0});
    }

    public void setIcon(Iconos ICONO) {
        if(!esquivando) {
            try {
                this.setGrafico(ImageIO.read(AvionAmigo.class.getResource("imagenes/" + ICONO.filename)));
                this.icono = ICONO;
            } catch (IOException e) {
                System.out.println("Error animacion avion amigo en metodo setIcon");
            }
        }
    }
    
    private int contador = 0;
    @Override
    public void update() {
        super.update();

        if(esquivando) {
            if(contador == 5 && Math.abs(angulo) < Math.PI*2) {
                angulo -= 0.2;

                this.posicion.x += 10 * Math.sin(angulo);
                this.posicion.y -= 10 * Math.cos(angulo);

                contador = 0;
            } else if(Math.abs(angulo) >= Math.PI*2) {
                angulo = 0;
                contador = 0;
                esquivando = false;

                Rectangle pantalla = ((Juego1943)Juego1943.getInstance()).getViewPort();

                if(this.getX() < 0) {
                    this.posicion.x = 0;
                } else if(this.getX() > pantalla.getWidth()) {
                    this.posicion.x = (int)(pantalla.getWidth() - (int)this.grafico.getWidth());
                }

                if(this.getY() > pantalla.getY() + pantalla.getHeight()) {
                    this.posicion.y = (int)(pantalla.getY() + pantalla.getHeight() - this.grafico.getHeight());
                } else if(this.getY() + this.grafico.getHeight() < pantalla.getY()) {
                    this.posicion.y = (int)(pantalla.getY() + this.grafico.getHeight());
                }
            }
    
            contador++;
        }
    }

    public boolean isEsquivando() {
        return esquivando;
    }

    public void esquivar() {
        this.esquivando = true;
    }

    public void modificarEnergia(double deltaE) {
        if(!esquivando) {
            super.modificarEnergia(deltaE);
        }
    }

    public void setX(int x) {
        if(!esquivando) {
            super.setX(x);
        }
    }

    public void setY(int y) {
        if(!esquivando) {
            super.setY(y);
        }
    }

    public Iconos getIcon() {
        return this.icono;
    }

    public void pasarPuntaje(int puntos){
        jugador.setPuntaje(puntos);
    }

    public int PuntajeJugador(){
        return jugador.getPuntaje();
    }

    @Override
    public Municion[][] disparar() {
        return new Municion[][]{arma.disparar()};
    }
    
    public void draw(Graphics2D g) {
        Graphics2D g1 = (Graphics2D) g.create();

        if(esquivando) {
            g1.rotate(angulo, this.getX()+this.grafico.getWidth()/2, this.getY()+this.grafico.getHeight()/2);
        }

        g1.drawImage(grafico, posicion.x, posicion.y, null);
        arma.draw(g);
    }
}