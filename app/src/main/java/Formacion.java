import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Formacion extends ObjetoGrafico {
    public enum FORMACIONES {
        SIMPLE(1, 3), ROMBO(2, 4), PROTEGER(3, 5);
                                                            // el protegido sera el ultimo de los integrantes
        private int tipo, tamano;
        private FORMACIONES(int tipo, int tamano) {
            this.tipo = tipo;
            this.tamano = tamano;
        }
    }

    private Enemigo[] integrantes;
    private int tipo;

    public Formacion(Enemigo[] integrantes, FORMACIONES tipo) throws Exception { // el enemigo[0] sera quien dirija la formacion
        super("Formacion", null, null);

        if(integrantes.length != tipo.tamano) {
            throw new Exception("Error al crear formacion: La cantidad de " 
            + "integrantes no concuerdan con el tipo de formacion especificada");
        }
        
        this.integrantes = integrantes;                         
        this.tipo = tipo.tipo;
    }

    public void update() {
        switch(tipo) {
            case 1:
                integrantes[0].update();

                integrantes[1].setX(integrantes[0].getX()-integrantes[0].grafico.getWidth());
                integrantes[1].setY((int)(integrantes[0].getY()-integrantes[0].grafico.getHeight()));

                integrantes[2].setX(integrantes[0].getX()+integrantes[0].grafico.getWidth());
                integrantes[2].setY((int)(integrantes[0].getY()-integrantes[0].grafico.getHeight()));
                break;
            case 2:
                integrantes[0].update();

                integrantes[1].setX(integrantes[0].getX()-integrantes[0].grafico.getWidth());
                integrantes[1].setY((int)(integrantes[0].getY()-integrantes[0].grafico.getHeight()));

                integrantes[2].setX(integrantes[0].getX()+integrantes[0].grafico.getWidth());
                integrantes[2].setY((int)(integrantes[0].getY()-integrantes[0].grafico.getHeight()));
                
                integrantes[3].setX(integrantes[0].getX());
                integrantes[3].setY((int)(integrantes[0].getY()-integrantes[0].grafico.getHeight()*2));
                break;
            case 3:
                integrantes[0].update();

                integrantes[1].setX(integrantes[0].getX()-integrantes[0].grafico.getWidth()*2);
                integrantes[1].setY((int)(integrantes[0].getY()-integrantes[0].grafico.getHeight()));

                integrantes[2].setX(integrantes[0].getX()+integrantes[0].grafico.getWidth()*2);
                integrantes[2].setY((int)(integrantes[0].getY()-integrantes[0].grafico.getHeight()));
                
                integrantes[3].setX(integrantes[0].getX());
                integrantes[3].setY((int)(integrantes[0].getY()-integrantes[0].grafico.getHeight()*3));
                
                integrantes[4].setX(integrantes[0].getX());
                integrantes[4].setY((int)(integrantes[0].getY()-integrantes[0].grafico.getHeight()*1.5));
                break;
        }
    }

    public Point getPosicion() {
        return integrantes[0].getPosicion();
    }

    public static FORMACIONES getTipo(int tipo) {
        if(tipo == 1) {
            return FORMACIONES.SIMPLE;
        } else if(tipo == 2) {
            return FORMACIONES.ROMBO;
        } else if(tipo == 3) {
            return FORMACIONES.PROTEGER;
        }
        
        return FORMACIONES.SIMPLE;
    }

    // public ArrayList<Municion> disparar() {
    //     ArrayList<Municion> temp = new ArrayList<>();

    //     for(Enemigo e : integrantes) {
    //         Municion[][] municiones = e.disparar();

    //         if(municiones != null) {
    //             for(Municion[] ms : municiones) {
    //                 if(ms != null) {
    //                     for(Municion m : ms) {
    //                         if(m != null) {
    //                             temp.add(m);
    //                         }
    //                     }
    //                 }
    //             }
    //         }
    //     }

    //     return temp;
    // }

    // public Enemigo[] getIntegrantes() {
    //     return this.integrantes;
    // }

    @Override
    public void draw(Graphics2D g) {
        for(Enemigo e : integrantes) {
            e.draw(g);
        }
    }
}
