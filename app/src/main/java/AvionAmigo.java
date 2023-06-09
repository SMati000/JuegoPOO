import java.awt.Point;
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

    public AvionAmigo(Point posicion) throws IOException {
        super("Avion Amigo", "avionAmigo.png", posicion);
        this.jugador = ((Juego1943)Juego1943.getInstance()).getJugador();  //esta instancia es para poder pasar el puntaje al jugador
        this.resistencia = this.energia/50;

        this.arma.seguir(false);
        this.arma.setAngulo(180);
        this.arma.setFrecuenciaDisparos(5);
        this.arma.setTiros(2, new double[]{0, 0});
        
        // this.arma.setTiros(4, new double[]{-20, 0, 0, 20});
        // this.arma.setModoDisparo(true);
    }

    public void modificarArma(boolean b){
        this.arma.setModoDisparo(b);
    }

    public void setIcon(Iconos ICONO) {
        try {
            this.setGrafico(ImageIO.read(AvionAmigo.class.getResource("imagenes/" + ICONO.filename)));
        } catch (IOException e) {
            System.out.println("Error animacion avion amigo en metodo setIcon");
        }
    }

    public void pasarPuntaje(int puntos){
        jugador.setPuntaje(puntos);
    }

    @Override
    public Municion[][] disparar() {
        return new Municion[][]{arma.disparar()};
    }

    @Override
    public void destruir() {}
}