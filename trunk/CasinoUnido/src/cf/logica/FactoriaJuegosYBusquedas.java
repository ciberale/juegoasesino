/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cf.logica;

import cf.ParserXML;
import cf.logica.busqueda.Busqueda;
import cf.logica.busqueda.BusquedaAEstrella;
import cf.logica.busqueda.BusquedaAnchura;
import cf.logica.busqueda.BusquedaEnfriamientoSimulado;
import cf.logica.busqueda.BusquedaEscalada;
import cf.logica.busqueda.BusquedaGreedy;
import cf.logica.busqueda.BusquedaProfundidad;
import cf.logica.busqueda.BusquedaProfundidadLimitada;
import cf.logica.busqueda.BusquedaUniforme;
import cf.logica.minijuegos.CasillasVecinas;
import cf.logica.minijuegos.Garrafas;
import cf.logica.minijuegos.GranjeroLoboCabraCol;
import cf.logica.minijuegos.Hannoi;
import cf.logica.minijuegos.Laberinto;
import cf.logica.minijuegos.Minijuego;
import cf.logica.minijuegos.MisionerosYCanibales;
import cf.logica.minijuegos.MundoBloques;
import cf.logica.minijuegos.OchoReinas;
import cf.logica.minijuegos.Puzzle8;
import cf.logica.minijuegos.RelojesArena;
import cf.logica.minijuegos.TestJapo;
import cf.logica.minijuegos.VendedorAlfombras;

/**
 *
 * @author luigi
 */
public class FactoriaJuegosYBusquedas {


    public Busqueda dameBusqueda(TipoBusquedas busqueda,Minijuego juego){

            switch(busqueda){
                //case AEstrella: return new BusquedaAEstrella(juego);
                case Anchura: return new BusquedaAnchura(juego);
                case Profundidad: return new BusquedaProfundidad(juego);
                case Greedy: return new BusquedaGreedy(juego);

                //case Escalada: return new BusquedaEscalada(juego);
                case ProfundidadLimitada: return new BusquedaProfundidadLimitada(juego);
                case Uniforme: return new BusquedaUniforme(juego);
               // case EnfriamientoSimulado: return new BusquedaEnfriamientoSimulado(juego);

            }
            return null;

    }

    public Minijuego dameJuego(TipoJuegos juego,ParserXML parserXML){


            switch(juego){

                case CasillasVecinas: return new CasillasVecinas(parserXML);
                case Garrafas: return new Garrafas();
                case GranjeroLoboCabra: return new GranjeroLoboCabraCol();
                case Laberinto: return new Laberinto(parserXML);
                case Hannoi: return new Hannoi();
                case MisionerosYCanibales: return new MisionerosYCanibales();
                case OchoReinas: return new OchoReinas(parserXML);
                //case TestJapo: return new TestJapo();
                //case OchoPuzzle: return new Puzzle8(parserXML);

                case MundoBloques: return new MundoBloques();
                case RelojesArena: return new RelojesArena();
                case VendedorAlfombras: return new VendedorAlfombras(parserXML);


            }
            return null;
    }

}
