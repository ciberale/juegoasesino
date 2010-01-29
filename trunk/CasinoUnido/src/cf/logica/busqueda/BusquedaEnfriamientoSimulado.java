package cf.logica.busqueda;

import cf.logica.estados.Estado;
import cf.logica.minijuegos.Minijuego;
import cf.logica.minijuegos.MundoBloques;
import java.util.LinkedList;
import java.util.Vector;

/**
 *
 * @author Qiang
 */
public class BusquedaEnfriamientoSimulado extends Busqueda {

    //ColaOrdenadaNodos listaNodos;
    LinkedList<Estado> estadosGenerados = new LinkedList<Estado>();
    //static Logger logger = Logger.getLogger(BusquedaAnchura.class);
    LinkedList<Estado> solucion;

    public BusquedaEnfriamientoSimulado(Minijuego miniJuego) {
        //listaNodos = new ColaOrdenadaNodos();
        this.miniJuego = miniJuego;
        estadosGenerados = new LinkedList<Estado>();
    }

    public void busca() {

       muestraInformacion("**************************************************************");
       muestraInformacion("                 Búsqueda Enfriamiento simulado               ");
       muestraInformacion("**************************************************************");

       muestraInformacion(miniJuego.getExplicacionEstado());
       muestraInformacion("Lista de nodos y estados generados");
        /**
        evaluar(INICIAL)
        si INICIAL es solución entonces devolverlo y parar
        si no
        ACTUAL := INICIAL
        MEJOR_HASTA_AHORA := ACTUAL
        T := TEMPERATURA_INICIAL
        mientras haya operadores aplicables a ACTUAL y no se haya encontrado
        solución hacer
        seleccionar aleatoriamente operador no aplicado a ACTUAL
        {escoge movimiento aleatoriamente (no el mejor)}
        aplicar operador y obtener NUEVOESTADO
        calcular ΔE := evaluar(NUEVOESTADO) - evaluar(ACTUAL)
        si NUEVOESTADO es solución entonces devolverlo y parar
         * si no
         * si NUEVOESTADO mejor que ACTUAL {si mejora situación}
        ACTUAL := NUEVOESTADO {se acepta el movimiento}
        si NUEVOESTADO mejor que
        MEJOR_HASTA_AHORA
        entonces MEJOR_HASTA_AHORA := NUEVOESTADO
        si no {si no mejora la situación, se acepta con prob. < 1}
        calcular P':= e-ΔE/T
        {probabilidad de pasar a un estado peor: se disminuye
        exponencialmente con la “maldad” del movimiento, y
        cuando la temperatura T baja}
        obtener N {nº aleatorio en el intervalo [0,1]}
        si N < P' {se acepta el movimiento}
        entonces ACTUAL := NUEVOESTADO
        actualizar T de acuerdo con la planificación del
        enfriamiento
         * devolver MEJOR_HASTA_AHORA como solución
         */
        Estado estado = (Estado) miniJuego.getEstado().clone();
        Estado estadoActual;
        Estado mejorActual;
        Vector<Integer> movimientos = new Vector<Integer>();
        for (Integer in : miniJuego.getMovimientos()) {
            movimientos.add(in);
        }
        boolean encontrado = false;
        double temperaturainicial = 100;
        double t;// T
        double incremento;// Epsilon.
        double pPrima; //P'
        muestraInformacion("Inicio de la busqueda estado del tablero :" + estado);
        if (miniJuego.estadoObjetivo()) {
            System.out.print("Estado objetivo");
        } else {
            estadoActual = estado;
            mejorActual = estado;
            t = temperaturainicial;
            int movActual = 0;
            int pos = 0;
            double numeroAleatorio = 0;
            while (!movimientos.isEmpty() && !encontrado) {
                pos = (int) ((Math.random() * (movimientos.size())));
                movActual = movimientos.get(pos);
                
                try {
                movimientos.remove(pos);
                } catch(java.lang.ArrayIndexOutOfBoundsException e) {
                    muestraInformacion("t");
                }
                if (miniJuego.hazMovimiento(movActual)) {
                incremento = miniJuego.getValorHeuristico(miniJuego.getEstado()) - miniJuego.getValorHeuristico(estadoActual);
                if (miniJuego.estadoObjetivo()) {
                    // TODO
                    muestraInformacion("Se ha llegado al estado objetivo:" + miniJuego.getEstado());
                    return;
                } else {
                    if (miniJuego.getValorHeuristico(miniJuego.getEstado()) < miniJuego.getValorHeuristico(estadoActual)) {
                        // Si nuevo estado es mejor que el actual.

                        //actualizamos el estado
                        estadoActual = (Estado) miniJuego.getEstado().clone();
                        muestraInformacion("Operador aplicado:" + movActual);
                        muestraInformacion("Estado actual:" + estadoActual);
                        for (Integer in : miniJuego.getMovimientos()) {
                                    movimientos.add(in);
                        }
                        if (miniJuego.getValorHeuristico(miniJuego.getEstado()) < miniJuego.getValorHeuristico(mejorActual)) {
                            //Actualizamos el mejor hasta ahora.
                            mejorActual = (Estado) miniJuego.getEstado().clone();
                        }
                        //Noting nos quedamos con el estado viejo.


                    } else {
                        //Si el nuevo  estado no es mejor que el actual.
                        pPrima = Math.exp(incremento / t);
                            numeroAleatorio = Math.random();
                            if (numeroAleatorio < pPrima) {
                                muestraInformacion("Operador aplicado:" + movActual);
                                //Aceptamos el nuevo estado por aletoriedad.
                                estadoActual = (Estado) miniJuego.getEstado().clone();
                                movimientos = new Vector<Integer>();
                                //Actualizamos los movimientos disponibles.
                                for (Integer in : miniJuego.getMovimientos()) {
                                    movimientos.add(in);
                                }
                                t = t/2;
                            }
                    }
                }
                }else {

                }
            }
            muestraInformacion("No se ha podido encontrar la solucion");
            muestraInformacion("El mejor estado al que se ha llegado es: ");
            miniJuego.setEstado(estado);
            muestraInformacion(miniJuego.pintaEstado());
        }

    //Estado estadoInicial = (Estado) miniJuego.getEstado().clone();





    }

