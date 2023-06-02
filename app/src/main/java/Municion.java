import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

public class Municion extends ObjetoGrafico {
    private int velocidad;
    private double angulo;

    public Municion(String filename, Point posicion, double angulo) throws IOException {
        super("Municion", filename, posicion);
    
        this.angulo = angulo;
        this.velocidad = 7;
    }

    private void avanzar() {
        if(Math.sin(angulo) < 0)
            this.posicion.x += 1 + Math.abs(Math.sin(angulo)) * velocidad;
        else 
            this.posicion.x -= 1 + Math.abs(Math.sin(angulo)) * velocidad;

        this.posicion.y += 1 + Math.cos(angulo) * velocidad;
    }

    public void setVelocidad(int velocidad) {
        if(velocidad > 1)
            this.velocidad = velocidad;
    }

    @Override
    public void update() {
        avanzar();
    }

    @Override
    public void draw(Graphics2D g) {
        if(this.posicion != null) {
            Graphics2D g1 = (Graphics2D)g.create();

            // if(impacto()) {
            //     g1.dispose();
            //     return;
            // }

            g1.rotate(angulo, posicion.x, posicion.y);
            g1.drawImage(grafico, posicion.x, posicion.y, null);
        }
    }
}
