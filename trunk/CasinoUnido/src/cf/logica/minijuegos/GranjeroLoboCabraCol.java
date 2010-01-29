/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cf.logica.minijuegos;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.Vector;
import movimientos.MovimientosGranjero;

/** 
 * Los estados son cada uno de los personajes que participan en el juego.
 * y el valor de su casilla corresponde a la posicion que se encuentra.
 * 
 *  - Personajes:
 *  0 - Granjero.
 *  1 - Lobo.
 *  2 - Cabra.
 *  3 - Col.
 * 
 *  - Posiciones:
 *  0 - Izquierda del rio.
 *  1 - Derecha del rio. 
 * @author Qiang
 */
public class GranjeroLoboCabraCol extends Minijuego {

    private Posicion granjero;
    private Posicion lobo;
    private Posicion cabra;
    private Posicion col;

    public GranjeroLoboCabraCol() {
        granjero = new Posicion(0, 0);
        lobo = new Posicion(1, 0);
        cabra = new Posicion(2, 0);
        col = new Posicion(3, 0);
        estado = estadoInicial();
        movimientos = new Vector<Integer>();
        for (int i = 0; i < MovimientosGranjero.values().length;i++)
            movimientos.add(MovimientosGranjero.values()[i].ordinal());


    }

    @Override
    public String getExplicacionEstado() {

        return "explicacion";
    }

    /**
     * Comprobacion de que todos los elementos esten a la izquierda del rio.
     * @return
     */
    @Override
    public boolean estadoObjetivo() {
        int cont = 0;
        boolean objetivo = true;
        while (cont < estado.getColumnas() && objetivo) {
            objetivo &= estado.getCasilla(cont, 0) == 1;
            cont++;
        }
        return objetivo;
    }

    /**
     * El número indica que personaje quiere desplazar al otro lado del rio.
     * Como solo puede llevar la barca el granjero , el movimiento sera valido solo
     * si el personaje a desplazar esta en el mismo lado del granjero.
     * Movimientos : 
     *  - 0 : El granjero se desplaza solo a la otra orilla del rio.
     *  - 1 : El granjero se desplaza con  el lobo a la otra orilla del rio.
     *  - 2 : El granjero se desplaza con  la cabra a la otra orilla del rio.
     *  - 3 : El granjero se desplaza con  la col a la otra orilla del rio.
     * @param movimiento
     * @return
     */
    @Override
    public boolean hazMovimiento(int movimiento) {
        int situacionGranjero = 0;
        MovimientosGranjero mov = MovimientosGranjero.values()[movimiento];
        boolean movimientoValido = false;
        switch (mov) {
            case Granjero:
                situacionGranjero = estado.getCasilla(granjero);
                situacionGranjero = (situacionGranjero + 1) % 2;
                estado.setNumero(granjero, situacionGranjero);
                if (!esPeligro(estado)) {
                    movimientoValido = true;
                } else {
                situacionGranjero = (situacionGranjero + 1) % 2;
                estado.setNumero(granjero, situacionGranjero);
                }
                break;
            case Lobo:
                movimientoValido = cambiarDeOrilla(lobo);
                break;
            case Cabra:
                movimientoValido = cambiarDeOrilla(cabra);
                break;
            case Col:
                movimientoValido = cambiarDeOrilla(col);
                break;

        }
        return movimientoValido;
    }

    private boolean cambiarDeOrilla(Posicion pasajero) {
        int situacionGranjero = estado.getCasilla(granjero);
        int situacionPasajero = estado.getCasilla(pasajero);
                if (situacionGranjero == situacionPasajero) {
                    situacionGranjero = (situacionGranjero + 1) % 2;
                    estado.setNumero(granjero, situacionGranjero);
                    estado.setNumero(pasajero, situacionGranjero);
                    if (!esPeligro(estado)) {
                        return true;
                    }
                    estado.setNumero(granjero, situacionPasajero);
                    estado.setNumero(pasajero, situacionPasajero);
                }
        return false;
        
    }
    public Estado estadoInicial() {
        // Todos los elementos estan a la izquierda del rio.
        Estado estadoAux = new Estado(new Dimension(4, 1));
        estadoAux.setNumero(granjero, 0);
        estadoAux.setNumero(lobo, 0);
        estadoAux.setNumero(col, 0);
        estadoAux.setNumero(cabra, 0);
        return estadoAux;
    }

    @Override
    public boolean esPeligro(Estado status) {
        int statusGrajero = status.getCasilla(granjero);
        int statusLobo = status.getCasilla(lobo);
        int statusCabra = status.getCasilla(cabra);
        int statusCol = status.getCasilla(col);
        return (statusCabra != statusGrajero) && (statusCol == statusCabra || statusCabra == statusLobo);

    }

    @Override
    public double getValorHeuristico(Estado estado) {
        int cont = 0;
        /*
         * Numero de vueltas que tiene que dar el granjero para
         * llevar a todos los pasajeros sin temer por que el lobo se
         * coma a la cabra.
         */
        for (int control = 1; control< estado.getColumnas();control++) {
            if (estado.getCasilla(control, 0) == 1) {
            cont++;
            }
        }
        if (cont == 3) {
            return 5;
        }
        if (estado.getCasilla(0, 0) == 0) {
            //cont +=2;
        }
        return cont;
    }

    @Override
    public double getCosteMovimiento(int movimiento, Estado estado) {
        return 1;
    }

    @Override
    public String pintaEstado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
