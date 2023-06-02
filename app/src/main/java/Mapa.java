import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mapa extends ObjetoGrafico {
    private BufferedImage aire = null, tierra = null, transicion = null;
	
    public Mapa(String filenameAire, String filenameTierra) throws IOException {
		super("mapa", filenameAire, new Point(0, 0));
        aire = ImageIO.read(getClass().getResource("imagenes/" + filenameAire));
		grafico = aire;

        tierra = ImageIO.read(getClass().getResource("imagenes/" + filenameTierra));
    }

	public int getWidth(){
		return grafico.getWidth();
	}

	public int getHeight(){
		return grafico.getHeight();
	}

	public void setPosition(int x, int y){
		this.posicion.x = x;
		this.posicion.y = y;
	}

	public void setTransicion(String filename) {
		try {
			this.transicion =  ImageIO.read(getClass().getResource("imagenes/" + filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// private int contador = 0;
	public void cambiarMapa() {
		if(grafico == aire) {
			if(transicion != null)
				grafico = transicion;
			else
				grafico = tierra;
		}
		else if(grafico == transicion)
			grafico = tierra;
	}

    @Override
    public void update() {}

	@Override
   	public void draw(Graphics2D g) {
			g.drawImage(grafico, this.posicion.x, 
			this.posicion.y-grafico.getHeight()+Juego1943.getInstance().getHeight()+25, null);
	}

	public int getX(){
		return posicion.x;
	}

	public int getY(){
		return posicion.y;
	}
}
