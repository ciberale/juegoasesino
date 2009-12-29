package cf.logica;

import cf.util.Posicion;
import movimientos.MovimientosLaberinto;

/**
 *
 * @author luigi
 */
public class Laberinto extends Minijuego {

    Posicion actual,objetivo;

    public Laberinto(){

        /*** Podemos poner que esto sea aleatorio?*/

        /** Tenemos definido un laberinto, podriamos poner la entrada y la salida aletoria?
         *
         */
        /**
         * Si no me equivoco, el estado que tenemos que guardar es el laberinto entero junto
         * con la posición, porque de lo contrario, ¿como haces para determinar la heuristica del
         * tablero si solo saben la posicion y no conocen el laberinto?
         */

        actual = new Posicion(0,0);
        objetivo = new Posicion (3,3);

    }

    @Override
    public boolean estadoObjetivo() {

       return actual.equals(objetivo);
    }

    @Override
    public boolean hazMovimiento(int movimiento) {

        MovimientosLaberinto mov = MovimientosLaberinto.values()[movimiento];
        Posicion aux;
        switch(mov){

            case arriba:
                aux = new Posicion(actual.getEjeX()-1,actual.getEjeY());
                break;
            case abajo:
                aux = new Posicion(actual.getEjeX()+1,actual.getEjeY());
                break;
            case izquierda:
                aux = new Posicion(actual.getEjeX(),actual.getEjeY()-1);
                break;
            case derecha:
                aux = new Posicion(actual.getEjeX()-1,actual.getEjeY()+1);
                break;
             default:return false;

        }

         /**
             * If (laberinto.enRango(aux){
             *  borra pos actual y pon blanco
             *  actual = aux;
             *  pon jugador en laberinto.
             *     return true.
          *
          * }
          * else return false;
             */



    }

}
