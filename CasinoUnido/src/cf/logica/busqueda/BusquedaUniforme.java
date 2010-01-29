package cf.logica.busqueda;

import cf.ParserXML;
import cf.logica.estados.Estado;
import cf.logica.minijuegos.CasillasVecinas;
import cf.logica.minijuegos.Garrafas;
import cf.logica.minijuegos.GranjeroLoboCabraCol;
import cf.logica.minijuegos.Hannoi;
import cf.logica.minijuegos.Minijuego;
import cf.logica.minijuegos.MundoBloques;
import cf.logica.minijuegos.TestJapo;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 *
 * @author Qiang
 */
public class BusquedaUniforme extends Busqueda {

    /***
     *  ¡¡¡¡¡¡¡¡¡¡Control de bucles !!!!!!!!!!!!!!!!!!!!!!!!
     *
     *
     */
    ArrayList<Nodo> listaNodos = new ArrayList<Nodo>();
    ArrayList<Estado> estadosGenerados = new ArrayList<Estado>();
    private static Logger log = Logger.getLogger(BusquedaAnchura.class);
    //Cola de prioridad
    PriorityQueue<Nodo> cola;
    ArrayList<Estado> solucion;
    HashMap<Integer, Double> distancias;
    HashMap<Integer, Nodo> padre;
    HashMap<Integer, Nodo> visitados;
    Nodo raiz;
    int nodeCount;

    public BusquedaUniforme(Minijuego miniJuego) {

        //  PropertyConfigurator.configure("/home/luigi/Escritorio/log4j.properties");
        this.miniJuego = miniJuego;
        listaNodos = new ArrayList<Nodo>(500);
        estadosGenerados = new ArrayList<Estado>();
        Comparator<Nodo> comparador = new Comparator<Nodo>() {

            public int compare(Nodo o1, Nodo o2) {
                return o1.compareTo(o2);
            }
        };
        distancias = new HashMap<Integer, Double>();
        padre = new HashMap<Integer, Nodo>();
        cola = new PriorityQueue<Nodo>(10, comparador);
        nodeCount = 0;
        //TODO arreglar ñapa
        raiz = new Nodo(-1, (Estado) miniJuego.getEstado().clone());
        nodeCount = raiz.setId(nodeCount);
        visitados = new HashMap<Integer, Nodo>();

    }

