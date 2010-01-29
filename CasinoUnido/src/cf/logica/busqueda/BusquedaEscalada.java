package cf.logica.busqueda;

import cf.logica.estados.Estado;
import cf.logica.minijuegos.Minijuego;
import cf.logica.minijuegos.MundoBloques;
import cf.util.ColaOrdenadaNodos;
import java.util.LinkedList;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Qiang
 */
public class BusquedaEscalada extends Busqueda {

    
    ColaOrdenadaNodos listaNodos;
    LinkedList<Estado> estadosGenerados = new LinkedList<Estado>();
    static Logger logger = Logger.getLogger(BusquedaAnchura.class);
    LinkedList<Estado> solucion;

    public BusquedaEscalada(Minijuego miniJuego) {

        
        listaNodos = new ColaOrdenadaNodos();
        this.miniJuego = miniJuego;
        estadosGenerados = new LinkedList<Estado>();
    }

    public boolean  busca() {

        try{
        /**
         * si es un estado objetivo entonces devolverlo y parar
        si no ACTUAL := INICIAL
        mientras haya operadores aplicables a ACTUAL y no se haya
        encontrado solución hacer
        seleccionar un operador no aplicado todavía a ACTUAL
        aplicar operador y generar NUEVO_ESTADO
        evaluar NUEVO_ESTADO
        si es un estado objetivo entonces devolverlo y parar
        si no
        si NUEVO_ESTADO es mejor que ACTUAL
        entonces ACTUAL := NUEVO_ESTADO
         */

       muestraInformacion("**************************************************************");
       muestraInformacion("                 Búsqueda Escalada                          ");
       muestraInformacion("**************************************************************");

       muestraInformacion(miniJuego.getExplicacionEstado());
       muestraInformacion("Lista de nodos y estados generados");
        Estado estado = (Estado) miniJuego.getEstado().clone();
        Estado estadoActual;
        Vector<Integer> movimientos = miniJuego.getMovimientos();
        boolean encontrado = false;
        boolean bloqueado = false;
        boolean estadoActualizado = false;
        double valorActual = 0;
        double valorNuevo = 0;
        muestraInformacion("Inicio de la busqueda estado del tablero :" + estado);
        if (miniJuego.estadoObjetivo()) {
            System.out.print("Estado objetivo");
        } else {
            estadoActual = estado;
            while (!encontrado && !bloqueado) {
                estadoActualizado = false;
                for (Integer mov : movimientos) {
                    if (miniJuego.hazMovimiento(mov)) {
                    valorActual = miniJuego.getValorHeuristico(estadoActual);
                    valorNuevo = miniJuego.getValorHeuristico(miniJuego.getEstado());
                    if (miniJuego.estadoObjetivo()) {
                        encontrado = true;
                    } else {
                        if (valorActual >= valorNuevo && !miniJuego.getEstado().equals(estadoActual)) {
                            muestraInformacion("Movimiento aplicado "+mov+" estado del tablero :" + miniJuego.getEstado());
                            estadoActual = (Estado) miniJuego.getEstado().clone();
                            estadoActualizado = true;
                            return true;
                        } else {
                            miniJuego.setEstado((Estado) estadoActual.clone());
                        }
                    }
                    
                    }
                }
                bloqueado = !estadoActualizado;
            }
            if (bloqueado) {
                muestraInformacion("No se ha podido encontrar la solucion");
            } else {
                muestraInformacion("El estado solucion es : " +miniJuego.getEstado());
            }
        }

        //Estado estadoInicial = (Estado) miniJuego.getEstado().clone();

        return false;

        }catch(Exception ex){
            return false;
        }





    }

