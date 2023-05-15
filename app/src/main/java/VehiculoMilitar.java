import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class VehiculoMilitar {
    protected String nombre;
    protected BufferedImage grafico;
    Point posicion;

    public VehiculoMilitar(String nombre, BufferedImage grafico) {
        this.nombre = nombre;
        this.grafico = grafico;
        this.posicion = new Point(0, 0); 
    }

    public void setPosicion(Point p) {
        this.posicion = p;
    }

    public Point getPosicion() {
        return this.posicion;
    }

    public void setX(int x) {
        this.posicion.x = x;
    }

    public void setY(int y) {
        this.posicion.y = y;
    }

    public int getX() {
        return this.posicion.x;
    }

    public int getY() {
        return this.posicion.y;
    }

    public abstract void disparar();
    public abstract void destruir();

    public void draw(Graphics2D g){
        g.drawImage(grafico, posicion.x, posicion.y, null);
    }
}