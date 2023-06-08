import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

public abstract class Bonus extends ObjetoGrafico{
    private int tiempo;                 //tiempo que dura en en el avion
    private boolean visible;
    protected double vidaBonus, resistenciaBonus;
    
    public Bonus(String nombre, String grafico, Point posicion) throws IOException {
        super(nombre, grafico, posicion);
        this.vidaBonus = 100;
        this.resistenciaBonus = vidaBonus/5;
    }

    public void draw(Graphics2D g) {
        g.drawImage(grafico, posicion.x, posicion.y, null);
    }   
    public void modificarVida(double deltaE){
        if((vidaBonus + deltaE) > 0){
            //System.out.println("entra if");
            vidaBonus +=  deltaE;
        }else { 
            vidaBonus = 0;
           destruir();
        }
    }

    public double getResistencia(){
        return this.resistenciaBonus;
    }

    public double getVida(){
        return this.vidaBonus;
    }
   
    public abstract Bonus clone();
 
    public abstract void AsignarBonus(AvionAmigo avion);
}
