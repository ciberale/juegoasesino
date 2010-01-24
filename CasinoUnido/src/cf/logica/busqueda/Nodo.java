package cf.logica.busqueda;

import cf.logica.estados.Estado;

/**
 *
 * @author luigi
 */
public class Nodo implements Comparable {
    protected int numMovimiento;
    protected Estado estado;
    private double costeActual;
    private double costeHeuristico;



    public Nodo(int numMovimiento,Estado estado){
        this.numMovimiento = numMovimiento;
        this.estado = estado;
        costeHeuristico = estado.getCosteHeuristico();
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
}
