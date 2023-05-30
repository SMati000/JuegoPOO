import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mapa {
    private BufferedImage aire = null, tierra = null, enUso = null, transicion = null;

	private double positionX = 0;
	private double positionY = 0;
	
    public Mapa(String filenameAire, String filenameTierra) throws IOException {
        aire = ImageIO.read(getClass().getResource(filenameAire));
		enUso = aire;

        tierra = ImageIO.read(getClass().getResource(filenameTierra));
        this.positionX = this.positionY = 0;
    }

	public int getWidth(){
		return enUso.getWidth();
	}

	public int getHeight(){
		return enUso.getHeight();
	}

	public void setPosition(int x, int y){
		this.positionX = x;
		this.positionY = y;
	}

	public void setTransicion(String filename) {
		try {
			this.transicion =  ImageIO.read(getClass().getResource(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// private int contador = 0;
	public void cambiarMapa() {
		if(enUso == aire) {
			if(transicion != null)
				enUso = transicion;
			else
				enUso = tierra;
		}
		else if(enUso == transicion)
			enUso = tierra;
	}

   	public void draw(Graphics2D g) {
			g.drawImage(enUso, (int)this.positionX, 
			(int)(this.positionY-enUso.getHeight()+Juego1943.getInstance().getHeight()+25), null);
	}

	public double getX(){
		return positionX;
	}

	public double getY(){
		return positionY;
	}
}
