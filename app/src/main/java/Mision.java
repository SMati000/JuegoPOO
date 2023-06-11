import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.io.IOException;

public class Mision {
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

    private boolean generarBonusSecreto, bonusSecretoAgarrado;
    private Rectangle bonusSecreto;

    private AtaqueEspecial ataqueEspecial;
    private AvionAmigo[] refuerzos;
    private final ArrayList<Enemigo> enemigosCreados;
    private final ArrayList<Municion> balasEnCurso;
    private final ArrayList<Municion> balasHeroe;
    private final ArrayList<Bonus> bonusEnPantalla, bonusAsignados; 
    private final ArrayList<Impacto> impactos;

    private Mision(MisionBuilder builder) {
        refuerzos = new AvionAmigo[2];
        enemigosCreados = new ArrayList<Enemigo>();
        balasEnCurso = new ArrayList<Municion>();
        balasHeroe = new ArrayList<Municion>();
        bonusEnPantalla = new ArrayList<Bonus>();
        bonusAsignados = new ArrayList<Bonus>();
        impactos = new ArrayList<Impacto>();
        
        estado = ESTADO.AIRE;

        this.dificultad = builder.dificultad;
        this.generarBonusSecreto = builder.generarBonusSecreto;
        this.bonusSecretoAgarrado = false;

        this.heroe = builder.heroe;
        this.enemigos = builder.enemigos;
        this.jefe = builder.jefe;

        this.tiempo = builder.tiempo;
        this.tiempoEnCurso = this.tiempo;

        try{
            bonus = new Bonus[]{
                new Pow("pow.png", new Point(0, 0)),
                new Auto("auto.png", new Point(0, 0)),
                new EstrellaNinja("estrellaNinja.png", new Point(0, 0)),
                new SuperShell("SuperShell.png", new Point(0, 0)),
                new Refuerzos( "refuerzo.png", new Point(0, 0)),
                new AmetralladoraTresCañones( "ametralladora.png", new Point(0, 0)),
                new Laser( "laser.png", new Point(0, 0)),
                new Escopeta( "escopeta.png", new Point(0, 0)),
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

        if(ataqueEspecial != null) {
            manejarAtaqueEspecial();
        }

        if(bonusSecreto != null) {
            manejarBonusSecreto();
        }

        if(tiempoEnCurso == tiempo/2) {
            this.estado = ESTADO.TIERRA;
        } if(tiempoEnCurso == 0) {
            this.estado = ESTADO.FIN;
        }

        if(tiempoEnCurso <= apareceJefe) {
            // manejarJefe();
        }

        if(contadorSegundo == 60) { // pensando siempre en 60 fps
            heroe.modificarEnergia(-0.01);
            eliminarImpactos();
            
            tiempoEnCurso--;
            contadorSegundo = 0;
        } else {
            contadorSegundo++;
        }
    }
 
    private void crearBonus(){
        int random = (int)(Math.floor(Math.random()*999+1));
        
        if(bonusEnPantalla.size() < 1/dificultad.dif && random < 10/dificultad.dif ) {
            Bonus b = generarBonus();
            
            if(b != null) {
                bonusEnPantalla.add(b);
            }
        }
        
        int bs = (int)(Math.floor(Math.random()*5000+1));
        if(bs == 3121 && bonusSecreto == null && generarBonusSecreto) {
            crearBonusSecreto();
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

        int index = bonusEnPantalla.indexOf(bn);
        if(index != -1) {
            bonusEnPantalla.remove(index);
            bonusEnPantalla.add(index, b);
        }
    }
        
    private Bonus generarBonus() {
        int randomBonus = (int)(Math.floor(Math.random()*((bonus.length-1)+1)));
        Bonus b = bonus[randomBonus].clone();

        b.setX((int)(Math.floor(Math.random()*(700-100+1)+100)));
        b.setY(heroe.getY()-500);    

        return b;
    }
    
    private void manejarBonus() {
        for(int i = 0; i < bonusEnPantalla.size(); i++) {
            Bonus temp = bonusEnPantalla.get(i);
            Rectangle bonusRectangle = new Rectangle(temp.getPosicion(), 
                    new Dimension(temp.grafico.getWidth(), temp.grafico.getHeight()));

            if(new Rectangle(heroe.getPosicion(), new Dimension(heroe.grafico.getWidth(),heroe.grafico.getHeight()))
            .intersects(bonusRectangle)) {
                bonusEnPantalla.remove(i);
                asignarBonus(temp);
            }

            // eliminar enemigos fuera de la pantalla
            if(temp.getY() > (heroe.getY() + (Juego1943.getInstance().getHeight())) + 25) { 
                bonusEnPantalla.remove(i);
                i--;
                continue;
            }
        }

        for(int i = 0; i < bonusAsignados.size(); i++) {
            Bonus b = bonusAsignados.get(i);
            
            if(!b.activo()) {
                bonusAsignados.remove(i);

                if(b.getClass().getName().equals("Refuerzos")) {
                    refuerzos = new AvionAmigo[2];
                }
            }

            b.update();
        }

        manejarRefuerzos();
    }

    private void asignarBonus(Bonus bonus) {
        String incompatibles = "Escopeta, Laser, AmetralladoraTresCañones";

        for(int i = 0; i < bonusAsignados.size(); i++) {
            Bonus b = bonusAsignados.get(i);

            boolean incompatibilidad = incompatibles.toString().contains(b.getClass().getName()) && incompatibles.toString().contains(bonus.getClass().getName());

            if(b.getClass().getName().equals(bonus.getClass().getName()) 
                || incompatibilidad) { 
                b.destruir();             // si se agarra un tipo de bonus cuando ya se tiene,
                bonusAsignados.remove(i); // se reinicia el tiempo del bonus
            }                             
        }

        bonusAsignados.add(bonus);
        bonus.AsignarBonus(heroe);
        bonus.activar();

        if(bonus.getClass().getName().equals("Refuerzos")) {
            this.refuerzos = ((Refuerzos)bonus).getRefuerzos();
        }
    }

    private void crearBonusSecreto() {
        int x = (int)(Math.floor(Math.random()*(700-100+1)+100));
        
        int yMax = (int) ((Juego1943)Juego1943.getInstance()).getViewPort().getY();
        int yMin = (int) (((Juego1943)Juego1943.getInstance()).getViewPort().getY() + ((Juego1943)Juego1943.getInstance()).getViewPort().getHeight());
        int y = (int)(Math.floor(Math.random()*(yMax-yMin+1)+yMin));

        bonusSecreto = new Rectangle(x, y, 10, 10);
    }

    private void manejarBonusSecreto() {
        if(bonusSecreto.getY() > (heroe.getY() + (Juego1943.getInstance().getHeight())) + 25) {
            bonusSecreto = null;
        }
    }

    public void ataqueEspecial() {
        try {
            if(this.estado == ESTADO.AIRE) {
                this.ataqueEspecial = new AtaqueEspecial(AtaqueEspecial.ATAQUE.RAYO);
                enemigosCreados.removeIf(enemigo -> enemigo.getClass().getName().equals("AvionEnemigo"));
            } else if(this.estado == ESTADO.TIERRA) {
                this.ataqueEspecial = new AtaqueEspecial(AtaqueEspecial.ATAQUE.TSUNAMI);
                enemigosCreados.removeIf(enemigo -> enemigo.getClass().getName().equals("Barco"));
            }
            
            heroe.modificarEnergia(-100/5);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarAtaqueEspecial() {
        if(ataqueEspecial != null && !ataqueEspecial.isEnabled()) {
            this.ataqueEspecial = null;
        }
    }

    private void manejarRefuerzos() {
        for(int i = 0; i < refuerzos.length; i++) {
            AvionAmigo temp = refuerzos[i];

            if(temp == null) {
                for(int j = 0; j < bonusAsignados.size(); j++) {
                    Bonus b = bonusAsignados.get(j);

                    if(b.getClass().getName().equals("Refuerzos")) {
                        ((Refuerzos)b).destruirRefuerzo(i+1);
                    }
                }

                continue;
            }

            enemigosCreados.forEach(enemigo -> {
                int dy = Math.abs(enemigo.getY()+enemigo.grafico.getHeight()/2) - Math.abs(temp.getY());
                int dx = Math.abs(temp.getX() - enemigo.getX()+enemigo.grafico.getWidth()/2);

                if(dy < 300 && dx < 60) {
                    Municion[][] balas = temp.disparar();

                    for(Municion[] bala : balas) {
                        if(bala != null) {
                            for(Municion b : bala) {
                                if(b != null) {
                                    disparoHeroe(b);
                                }
                            }
                        }
                    }
                }
            });
        }
    }


    private void crearEnemigos() {
        int random = (int)(Math.floor(Math.random()*999+1));
                         // cuanto mas alta la dificultad, mas probabilidades de q se generen enemigos
        if(enemigosCreados.size() < 10*dificultad.dif && random <= factorProb*dificultad.dif) {

            if(random == factorProb*dificultad.dif) {
                try {
                    crearFormacion(Formacion.getTipo((int)(Math.floor(Math.random()*(3)+1))));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return;
            }

            Enemigo e = crearEnemigo();

            if(e != null) {
                enemigosCreados.add(e);
            }
        }
    }

    private void crearFormacion(Formacion.FORMACIONES tipo) throws Exception {
        Enemigo[] integrantes = new Enemigo[tipo.tamano];

        for(int i = 0; i < tipo.tamano; i++) {
            Enemigo temp = crearEnemigo();

            if(temp != null && !temp.getClass().getName().equals("Barco")) { // formaciones solo de aviones
                integrantes[i] = temp;
                enemigosCreados.add(temp);
            } else {
                i--;
            }
        }

        Formacion.iniciar(integrantes, tipo);
    }

    private Enemigo crearEnemigo() {
        int nuevoEnemigo = (int)(Math.floor(Math.random()*(enemigos.length-1-0+1)+0));
            
        if(enemigos[nuevoEnemigo].getClass().getName().equals("Barco") 
            && this.estado != ESTADO.TIERRA) {
                return null;
            }

        Enemigo e = enemigos[nuevoEnemigo].clone();

        e.setX((int)(Math.floor(Math.random()*(700-100+1)+100)));
        e.setY(heroe.getY()-(int)(Math.floor(Math.random()*(1300-800+1)+800)));

        return e;
    }

    private void eliminarImpactos() {
        for(int i = 0; i < impactos.size(); i++) {
            if(!impactos.get(i).isEnabled()) {
                impactos.remove(i);
            }
        }
    }

    private void manejarEnemigos() {
        for(int i = 0; i < enemigosCreados.size(); i++) {
            Enemigo temp = enemigosCreados.get(i);

            temp.update();

            // eliminar enemigos fuera de la pantalla
            if(temp.getY() > (heroe.getY() + (Juego1943.getInstance().getHeight())) + 25) {
                enemigosCreados.remove(i);
                i--;
                continue;
            }

            disparar(temp);
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

    private void disparar(Enemigo e) {
        Municion municiones[][] = e.disparar();

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
        
        Rectangle jefeR = new Rectangle(this.jefe.getPosicion(), new Dimension(this.jefe.grafico.getWidth(), this.jefe.grafico.getHeight()));
        
        Rectangle refuerzo1 = null, refuerzo2 = null;
        if(refuerzos[0] != null) {
            refuerzo1 = new Rectangle(refuerzos[0].getPosicion(), new Dimension(refuerzos[0].grafico.getWidth(), refuerzos[0].grafico.getHeight()));
        }
        
        if(refuerzos[1] != null) {
            refuerzo2 = new Rectangle(refuerzos[1].getPosicion(), new Dimension(refuerzos[1].grafico.getWidth(), refuerzos[1].grafico.getHeight()));
        }

        for(int i = 0; i < balasEnCurso.size(); i++) {
            Municion bala = balasEnCurso.get(i);
            Rectangle balaR = new Rectangle(bala.getPosicion(), new Dimension(bala.grafico.getWidth(), bala.grafico.getHeight()));

            if(balaR.intersects(avionAmigo)) {
                if(!heroe.isEsquivando()) {
                    anadirImpacto(avionAmigo, Impacto.tipoImpacto.DISPARO);
                }
                
                heroe.modificarEnergia(-heroe.getResistencia());
                balasEnCurso.remove(i);
            }

            for(int j = 0; j < refuerzos.length; j++) {
                if(refuerzos[j] != null) {
                    Rectangle r = j == 0 ? refuerzo1 : refuerzo2;

                    if(balaR.intersects(r)) {
                        anadirImpacto(r, Impacto.tipoImpacto.DISPARO);
                        refuerzos[j].modificarEnergia(-refuerzos[j].getResistencia());
                        balasEnCurso.remove(i);

                        if(refuerzos[j].getEnergia() <= 0) {
                            refuerzos[j] = null;
                        }
                    }
                }
            }
        }           
        
        for(int i = 0; i < balasHeroe.size(); i++) {
            Municion bala = balasHeroe.get(i);
            Rectangle balaR = new Rectangle(bala.getPosicion(), new Dimension(bala.grafico.getWidth(), bala.grafico.getHeight()));

            for(int j = 0; j < enemigosCreados.size(); j++) {
                Enemigo enemigo = enemigosCreados.get(j);
                
                Rectangle enemigoR = new Rectangle(enemigo.getPosicion(), new Dimension(enemigo.grafico.getWidth(), enemigo.grafico.getHeight()));

                if(new Rectangle(bala.getPosicion(), new Dimension(bala.grafico.getWidth(), bala.grafico.getHeight()))
                .intersects(enemigoR)) {
                    anadirImpacto(enemigoR, Impacto.tipoImpacto.DISPARO);
                    
                    enemigo.modificarEnergia(-enemigo.getResistencia() * bala.getDano());
                    
                    balasHeroe.remove(i);


//en este metodo se haria el modificar puntaje
                    if(enemigo.getEnergia() <= 0) {
                        heroe.pasarPuntaje(enemigo.puntajeDado());
                        enemigosCreados.remove(j);        
                    }
                }
            }

            if(bala.esDestructora()) {
                for(int j = 0; j < balasEnCurso.size(); j++) {
                    Municion ebala = balasEnCurso.get(j);
                    Rectangle ebalaR = new Rectangle(ebala.getPosicion(), new Dimension(ebala.grafico.getWidth(), ebala.grafico.getHeight()));
        
                    if(balaR.intersects(ebalaR)) {
                        anadirImpacto(ebalaR, Impacto.tipoImpacto.DISPARO);
                        balasEnCurso.remove(j);
                    }
                }
            }

            if(balaR.intersects(jefeR)) {
                anadirImpacto(jefeR, Impacto.tipoImpacto.DISPARO);
                
                this.jefe.modificarEnergia(-this.jefe.getResistencia() * bala.getDano());
                
                balasHeroe.remove(i);

                if(this.jefe.getEnergia() <= 0) {
                    this.estado = ESTADO.GANA;
                }
            }
        }        
        
        for (int i = 0; i < bonusEnPantalla.size(); i++) {
            Bonus b = bonusEnPantalla.get(i);
            Rectangle bonusR = new Rectangle(b.getPosicion(), new Dimension(b.grafico.getWidth(), b.grafico.getHeight()));

            for (int j = 0; j < balasHeroe.size(); j++) {
                Municion bala = balasHeroe.get(j);
                Rectangle balaR = new Rectangle(bala.getPosicion(), new Dimension(bala.grafico.getWidth(), bala.grafico.getHeight()));

                if(balaR.intersects(bonusR)) {
                    anadirImpacto(bonusR, Impacto.tipoImpacto.DISPARO);

                    balasHeroe.remove(j);
    
                    b.modificarVida(-b.getResistencia() * bala.getDano());
                    
                    if(b.getVida() <= 0) {  
                        reemplazarBonus(b);
                    }
                }
            }
        }
        
        for(int i = 0; i < enemigosCreados.size(); i++) {
            Enemigo enemigo = enemigosCreados.get(i);
            Rectangle enemigoR = new Rectangle(enemigo.getPosicion(), new Dimension(enemigo.grafico.getWidth(), enemigo.grafico.getHeight()));
            
            if(enemigo.getClass().getName().equals("AvionEnemigo") && enemigoR.intersects(avionAmigo)) {
                anadirImpacto(avionAmigo, Impacto.tipoImpacto.COLISION);
                anadirImpacto(enemigoR, Impacto.tipoImpacto.COLISION);
                
                enemigosCreados.remove(i);
                
                enemigo.modificarEnergia(-100);
                heroe.modificarEnergia(-heroe.getResistencia()*2); 
            }
            
            for(int j = 0; j < refuerzos.length; j++) {
                if(refuerzos[j] != null) {
                    Rectangle r = j == 0 ? refuerzo1 : refuerzo2;
                    
                    if(enemigoR.intersects(r)) {
                        anadirImpacto(enemigoR, Impacto.tipoImpacto.COLISION);
                        anadirImpacto(r, Impacto.tipoImpacto.COLISION);

                        refuerzos[j] = null;

                        enemigosCreados.remove(i);
                        enemigo.modificarEnergia(-100);
                    }
                }
            }
        }

        if(bonusSecreto != null) {
            if(avionAmigo.intersects(bonusSecreto)) {
                this.bonusSecretoAgarrado = true;
            }
        }
    }

    private void anadirImpacto(Rectangle objetivo, Impacto.tipoImpacto impacto) {
        try {
            impactos.add(new Impacto(
                new Point((int)(objetivo.getX()+objetivo.getWidth()/2), (int)(objetivo.getY()+objetivo.getHeight()/2-5)), 
                impacto)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disparoHeroe(Municion bala) {
        balasHeroe.add(bala);
    }

    public ESTADO getEstado() {
        return estado;
    }

    public void draw(Graphics2D g, int pos) {
        bonusEnPantalla.forEach(bonus -> {
            bonus.draw(g);
        });

        enemigosCreados.forEach(enemigo -> {
            enemigo.draw(g);
        });

        if(tiempoEnCurso <= apareceJefe) {
            jefe.draw(g);
        }

        if(!bonusAsignados.isEmpty()) {
            g.setColor(Color.black);
            g.drawString("Bonos Activos:", 11, pos+60);

            g.setColor(Color.white);
            g.drawString("Bonos Activos:", 10, pos+59);
        }
        
        for(int i = 0; i < bonusAsignados.size(); i++) {
            Bonus bonus = bonusAsignados.get(i);

            bonus.draw(g);
            
            g.setColor(Color.black);
            g.drawString("    " + bonus.toString(), 11, pos+60+17*(i+1));

            g.setColor(Color.white);
            g.drawString("    " + bonus.toString(), 10, pos+59+17*(i+1));
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

        if(this.ataqueEspecial != null) {
            this.ataqueEspecial.draw(g);
        }

        if(this.bonusSecretoAgarrado) {
            g.setColor(Color.black);
            g.drawString("Has agarrado el bonus secreto. Felicitaciones!!", heroe.getX()-heroe.grafico.getWidth()/2-50+1, heroe.getY()-heroe.grafico.getHeight()-10+1);
            
            g.setColor(Color.white);
            g.drawString("Has agarrado el bonus secreto. Felicitaciones!!", heroe.getX()-heroe.grafico.getWidth()/2-50, heroe.getY()-heroe.grafico.getHeight()-10);

            TimerTask task = new TimerTask() {
                public void run() {
                    Mision.this.estado = ESTADO.GANA;
                }
            };

            Timer timer = new Timer();            
            timer.schedule(task, 3000);
        }

        String t = tiempoEnCurso/60 + ":" + tiempoEnCurso%60;

        g.setColor(Color.black);
        g.drawString("Tiempo restante: " + t, 11, pos+1);
        g.drawRect(11, pos+23, 200, 15);
        g.fillRect(11, pos+23, (int)heroe.getEnergia()*2, 15);
        g.drawString("Energia: " + String.format("%.2f", Math.floor(heroe.getEnergia())), 11, pos+18);
        
        g.setColor(Color.white);
        g.drawString("Tiempo restante: " + t, 10, pos);
        g.drawString("Energia: " + String.format("%.2f", Math.floor(heroe.getEnergia())), 10, pos+17);
    
        Juego juego = Juego1943.getInstance();
        g.drawString("Score: "+ String.valueOf(heroe.PuntajeJugador()), (juego.getWidth()/2)-5, pos);
    } 

    public static class MisionBuilder {
        private final AvionAmigo heroe;
        private final Enemigo[] enemigos;
        private final Enemigo jefe;
        
        private int tiempo;
        private DIFICULTAD dificultad;
        private boolean generarBonusSecreto;

        public MisionBuilder(AvionAmigo heroe, Enemigo[] enemigos, Enemigo jefe) {
            this.heroe = heroe;
            this.enemigos = enemigos;
            this.jefe = jefe;
            
            this.tiempo = 60*2;
            this.dificultad = DIFICULTAD.FACIL;
            this.generarBonusSecreto = false;
        }

        public MisionBuilder setTiempo(int tiempo) {
            this.tiempo = tiempo;
            return this;
        }

        public MisionBuilder setDificultad(DIFICULTAD dificultad) {
            this.dificultad = dificultad;
            return this;
        }

        public MisionBuilder generarBonusSecreto(boolean generarBonusSecreto) {
            this.generarBonusSecreto = generarBonusSecreto;
            return this;
        }

        public Mision build(){
			return new Mision(this);
		}
    }
}