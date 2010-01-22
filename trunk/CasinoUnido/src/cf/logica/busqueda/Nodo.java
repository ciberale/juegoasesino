package cf.logica.busqueda;

import cf.logica.estados.Estado;

/**
 *
 * @author luigi
 */
public class Nodo {
    protected int numMovimiento;
    protected Estado estado;



    public Nodo(int numMovimiento,Estado estado){
        this.numMovimiento = numMovimiento;
        this.estado = estado;
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
}
