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

    public void comenzarPartida(){

          partida.comenzarPartida();
    }

    public void finalizarPartida(){
        
        
    }
    
    public void  verificarPartida(){
        System.out.println("Hola");
        partida.verificaTablero();
    }
    
    public void insertarMovimiento(Posicion pos){

        partida.insertaMovimiento(pos,Color.BLACK);
        
    }

    public void abrirFichero(String pathFichero) {

        partida.abrirFichero(pathFichero);
    }



}
