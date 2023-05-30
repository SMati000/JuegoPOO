import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemigo extends VehiculoMilitar {
    protected int velocidad, rangoDeteccion;
    protected BufferedImage comun, izq, der;

    public Enemigo(String nombre, String grafico, Point posicion) throws IOException {
        super(nombre, grafico, posicion);
        this.comun = ImageIO.read(Enemigo.class.getResource("imagenes/" + grafico));
        rangoDeteccion = 400;
    }

    public Enemigo(Enemigo enemigo) throws IOException {
        super(enemigo.nombre, "", (Point)enemigo.getPosicion().clone());
        
        this.setGrafico(enemigo.grafico);
        this.comun = enemigo.grafico;
        this.izq = enemigo.izq;
        this.der = enemigo.der;

        rangoDeteccion = 400;
    }

    public void setGraficosDoblar(String izq, String der) {
        try {
            this.izq = ImageIO.read(VehiculoMilitar.class.getResource("imagenes/" + izq));
            this.der = ImageIO.read(VehiculoMilitar.class.getResource("imagenes/" + der));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad < 10 ? velocidad : 10;
    }

    public void avanzar() {
        posicion.y += velocidad;
    }

    public boolean objetivoEnRadar(int objetivoY) {
        int distancia = objetivoY - this.posicion.y;

        return distancia > 0 && distancia <= rangoDeteccion;
    }

    @Override
    public void disparar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disparar'");
    }

    @Override
    public void destruir() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'destruir'");
    }
    
}
