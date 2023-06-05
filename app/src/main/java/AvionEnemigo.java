import java.awt.Point;
import java.io.IOException;

public class AvionEnemigo extends Enemigo {
    public AvionEnemigo(String filename, Point posicion, Point objetivo) throws IOException {
        super(filename, posicion, objetivo);
    }
    
    public AvionEnemigo(Enemigo enemigo) throws IOException {
        super(enemigo);
    }
    
    public void seguirHorizontalmente() {
        if(objetivo.x - this.posicion.x > 20) { // a la izq del objetivo
            this.posicion.x = this.posicion.x + 1;
            this.grafico = this.der;
        } else if(objetivo.x - this.posicion.x < -20) { // a la der del objetivo
            this.posicion.x = this.posicion.x - 1;
            this.grafico = this.izq;
        } 
        
        // if(!this.objetivoEnRadar(objetivo.y - 5)) { 
        //     this.grafico = this.comun;
        // }
    }

    // public void disparar() {}
    
    @Override
    public void update() {
        this.avanzar();
        arma.update();

        if(this.objetivoEnRadar()) {
            this.setVelocidad(3);
        
            this.seguirHorizontalmente();
        }
    }
    
    // public void draw(Graphics2D g) {
    //     g.drawImage(grafico, posicion.x, posicion.y, null);
    //     arma.draw(g);
        
    //     // if(objetivo != null) {
    //     //     municion.avanzar(g, objetivo);
    //     // }
        
    //     // if(municion.impacto()) {
    //     //     disparo.setObjetivo(objetivo);
    //     //     disparo.draw(g);
    //     // }
    // }
}
