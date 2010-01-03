package cf.logica;

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
        

    }




    public boolean hazMovimiento(int movimiento){


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
    public boolean esPeligro(Estado status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
