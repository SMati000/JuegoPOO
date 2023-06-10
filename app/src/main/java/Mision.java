import java.util.ArrayList;
import java.awt.*;
import java.io.IOException;

public class Mision { // pair, destruir, objetografico/interfaces, 
    public enum ESTADO {
        AIRE, TIERRA, FIN, GANA;
    }

    public enum DIFICULTAD {
        FACIL(1), DIFICIL(2);

        private final int dif;
        private DIFICULTAD(int dif) {
            this.dif = dif;
        }
    }
    private ESTADO estado;
    private final int factorProb = 10; 
    private final DIFICULTAD dificultad;

    private final AvionAmigo heroe;
    private final Enemigo[] enemigos;
    private Bonus[] bonus;
    private final Enemigo jefe;
    private final int apareceJefe = 30;

    private final int tiempo; // en segundos
    private int tiempoEnCurso; // en segundos
    private int contadorSegundo = 0; 

                              // enemigo, colisionDetectada 
    private final ArrayList<Enemigo> enemigosCreados;
    private final ArrayList<Municion> balasEnCurso;
    private final ArrayList<Municion> balasHeroe;
    private final ArrayList<Bonus> bonusCreados; 
    private final ArrayList<Impacto> impactos;

    private Mision(MisionBuilder builder) {
        enemigosCreados = new ArrayList<Enemigo>();
        balasEnCurso = new ArrayList<Municion>();
        balasHeroe = new ArrayList<Municion>();
        bonusCreados = new ArrayList<Bonus>();
        impactos = new ArrayList<Impacto>();
        
        estado = ESTADO.TIERRA;

        this.dificultad = builder.dificultad;

        this.heroe = builder.heroe;
        this.enemigos = builder.enemigos;
        this.jefe = builder.jefe;

        this.tiempo = builder.tiempo;
        this.tiempoEnCurso = this.tiempo;
        try{
            bonus = new Bonus[]{new Pow("pow.png", new Point(0, 0)),
                                new Auto("auto.png", new Point(0, 0)),
                                new EstrellaNinja("estrellaNinja.png", new Point(0, 0)),
                                new SuperShell("SuperShell.png", new Point(0, 0)),
                                new Refuerzos( "refuerzo.png", new Point(0, 0)),
                                // new AmetralladoraTresCañones( "ametralladora.png", new Point(0, 0)),
                                // new Laser( "laser.png", new Point(0, 0)),
                                // new Escopeta( "escopeta.png", new Point(0, 0)),

                                };

        }catch(IOException e){
            System.out.println("error al crear el bonus");
        }
        
    }

    public void update() {
        heroe.update();
        crearEnemigos();
        crearBonus();
                
        manejarBonus(); 
        manejarEnemigos();
        manejarImpactos();

        if(tiempoEnCurso == tiempo/2) {
            this.estado = ESTADO.TIERRA;
        } if(tiempoEnCurso == 0) {
            this.estado = ESTADO.FIN;
        }

        if(tiempoEnCurso <= apareceJefe) {
            manejarJefe();
        }
    }
 
    private void crearBonus(){
        int random = (int)(Math.floor(Math.random()*999+1));
        
        if(bonusCreados.size() < 1/dificultad.dif && random < 10/dificultad.dif ) {
            Bonus b = generarBonus();
            
            if(b != null) {
                bonusCreados.add(b);
            }
        }
    }
    
    private void reemplazarBonus(Bonus bn) {
        Bonus b = generarBonus();

        if(b == null) {
            bn.modificarVida(100); // Si hubiera un error, se le rellena la vida al bonus q ya hay y queda ese.
            return;
        }

        int i = 0; // solo para garantizar q no se quede en bucle mas de lo debido
        while(b.getClass().equals(bn.getClass()) && i < 10) {
            b = generarBonus();
            i++;
        }

        b.setX(bn.getX());
        b.setY(bn.getY());

        int index = bonusCreados.indexOf(bn);
        bonusCreados.remove(index);
        bonusCreados.add(index, b);
    }
        
    private Bonus generarBonus() {
        int randomBonus = (int)(Math.floor(Math.random()*((bonus.length-1)+1)));
        Bonus b = bonus[randomBonus].clone();

        b.setX((int)(Math.floor(Math.random()*(700-100+1)+100)));
        b.setY(heroe.getY()-500);    

        return b;
        
    }


