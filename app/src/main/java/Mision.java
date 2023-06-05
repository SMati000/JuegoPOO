import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.RunElement;

import java.awt.*;
import java.io.IOException;

public class Mision {
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
    private final Enemigo[] enemigos;
    private Bonus[] bonus;
    private final Enemigo jefe;
    private final int tiempo; // en segundos
    private int tiempoEnCurso; // en segundos
    private int contadorSegundo = 0; 


    private final ArrayList<Enemigo> enemigosCreados;
    private final ArrayList<Municion> balasEnCurso;
    private final ArrayList<Bonus> bonusCreados; 

    private Mision(MisionBuilder builder) {
        enemigosCreados = new ArrayList<Enemigo>();
        balasEnCurso = new ArrayList<Municion>();
        bonusCreados = new ArrayList<Bonus>();
        Point pos = new Point(0 ,0);
    
        estado = ESTADO.AIRE;

        this.dificultad = builder.dificultad;
       // this.bonus = builder.bonus; 
        this.enemigos = builder.enemigos;
        this.jefe = builder.jefe;
        this.tiempo = builder.tiempo;
        this.tiempoEnCurso = this.tiempo;
        try{
            bonus = new Bonus[]{new Pow("pow.png", pos), new Auto("auto.png", pos),
                                new EstrellaNinja("estrellaNinja.png", pos),
                                new SuperShell("SuperShell.png", pos),
                                new Refuerzos( "refuerzo.png", pos)
                                };

        }catch(IOException e){
            System.out.println("error al crear el bonus");
        }
        
    }

    public void update(Point posicion) {
        crearEnemigos(posicion);
        manejarEnemigos(posicion);
        
            crearBonus(posicion);
            manejarBonus(posicion);    
        
    }
 
    private void crearBonus(Point posBonus){
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
                b.setY(posBonus.y-500);
                bonusCreados.add(b);
            } catch (IOException e) {
                System.out.println("No se pudo crear el bonus");
            }
        }
    }


    // Hacer que no se carguen las imgs del disco a cada rato. Arreglar codigo. Implementar Interfaces

    private void crearEnemigos(Point avionAmigo) {
        int random = (int)(Math.floor(Math.random()*999+1));
                         // cuanto mas alta la dificultad, mas probabilidades de q se generen enemigos
        if(enemigosCreados.size() < 20*dificultad.dif && random < factorProb*dificultad.dif) { 

            try {
                int nuevoEnemigo = (int)(Math.floor(Math.random()*(enemigos.length-1-0+1)+0));

                Enemigo e = null;
                    
                System.out.println(nuevoEnemigo);

                if(enemigos[nuevoEnemigo].getClass().getName().equals("AvionEnemigo"))
                    e = new AvionEnemigo(enemigos[nuevoEnemigo]);
                else if(enemigos[nuevoEnemigo].getClass().getName().equals("Barco") && this.estado == ESTADO.TIERRA)
                    e = new Barco(enemigos[nuevoEnemigo]);
                else
                    return;

                e.setX((int)(Math.floor(Math.random()*(700-100+1)+100)));
                e.setY(avionAmigo.y-(int)(Math.floor(Math.random()*(1300-800+1)+800)));

                e.setVelocidad((int)(Math.floor(Math.random()*(10-2+1)+2)));
             
                enemigosCreados.add(e);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void manejarBonus(Point avionAmigo) {
        for(int i = 0; i < bonusCreados.size(); i++) {
            Bonus temp = bonusCreados.get(i);

            // eliminar enemigos fuera de la pantalla
            if(temp.getY() > (avionAmigo.y + (Juego1943.getInstance().getHeight())) + 25) { 
                bonusCreados.remove(i);
                i--;
                continue;
            }
        }
    }

    private void manejarEnemigos(Point avionAmigo) {
        for(int i = 0; i < enemigosCreados.size(); i++) {
            Enemigo temp = enemigosCreados.get(i);

            // eliminar enemigos fuera de la pantalla
            if(temp.getY() > (avionAmigo.y + (Juego1943.getInstance().getHeight())) + 25) { 
                enemigosCreados.remove(i);
                i--;
                continue;
            }

            temp.update();

            Municion m = temp.disparar();

            if(m != null)
                balasEnCurso.add(m);

            balasEnCurso.forEach(bala -> {
                bala.update();
            });
            // temp.avanzar();

            // if(temp.objetivoEnRadar(avionAmigo.y)) {
            //     temp.setVelocidad(3);
            
            //     if(temp.getClass().getName().equals("AvionEnemigo")) {
            //         ((AvionEnemigo)temp).seguirHorizontalmente(avionAmigo);
            //         ((AvionEnemigo)temp).disparar();
            //     }
            // }
        }

        if(tiempoEnCurso == tiempo/2) {
            this.estado = ESTADO.TIERRA;
        } if(tiempoEnCurso == 0) {
            this.estado = ESTADO.FIN;
        }
    }

    public ESTADO getEstado() {
        return estado;
    }

    public void draw(Graphics2D g, int pos) {
        String t = tiempoEnCurso/60 + ":" + tiempoEnCurso%60;

        g.setColor(Color.black);
        g.drawString("Tiempo restante: " + t, 11, pos+1);

        g.setColor(Color.white);
        g.drawString("Tiempo restante: " + t, 10, pos);

        enemigosCreados.forEach(enemigo -> {
            enemigo.draw(g);
        });

        bonusCreados.forEach(bonus -> {
            bonus.draw(g);
            
        });

        balasEnCurso.forEach(bala -> {
            bala.draw(g);
        });

        if(contadorSegundo == 60) { // pensando siempre en 60 fps
            tiempoEnCurso--;
            contadorSegundo = 0;
        } else {
            contadorSegundo++;
        }
    } 

    public static class MisionBuilder {
        public Bonus[] bonus;
        private final Enemigo[] enemigos;
        private final Enemigo jefe;

        private int tiempo;
        private DIFICULTAD dificultad;

        public MisionBuilder(Enemigo[] enemigos, Enemigo jefe) {
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