package cf.logica.minijuegos;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;

import java.util.Vector;
import movimientos.Movimientos8Puzzle;

/**
 *
 * @author luigi
 */
public class Puzzle8 extends Minijuego {

    private Posicion posicionNegro;



    public Puzzle8(){

        /***
         *  Inicializamos el juego aleatoriamente? del 1 al 8 y luego pon el negro donde quieras.
         */

        estado = new Estado(new Dimension(3,3));

        movimientos = new Vector<Integer>();
        movimientos.add(Movimientos8Puzzle.arriba.ordinal());
        movimientos.add(Movimientos8Puzzle.abajo.ordinal());
        movimientos.add(Movimientos8Puzzle.izquierda.ordinal());
        movimientos.add(Movimientos8Puzzle.derecha.ordinal());

        /**
         Hay que poner la solucion*/

            estadoObjetivo = new Estado(new Dimension(3,3));

            estadoObjetivo.setNumero(new Posicion(0,0),1);
            estadoObjetivo.setNumero(new Posicion(1,0),2);
            estadoObjetivo.setNumero(new Posicion(2,0),3);

            estadoObjetivo.setNumero(new Posicion(0,1),4);
            estadoObjetivo.setNumero(new Posicion(1,1),0);
            estadoObjetivo.setNumero(new Posicion(2,1),5);

            estadoObjetivo.setNumero(new Posicion(0,2),6);
            estadoObjetivo.setNumero(new Posicion(1,2),7);
            estadoObjetivo.setNumero(new Posicion(2,2),8);

            /**
             * Esto hay que setearlo desde fuera?, mejor leemos el estado y lo encontramos.
             */


           // posicionNegro = new Posicion(1,1);
    }





    public boolean hazMovimiento(int movimiento){


        for (int i = 0; i < estado.getColumnas();i++)
            for (int j=0; j < estado.getFilas();j++)
                if (estado.getCasilla(i, j) == 0){
                    posicionNegro = new Posicion(i,j);  // revisa
                    break;
                }

        Movimientos8Puzzle mov = Movimientos8Puzzle.values()[movimiento];

       /**
        *  Comprobar si el estado está permitido.
        */
        Posicion posIntercambia = null;
        /// comprobar que esto está bien.
        switch(mov){
            case arriba:
                posIntercambia = new Posicion(posicionNegro.getEjeX(),posicionNegro.getEjeY() - 1);
                break;

            case abajo:
                posIntercambia = new Posicion(posicionNegro.getEjeX(),posicionNegro.getEjeY() + 1);
                break;

            case izquierda:
                posIntercambia = new Posicion(posicionNegro.getEjeX() - 1,posicionNegro.getEjeY());
                break;
            case derecha:
                posIntercambia = new Posicion(posicionNegro.getEjeX() + 1,posicionNegro.getEjeY());
                break;
        }

        /**
         * Comprobamos que el movimiento es legal.
         */
        if (estado.enRango(posIntercambia)){
            int valor = estado.getCasilla(posIntercambia.getEjeX(),posIntercambia.getEjeY());
            estado.setNumero(posicionNegro,valor);
            posicionNegro = posIntercambia;
            estado.setNumero(posIntercambia,0);
            return true;
        }
        else return false;

      
    }

    public boolean estadoObjetivo() {

        return estado.equals(estadoObjetivo);
    }

    @Override
    public double getValorHeuristico(Estado estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean esPeligro(Estado status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
