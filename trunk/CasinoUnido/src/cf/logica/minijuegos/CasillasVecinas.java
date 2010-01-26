package cf.logica.minijuegos;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.Vector;



public class CasillasVecinas extends Minijuego {

     private final int numFilas = 3;
     private final int numColumnas = 3;


    public CasillasVecinas(){

          estado = new Estado(new Dimension(numColumnas,numFilas));
          movimientos = new Vector<Integer>();
          /**
           * Inicializa como sea.
           * El 0 es blanco el 1 es negro.
           */

       /**
         *  Como el tablero es de 3x3, tenemos 9 posibles movimientos.
         *
         */
         for (int i = 0; i < numFilas * numColumnas;i++)
             movimientos.add(i);

          /**
           * Tienes que poner la solución,
           */
          estadoObjetivo = new Estado (new Dimension(numColumnas,numFilas));
          for (int i = 0; i < estadoObjetivo.getFilas();i++)
              for (int j= 0; j < estadoObjetivo.getColumnas();j++)
                  estadoObjetivo.setNumero(new Posicion(j,i),1);

    }

    @Override
    public boolean estadoObjetivo() {
          return estado.equals(estadoObjetivo);
    }

    @Override
    public boolean hazMovimiento(int movimiento) {


      /**
       *    En este juego todos los movimientos son legales. Siempre y cuando
       *    la casilla que demos sea legal.
       */

        /** 
         * Comprueba si esto está bien.
         */
        int columna = movimiento % numColumnas;
        int fila = movimiento - columna * numColumnas;
        Posicion pulsacion = new Posicion(columna,fila);
        if (estado.enRango(pulsacion)){

            /// Calculamos las 5 posiciones que van a cambiar.
            Posicion arriba,abajo,derecha,izquierda,centro;
            arriba = new Posicion(pulsacion.getEjeX(),pulsacion.getEjeY() - 1);
            abajo = new Posicion(pulsacion.getEjeX(),pulsacion.getEjeY() + 1);
            derecha = new Posicion(pulsacion.getEjeX() + 1,pulsacion.getEjeY());
            izquierda = new Posicion(pulsacion.getEjeX() - 1,pulsacion.getEjeY());
            centro = pulsacion;

            /// Hay que invertir todas las posiciones.

            cambiaCasilla(arriba);
            cambiaCasilla(abajo);
            cambiaCasilla(derecha);
            cambiaCasilla(izquierda);
            cambiaCasilla(centro);

            return true;
        }

        return false;

    }


    private void cambiaCasilla(Posicion pos){

            if (estado.getCasilla(pos.getEjeX(),pos.getEjeY()) == 0)
                estado.setNumero(pos,1);
            else estado.setNumero(pos,0);

    }

    @Override
    public double getValorHeuristico(Estado estado) {

        /** Con esta heuristica calculamos el numero de luces apagadas, el estado
         * de mayor valor, es el que menos luces encendidas tiene, dado el tamaño del tablero (3x3)
         como mínimo puede ser 0 y como maximo 9 **/

        int numLucesApagadas = 0;

        for (int i = 0; i < numColumnas; i++)
            for (int j = 0; j < numFilas; j++) /** Si la bombilla esta apagada **/
                if (estado.getCasilla(i,j) == 0)
                    numLucesApagadas++;



        return numLucesApagadas;

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

        return "Los 1's son las luces encendidas y los 0's las luces apagadas";
    }




}
