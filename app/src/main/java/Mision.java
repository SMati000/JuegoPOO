import java.util.ArrayList;

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
    private final DIFICULTAD dificultad;
    private final Enemigo[] enemigos;
    private final Enemigo jefe;
    private final int tiempo; // en segundos
    private int tiempoEnCurso; // en segundos
    private int contadorSegundo = 0; 

    private final ArrayList<Enemigo> enemigosCreados;

    private Mision(MisionBuilder builder) {
        enemigosCreados = new ArrayList<Enemigo>();
        estado = ESTADO.AIRE;

        this.dificultad = builder.dificultad;
        this.enemigos = builder.enemigos;
        this.jefe = builder.jefe;
        this.tiempo = builder.tiempo;
        this.tiempoEnCurso = this.tiempo;
    }

    public void manejarEnemigos(Point avionAmigo) {
        crearEnemigos(avionAmigo);

        for(int i = 0; i < enemigosCreados.size(); i++) {
            Enemigo temp = enemigosCreados.get(i);

            // eliminar enemigos fuera de la pantalla
            if(temp.getY() > (avionAmigo.y + (Juego1943.getInstance().getHeight())) + 25) { 
                enemigosCreados.remove(i);
                i--;
                continue;
            }

            temp.avanzar();

            if(temp.objetivoEnRadar(avionAmigo.y)) {
                temp.setVelocidad(2);
            
                if(temp.getClass().getName().equals("AvionEnemigo"))
                    ((AvionEnemigo)temp).seguirHorizontalmente(avionAmigo);
            }
        }

        if(tiempoEnCurso == tiempo/2) {
            this.estado = ESTADO.TIERRA;
        } if(tiempoEnCurso == 0) {
            this.estado = ESTADO.FIN;
        }
    }

    // Hacer que no se carguen las imgs del disco a cada rato. Arreglar codigo. Implementar Interfaces

    private static final int factorProb = 10; 
    private void crearEnemigos(Point avionAmigo) {
        int random = (int)(Math.floor(Math.random()*999+1));
                         // cuanto mas alta la dificultad, mas probabilidades de q se generen enemigos
        if(enemigosCreados.size() < 20*dificultad.dif && random < factorProb*dificultad.dif) { 

            try {
                int nuevoEnemigo = (int)(random/(enemigos.length*dificultad.dif+1));

                Enemigo e = null;
                
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

        if(contadorSegundo == 60) { // pensando siempre en 60 fps
            tiempoEnCurso--;
            contadorSegundo = 0;
        } else {
            contadorSegundo++;
        }
    } 

    public static class MisionBuilder {
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