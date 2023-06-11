import java.awt.Point;
import java.io.IOException;



public class AvionesRojos extends Enemigo{

    private AvionesRojos[] aviones;
    
    
    public AvionesRojos(Point posicion) throws IOException{
       super("avionEspecial.png", posicion, new Point(0, 0));
       this.resistencia = this.energia/2;
       this.objetivo = null;
       
    }


    public Municion[][] disparar(){
        return null;
    }

    public void crearAvionRojo(Point posicion) throws IOException{
        
        AvionesRojos a1 = new AvionesRojos(posicion);
        AvionesRojos a2 = new AvionesRojos(posicion);
        AvionesRojos a3 = new AvionesRojos(posicion);
        
        aviones = new AvionesRojos[3];
        aviones[0]= a1;       
        aviones[1]= a2;
        aviones[2]= a3;

        
        Formacion.iniciar(aviones, Formacion.FORMACIONES.SIMPLE);

    }

    public void update(){
        this.avanzar();
      
    }

    public void avanzar(){
        posicion.y += 1;
    }

    public void modificarEnergia(double deltaE) {
        super.modificarEnergia(deltaE);
    }

    @Override
    public Enemigo clone() {
      return null;
    }


}