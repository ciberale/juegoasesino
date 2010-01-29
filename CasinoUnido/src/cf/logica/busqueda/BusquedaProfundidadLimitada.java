package cf.logica.busqueda;

import cf.logica.estados.Estado;
import cf.logica.minijuegos.Minijuego;
import cf.logica.minijuegos.Puzzle8;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.LinkedList;
import java.util.Vector;


/**
 *
 * @author Qiang
 */
public class BusquedaProfundidadLimitada extends Busqueda {

    /***
     *  ¡¡¡¡¡¡¡¡¡¡Control de bucles !!!!!!!!!!!!!!!!!!!!!!!!
     *
     *
     */

   LinkedList<Nodo> listaNodos = new LinkedList<Nodo>();
   LinkedList<Estado> estadosGenerados = new LinkedList<Estado>();
   //static Logger logger = Logger.getLogger(BusquedaAnchura.class);
   LinkedList<Estado> solucion;



   public BusquedaProfundidadLimitada(Minijuego miniJuego){

     //  PropertyConfigurator.configure("/home/luigi/Escritorio/log4j.properties");
       this.miniJuego = miniJuego;
       listaNodos = new LinkedList<Nodo>();
        estadosGenerados = new LinkedList<Estado>();
   }



   public void busca(){


       muestraInformacion("**************************************************************");
       muestraInformacion("                 Búsqueda Profundidad limitada                ");
       muestraInformacion("**************************************************************");

       muestraInformacion(miniJuego.getExplicacionEstado());
       muestraInformacion("Lista de nodos y estados generados");
       Estado estado = (Estado) miniJuego.getEstado().clone();
       //Estado estadoInicial = (Estado) miniJuego.getEstado().clone();
       Vector<Integer> movimientos = miniJuego.getMovimientos();
       boolean tenemosSolucion = false;
       for (Integer i:movimientos)
           listaNodos.addLast(new Nodo(i,(Estado) estado.clone()));
           /**
            * Asegurate que se insertan bien..
            */

       while (listaNodos.size() > 0 && !tenemosSolucion){

           Nodo nodo = listaNodos.getFirst();
           miniJuego.setEstado((Estado) nodo.getEstado().clone());
           /****
            * Esto es inncesario, de hecho es posible que debamos redefinirlo
            * en la clase reinas, o generalizar para todos los mini-juegos.
            */
           movimientos = miniJuego.getMovimientos();
              if(miniJuego.hazMovimiento(nodo.getNumMovimiento())){

                 // miniJuego.pintaEstado();
                   /**
                    * Si el movimiento esta permitido,comprobamos lo siguiente:
                    */
                   if (miniJuego.estadoObjetivo()){
                       tenemosSolucion = true;
                           // tenemo que ofrecer la solucion, la secuencia de movimientos.

                       boolean fin = false;
                       solucion = new LinkedList<Estado>();

                       /***
                        * Aqui no agregabas el ultimo estado.
                        */

                       miniJuego.getEstado().setEstadoPadre((Estado) nodo.getEstado().clone());
                       Estado actual = miniJuego.getEstado();
                       while (!fin){
                           solucion.addFirst(actual);
                           if (actual.getEstadoPadre() != null)
                                actual = actual.getEstadoPadre();
                           else fin = true;
                       }
                      // solucion.addFirst(estadoInicial);
                       muestraInformacion("Esta es la solucion");
                       for (int i = 0; i < solucion.size();i++)
                           muestraInformacion(solucion.get(i).toString());
                       break;
                   }
                   else if (!estaRepetido(nodo,miniJuego.getEstado())){
                       miniJuego.pintaEstado();
                       Estado estadoNodo = (Estado) nodo.getEstado().clone();
                       /**
                        * Esto estaba mal, quitabas el first al final del codigo, luego
                        * no eliminabas el nodo actual.
                        */
                        listaNodos.removeFirst();
                        nodo = null;
                       for (int i = movimientos.size() -1 ; i >= 0;i--){
                          Nodo n = new Nodo(movimientos.elementAt(i),(Estado) miniJuego.getEstado().clone());
                          n.getEstado().setEstadoPadre(estadoNodo);
                          listaNodos.addFirst(n);
                        }
                   }
                   else {
                        listaNodos.removeFirst();
                        nodo = null;
                   }
                }else {
                        listaNodos.removeFirst();
                        nodo = null;
                   }

           /**
            * Sea valido o no quitamos el nodo. ¿Como vamos almacenando la solucion?
            */
               

                  /**
        * 1.- Tenemos el juego inicializado.
        * 2.- Tenemos un estado inicial.
        * 3.- Tenemos una lista de movimientos.
        * 4.- Inicializamos el bucle, mientras no encontremos la solución, o
        *   la lista de nodos este vacia. seguimos mirando.
        *
        * 4.- Podemos insertar el nodo inicial en la lista de nodos.
        * 5.- Lo cogemos y vamos generando.
        * 6.- Podemos setear el juego, con el estado actual y el movimiento que vamos a aplicar
        * 7.- Que nos devuelva el estado generado.
        */


       }




   }



   public static void main(String args[]){

   /** Probando el laberinto **
        Laberinto laberinto = new Laberinto();
        BusquedaProfundidad busqueda = new BusquedaProfundidad(laberinto);
        busqueda.busca();*/


       /***
        * Probando el 8 puzzle.
        */

           /* Puzzle8 juego = new Puzzle8();
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
            BusquedaProfundidadLimitada busqueda = new BusquedaProfundidadLimitada(juego);
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


                

       /*** Probando las garrafas **
               Garrafas juego = new Garrafas();
                Estado estado = new Estado(new Dimension(2,1));
                estado.setNumero(new Posicion(0,0),0);
                estado.setNumero(new Posicion(1,0),0);
                juego.setEstado(estado);
                BusquedaProfundidad busqueda = new BusquedaProfundidad(juego);
                busqueda.busca();*/




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

    private boolean estaRepetido(Nodo nodo,Estado estado){

       /**
        * Esta función nos indica si tenemos un estado repetido en el camino de la solución
        * actual y lo descarta, (opcion 1).
        */
       if (nodo.getEstado().getEstadoPadre() == null && !nodo.getEstado().equals(estado))
           return false;
       else if (nodo.getEstado().equals(estado))
           return true;
       else{
            Estado actual = estado.getEstadoPadre();
            while (actual != null){
            if (estado.equals(actual))
                return true;
            else actual = actual.getEstadoPadre();
        }
            return false;
       }


    }

}
