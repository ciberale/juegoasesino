package cf.logica.minijuegos;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.Vector;
import movimientos.MovimientosRelojes;

/**
 *
 * @author Qiang
 */
public class RelojesArena extends Minijuego {


    //private Integer [][] valorHeuristica;
    /**
     * Relojes de arena:
     * Disponemos de dos relojes de arena. Uno mide 7 minutos y el otro mide 11 minutos. Con
        estos dos relojes podemos hacer las siguientes cosas:
        - Girar un reloj (con lo que la arena de un lado cae en el otro)
        - Girar los dos relojes a la vez hasta que uno de los dos se vacíe.
     * Representación :
     * Posicion (0,0) = cantidad que arena que tiene en el lado inferior el reloj de 7 minutos
     * Posicion (1,0) = cantidad que arena que tiene en el lado inferior el reloj de 11 minutos
     */
    private Posicion reloj7;
    private Posicion reloj11;
    public RelojesArena(){
        reloj7= new Posicion(0,0);
        reloj11 = new Posicion(1,0);
        estado = new Estado(new Dimension(2,1));
        estado.setNumero(reloj7,7);
        estado.setNumero(reloj11,11);

        movimientos = new Vector<Integer>();
        for (int i = 0; i < MovimientosRelojes.values().length;i++)
            movimientos.add(MovimientosRelojes.values()[i].ordinal());




      
    }

    @Override
    public boolean estadoObjetivo() {
        int tiempo7 = estado.getCasilla(reloj7);
        int tiempo11 = estado.getCasilla(reloj11);
        if (tiempo7>1 && tiempo7<6) {
            return true;
        }
        if (tiempo11>2 && tiempo11<9)
            return true;
        return false;

    }

    @Override
    public boolean hazMovimiento(int movimiento) {


        MovimientosRelojes mov = MovimientosRelojes.values()[movimiento];

        switch(mov){

            case girar11:
                estado.setNumero(reloj11,11);
                return true;

            case girar7:
                estado.setNumero(reloj7,7);
                return true;


            case girarAmbos:
                int estado7 = estado.getCasilla(reloj7);
                int estado11 = estado.getCasilla(reloj11);
                int traspaso = Math.min(estado7, estado11);
                estado.setNumero(reloj7,7 - estado7 + traspaso);
                estado.setNumero(reloj11,11 - estado11 + traspaso);
                return true;
            default:return false;
        }

    }

    @Override
    public double getValorHeuristico(Estado estado) {
    //TODO hacer heuristica!!!!
        return Math.random();
        
    }

    @Override
    public boolean esPeligro(Estado status) {
        return false;
    }

    @Override
    public double getCosteMovimiento(int movimiento, Estado estado) {
        MovimientosRelojes mov = MovimientosRelojes.values()[movimiento];

        switch(mov){

            case girar11:
                return estado.getCasilla(reloj11);


            case girar7:
                return estado.getCasilla(reloj7);


            case girarAmbos:
                int estado7 = estado.getCasilla(reloj7);
                int estado11 = estado.getCasilla(reloj11);
                int traspaso = Math.min(estado7, estado11);
                return traspaso*2;
            default:return 1;
        }
    }

    @Override
    public String getExplicacionEstado() {

        return "Relojes de arena: Disponemos de dos relojes de arena. Uno mide 7 minutos y el otro mide 11 minutos. " +
                "Con estos dos relojes podemos hacer las siguientes cosas:" +
                "Girar un reloj (con lo que la arena de un lado cae en el otro)" +
                "Girar los dos relojes a la vez hasta que uno de los dos se vacíe." +
                "Representación :"+
     " Posicion (0,0) = cantidad que arena que tiene en el lado inferior el reloj de 7 minutos"+
      "Posicion (1,0) = cantidad que arena que tiene en el lado inferior el reloj de 11 minutos";

    }

}