    public void busca() {


       muestraInformacion("**************************************************************");
       muestraInformacion("                 Búsqueda Uniforme                          ");
       muestraInformacion("**************************************************************");

       muestraInformacion(miniJuego.getExplicacionEstado());
       muestraInformacion("Lista de nodos y estados generados");

        Estado estado; //= (Estado) miniJuego.getEstado().clone();
        Estado estadoInicial = (Estado) miniJuego.getEstado().clone();
        Vector<Integer> movimientos = miniJuego.getMovimientos();
        boolean tenemosSolucion = false;
        Nodo nodoAux = null;
        int hashNumber; //Esta variable la usamos para el control de estados

        /*
         * Inicializacion de datos:
         * para cada nodo conectado al nodo inicial  le asignamos su coste y su
         * nodo padre.
         */
        for (Integer i : movimientos) {
            if (miniJuego.hazMovimiento(i)) {
                estado = miniJuego.getEstado();
                // En el nodo le guardamos el estado con el movimiento aplicado.s
                hashNumber = estado.hashCode();
                if (visitados.containsKey(hashNumber)) {
                    muestraInformacion("Nodos de nivel 1 duplicados, posiblemente haya bucles.");
                } else {                                
                nodoAux = new Nodo(i, estado);
                nodeCount = nodoAux.setId(nodeCount);
                cola.add(nodoAux);
                visitados.put(hashNumber,nodoAux );
                }
                distancias.put(nodoAux.getId(), miniJuego.getCosteMovimiento(i, estadoInicial));
                padre.put(nodoAux.getId(), raiz);
                miniJuego.setEstado((Estado) estadoInicial.clone());
            }
        }
        Estado estadoAux;
        double coste;
        double costeAnterior;
        Estado estadoAuxInicial;
        Nodo nodoAuxiliar;

        //while (!cola.isEmpty()&& !tenemosSolucion){
        // Exploramos todo el arbol de busquedas.
        while (!cola.isEmpty() && ! tenemosSolucion) {
            muestraInformacion("Size de la cola " + cola.size());
            Nodo nodoPadre = cola.poll();
            muestraInformacion("Nuevo elemento de la cola quitado " + nodoPadre.getId());
            muestraInformacion("Size de la cola " + cola.size());
            estadoAuxInicial = nodoPadre.getEstado();
            coste = distancias.get(nodoPadre.getId());

            //Sabemos que el movimiento es valido.
            miniJuego.setEstado((Estado) estadoAuxInicial.clone());
            estadoAux = miniJuego.getEstado();
            for (Integer i : movimientos) {
                costeAnterior = Double.MAX_VALUE;
                if (miniJuego.hazMovimiento(i)) {

                    estado = miniJuego.getEstado();
                    // En el nodo le guardamos el estado con el movimiento aplicado.s
                    hashNumber = estado.hashCode();
                    if (visitados.containsKey(hashNumber)) {
                        nodoAux = visitados.get(hashNumber);
                        costeAnterior = distancias.get(nodoAux.getId());
                        // TODO buscar el nodo
                        //costeAnterior = asignar valor.
                        log.warn("Ha entrado en la parte que falta");
                        } else {                        
                        nodoAux = new Nodo(i, estado);
                        nodeCount = nodoAux.setId(nodeCount);
                        cola.add(nodoAux);
                        visitados.put(hashNumber, nodoAux);
                    }
                    coste = miniJuego.getCosteMovimiento(i, estadoAuxInicial)+distancias.get(nodoPadre.getId());
                    if (coste < costeAnterior ) {
                        distancias.put(nodoAux.getId(),coste);
                        padre.put(nodoAux.getId(), nodoPadre);
                    }
                    if (miniJuego.estadoObjetivo()) {
                        log.info("Solucion encontrada " + padre);
                        log.info("El nodo final es " + nodoAux.getId());
                        muestraInformacion("El nodo final es " + nodoAux.getId());
                        muestraInformacion("El  tablero quedo " +nodoAux.getEstado().toString());
                        nodoAuxiliar =  padre.get(nodoAux.getId());
                        int count = 0;
                        while (nodoAuxiliar.getId() != 0) {
                            muestraInformacion("El padre  es " +  nodoAuxiliar.getId());
                            nodoAuxiliar = padre.get(nodoAuxiliar.getId());
                            count++;
                        }
                        muestraInformacion(miniJuego.pintaEstado());
                        muestraInformacion("Numero de pasos dados: " +  count);
                        return;

                    }

                    miniJuego.setEstado((Estado) estadoAuxInicial.clone());
                }  
            }

  
        }


    }

