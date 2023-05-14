import java.awt.*;

public class Juego1943 extends Juego{

    public Juego1943(){
        super("1943", "Juego 1943");
    }

    @Override
    public void gameStartup() {
        
    }

    @Override
    public void gameShutdown() {
        
    }

    @Override
    public void gameUpdate(double arg0) {
        
    };

    @Override
    public void gameDraw(Graphics2D g) {
        g.setColor(Color.white);
        g.fillOval(-200, -200, 1500, 1200); // fondo trucho

        g.setColor(Color.black);
        g.drawString("Juego llamado: " + nombre, 300, 290);
        g.drawString("Descripcion: " + descripcion, 300, 310);
    }
}
