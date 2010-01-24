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

    private Juegos juego;
    private Busquedas busqueda;
    private double dificultad;

    /**
     * @return the juego
     */
    public Juegos getJuego() {
        return juego;
    }

    /**
     * @param juego the juego to set
     */
    public void setJuego(Juegos juego) {
        this.juego = juego;
    }

    /**
     * @return the busqueda
     */
    public Busquedas getBusqueda() {
        return busqueda;
    }

    /**
     * @param busqueda the busqueda to set
     */
    public void setBusqueda(Busquedas busqueda) {
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
}
