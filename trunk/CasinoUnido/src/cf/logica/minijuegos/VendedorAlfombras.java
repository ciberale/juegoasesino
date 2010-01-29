/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cf.logica.minijuegos;

import cf.ParserXML;
import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.Vector;

/**
 *
 * @author luigi
 */
public class VendedorAlfombras extends Minijuego {


    private Estado mapa;
    private Estado heuristica;
    private double costeMovimiento;


    public VendedorAlfombras(ParserXML parser){

       /* mapa = new Estado(new Dimension(6,6));
        heuristica = new Estado(new Dimension(6,1));*/
        mapa = parser.parseaGrafoVendedorAlfombras();
        heuristica = parser.parseaHeuristicaVendedorAlfombras();
        costeMovimiento = 0;
        estado = new Estado(new Dimension(1,1));
        //// Nos ponemos en el nodo 1.
        estado.setNumero(0,0,1);
        
        /*** Inicializamos los movimientos **/

        movimientos = new Vector<Integer>();

        for (int i = 1; i < mapa.getFilas();i++)
            movimientos.add(i);


    }

    @Override
    public String getExplicacionEstado() {

        return "El vendedor ambulante de alfombras\n El juego consiste en obtener un recubrimiento del mapa de carreteras de un pueblo\n.El estado consiste en un unico numero que representa el pueblo en el que estamos\n";
    }

    @Override
    public double getCosteMovimiento(int movimiento, Estado estado) {

        /** Tiramos de la matriz de costes **/
        return mapa.getCasilla(movimiento,estado.getCasilla(0,0));

        
    }

    @Override
    public boolean estadoObjetivo() {

        //*** Estamos en el pueblo 5 **/
        return estado.getCasilla(0,0) == 5;

    }

    @Override
    public boolean hazMovimiento(int movimiento) {

        /*** Comprobamos si podemos ir al nodo desde el sitio en el que estamos...
         *
         *
         */



        if (mapa.enRango(new Posicion(movimiento,estado.getCasilla(0,0))) && mapa.getCasilla(movimiento,estado.getCasilla(0,0)) > 0){

                /** El estado es legal, luego devolvemos el nuevo estado **/
                costeMovimiento = mapa.getCasilla(movimiento,estado.getCasilla(0,0));
                estado.setNumero(0,0,movimiento);
      
                return true;
        }
        else return false;

    }

    @Override
    public double getValorHeuristico(Estado estado) {


            return heuristica.getCasilla(estado.getCasilla(0,0),0);
                  /** Tiramos de la matriz de heuristica **/
    }

    @Override
    public boolean esPeligro(Estado status) {

        return false;
    }




}
