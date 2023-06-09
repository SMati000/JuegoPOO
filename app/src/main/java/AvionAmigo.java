import java.awt.Point;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AvionAmigo extends VehiculoMilitar {
    public static enum Iconos {
        COMUN("avionAmigo.png"), IZQ("avionAmigoIzq.png"), DER("avionAmigoDer.png"),
        BAJANDO1("avionAmigoBajando1.png"), BAJANDO2("avionAmigoBajando2.png");

        private final String filename;
        private Iconos(String filename) {
            this.filename = filename;
        }
    }

    private Iconos icono;

    public AvionAmigo(Point posicion) throws IOException {
        super("Avion Amigo", "avionAmigo.png", posicion);
        this.resistencia = this.energia/50;
        this.icono = Iconos.COMUN;

        this.arma.seguir(false);
        this.arma.setAngulo(180);
        this.arma.setFrecuenciaDisparos(5);
        this.arma.setTiros(2, new double[]{0, 0});

        // this.arma.setTiros(4, new double[]{-20, 0, 0, 20});
        // this.arma.setModoDisparo(true);
    }

    public void setIcon(Iconos ICONO) {
        try {
            this.setGrafico(ImageIO.read(AvionAmigo.class.getResource("imagenes/" + ICONO.filename)));
            this.icono = ICONO;
        } catch (IOException e) {
            System.out.println("Error animacion avion amigo en metodo setIcon");
        }
    }

    public Iconos getIcon() {
        return this.icono;
    }

    @Override
    public Municion[][] disparar() {
        return new Municion[][]{arma.disparar()};
    }
}