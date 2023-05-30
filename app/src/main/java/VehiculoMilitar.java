import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class VehiculoMilitar {
    protected String nombre;
    protected BufferedImage grafico;
    protected Point posicion;

    public VehiculoMilitar(String nombre, String grafico, Point posicion) throws IOException {
        this.nombre = nombre;
        this.grafico = ImageIO.read(VehiculoMilitar.class.getResource("imagenes/" + grafico));
        this.posicion = posicion;
    }

    protected void setGrafico(BufferedImage grafico) {
        this.grafico = grafico;
    }

    // public void setPosicion(Point p) {
    //     this.posicion = p;
    //     // setX(p.x);
    //     // setY(p.y);
    // }

    public Point getPosicion() {
        return this.posicion;
    }

    public void setX(int x) {
        if(x > 0 && x < Juego1943.getInstance().getWidth()-grafico.getWidth())
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

    public void draw(Graphics2D g) {
        g.drawImage(grafico, posicion.x, posicion.y, null);
    }
}