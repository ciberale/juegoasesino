package cf.logica.minijuegos;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.Vector;
import movimientos.MovimientosGarrafas;

/**
 *
 * @author luigi
 */
public class Garrafas extends Minijuego {


    private Integer [][] valorHeuristica;

    public Garrafas(){


        valorHeuristica = new Integer[5][4];

        valorHeuristica[0][0] = 6;
        valorHeuristica[0][1] = 2;
        valorHeuristica[0][2] = 1;
        valorHeuristica[0][3] = 5;


        valorHeuristica[1][0] = 3;
        valorHeuristica[1][1] = 2;
        valorHeuristica[1][2] = 2;
        valorHeuristica[1][3] = 4;


        valorHeuristica[2][0] = 0;
        valorHeuristica[2][1] = 0;
        valorHeuristica[2][2] = 0;
        valorHeuristica[2][3] = 0;

        valorHeuristica[3][0] = 4;
        valorHeuristica[3][1] = 5;
        valorHeuristica[3][2] = 2;
        valorHeuristica[3][3] = 3;

        valorHeuristica[4][0] = 7;
        valorHeuristica[4][1] = 1;
        valorHeuristica[4][2] = 2;
        valorHeuristica[4][3] = 6;



        estado = new Estado(new Dimension(2,1));
        estado.setNumero(new Posicion(0,0),0);
        estado.setNumero(new Posicion(1,0),0);

        movimientos = new Vector<Integer>();
        for (int i = 0; i < MovimientosGarrafas.values().length;i++)
            movimientos.add(MovimientosGarrafas.values()[i].ordinal());




      /* No es muy necesario.
       estadoObjetivo = new Estado(new Dimension(2,1));
        estadoObjetivo.setNumero(new Posicion(0,0),2);
        estadoObjetivo.setNumero(new Posicion(1,0),0);*/


        // Nos definimos una clase privada para  manejar el estado?
    }

    @Override
    public boolean estadoObjetivo() {

        return estado.getCasilla(0,0) == 2;

    }

    @Override
    public boolean hazMovimiento(int movimiento) {


        MovimientosGarrafas mov = MovimientosGarrafas.values()[movimiento];

        switch(mov){

            case llenarBote3litros:

                estado.setNumero(new Posicion(1,0),3);
                return true;

            case llenarBote4litros:
                estado.setNumero(new Posicion(0,0),4);
                return true;


            case vaciarBote3litros:

                estado.setNumero(new Posicion(1,0),0);
                return true;


            case vaciarBote4litros:

                estado.setNumero(new Posicion(0,0),0);
                return true;



            case volcarBote3litrosEnBote4litros:

                int bote3 = estado.getCasilla(1,0);
                int bote4 = estado.getCasilla(0,0);
                int maxlitros = 4 - bote4; /// numero maximo de litros que podemos echar.
                estado.setNumero(new Posicion(0,0),bote4 + Math.min(maxlitros,bote3));
                estado.setNumero(new Posicion(1,0), bote3 - Math.min(maxlitros,bote3));

                return true;

                
            case volcarBote4litrosEnBote3litros:
                
                int b3 = estado.getCasilla(1,0);
                int b4 = estado.getCasilla(0,0);
                int maxl = 3 - b3;  /// numero maximo de litros que podemos echar.


                estado.setNumero(new Posicion(1,0),b3 + Math.min(maxl,b3));
                estado.setNumero(new Posicion(0,0),b4 - Math.min(maxl,b3));

                return true;

            default: return false;
        }

    }

    @Override
    public double getValorHeuristico(Estado estado) {

        return valorHeuristica[estado.getCasilla(0,0)][estado.getCasilla(1,0)];
        
    }

    @Override
    public boolean esPeligro(Estado status) {
        return false;
    }

    @Override
    public double getCosteMovimiento(int movimiento, Estado estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
