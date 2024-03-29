/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cf.logica.minijuegos;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import java.util.Vector;
import movimientos.MovimientosTestJapo;

/**
 *  Representacion:
 *  <p> 
 *  Cada fila corresponde a un tipo de personaje y cada columna a una orilla.
 *  El valor de cada casilla corresponde al numero de individios estan en esa parte
 *  de la orilla 0, 1 ó 2.
 * 
 * </p>
 * @author Qiang
 */
public class TestJapo extends Minijuego {

    int contador;
    private int hijos;
    private int hijas;
    private int padre;
    private int madre;
    private int policia;
    private int ladron;
    private int orillaDerecha;
    private int orillaIzquierda;
    private int barco;

    public TestJapo() {
        hijos = 0;
        hijas = 1;
        padre = 2;
        madre = 3;
        policia = 4;
        ladron = 5;
        orillaDerecha = 1;
        orillaIzquierda = 0;
        barco = orillaIzquierda;
        movimientos = new Vector<Integer>();
        for (int i = 0; i < MovimientosTestJapo.values().length;i++)
            movimientos.add(MovimientosTestJapo.values()[i].ordinal());

        estado = estadoInicial();
        contador = 0;
    }

    public Estado estadoInicial() {
        Estado estadoAux = new Estado(new Dimension(2, 6));
        estadoAux.setNumero(0, 0, 2);
        estadoAux.setNumero(0, 1, 2);
        estadoAux.setNumero(1, 0, 0);
        estadoAux.setNumero(1, 1, 0);
        for (int i = 2; i < 6; i++) {
            estadoAux.setNumero(1, i, 0);
            estadoAux.setNumero(0, i, 1);
        }
        return estadoAux;
    }

    @Override
    public boolean estadoObjetivo() {
        return contador == 6;
    }

    /**
     * 0 - Cambio de orilla Padre e hijo.
     * 1 - Cambio de orilla Madre e hija.
     * 2 - Cambio de orilla Madre y Padre.
     * 3 - Cambio de orilla Poli y ladron.
     * 4 - Cambio de orilla Poli y hijo.
     * 5 - Cambio de orilla Poli y hija.
     * 6 - Cambio de orilla Poli y padre.
     * 7 - Cambio de orilla Poli y madre.
     * @param movimiento
     * @return
     */
    @Override
    public boolean hazMovimiento(int movimiento) {
        boolean posible = false;
        MovimientosTestJapo mov = MovimientosTestJapo.values()[movimiento];
        switch (mov) {
            case padre:
                posible = cambiarOrillaDos(padre, padre);
                break;
            case madre :
                posible = cambiarOrillaDos(madre, madre);
                break;
            case poli:
                posible = cambiarOrillaDos(policia, policia);
                break;
            case padreEhijo:
                posible = cambiarOrillaDos(padre, hijos);
                break;
            case madreEhija:
                posible = cambiarOrillaDos(madre, hijas);
                break;
            case madrePadre:
                posible = cambiarOrilla(madre, padre);
                break;
            case poliLadron:
                posible = cambiarOrilla(policia, ladron);
                break;
            case poliHijo:
                posible = cambiarOrillaDos(policia, hijos);
                break;
            case poliHija:
                posible = cambiarOrillaDos(policia, hijas);
                break;
            case poliPadre:
                posible = cambiarOrillaDos(policia, padre);
                break;
            case poliMadre:
                posible = cambiarOrillaDos(policia, madre);
                break;
        }
        return posible;
    }

    private boolean cambiarOrillaDos(int conductor, int pasajero) {
        boolean aux = false;
        int estadoConductor = estado.getCasilla(barco, conductor);
        int estadoPasajero = estado.getCasilla(barco, pasajero);
        if (estadoConductor > 0 && estadoPasajero > 0) {
            estado.setNumero(cambiarOrilla(barco), conductor, 1);
            estado.setNumero(barco, conductor, 0);
            estado.setNumero(cambiarOrilla(barco), pasajero, 2 - estadoPasajero + 1);
            estado.setNumero(barco, pasajero, estadoPasajero - 1);
            if (esPeligro(estado)) {
                estado.setNumero(cambiarOrilla(barco), conductor, 0);
                estado.setNumero(barco, conductor, 1);
                estado.setNumero(cambiarOrilla(barco), pasajero, 2 - estadoPasajero);
                estado.setNumero(barco, pasajero, estadoPasajero);
            } else {
                barco = cambiarOrilla(barco);
                aux = true;
            }
        }
        return aux;
    }
    
    
    private boolean cambiarOrilla(int conductor, int pasajero) {
        boolean aux = false;
        int estadoConductor = estado.getCasilla(barco, conductor);
        int estadoPasajero = estado.getCasilla(barco, pasajero);
        if (estadoConductor > 0 && estadoPasajero > 0) {
            estado.setNumero(cambiarOrilla(barco), conductor, 1);
            estado.setNumero(barco, conductor, 0);
            estado.setNumero(cambiarOrilla(barco), pasajero, 1);
            estado.setNumero(barco, pasajero, 0);
            if (esPeligro(estado)) {
                estado.setNumero(cambiarOrilla(barco), conductor, 0);
                estado.setNumero(barco, conductor, 1);
                estado.setNumero(cambiarOrilla(barco), pasajero, 0);
                estado.setNumero(barco, pasajero, 1);
            } else {
                barco = cambiarOrilla(barco);
                aux = true;
            }
        }
        return aux;
    }

    @Override
    public boolean esPeligro(Estado status) {
        return comprobarOrilla(orillaIzquierda) || comprobarOrilla(orillaDerecha);
    }

    private boolean comprobarOrilla(int orilla) {
        boolean aux = false;
        int estadoPadre = estado.getCasilla(orilla, padre);
        int estadoMadre = estado.getCasilla(orilla, madre);
        if (estadoPadre != estadoMadre) {
            aux = (estadoPadre > 0 && estado.getCasilla(orilla, hijas) > 0) ||
                    (estadoMadre > 0 && estado.getCasilla(orilla, hijos) > 0);
        }
        if (!aux) {
            if (estado.getCasilla(orilla, ladron) > 0 && estado.getCasilla(orilla, policia) == 0) {
                aux = hayAlguien(orilla);
            }
        }
        return aux;
    }

    private boolean hayAlguien(int orilla) {
        int cont = 0;
        boolean aux = false;
        while (cont < 3 && !aux) {
            aux = estado.getCasilla(orilla, cont) > 0;
        }
        return aux;
    }

    private int cambiarOrilla(int estado) {
        return (estado + 1) % 2;
    }
/**
 *  *  <p>
 *  Cada fila corresponde a un tipo de personaje y cada columna a una orilla.
 *  El valor de cada casilla corresponde al numero de individios estan en esa parte
 *  de la orilla 0, 1 ó 2.
 * @param estado
 * @return
 */
    @Override
    public double getValorHeuristico(Estado estado) {
        //TODO int cont = 0;
        return Math.random();
        
    }

    @Override
    public double getCosteMovimiento(int movimiento, Estado estado) {
        if (movimiento>2) return 2;
        else return 1;

    }

    @Override
    public String getExplicacionEstado() {

        return "TestJapo:\n";
    }

    @Override
    public String pintaEstado() {


        return new String();

    }
}
