package cf.logica.busqueda;

import cf.logica.estados.Estado;
import cf.logica.minijuegos.CasillasVecinas;
import cf.logica.minijuegos.Garrafas;
import cf.logica.minijuegos.Laberinto;
import cf.logica.minijuegos.Minijuego;
import cf.logica.minijuegos.MisionerosYCanibales;
import cf.logica.minijuegos.OchoReinas;
import cf.logica.minijuegos.Puzzle8;
import cf.util.ColaOrdenadaNodos;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 *
 * @author luigi
 */
public class BusquedaAEstrella extends Busqueda{

    /***
     * Por completar !!!!!!!!!!!!!!!!!!!!!!!!!, esto es una copia de Greedy.
     *
     *
     */

   private ColaOrdenadaNodos listaNodosAbiertos;
   private ColaOrdenadaNodos listaNodosCerrados;



   //LinkedList<Estado> estadosGenerados = new LinkedList<Estado>();
   //static Logger logger = Logger.getLogger(BusquedaAnchura.class);
   private Nodo solucionNodo;
   private LinkedList<Estado> solucion;



   public BusquedaAEstrella(Minijuego miniJuego){

       listaNodosAbiertos = new ColaOrdenadaNodos();
       listaNodosCerrados = new ColaOrdenadaNodos();       
       this.miniJuego = miniJuego;

   }



   public void busca(){

       Estado estado = (Estado) miniJuego.getEstado().clone();
       Vector<Integer> movimientos = miniJuego.getMovimientos();
       boolean tenemosSolucion = false;

       /*** Agregamos el nodo inicial **/
      listaNodosAbiertos.aniade(new Nodo((Estado) estado.clone()));



       while (!tenemosSolucion){

           // Esta bien esto aqui ?
           if (listaNodosAbiertos.size() == 0)
               break;

           /** Cogemos el nodo mas prometedor de la lista de nodos **/
           Nodo nodo = listaNodosAbiertos.damePrimero();
           /** Lo quitamos de abiertos **/
           listaNodosAbiertos.quitaPrimero(); /// espero que no lo ponga a null:
           /** Lo insertamos en cerrados, debe ser ordenada esta lista? **/
           listaNodosCerrados.aniade(nodo);


           miniJuego.setEstado((Estado) nodo.getEstado().clone());

           /** Si es solucion **/
           if (miniJuego.estadoObjetivo()){
               tenemosSolucion = true;
               solucionNodo = nodo;
           }
           else{

               LinkedList <Nodo> listaNodos = new LinkedList<Nodo>();
                /** Generamos sucesores del nodo, ramificacion **/
               
               /** Para cada sucesor que generamos hacemos la función tratar_sucesor **/
                movimientos = miniJuego.getMovimientos();

                for (int mov:movimientos){
                    /** Seteamos el estado, clonado, y calculamos el movimiento **/
                    miniJuego.setEstado((Estado) nodo.getEstado().clone());
                    miniJuego.hazMovimiento(mov);

                    /** Creo que aqui deberiamos comprobar que el nodo no está repetido? o que el movimiento
                     * es valido, o si el estado es el mismo que el anterior (no hemos avanzao nada) etc.
                     */

                    listaNodos.add(new Nodo((Estado)miniJuego.getEstado().clone()));
                }
                /** Aqui tratamos a los nodos sucesores **/
                tratarSucesores(listaNodos,nodo);
           }

       }

   }

       private void tratarSucesores(LinkedList<Nodo> listaNodos,Nodo mejorNodo){

                /** Para cada uno de ellos ***/
                 
           
           for (Nodo n: listaNodos){
               
               /*** Comprobamos que el nodo no esté ni en abiertos ni en cerrados **/
               
               boolean noEnAbiertos= true ,noEnCerrados = true;
               Nodo viejoAbiertos = null,viejoCerrados = null;

               for(int i = 0; i < listaNodosAbiertos.size();i++){
                   noEnAbiertos = !listaNodosAbiertos.elementAt(i).getEstado().equals(n.getEstado());
                   if (!noEnAbiertos){
                       /** Guardamos el nodo repetido **/
                       viejoAbiertos = listaNodosAbiertos.elementAt(i);
                       break;
                   }
               }

               for(int i = 0; i < listaNodosCerrados.size();i++){
                   noEnAbiertos = !listaNodosCerrados.elementAt(i).getEstado().equals(n.getEstado());
                   if (!noEnCerrados){
                       viejoCerrados = listaNodosCerrados.elementAt(i);
                       break;
                   }
               }

               /** Si no esta ni en abiertos ni en cerrados **/
               if (noEnCerrados && noEnAbiertos){
                    /** Hay que calcular la f del nodo (si no estaba ya) **/
                    listaNodosAbiertos.aniade(n);
                    
                    /*** Aniadimos el nodo a la lista de sucesores **/
                    mejorNodo.getListaSucesores().add(n);
                    n.setPadre(mejorNodo);
               }

               else{

                    if (!noEnAbiertos){
                        
                        if (n.getCosteActual() < viejoAbiertos.getCosteActual()){
                            viejoAbiertos.setPadre(mejorNodo);
                            /** Actualizamos g(viejo) y f'(viejo) **/


                            
                            /** Aniadimos el nodo viejo a los sucesores de mejorNodo **/
                            mejorNodo.getListaSucesores().add(mejorNodo);
                        }
                           
                    }
                    
                    if (!noEnCerrados){
                        
                        if (n.getCosteActual() < viejoCerrados.getCosteActual()){

                              viejoAbiertos.setPadre(mejorNodo);
                            /** Actualizamos g(viejo) y f'(viejo) **/

                            /** Propagar g a sucesores de viejo,bucle... **/

                              /** eliminamos sucesor **/
                              
                              
                             mejorNodo.getListaSucesores().add(mejorNodo);
                            
                        }
                    }
               }   
           }
}



   public static void main(String args[]){

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
               Garrafas juego = new Garrafas();
                Estado estado = new Estado(new Dimension(2,1));
                estado.setNumero(new Posicion(0,0),0);
                estado.setNumero(new Posicion(1,0),0);
                juego.setEstado(estado);
                BusquedaGreedy busqueda = new BusquedaGreedy(juego);
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
