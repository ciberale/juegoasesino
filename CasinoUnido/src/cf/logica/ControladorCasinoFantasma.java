package cf.logica;

import cf.util.Dimension;
import cf.util.Posicion;
import java.awt.Color;


public class ControladorCasinoFantasma {
    
    CFPartida partida;

    public ControladorCasinoFantasma(CFPartida partida){
        
        this.partida = partida;   
    }

    /*public void iniciarPartida(){

        partida.iinicializarPartida();
    }*/


    public void iniciarPartida(int numCasillas){
        
        partida.inicializarPartida(new Dimension(numCasillas,numCasillas), 3);

    }

    public void comenzarPartida(TipoBusquedas busqueda){

          partida.comenzarPartida(busqueda);
    }

    public void finalizarPartida(){
        
        
    }
    
   
    public void insertarMovimiento(Movimientos mov){
        
        partida.hazMovimiento(mov.ordinal());
    }

    public void salir(){
        partida.salir();
    }

    public void abrirFichero(String pathFichero) {

        partida.cargarFicheroXML(pathFichero);
    }


    public void seguirPartida(){

        partida.seguirPartida();
    }

    public void dameNombreJuegoYEstrategia(int x, int y) {
        
        partida.dameNombreJuegoYEstrategia(x,y);

    }

    public void guardarTrazas() {

        partida.guardarTrazas();
    }

    public void setearVelocidad(int segundos) {

        partida.setVelocidad(segundos);
    }



}