    public static void main(String args[]) {

        /** Probando el laberinto **/
        /*Laberinto laberinto = new Laberinto();
        BusquedaEnfriamientoSimulado busqueda = new BusquedaEnfriamientoSimulado(laberinto);
        busqueda.busca();*/
        /*RelojesArena laberinto = new RelojesArena();
        BusquedaEnfriamientoSimulado busqueda = new BusquedaEnfriamientoSimulado(laberinto);
        busqueda.busca();*/
        //Hannoi laberinto = new Hannoi();
        MundoBloques laberinto = new MundoBloques();
        BusquedaEnfriamientoSimulado busqueda = new BusquedaEnfriamientoSimulado(laberinto);
        busqueda.busca();

        /***
         * Probando el 8 puzzle.
         */

        /*Puzzle8 juego = new Puzzle8();
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
        BusquedaEnfriamientoSimulado busqueda = new BusquedaEnfriamientoSimulado(juego);
        busqueda.busca();*/
       
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
        /*GranjeroLoboCabraCol juego = new GranjeroLoboCabraCol();
        BusquedaEnfriamientoSimulado busqueda = new BusquedaEnfriamientoSimulado(juego);
        busqueda.busca();*/




    /**
     * Probando el juego de los misioneros y los canibales*/
       /*MisionerosYCanibales juego = new MisionerosYCanibales();
    Estado estado = new Estado(new Dimension(3,1));
    estado.setNumero(new Posicion(0,0),3);
    estado.setNumero(new Posicion(0,1),3);
    estado.setNumero(new Posicion(0,2),1);


    juego.setEstado(estado);
    BusquedaEnfriamientoSimulado busqueda = new BusquedaEnfriamientoSimulado(juego);
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