    private void crearEnemigos() {
        int random = (int)(Math.floor(Math.random()*999+1));
                         // cuanto mas alta la dificultad, mas probabilidades de q se generen enemigos
        if(enemigosCreados.size() < 10*dificultad.dif && random < factorProb*dificultad.dif) { 

            int nuevoEnemigo = (int)(Math.floor(Math.random()*(enemigos.length-1-0+1)+0));
            
            if(enemigos[nuevoEnemigo].getClass().getName().equals("Barco") 
                && this.estado != ESTADO.TIERRA) {
                    return;
                }

            Enemigo e = enemigos[nuevoEnemigo].clone();

            int tempX = (int)(Math.floor(Math.random()*(700-100+1)+100));

            e.setX(tempX);
            e.setY(heroe.getY()-(int)(Math.floor(Math.random()*(1300-800+1)+800)));

            e.setVelocidad((int)(Math.floor(Math.random()*(10-2+1)+2)));

            enemigosCreados.add(e);
        }
    }
    
    private void manejarBonus() {
        for(int i = 0; i < bonusCreados.size(); i++) {
            Bonus temp = bonusCreados.get(i);
            Rectangle bonusRectangle = new Rectangle(temp.getPosicion(), 
                    new Dimension(temp.grafico.getWidth(), temp.grafico.getHeight()));

            if(new Rectangle(heroe.getPosicion(), new Dimension(heroe.grafico.getWidth(),heroe.grafico.getHeight()))
            .intersects(bonusRectangle)) {
                temp.AsignarBonus(heroe);
                bonusCreados.remove(i);
            }
            // eliminar enemigos fuera de la pantalla
            if(temp.getY() > (heroe.getY() + (Juego1943.getInstance().getHeight())) + 25) { 
                bonusCreados.remove(i);
                i--;
                continue;
            }
        }
    }

    private void manejarEnemigos() {
        for(int i = 0; i < enemigosCreados.size(); i++) {
            Enemigo temp = enemigosCreados.get(i);

            temp.update();

            // eliminar enemigos fuera de la pantalla
            if(temp.getY() > (heroe.getY() + (Juego1943.getInstance().getHeight())) + 25) { 
                temp.destruir();
                enemigosCreados.remove(i);
                i--;
                continue;
            }

            Municion municiones[][] = temp.disparar();

            if(municiones != null) {
                for(Municion[] ms : municiones) {
                    if(ms != null) {
                        for(Municion m : ms) {
                            if(m != null) {
                                balasEnCurso.add(m);
                            }
                        }
                    }
                }
            }
        }

        for(int i = 0; i < balasEnCurso.size(); i++) {
            Municion temp = balasEnCurso.get(i);
            
            temp.update();

            // eliminar balas fuera de la pantalla
            if(temp.getY() > (heroe.getY() + (Juego1943.getInstance().getHeight())) + 25) { 
                balasEnCurso.remove(i);
                i--;
                continue;
            }
        }

        for(int i = 0; i < balasHeroe.size(); i++) {
            Municion temp = balasHeroe.get(i);
            
            temp.update();

            // eliminar balas fuera de la pantalla
            if(!((Juego1943)(Juego1943.getInstance())).getViewPort().contains(
                    new Rectangle(temp.getPosicion(), new Dimension(temp.grafico.getWidth(), temp.grafico.getHeight())))) { 
                balasHeroe.remove(i);
                i--;
                continue;
            }
        }
    }

    private void manejarJefe() {
        if(tiempoEnCurso == apareceJefe) {
            jefe.setX(Juego1943.getInstance().getWidth()/2 - jefe.grafico.getWidth()/2);
            jefe.setY(heroe.getY()-1000);
        }

        Rectangle j = new Rectangle(jefe.getPosicion(), new Dimension(jefe.grafico.getWidth(), jefe.grafico.getHeight()));
        
        if(((Juego1943)Juego1943.getInstance()).getViewPort().contains(j)) {
            jefe.setY((int)(((Juego1943)Juego1943.getInstance()).getViewPort().getY()+35));
        }

        jefe.update();

        Municion municiones[][] = jefe.disparar();

        if(municiones != null) {
            for(Municion[] ms : municiones) {
                if(ms != null) {
                    for(Municion m : ms) {
                        if(m != null) {
                            balasEnCurso.add(m);
                        }
                    }
                }
            }
        }
    }

