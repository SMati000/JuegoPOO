import java.io.IOException;

import javax.imageio.ImageIO;

public class AvionAmigo extends VehiculoMilitar {

    public AvionAmigo() throws IOException {
        super("Avion Amigo", ImageIO.read(AvionAmigo.class.getResource("imagenes/1943ico.png")));
    }

    @Override
    public void disparar() {}

    @Override
    public void destruir() {}
    
}
