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

    public AvionAmigo(Point posicion) throws IOException {
        super("Avion Amigo", "avionAmigo.png", posicion);
        this.resistencia = this.energia/50;
    }

    public void setIcon(Iconos ICONO) {
        try {
            this.setGrafico(ImageIO.read(AvionAmigo.class.getResource("imagenes/" + ICONO.filename)));
        } catch (IOException e) {
            System.out.println("Error animacion avion amigo en metodo setIcon");
        }
    }

    @Override
    public Municion[] disparar() {
        return null;
    }

    @Override
    public void destruir() {}

    @Override
    public void update() {}
    
}