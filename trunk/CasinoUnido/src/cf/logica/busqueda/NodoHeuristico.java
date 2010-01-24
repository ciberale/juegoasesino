package cf.logica.busqueda;

import cf.logica.estados.Estado;

/**
 *
 * @author luigi
 */
public class NodoHeuristico extends Nodo {

    protected double costeActual;
    protected double costeHeuristico;

    public NodoHeuristico(int numMovimiento,Estado estado){
        super(numMovimiento,estado);
    }


  
}
