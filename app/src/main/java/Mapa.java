import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mapa {
    private BufferedImage imagen = null;

	private double positionX = 0;
	private double positionY = 0;
	
    public Mapa(String filename) throws IOException {
        imagen = ImageIO.read(getClass().getResource(filename));
        this.positionX = this.positionY = 0;
    }

	public int getWidth(){
		return imagen.getWidth();
	}

	public int getHeight(){
		return imagen.getHeight();
	}

	public void setPosition(int x,int y){
		this.positionX = x;
		this.positionY = y;
	}

   	public void display(Graphics2D g2) {
			g2.drawImage(imagen, (int)this.positionX, 
			(int)(this.positionY-imagen.getHeight()+Juego1943.getInstance().getHeight()+25), null);
	}

	public double getX(){
		return positionX;
	}

	public double getY(){
		return positionY;
	}
}