    public static void main(String args[]) {

        /** Probando el laberinto **
        Laberinto laberinto = new Laberinto();
        BusquedaProfundidad busqueda = new BusquedaProfundidad(laberinto);
        busqueda.busca();*/
        /***
         * Probando el 8 puzzle.
         *

        Puzzle8 juego = new Puzzle8();
        Estado estado = new Estado(new Dimension(3,3));

        estado.setNumero(new Posicion(0,0),1);
        estado.setNumero(new Posicion(1,0),2);
        estado.setNumero(new Posicion(2,0),3);

        estado.setNumero(new Posicion(0,1),4);
        estado.setNumero(new Posicion(1,1),5);
        estado.setNumero(new Posicion(2,1),8);

        estado.setNumero(new Posicion(0,2),0);
        estado.setNumero(new Posicion(1,2),6);
        estado.setNumero(new Posicion(2,2),7);



        juego.setEstado(estado);
        BusquedaProfundidad busqueda = new BusquedaProfundidad(juego);
        busqueda.busca();

        // */
        /**
         * Probando las casillas vecinas. *

        CasillasVecinas juego = new CasillasVecinas();
        Estado estado = new Estado(new Dimension(5,5));

        estado.setNumero(new Posicion(0,0),1);
        estado.setNumero(new Posicion(1,0),1);
        estado.setNumero(new Posicion(2,0),1);
        estado.setNumero(new Posicion(3,0),1);
        estado.setNumero(new Posicion(4,0),1);

        estado.setNumero(new Posicion(0,1),1);
        estado.setNumero(new Posicion(1,1),1);
        estado.setNumero(new Posicion(2,1),0);
        estado.setNumero(new Posicion(3,1),1);
        estado.setNumero(new Posicion(4,1),1);

        estado.setNumero(new Posicion(0,2),1);
        estado.setNumero(new Posicion(1,2),0);
        estado.setNumero(new Posicion(2,2),0);
        estado.setNumero(new Posicion(3,2),0);
        estado.setNumero(new Posicion(4,2),1);

        estado.setNumero(new Posicion(0,3),0);
        estado.setNumero(new Posicion(1,3),1);
        estado.setNumero(new Posicion(2,3),0);
        estado.setNumero(new Posicion(3,3),1);
        estado.setNumero(new Posicion(4,3),0);

        estado.setNumero(new Posicion(0,4),0);
        estado.setNumero(new Posicion(1,4),0);
        estado.setNumero(new Posicion(2,4),1);
        estado.setNumero(new Posicion(3,4),0);
        estado.setNumero(new Posicion(4,4),0);


        juego.setEstado(estado);
        BusquedaProfundidad busqueda = new BusquedaProfundidad(juego);
        busqueda.busca();*/
        /*** Probando las garrafas **/
        /*Garrafas juego = new Garrafas();
        Estado estado = new Estado(new Dimension(2, 1));
        estado.setNumero(new Posicion(0, 0), 0);
        estado.setNumero(new Posicion(1, 0), 0);
        juego.setEstado(estado);
        BusquedaEscalada busqueda = new BusquedaEscalada(juego);
        busqueda.busca();*/
        //GranjeroLoboCabraCol juego = new GranjeroLoboCabraCol();
        //Hannoi juego = new Hannoi();
        MundoBloques juego = new MundoBloques();


        
        BusquedaEscalada busqueda = new BusquedaEscalada(juego);
        busqueda.busca();




    /**
     * Probando el juego de los misioneros y los canibales*/
    /*   MisionerosYCanibales juego = new MisionerosYCanibales();
    Estado estado = new Estado(new Dimension(3,1));
    estado.setNumero(new Posicion(0,0),3);
    estado.setNumero(new Posicion(0,1),3);
    estado.setNumero(new Posicion(0,2),1);


    juego.setEstado(estado);
    BusquedaProfundidad busqueda = new BusquedaProfundidad(juego);
    busqueda.busca();*/

    /*OchoReinas juego = new OchoReinas();
    Estado estado = new Estado(new Dimension(8,1));
    estado.setNumero(new Posicion(0,0),-1);
    estado.setNumero(new Posicion(1,0),-1);
    estado.setNumero(new Posicion(2,0),-1);
    estado.setNumero(new Posicion(3,0),-1);
    estado.setNumero(new Posicion(4,0),-1);
    estado.setNumero(new Posicion(5,0),-1);
    estado.setNumero(new Posicion(6,0),-1);
    estado.setNumero(new Posicion(7,0),-1);



    juego.setEstado(estado);
    BusquedaProfundidad busqueda = new BusquedaProfundidad(juego);
    busqueda.busca(); */
    }

    /**
     *  Para el control de bucles tenemos varias opciones:
     *
     * 1.- Mirar los nodos de la solución actual: Bien facil, coges el nodo y subes hacia arriba comparandolo
     * con sus antecesores.
     *
     * 2.- Mirar los nodos abiertos y cerrados (muy ineficiente) aparte de ocupar bastante espacio, ya
     * que aparte tenemos que almacenar los nodos cerrados tambien.
     *
     *
     */
    private boolean estaRepetido(Nodo nodo, Estado estado) {

        /**
         * Esta función nos indica si tenemos un estado repetido en el camino de la solución
         * actual y lo descarta, (opcion 1).
         */
        if (nodo.getEstado().getEstadoPadre() == null && !nodo.getEstado().equals(estado)) {
            return false;
        } else if (nodo.getEstado().equals(estado)) {
            return true;
        } else {
            Estado actual = estado.getEstadoPadre();
            while (actual != null) {
                if (estado.equals(actual)) {
                    return true;
                } else {
                    actual = actual.getEstadoPadre();
                }
            }
            return false;
        }


    }
}