    private void manejarImpactos() {
        Rectangle avionAmigo = new Rectangle(heroe.getPosicion(), new Dimension(heroe.grafico.getWidth(), heroe.grafico.getHeight()));

        for(int i = 0; i < balasEnCurso.size(); i++) {
            Municion bala = balasEnCurso.get(i);

            if(new Rectangle(bala.getPosicion(), new Dimension(bala.grafico.getWidth(), bala.grafico.getHeight()))
            .intersects(avionAmigo)) {
                try {
                    impactos.add(new Impacto(
                        new Point(heroe.getX()+heroe.grafico.getWidth()/2, heroe.getY()+heroe.grafico.getHeight()/2-5), 
                        Impacto.tipoImpacto.DISPARO)
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                heroe.modificarEnergia(-heroe.getResistencia());
                
                balasEnCurso.remove(i);
            }
        }
     
                    
        
        for(int i = 0; i < balasHeroe.size(); i++) {
            Municion bala = balasHeroe.get(i);

            for(int j = 0; j < enemigosCreados.size(); j++) {
                Enemigo enemigo = enemigosCreados.get(j);
                Rectangle e = new Rectangle(enemigo.getPosicion(), 
                    new Dimension(enemigo.grafico.getWidth(), enemigo.grafico.getHeight()));

                if(new Rectangle(bala.getPosicion(), new Dimension(bala.grafico.getWidth(), bala.grafico.getHeight()))
                .intersects(e)) {
                    try {
                        impactos.add(new Impacto(
                            new Point(enemigo.getX()+enemigo.grafico.getWidth()/2, enemigo.getY()+enemigo.grafico.getHeight()/2+5), 
                            Impacto.tipoImpacto.DISPARO)
                        );
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    
                    enemigo.modificarEnergia(-enemigo.getResistencia());
                    
                    balasHeroe.remove(i);


//en este metodo se haria el modificar puntaje
                    if(enemigo.getEnergia() <= 0) {
                        heroe.pasarPuntaje(enemigo.puntajeDado());
                        enemigosCreados.remove(j);        
                    }
                }
            }

            if(new Rectangle(bala.getPosicion(), new Dimension(bala.grafico.getWidth(), bala.grafico.getHeight()))
                .intersects(new Rectangle(this.jefe.getPosicion(), new Dimension(this.jefe.grafico.getWidth(), this.jefe.grafico.getHeight())))) {
                try {
                    impactos.add(new Impacto(
                        new Point(this.jefe.getX()+this.jefe.grafico.getWidth()/2, this.jefe.getY()+this.jefe.grafico.getHeight()/2+5), 
                        Impacto.tipoImpacto.DISPARO)
                    );
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                
                this.jefe.modificarEnergia(-this.jefe.getResistencia());
                
                balasHeroe.remove(i);

                if(this.jefe.getEnergia() <= 0) {
                    this.estado = ESTADO.GANA;
                }
            }
        }        
        
        for ( int i = 0; i < bonusCreados.size(); i++) {
            Bonus b = bonusCreados.get(i);

            for (int j = 0; j < balasHeroe.size(); j++) {
                Municion bala = balasHeroe.get(j);
                Rectangle bonusRectangle = new Rectangle(b.getPosicion(), 
                    new Dimension(b.grafico.getWidth(), b.grafico.getHeight()));

                if(new Rectangle(bala.getPosicion(), new Dimension(bala.grafico.getWidth(), bala.grafico.getHeight()))
                .intersects(bonusRectangle)) {
                    
                    try {
                        impactos.add(new Impacto(new Point(b.getX()+ b.grafico.getWidth()/2, b.getY()+b.grafico.getHeight()/2),
                            Impacto.tipoImpacto.DISPARO));
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }

                    balasHeroe.remove(j);
    
                    b.modificarVida(-b.getResistencia());
                    
                    if(b.getVida() <= 0) {  
                        reemplazarBonus(b);
                    }
                }
            }
        }

        for(int i = 0; i < enemigosCreados.size(); i++) {
            Enemigo enemigo = enemigosCreados.get(i);
            
            if(enemigo.getClass().getName().equals("AvionEnemigo") && 
            new Rectangle(enemigo.getPosicion(), new Dimension(enemigo.grafico.getWidth(), enemigo.grafico.getHeight()))
            .intersects(avionAmigo)) {
                try {
                    impactos.add(new Impacto(
                        new Point(heroe.getX()+heroe.grafico.getWidth()/2, heroe.getY()+heroe.grafico.getHeight()/2-5), 
                        Impacto.tipoImpacto.COLISION)
                    );
                    
                        impactos.add(new Impacto(
                            new Point(enemigo.getX()+enemigo.grafico.getWidth()/2, enemigo.getY()+enemigo.grafico.getHeight()/2+5), 
                            Impacto.tipoImpacto.COLISION)
                        );
                        heroe.pasarPuntaje(enemigo.puntajeDado());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                enemigosCreados.remove(i);
                
                enemigo.modificarEnergia(-100);
                heroe.modificarEnergia(-heroe.getResistencia()*2); 
            }
        }
    }

    public void disparoHeroe(Municion bala) {
        balasHeroe.add(bala);
    }

    public ESTADO getEstado() {
        return estado;
    }

    public void draw(Graphics2D g, int pos) {
        bonusCreados.forEach(bonus -> {
            bonus.draw(g);
        });

        enemigosCreados.forEach(enemigo -> {
            enemigo.draw(g);
        });

        if(tiempoEnCurso <= apareceJefe) {
            jefe.draw(g);
        }
        
        heroe.draw(g);

        balasEnCurso.forEach(bala -> {
            bala.draw(g);
        });

        balasHeroe.forEach(bala -> {
            bala.draw(g);
        });

        impactos.forEach(impacto -> {
            impacto.draw(g);
        });

        String t = tiempoEnCurso/60 + ":" + tiempoEnCurso%60;

        g.setColor(Color.black);
        g.drawString("Tiempo restante: " + t, 11, pos+1);
        g.drawRect(11, pos+23, 200, 15);
        g.fillRect(11, pos+23, (int)heroe.getEnergia()*2, 15);
        g.drawString("Energia: " + String.format("%.2f", Math.floor(heroe.getEnergia())), 11, pos+18);
        // g.drawString("Energia: " + new String(new char[(int)(heroe.getEnergia()/2)]).replace("\0", "|"), 11, pos+16);
        
        g.setColor(Color.white);
        g.drawString("Tiempo restante: " + t, 10, pos);
        g.drawString("Energia: " + String.format("%.2f", Math.floor(heroe.getEnergia())), 10, pos+17);
        // g.drawString("Energia: " + new String(new char[(int)(heroe.getEnergia()/2)]).replace("\0", "|"), 10, pos+15);
    

        Juego juego = Juego1943.getInstance();
        g.drawString("Score: "+ String.valueOf(heroe.PuntajeJugador()), (juego.getWidth()/2)-5, pos);

        if(contadorSegundo == 60) { // pensando siempre en 60 fps
            heroe.modificarEnergia(-0.01);
            
            tiempoEnCurso--;
            contadorSegundo = 0;
        } else {
            contadorSegundo++;
        }
    } 

    public static class MisionBuilder {
        private final AvionAmigo heroe;
        private final Enemigo[] enemigos;
        private final Enemigo jefe;

        private int tiempo;
        private DIFICULTAD dificultad;

        public MisionBuilder(AvionAmigo heroe, Enemigo[] enemigos, Enemigo jefe) {
            this.heroe = heroe;
            this.enemigos = enemigos;
            this.jefe = jefe;
            
            this.tiempo = 60*2;
            this.dificultad = DIFICULTAD.FACIL;
        }

        public MisionBuilder setTiempo(int tiempo) {
            this.tiempo = tiempo;
            return this;
        }

        public MisionBuilder setDificultad(DIFICULTAD dificultad) {
            this.dificultad = dificultad;
            return this;
        }

        public Mision build(){
			return new Mision(this);
		}
    }
}