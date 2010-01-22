package cf.logica.minijuegos;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.Vector;



public class CasillasVecinas extends Minijuego {



    public CasillasVecinas(){

          estado = new Estado(new Dimension(5,5));
          movimientos = new Vector<Integer>();
          /**
           * Inicializa como sea.
           * El 0 es blanco el 1 es negro.
           */

       /**
         *  Como el tablero es de 5x5, tenemos 25 posibles movimientos.
         *
         */
         for (int i = 0; i < 25;i++)
             movimientos.add(i);

          /**
           * Tienes que poner la solución,
           */
          estadoObjetivo = new Estado (new Dimension(5,5));
          for (int i = 0; i < estadoObjetivo.getFilas();i++)
              for (int j= 0; j < estadoObjetivo.getColumnas();j++)
                  estadoObjetivo.setNumero(new Posicion(j,i),1);

    }

  /*  @Override
    public Vector<Integer> getMovimientos() {

       
    */

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
        int columna = movimiento % 5;
        int fila = movimiento - columna * 5;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean esPeligro(Estado status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }




}
