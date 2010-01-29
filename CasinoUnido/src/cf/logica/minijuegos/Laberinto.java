package cf.logica.minijuegos;

import cf.ParserXML;
import cf.logica.MatrizColores;
import cf.logica.TableroCasillas;
import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;
import java.awt.Color;
import java.util.Vector;
import movimientos.MovimientosLaberinto;

public class Laberinto extends Minijuego {

   // private Posicion actual,objetivo;
    /**
     * Habrá que inicializar el laberinto.
     */
    private Estado laberinto;


    public Laberinto(ParserXML parserXML){

        /*** Podemos poner que esto sea aleatorio?*/

        /** Tenemos definido un laberinto, podriamos poner la entrada y la salida aletoria?
         *
         */
        /**
         * Si no me equivoco, el estado que tenemos que guardar es el laberinto entero junto
         * con la posición, porque de lo contrario, ¿como haces para determinar la heuristica del
         * tablero si solo saben la posicion y no conocen el laberinto?
         */

        movimientos = new Vector<Integer>();
            for (int i = 0; i < MovimientosLaberinto.values().length;i++)
                movimientos.add(MovimientosLaberinto.values()[i].ordinal());


        this.parserXML = parserXML;
        laberinto = parserXML.parseaLaberinto();
        estado = parserXML.getEntradaLaberinto();
        estadoObjetivo = parserXML.getSalidaLaberinto();

       /* if (laberinto.getCasilla(estado.getCasilla(0,0),estado.getCasilla(1,0)) == 0)
            System.out.println("El estado inicial es alcanzable");
        else System.out.println("El estado inicial no es alcanzable");


        if (laberinto.getCasilla(estadoObjetivo.getCasilla(0,0),estadoObjetivo.getCasilla(1,0)) == 0)
            System.out.println("El estadoObjetivo es alcanzable");
        else System.out.println("El estadoObjetivo no es alcanzable");*/
    }

    @Override
    public boolean estadoObjetivo() {

       return estado.equals(estadoObjetivo);
    }

    @Override
    public boolean hazMovimiento(int movimiento) {

        MovimientosLaberinto mov = MovimientosLaberinto.values()[movimiento];
        Posicion aux = new Posicion(estado.getCasilla(0,0),estado.getCasilla(1,0));
        switch(mov){

            case arriba:
                aux = new Posicion(estado.getCasilla(0,0),estado.getCasilla(1,0)-1);
                break;
            case abajo:
                aux = new Posicion(estado.getCasilla(0,0),estado.getCasilla(1,0)+1);
                break;
            case izquierda:
                aux = new Posicion(estado.getCasilla(0,0)-1,estado.getCasilla(1,0));
                break;
            case derecha:
                aux = new Posicion(estado.getCasilla(0,0)+1,estado.getCasilla(1,0));
                break;
             default:return false;

        }

            if (laberinto.enRango(aux) && laberinto.getCasilla(aux.getEjeX(),aux.getEjeY()) == 0){
                estado.setNumero(new Posicion(0,0),aux.getEjeX());
                estado.setNumero(new Posicion(1,0),aux.getEjeY());

                laberinto.setNumero(new Posicion(estado.getCasilla(0,0),estado.getCasilla(1,0)),2);
                laberinto.dibujaEstado();
                laberinto.setNumero(new Posicion(estado.getCasilla(0,0),estado.getCasilla(1,0)),0);
                return true;


            }
            else return false;
    }

    @Override
    public double getValorHeuristico(Estado estado) {


            return 0;



    }

    @Override
    public boolean esPeligro(Estado status) {

        return false;
    }

    @Override
    public double getCosteMovimiento(int movimiento, Estado estado) {

        return 1;
    }

    @Override
    public String getExplicacionEstado() {
       return "El Laberinto: \nEs la situación dentro del laberinto, la primera cifra es la columna y la segunda la fila";
    }

   /* @Override
    public String pintaEstado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/


    /*public void setEstado (Estado estado){




    }*/
}
