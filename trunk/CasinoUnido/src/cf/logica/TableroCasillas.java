package cf.logica;

import cf.util.Dimension;
import cf.util.Posicion;
import java.awt.Color;
import java.util.Vector;

/**
 *
 * @author luigi
 */
public class TableroCasillas {

    Casilla casillas [][];
    private int numColumnas;
    private int numFilas;
    private Posicion entrada;
    private Vector<Posicion> salidas;
    private Posicion jugador;




    public TableroCasillas(Dimension dim){

        casillas = new Casilla[dim.getColumnas()][dim.getFilas()];
        numColumnas = dim.getColumnas();
        numFilas = dim.getFilas();

    }

    public Color getColorCasilla(int columna, int fila) {

        /** Comprobamos si es la entrada **/

        Posicion aux = new Posicion(columna,fila);

        if (aux.equals(entrada))
            return Color.BLUE;
        else if (aux.equals(jugador))
            return Color.RED;
        else for (int i = 0; i < salidas.size();i++)
                if (aux.equals(salidas.elementAt(i)))
                    return Color.GREEN;
        /** Color por defecto **/
        return Color.WHITE;
      
    }

    public int getFilas(){
       return getNumFilas();
    }

    public int getColumnas(){
        return getNumColumnas();
    }

    

    public void setCasilla(Posicion pos,Casilla casilla){
        try {
        casillas[pos.getEjeX()][pos.getEjeY()] = casilla;
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Te has salido");
        }
    }

    public boolean enRango(Posicion pos) {

        return pos.getEjeX() < getColumnas() && pos.getEjeX() >= 0 && pos.getEjeY() < getFilas() && pos.getEjeY() >= 0;
    }

    /**
     * @return the numColumnas
     */
    public int getNumColumnas() {
        return numColumnas;
    }

    /**
     * @param numColumnas the numColumnas to set
     */
    public void setNumColumnas(int numColumnas) {
        this.numColumnas = numColumnas;
    }

    /**
     * @return the numFilas
     */
    public int getNumFilas() {
        return numFilas;
    }

    /**
     * @param numFilas the numFilas to set
     */
    public void setNumFilas(int numFilas) {
        this.numFilas = numFilas;
    }

    /**
     * @return the entrada
     */
    public Posicion getEntrada() {
        return entrada;
    }

    /**
     * @param entrada the entrada to set
     */
    public void setEntrada(Posicion entrada) {
        this.entrada = entrada;
    }

    /**
     * @return the salidas
     */
    public Vector<Posicion> getSalidas() {
        return salidas;
    }

    /**
     * @param salidas the salidas to set
     */
    public void setSalidas(Vector<Posicion> salidas) {
        this.salidas = salidas;
    }

    /**
     * @return the jugador
     */
    public Posicion getJugador() {
        return jugador;
    }

    /**
     * @param jugador the jugador to set
     */
    public void setJugador(Posicion jugador) {
        this.jugador = jugador;
    }


    public Casilla getCasilla(Posicion pos){
        try {
            return casillas[pos.getEjeX()][pos.getEjeY()];
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Te has salido");
            return null;
        }
    }

}

