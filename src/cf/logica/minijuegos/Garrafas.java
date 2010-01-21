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




    public Garrafas(){

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

        return estado.getCasilla(1,0) == 2;

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean esPeligro(Estado status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
