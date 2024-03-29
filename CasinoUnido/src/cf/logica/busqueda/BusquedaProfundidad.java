package cf.logica.busqueda;

import cf.ParserXML;
import cf.logica.estados.Estado;
import cf.logica.minijuegos.CasillasVecinas;
import cf.logica.minijuegos.Garrafas;
import cf.logica.minijuegos.Hannoi;
import cf.logica.minijuegos.Laberinto;
import cf.logica.minijuegos.Minijuego;
import cf.logica.minijuegos.MisionerosYCanibales;
import cf.logica.minijuegos.OchoReinas;
import cf.logica.minijuegos.Puzzle8;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.LinkedList;
import java.util.Vector;



/**
 *
 * @author luigi
 */
public class BusquedaProfundidad extends Busqueda {

    /***
     *  ¡¡¡¡¡¡¡¡¡¡Control de bucles !!!!!!!!!!!!!!!!!!!!!!!!
     *
     *
     */

   LinkedList<Nodo> listaNodos = new LinkedList<Nodo>();
   LinkedList<Estado> estadosGenerados = new LinkedList<Estado>();
   //static Logger logger = Logger.getLogger(BusquedaAnchura.class);
   LinkedList<Estado> solucion;



   public BusquedaProfundidad(Minijuego miniJuego){

     //  PropertyConfigurator.configure("/home/luigi/Escritorio/log4j.properties");
       this.miniJuego = miniJuego;
       listaNodos = new LinkedList<Nodo>();
        estadosGenerados = new LinkedList<Estado>();
   }



   public boolean busca(){

       try{


       muestraInformacion("**************************************************************");
       muestraInformacion("                 Búsqueda profundidad                          ");
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
                       System.out.println("Esta es la solucion");
                       for (int i = 0; i < solucion.size();i++){
                           miniJuego.setEstado(solucion.get(i));
                           muestraInformacion(miniJuego.pintaEstado());
                       }
                       return true;
                   }
                   else if (!estaRepetido(nodo,miniJuego.getEstado())){
                       
                       muestraInformacion(miniJuego.pintaEstado());
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

        return false;

        }catch(Exception ex){
            return false;
        }

   }



   public static void main(String args[]){

        ParserXML parser = new ParserXML("/home/luigi/casino2.xml");
        //CasillasVecinas juego = new CasillasVecinas(parser);
        Puzzle8 juego = new Puzzle8(parser);
        //MisionerosYCanibales juego = new MisionerosYCanibales();
        //Laberinto juego = new Laberinto(parser);
        
            BusquedaProfundidad busqueda = new BusquedaProfundidad(juego);
            busqueda.busca();

            /*
             * //OchoReinas juego = new OchoReinas(parser);
           /* Estado estado = new Estado(new Dimension(8,1));
            estado.setNumero(new Posicion(0,0),-1);
            estado.setNumero(new Posicion(1,0),-1);
            estado.setNumero(new Posicion(2,0),-1);
            estado.setNumero(new Posicion(3,0),-1);
            estado.setNumero(new Posicion(4,0),-1);
            estado.setNumero(new Posicion(5,0),-1);
            estado.setNumero(new Posicion(6,0),-1);
            estado.setNumero(new Posicion(7,0),-1);*/

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
