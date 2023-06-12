import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;


public abstract class Enemigo extends VehiculoMilitar {
    protected int velocidad, rangoDeteccion;

    // protected BufferedImage comun, izq, der;

    protected Point objetivo;

    public Enemigo(String grafico, Point posicion, Point objetivo) throws IOException {
        super("enemigo", grafico, posicion);
        // this.comun = ImageIO.read(Enemigo.class.getResource("imagenes/" + grafico));

        this.objetivo = objetivo;
        this.velocidad = 2;
        this.rangoDeteccion = 400;

        arma.setObjetivo(objetivo);
    }
    

    //puntaje que se da por destruir
    public int puntajeDado(){
        return 0; //no se si va el 0, lo puse porque despues se sobreescribe
    }


    public Enemigo(Enemigo enemigo) throws IOException {
        super(enemigo.nombre, null, (Point)enemigo.getPosicion().clone());
        
        this.setGrafico(enemigo.grafico);
        
        this.objetivo = enemigo.objetivo;
        this.velocidad = enemigo.velocidad;
        this.rangoDeteccion = enemigo.rangoDeteccion;
        
        arma.setObjetivo(enemigo.objetivo);
    }

    // public void setGraficosDoblar(String izq, String der) {
    //     try {
    //         this.izq = ImageIO.read(VehiculoMilitar.class.getResource("imagenes/" + izq));
    //         this.der = ImageIO.read(VehiculoMilitar.class.getResource("imagenes/" + der));
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad < 10 ? velocidad : 10;
    }

    protected void avanzar() {
        posicion.y += velocidad;
    }

    public boolean objetivoEnRadar() {
        int distancia = objetivo.y - this.posicion.y;

        return distancia > 0 && distancia <= rangoDeteccion;
    }

    @Override
    public Municion[][] disparar() {
        if(this.objetivoEnRadar())
            return new Municion[][]{arma.disparar()};
         
        return null;
    }

    @Override
    public void update() {
        this.avanzar();
        arma.update(new Point(this.getX()+this.grafico.getWidth()/2, this.getY()+this.grafico.getHeight()/2));

        if(this.objetivoEnRadar()) {
            this.setVelocidad(2);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(grafico, posicion.x, posicion.y, null);
        arma.draw(g);
    }

    public abstract Enemigo clone();
}
