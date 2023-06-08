import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

public class Municion extends ObjetoGrafico {
    private int velocidad/*, tiros*/;
    private double angulo;

    public Municion(String filename, Point posicion, double angulo) throws IOException {
        super("Municion", filename, posicion);
    
        this.angulo = angulo;
        this.velocidad = 6;
        // this.tiros = 1;
    }

    // public void setTiros(int cantidad) {
    //     this.tiros = cantidad > 0 ? cantidad : 1;
    // }

    private void avanzar() {
        if(angulo%Math.PI != 0) {    
            if(Math.sin(angulo) < 0)
                this.posicion.x += 1 + Math.abs(Math.sin(angulo)) * velocidad;
            else 
                this.posicion.x -= 1 + Math.abs(Math.sin(angulo)) * velocidad;
        }

        this.posicion.y += 1 + Math.cos(angulo) * velocidad;
    }

    public void setVelocidad(int velocidad) {
        if(velocidad > 1)
            this.velocidad = velocidad;
    }

    // public int getTiros() {
    //     return tiros;
    // }

    @Override
    public void update() {
        avanzar();
    }

    private final int gap = grafico.getWidth() + 5;
    @Override
    public void draw(Graphics2D g) {
        if(this.posicion != null) {
            Graphics2D g1 = (Graphics2D)g.create();

            int x = posicion.x;
            int y = posicion.y;
            
            g1.rotate(angulo, x, y);

            // if(angulo < 0) 
                y += (int)(this.grafico.getHeight()/2 /** Math.sin(angulo)*/);

            x -= this.grafico.getWidth()/2;

            g1.drawImage(grafico, x, y, null);
        }
    }
}
