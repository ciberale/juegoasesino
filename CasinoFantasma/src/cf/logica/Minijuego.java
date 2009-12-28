package cf.logica;

import cf.logica.estados.Estado;

import java.util.Vector;

/**
 *
 * @author luigi
 */
public abstract class Minijuego {

    public Estado getEstado(){
        return estado;
    }
    
    public Vector<Integer> getMovimientos(){
        return movimientos;
    }

    /**
     * Este metodo nos dice si hemos llegado a un estado objetivo.
     * Hay que sobreescribirlo en cada juego, en concreto los que tienen
     * varias posibles soluciones.
     */
    
    public abstract boolean estadoObjetivo();
    public abstract boolean hazMovimiento(int movimiento);

    protected Estado estado;
    protected Estado estadoObjetivo;
    protected Vector<Integer> movimientos;
    
}
