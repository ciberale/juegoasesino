package cf.logica.busqueda;

import cf.logica.estados.Estado;
import java.util.LinkedList;

/**
 *
 * @author luigi
 */
public class Nodo implements Comparable {
    protected int numMovimiento;
    protected Estado estado;

    private double costeActual;
    private double costeHeuristico;
    private LinkedList<Nodo> listaSucesores;
    private Nodo padre;


    public Nodo(int numMovimiento,Estado estado){
        this.numMovimiento = numMovimiento;
        this.estado = estado;
        costeHeuristico = estado.getCosteHeuristico();
        listaSucesores = new LinkedList<Nodo>();
    }

    /** Constructor solo con Estado **/
    public Nodo (Estado estado){

    }


    public int getNumMovimiento() {
        return numMovimiento;
    }


    public void setNumMovimiento(int numMovimiento) {
        this.numMovimiento = numMovimiento;
    }


    public Estado getEstado() {
        return estado;
    }


    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * @return the costeActual
     */
    public double getCosteActual() {
        return costeActual;
    }

    /**
     * @param costeActual the costeActual to set
     */
    public void setCosteActual(double costeActual) {
        this.costeActual = costeActual;
    }

    /**
     * @return the costeHeuristico
     */
    public double getCosteHeuristico() {
        return costeHeuristico;
    }

    /**
     * @param costeHeuristico the costeHeuristico to set
     */
    public void setCosteHeuristico(double costeHeuristico) {
        this.costeHeuristico = costeHeuristico;
    }

    public int compareTo(Object nodo) {

        Nodo comparado = (Nodo)nodo;
        return getEstado().compareTo(comparado.getEstado());
       
    }

    /**
     * @return the listaSucesores
     */
    public LinkedList<Nodo> getListaSucesores() {
        return listaSucesores;
    }

    /**
     * @param listaSucesores the listaSucesores to set
     */
    public void setListaSucesores(LinkedList<Nodo> listaSucesores) {
        this.listaSucesores = listaSucesores;
    }

    /**
     * @return the padre
     */
    public Nodo getPadre() {
        return padre;
    }

    /**
     * @param padre the padre to set
     */
    public void setPadre(Nodo padre) {
        this.padre = padre;
    }
}
