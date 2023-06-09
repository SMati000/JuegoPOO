public class Jugador1943 extends Jugador {
    private int puntaje;
    public String nombre;

    public Jugador1943(){
        super(null);
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }


    public void setPuntaje(int puntos){
        this.puntaje = this.puntaje + puntos;
    }

    public int getPuntaje(){
        System.out.println("puntaje que paso: "+ puntaje);
        return this.puntaje;
    }

    //este constructor no se si iria
    // public Jugador1943(String nombre) {
    //     super(nombre);
    // }
}
