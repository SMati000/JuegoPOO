import java.io.IOException;

import javax.imageio.ImageIO;

public class AvionAmigo extends VehiculoMilitar {
    public static enum Iconos {
        COMUN("avionAmigo.png"), IZQ("avionAmigoIzq.png"), DER("avionAmigoDer.png");

        public final String filename;
        private Iconos(String filename) {
            this.filename = filename;
        }
    }

    public AvionAmigo() throws IOException {
        super("Avion Amigo", ImageIO.read(AvionAmigo.class.getResource("imagenes/avionAmigo.png")));
    }

    public void setIcon(Iconos ICONO) {
        try {
            this.setGrafico(ImageIO.read(AvionAmigo.class.getResource("imagenes/" + ICONO.filename)));
        } catch (IOException e) {
            System.out.println("Error animacion avion amigo izq/der");
        }
    }

    @Override
    public void disparar() {}

    @Override
    public void destruir() {}
    
}
