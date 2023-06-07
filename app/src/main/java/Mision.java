import java.util.ArrayList;

import javafx.util.Pair;

import java.awt.*;
import java.io.IOException;

public class Mision { // pair, destruir, objetografico/interfaces, 
    public enum ESTADO {
        AIRE, TIERRA, FIN;
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
            bonus = new Bonus[]{new Pow("pow.png", new Point(0, 0)), new Auto("auto.png", new Point(0, 0)),
                                new EstrellaNinja("estrellaNinja.png", new Point(0, 0)),
                                new SuperShell("SuperShell.png", new Point(0, 0)),
                                new Refuerzos( "refuerzo.png", new Point(0, 0))
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
            // manejarJefe();
        }
    }
 
    private void crearBonus(){
        int random = (int)(Math.floor(Math.random()*999+1));
        // cuanto mas alta la dificultad, mas probabilidades de q se generen enemigos
        if(bonusCreados.size() < 1/dificultad.dif && random < 10/dificultad.dif ) { 

            try {
                int randomBonus = (int)(Math.floor(Math.random()*(bonus.length-1-0+1)+0));
             
                Bonus b = null;
                if(bonus[randomBonus].getClass().getName().equals("Pow")){
                    b = new Pow(bonus[randomBonus]);
                }else if(bonus[randomBonus].getClass().getName().equals("Auto")){ 
                    b = new Auto(bonus[randomBonus]);
                }else if(bonus[randomBonus].getClass().getName().equals("EstrellaNinja")){
                    b = new EstrellaNinja(bonus[randomBonus]);
                }else if(bonus[randomBonus].getClass().getName().equals("SuperShell")){
                    b = new SuperShell(bonus[randomBonus]);
                }else if(bonus[randomBonus].getClass().getName().equals("Refuerzos")){
                    b = new Refuerzos(bonus[randomBonus]);
                }else{
                    return;
                }
                b.setX((int)(Math.floor(Math.random()*(700-100+1)+100)));
                b.setY(heroe.getY()-500);
                bonusCreados.add(b);
            } catch (IOException e) {
                System.out.println("No se pudo crear el bonus");
            }
        }
    }


    // Hacer que no se carguen las imgs del disco a cada rato. Arreglar codigo. Implementar Interfaces

    private void crearEnemigos() {
        int random = (int)(Math.floor(Math.random()*999+1));
                         // cuanto mas alta la dificultad, mas probabilidades de q se generen enemigos
        if(enemigosCreados.size() < 10*dificultad.dif && random < factorProb*dificultad.dif) { 

            try {
                int nuevoEnemigo = (int)(Math.floor(Math.random()*(enemigos.length-1-0+1)+0));

                Enemigo e = null;
                
                if(enemigos[nuevoEnemigo].getClass().getName().equals("AvionEnemigo"))
                    e = new AvionEnemigo((AvionEnemigo)enemigos[nuevoEnemigo]);
                else if(enemigos[nuevoEnemigo].getClass().getName().equals("Barco") && this.estado == ESTADO.TIERRA)
                    e = new Barco((Barco)enemigos[nuevoEnemigo]);
                else
                    return;

                int tempX = (int)(Math.floor(Math.random()*(700-100+1)+100));
                // int tempY = heroe.getY()-(int)(Math.floor(Math.random()*(1300-800+1)+800));

                // Rectangle nuevoE = new Rectangle(tempX, 0, e.grafico.getWidth(), e.grafico.getHeight());
                // // int contador = 0;
                // for(int contador = 0, i = 0; i < enemigosCreados.size(); i++) {
                //     Rectangle enemigo = new Rectangle(
                //         enemigosCreados.get(i).getKey().getPosicion().x, 0, 
                //         enemigosCreados.get(i).getKey().grafico.getWidth(), 
                //         enemigosCreados.get(i).getKey().grafico.getHeight()
                //     );

                //     if(enemigo.intersects(nuevoE)) {
                //         tempX = (int)(Math.floor(Math.random()*(700-100+1)+100));
                //         // tempY = heroe.getY()-(int)(Math.floor(Math.random()*(1300-800+1)+800));

                //         if(contador == 5) // para asegurar q no se quede en un bucle infinito
                //             return;

                //         contador++;
                //     }

                    e.setX(tempX);
                    e.setY(heroe.getY()-(int)(Math.floor(Math.random()*(1300-800+1)+800)));
                    
                    // Enemigo temp = enemigosCreados.get(i).getKey();

                    // if(tempX > temp.getX() - temp.grafico.getWidth()/2 - e.grafico.getWidth()/2 &&
                    //     tempX < temp.getX() + temp.grafico.getWidth()/2 + e.grafico.getWidth()/2) {
                    //         tempX = (int)(Math.floor(Math.random()*(700-100+1)+100));
                    //         i = 0;

                    //         if(contador == 5) // para asegurar q no se quede en un bucle infinito
                    //             return;

                    //         contador++;
                    //     } else {
                    //         e.setX(tempX);
                    //     }
                // }


                e.setVelocidad((int)(Math.floor(Math.random()*(10-2+1)+2)));

                enemigosCreados.add(e);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void manejarBonus() {
        for(int i = 0; i < bonusCreados.size(); i++) {
            Bonus temp = bonusCreados.get(i);

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

            Municion ms[] = temp.disparar();

            if(ms != null) {
                for(Municion m : ms) {
                    if(m != null) {
                        balasEnCurso.add(m);
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

        Municion ms[] = jefe.disparar();

        if(ms != null) {
            for(Municion m : ms) {
                if(m != null)
                    balasEnCurso.add(m);
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
                
                heroe.modificarEnergia(-heroe.getResistencia() * bala.getTiros());
                
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
                    
                    enemigo.modificarEnergia(-enemigo.getResistencia() * bala.getTiros());
                    
                    balasHeroe.remove(i);

                    if(enemigo.getEnergia() <= 0) {
                        enemigosCreados.remove(j);
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