import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Impacto {
    public static enum tipoImpacto {
        DISPARO(new int[]{1, 2, 3, 4});

        private int[] secuencia;
        private tipoImpacto(int[] sec) {
            secuencia = sec;
        }
    }

    private Point objetivo;
    private final tipoImpacto tipo;
    private final BufferedImage[] impactos;
    private int puntero;
    private boolean entrar;

    public Impacto(Point objetivo, tipoImpacto tipo) throws IOException {
        this.objetivo = objetivo;
        this.tipo = tipo;
        this.puntero = 0;
        this.entrar = true;
        this.impactos = new BufferedImage[4];

        impactos[0] = ImageIO.read(Impacto.class.getResource("imagenes/impacto1.png"));
        impactos[1] = ImageIO.read(Impacto.class.getResource("imagenes/impacto2.png"));
        impactos[2] = ImageIO.read(Impacto.class.getResource("imagenes/impacto3.png"));
        impactos[3] = ImageIO.read(Impacto.class.getResource("imagenes/impacto4.png"));
    }

    public void setObjetivo(Point objetivo) {
        this.objetivo = objetivo;
    }

    public void reset() {
        puntero = 0;
    }

    public void draw(Graphics2D g) {
        if(entrar && puntero < tipo.secuencia.length) {
            g.drawImage(impactos[tipo.secuencia[puntero]-1], objetivo.x, objetivo.y, null);
            puntero++;
        }

        entrar = !entrar;
    }
}