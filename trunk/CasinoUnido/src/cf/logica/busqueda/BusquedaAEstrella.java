package cf.logica.busqueda;

import cf.ParserXML;
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
           Nodo nodo = dameYBorraMejorNodo();

           
           /** Lo insertamos en cerrados, debe ser ordenada esta lista? **/
           listaNodosCerrados.aniade(nodo);


           miniJuego.setEstado((Estado) nodo.getEstado().clone());

           /** Si es solucion **/
           if (miniJuego.estadoObjetivo()){
               tenemosSolucion = true;
               solucionNodo = nodo;
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
           /*** En caso contrario seguimos buscando **/
           else{

               LinkedList <Nodo> listaNodos = new LinkedList<Nodo>();
                /** Generamos sucesores del nodo, ramificacion **/
               
               /** Para cada sucesor que generamos hacemos la función tratar_sucesor **/
                movimientos = miniJuego.getMovimientos();

                for (int mov:movimientos){
                    /** Seteamos el estado, clonado, y calculamos el movimiento **/
                    miniJuego.setEstado((Estado) nodo.getEstado().clone());
                    miniJuego.hazMovimiento(mov);
                    muestraInformacion(miniJuego.getEstado().toString());
                    /** Creo que aqui deberiamos comprobar que el nodo no está repetido? o que el movimiento
                     * es valido, o si el estado es el mismo que el anterior (no hemos avanzao nada) etc.
                     */

                    listaNodos.add(new Nodo((Estado)miniJuego.getEstado().clone()));
                }
                /** Aqui tratamos a los nodos sucesores **/
                tratarSucesores(listaNodos,nodo);

                /** Habria que reordenar la lista de abiertos, no?,casi mejor recorrer el array y extraer el mas prometedor **/
           }

       }

   }

       private void tratarSucesores(LinkedList<Nodo> listaNodos,Nodo mejorNodo){

                /** Para cada uno de ellos ***/
                 
           
           for (Nodo n: listaNodos){
               
               /*** Comprobamos que el nodo no esté ni en abiertos ni en cerrados **/
               
               boolean noEnAbiertos= true ,noEnCerrados = true;
               Nodo viejoAbiertos = null,viejoCerrados = null;


               if (listaNodosAbiertos.size() == 0)
                   noEnAbiertos = true;
               else
               for(int i = 0; i < listaNodosAbiertos.size();i++){
                   if (listaNodosAbiertos.elementAt(i).getEstado().equals(n.getEstado())){
                       /** Guardamos el nodo repetido **/
                       viejoAbiertos = listaNodosAbiertos.elementAt(i);
                       noEnAbiertos = false;
                       break;
                   }
               }

                if (listaNodosCerrados.size() == 0)
                   noEnCerrados = true;
               else
               for(int i = 0; i < listaNodosCerrados.size();i++){
                   
                   if (!noEnCerrados){
                       viejoCerrados = listaNodosCerrados.elementAt(i);
                       noEnCerrados = false;
                       break;
                   }
                  // noEnAbiertos = !listaNodosCerrados.elementAt(i).getEstado().equals(n.getEstado());
               }

               /** Si no esta ni en abiertos ni en cerrados **/
               if (noEnCerrados && noEnAbiertos){
                    /** Hay que calcular la f del nodo (si no estaba ya) **/
                   /*** f = g(coste,pasos para llegar al nodo,) + h(heuristica del estado) **/
                   n.setCosteHeuristico(n.getCosteActual() + n.getEstado().getCosteHeuristico());
                   listaNodosAbiertos.aniade(n);
                    
                    /*** Aniadimos el nodo a la lista de sucesores **/
                    mejorNodo.getListaSucesores().add(n);
                    n.setPadre(mejorNodo);
               }

               else{

                    if (!noEnAbiertos){

                        /// La g es getCosteActual **/
                        if (n.getCosteActual() < viejoAbiertos.getCosteActual()){
                            viejoAbiertos.setPadre(mejorNodo);
                            /** Actualizamos g(viejo) y f'(viejo) **/
                            viejoAbiertos.setCosteHeuristico(n.getCosteActual() + n.getEstado().getCosteHeuristico());
                            viejoAbiertos.setCosteActual(n.getCosteActual());
                            
                            /** Aniadimos el nodo viejo a los sucesores de mejorNodo **/
                            mejorNodo.getListaSucesores().add(mejorNodo);
                        }
                           
                    }
                    
                    if (!noEnCerrados){
                        
                        if (n.getCosteActual() < viejoCerrados.getCosteActual()){

                              viejoAbiertos.setPadre(mejorNodo);
                            /** Actualizamos g(viejo) y f'(viejo) **/
                            viejoAbiertos.setCosteHeuristico(n.getCosteActual() + n.getEstado().getCosteHeuristico());
                            viejoAbiertos.setCosteActual(n.getCosteActual());

                            /** Propagar g a sucesores de viejo,bucle... **/
                            progagaGaSucesores(viejoAbiertos);

                            /** eliminamos sucesor **/

                             mejorNodo.getListaSucesores().add(mejorNodo);
                            
                        }
                    }
               }   
           }
}



   public static void main(String args[]){

             /*   ParserXML parser = new ParserXML("/home/luigi/casino.xml");
                CasillasVecinas juego = new CasillasVecinas(parser);
                BusquedaAEstrella busqueda = new BusquedaAEstrella(juego);
                busqueda.busca();*/


                 ParserXML parser = new ParserXML("/home/luigi/casino.xml");

       MisionerosYCanibales juego = new MisionerosYCanibales();
       Busqueda busqueda = new BusquedaAnchura(juego);
       busqueda.busca();

       /***
        * Probando el 8 puzzle.*/


           /* Puzzle8 juego = new Puzzle8(parser);
            BusquedaAnchura busqueda = new BusquedaAnchura(juego);
            busqueda.busca();*/
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

    private void progagaGaSucesores(Nodo viejoAbiertos) {

        for (Nodo n:viejoAbiertos.getListaSucesores()){

            //// Asumimos que todos los movimientos tienen coste 1.
            n.setCosteActual(viejoAbiertos.getCosteActual() + 1);
            progagaGaSucesores(n);
        }

    }

    private Nodo dameYBorraMejorNodo() {

        int index = 0;
        Nodo masPrometedor = listaNodosAbiertos.damePrimero();
        double h = listaNodosAbiertos.damePrimero().getCosteHeuristico();
        for (int i = 0; i < listaNodosAbiertos.size();i++){

            if (h < listaNodosAbiertos.elementAt(i).getCosteHeuristico()){
                masPrometedor = listaNodosAbiertos.elementAt(i);
                index = i;
            }
        }

        /** Eliminamos el nodo de la listaNodosAbiertos **/

        listaNodosAbiertos.elimina(index);
        return masPrometedor;

    }

}