    public static void main(String args[]) {


        /***
         * Probando el 8 puzzle.
        */

        /*Puzzle8 juego = new Puzzle8();
        Estado estado = new Estado(new Dimension(3,3));

        estado.setNumero(new Posicion(0,0),0);
        estado.setNumero(new Posicion(1,0),1);
        estado.setNumero(new Posicion(2,0),2);

        estado.setNumero(new Posicion(0,1),3);
        estado.setNumero(new Posicion(1,1),4);
        estado.setNumero(new Posicion(2,1),5);

        estado.setNumero(new Posicion(0,2),6);
        estado.setNumero(new Posicion(1,2),7);
        estado.setNumero(new Posicion(2,2),8);



        juego.setEstado(estado);
        BusquedaUniforme busqueda = new BusquedaUniforme(juego);
        busqueda.busca();*/
        
        /**
         * Probando las casillas vecinas. */
        /// Desbordamiento de memoria....
          /*  CasillasVecinas juego = new CasillasVecinas();
        Estado estado = new Estado(new Dimension(3,3));

        estado.setNumero(new Posicion(0,0),1);
        estado.setNumero(new Posicion(1,0),0);
        estado.setNumero(new Posicion(2,0),1);

        estado.setNumero(new Posicion(0,1),0);
        estado.setNumero(new Posicion(1,1),0);
        estado.setNumero(new Posicion(2,1),0);


        estado.setNumero(new Posicion(0,2),1);
        estado.setNumero(new Posicion(1,2),0);
        estado.setNumero(new Posicion(2,2),1);


        juego.setEstado(estado);
        BusquedaAnchura busqueda = new BusquedaAnchura(juego);
        busqueda.busca(); */
        /*** Probando las garrafas **/
        /*Garrafas juego = new Garrafas();
        BusquedaUniforme busqueda = new BusquedaUniforme(juego);
        busqueda.busca();*/
    //Hannoi juegoM = new Hannoi();
    //MundoBloques juegoM =  new MundoBloques();
    //GranjeroLoboCabraCol juegoM = new GranjeroLoboCabraCol();
   // TestJapo juegoM = new TestJapo();
    ParserXML parser = new ParserXML("/home/luigi/casino2.xml");
    //CasillasVecinas juegoM = new CasillasVecinas(parser);


    Garrafas juegoM = new Garrafas();
    BusquedaUniforme busquedaM = new BusquedaUniforme(juegoM);
    busquedaM.busca();
    /**
     * Probando el juego de los misioneros y los canibales*/
     /*MisionerosYCanibales juegoM = new MisionerosYCanibales();
    Estado estadoM = new Estado(new Dimension(3,1));
    estadoM.setNumero(new Posicion(0,0),3);
    estadoM.setNumero(new Posicion(1,0),3);
    estadoM.setNumero(new Posicion(2,0),1);


    juegoM.setEstado(estadoM);
    BusquedaUniforme busquedaM = new BusquedaUniforme(juegoM);
    busquedaM.busca();*/

    /*GranjeroLoboCabraCol juego2 = new GranjeroLoboCabraCol();
    BusquedaUniforme busqueda2 = new BusquedaUniforme(juego2);
    busqueda2.busca();
    muestraInformacion("El estado final es" + juego2.getEstado());*/
    /**
     * Probando el juego de las 8-reinas.*


    OchoReinas juego = new OchoReinas();
    Estado estado = new Estado(new Dimension(8,1));
    /***
     * Si le damos valores en orden (de arriba a abajo, el juego la completa.
     *
     *

    estado.setNumero(new Posicion(0,0),4);
    estado.setNumero(new Posicion(1,0),-1);
    estado.setNumero(new Posicion(2,0),-1);
    estado.setNumero(new Posicion(3,0),-1);
    estado.setNumero(new Posicion(4,0),-1);
    estado.setNumero(new Posicion(5,0),-1);
    estado.setNumero(new Posicion(6,0),-1);
    estado.setNumero(new Posicion(7,0),-1);



    juego.setEstado(estado);
    BusquedaAnchura busqueda = new BusquedaAnchura(juego);
    busqueda.busca(); */
    /* Laberinto laberinto = new Laberinto();
    BusquedaAnchura busqueda = new BusquedaAnchura(laberinto);
    busqueda.busca();

    /* OchoReinas juego = new OchoReinas();
    Estado estado = new Estado(new Dimension(8,1));
    estado.setNumero(new Posicion(0,0),6);
    estado.setNumero(new Posicion(1,0),4);
    estado.setNumero(new Posicion(2,0),2);
    estado.setNumero(new Posicion(3,0),0);
    estado.setNumero(new Posicion(4,0),5);
    estado.setNumero(new Posicion(5,0),7);
    estado.setNumero(new Posicion(6,0),1);
    estado.setNumero(new Posicion(7,0),3);

    juego.setEstado(estado);

    if (juego.estadoObjetivo()){
    muestraInformacion("Es correcto");
    }
    else muestraInformacion("No es correcto");
     */
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
            nodo.getEstado().hashCode();
            return false;
        }



    }
}
