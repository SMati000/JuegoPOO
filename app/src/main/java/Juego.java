import java.awt.Frame;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;

import java.awt.*;

abstract public class Juego extends Frame{
    private String nombre;
    private String descripcion;

    abstract public void iniciar();
    abstract public void finalizar();

}
