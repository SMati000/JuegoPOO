import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pow extends PowerUp {
    private BufferedImage imagen = null;
    @Override
    public void AsignarBonus() {
        
        
    }
    public Pow(String filename) throws IOException {
        imagen = ImageIO.read(Mapa.class.getResource(filename));
    }
    public void draw(Graphics2D g2) {
        g2.drawImage(imagen,400, 300, null);
    }

    
}
