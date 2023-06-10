import java.awt.Point;
import java.io.IOException;

public class AvionEnemigo extends Enemigo {
    public AvionEnemigo(String filename, Point posicion, Point objetivo) throws IOException {
        super(filename, posicion, objetivo);
        this.resistencia = this.energia/5;
        this.arma.setFrecuenciaDisparos(30);
    }
    
    public AvionEnemigo(AvionEnemigo enemigo) throws IOException {
        super(enemigo);
        this.resistencia = enemigo.resistencia;
        this.arma = new Arma(enemigo.arma);
    }
    
    // public void seguirHorizontalmente() {
        // if(objetivo.x - this.posicion.x > 20) { // a la izq del objetivo
        //     this.posicion.x = this.posicion.x + 1;
        //     this.grafico = this.der;
        // } else if(objetivo.x - this.posicion.x < -20) { // a la der del objetivo
        //     this.posicion.x = this.posicion.x - 1;
        //     this.grafico = this.izq;
        // } 
        
        // if(!this.objetivoEnRadar(objetivo.y - 5)) { 
        //     this.grafico = this.comun;
        // }
    // }

    public Municion[][] disparar() {
        if(this.objetivoEnRadar() && 
            Math.abs((this.posicion.x + (grafico.getWidth()/2)) - (objetivo.x + (grafico.getWidth()/2))) < 70)
            return new Municion[][]{arma.disparar()};
         
        return null;
    }

    @Override
    public Enemigo clone() {
        AvionEnemigo temp = null;

        try {
            temp = new AvionEnemigo(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }
    
    // @Override
    // public void update() {
    //     this.avanzar();
    //     arma.update();

    //     if(this.objetivoEnRadar()) {
    //         this.setVelocidad(2);
        
    //         // this.seguirHorizontalmente();
    //     }
    // }
    
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
