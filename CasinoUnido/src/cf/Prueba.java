package cf;

import cf.logica.FactoriaJuegosYBusquedas;
import cf.logica.TipoBusquedas;
import cf.logica.TipoJuegos;
import cf.logica.busqueda.Busqueda;
import cf.logica.busqueda.Nodo;
import cf.logica.estados.Estado;
import cf.logica.minijuegos.Minijuego;
import cf.util.ColaOrdenadaNodos;
import cf.util.Dimension;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Prueba {

    public static void main (String args[]){


        /*** Pruebas de ejecucion de los mini-juegos **/

        Minijuego juego;
        Busqueda busqueda;
        ParserXML parser = new ParserXML("/home/luigi/casino.xml");
        FactoriaJuegosYBusquedas fJB = new FactoriaJuegosYBusquedas();
        //for (TipoBusquedas tb:TipoBusquedas.values())
        TipoBusquedas tb = TipoBusquedas.Greedy;
        {
        for (TipoJuegos t:TipoJuegos.values()){            
                juego = fJB.dameJuego(t, parser);
                busqueda = fJB.dameBusqueda(tb,juego);
                busqueda.busca();
            }
        }










       /* ColaOrdenadaNodos cola = new ColaOrdenadaNodos();

        Estado uno = new Estado(new Dimension(3,3));
        Estado dos = new Estado(new Dimension(3,3));
        Estado tres = new Estado(new Dimension(3,3));
        Estado cuatro = new Estado(new Dimension(3,3));

        uno.setCosteHeuristico(3);
        dos.setCosteHeuristico(1);
        cuatro.setCosteHeuristico(5);
        tres.setCosteHeuristico(3);



        cola.aniade(new Nodo(1,uno));
        cola.aniade(new Nodo(2,dos));
        cola.aniade(new Nodo(3,tres));
        cola.aniade(new Nodo(4,cuatro));


        while (cola.damePrimero() != null){

           System.out.println(cola.damePrimero().getCosteHeuristico());
           cola.quitaPrimero();
        }
        */
    }

}
