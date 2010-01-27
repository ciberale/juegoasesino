package cf.logica.minijuegos;

import cf.ParserXML;
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
    private final int numColumnas = 3;
     private final int numFilas = 3;



    public Puzzle8(ParserXML parserXML){

        /***
         *  Inicializamos el juego aleatoriamente? del 1 al 8 y luego pon el negro donde quieras.
         */

        estado = parserXML.parsea8PuzzleInicial();
        this.parserXML = parserXML;


        movimientos = new Vector<Integer>();
        movimientos.add(Movimientos8Puzzle.arriba.ordinal());
        movimientos.add(Movimientos8Puzzle.abajo.ordinal());
        movimientos.add(Movimientos8Puzzle.izquierda.ordinal());
        movimientos.add(Movimientos8Puzzle.derecha.ordinal());


        estadoObjetivo = parserXML.parsea8PuzzleObjetivo();


        /** Hay que poner la solucion*/

            /*estadoObjetivo = new Estado(new Dimension(3,3));

            estadoObjetivo.setNumero(new Posicion(0,0),1);
            estadoObjetivo.setNumero(new Posicion(1,0),2);
            estadoObjetivo.setNumero(new Posicion(2,0),3);

            estadoObjetivo.setNumero(new Posicion(0,1),4);
            estadoObjetivo.setNumero(new Posicion(1,1),0);
            estadoObjetivo.setNumero(new Posicion(2,1),5);

            estadoObjetivo.setNumero(new Posicion(0,2),6);
            estadoObjetivo.setNumero(new Posicion(1,2),7);
            estadoObjetivo.setNumero(new Posicion(2,2),8);*/

            /**
             * Esto hay que setearlo desde fuera?, mejor leemos el estado y lo encontramos.
             */


           // posicionNegro = new Posicion(1,1);
    }



    public boolean hazMovimiento(int movimiento){

        posicionNegro = null;
        for (int i = 0; i < estado.getColumnas();i++){
            if (posicionNegro != null)
                break;
            for (int j=0; j < estado.getFilas();j++)
                if (estado.getCasilla(i, j) == 0){
                    posicionNegro = new Posicion(i,j);  // revisa
                    break;
                }
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

         /** Heuristica "Numero de fichas descolocadas"
           * Esta heuristica puede valer de 0 a 9.
           */

        int numFichasDescolocadas = 0;

        for (int i = 0; i < numColumnas;i++)
            for (int j=0; j < numFilas;j++)
                if (estado.getCasilla(i,j) != estadoObjetivo.getCasilla(i,j))
                    numFichasDescolocadas++;


        /** ¿Hacer otra ? **/


        return numFichasDescolocadas;

    }

    @Override
    public boolean esPeligro(Estado status) {

        return false;
    }

    @Override
    public double getCosteMovimiento(int movimiento, Estado estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getExplicacionEstado() {

        return "Cada uno de los números está en la posición que se indica, el 0 es el cuadro negro";
    }


}
