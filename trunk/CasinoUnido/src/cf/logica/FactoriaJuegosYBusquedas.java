/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cf.logica;

import cf.logica.busqueda.Busqueda;
import cf.logica.busqueda.BusquedaAEstrella;
import cf.logica.busqueda.BusquedaAnchura;
import cf.logica.busqueda.BusquedaGreedy;
import cf.logica.busqueda.BusquedaProfundidad;
import cf.logica.minijuegos.CasillasVecinas;
import cf.logica.minijuegos.Garrafas;
import cf.logica.minijuegos.GranjeroLoboCabraCol;
import cf.logica.minijuegos.Hannoi;
import cf.logica.minijuegos.Laberinto;
import cf.logica.minijuegos.Minijuego;
import cf.logica.minijuegos.MisionerosYCanibales;
import cf.logica.minijuegos.OchoReinas;
import cf.logica.minijuegos.Puzzle8;
import cf.logica.minijuegos.TestJapo;

/**
 *
 * @author luigi
 */
public class FactoriaJuegosYBusquedas {


    public Busqueda dameBusqueda(TipoBusquedas busqueda,Minijuego juego){

            switch(busqueda){
                case AEstrella: return new BusquedaAEstrella(juego);
                case Anchura: return new BusquedaAnchura(juego);
                case Profundidad: return new BusquedaProfundidad(juego);
                case Greedy: return new BusquedaGreedy(juego);
            }
            return null;

    }

    public Minijuego dameJuego(TipoJuegos juego){


            switch(juego){

                case CasillasVecinas: return new CasillasVecinas();
                case Garrafas: return new Garrafas();
                case GranjeroLoboCabra: return new GranjeroLoboCabraCol();
                case Laberinto: return new Laberinto();
                case Hannoi: return new Hannoi();
                case MisionerosYCanibales: return new MisionerosYCanibales();
                case OchoReinas: return new OchoReinas();
                case TestJapo: return new TestJapo();
                case OchoPuzzle: return new Puzzle8();
            }
            return null;
    }

}
