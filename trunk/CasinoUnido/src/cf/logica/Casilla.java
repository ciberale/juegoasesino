/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cf.logica;

/**
 *
 * @author luigi
 */
public class Casilla {

    public enum TipoCasilla {entrada,salida,normal

    }

    private TipoJuegos juego;
    private TipoBusquedas busqueda;
    private double dificultad;
    private TipoCasilla tipoCasilla;

    /**
     * @return the juego
     */
    public TipoJuegos getJuego() {
        return juego;
    }

    /**
     * @param juego the juego to set
     */
    public void setJuego(TipoJuegos juego) {
        this.juego = juego;
    }

    /**
     * @return the busqueda
     */
    public TipoBusquedas getBusqueda() {
        return busqueda;
    }

    /**
     * @param busqueda the busqueda to set
     */
    public void setBusqueda(TipoBusquedas busqueda) {
        this.busqueda = busqueda;
    }

    /**
     * @return the dificultad
     */
    public double getDificultad() {
        return dificultad;
    }

    /**
     * @param dificultad the dificultad to set
     */
    public void setDificultad(double dificultad) {
        this.dificultad = dificultad;
    }


    public String toString(){

        return "La casilla es: "+ dificultad + " " + busqueda.toString() + " " + juego.toString();

    }

    public TipoCasilla getTipo() {

        return tipoCasilla;
    }
}
