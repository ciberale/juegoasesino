/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cf.logica.minijuegos;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import java.util.Vector;
import movimientos.MovimientosMundoBloque;

/** 
 * 
 * 
 * 
 *  - Personajes:
 *  0 - A.
 *  1 - B.
 *  2 - C.
 *  3 - Col.
 * 
 *  - Posiciones:
 *  0 - Izquierda del rio.
 *  1 - Derecha del rio. 
 * @author Qiang
 */
public class MundoBloques extends Minijuego {
    private static final int A =0;
    private static final int B =1;
    private static final int C=2;
    public MundoBloques() {
        
        estado = estadoInicial();
        movimientos = new Vector<Integer>();
        for (int i = 0; i < MovimientosMundoBloque.values().length;i++)
            movimientos.add(MovimientosMundoBloque.values()[i].ordinal());


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
return         estado.getCasilla(0,0) == 2 && estado.getCasilla(1,0) == 1&& estado.getCasilla(3,0)==0;
        
    }

    /**
     
     * @param movimiento
     * @return
     */
    @Override
    public boolean hazMovimiento(int movimiento) {
     
        MovimientosMundoBloque mov = MovimientosMundoBloque.values()[movimiento];
        boolean movimientoValido = false;
        switch (mov) {
            case desapilar:
                int pos = 0;
                int cont = 0;
                while(pos > 0  && cont < 3) {
                    pos = estado.getCasilla(cont, 0);
                    cont++;
                }
                if (pos <0 ){
                    return false;
                }
                estado.setNumero(cont,0, -1);
                
                break;
            case apilarA:
                if (esta(A)) {
                    return false;
                } else {
                    apilar(A);
                }
                
                break;
            case apilarB:
                if (esta(B)) {
                    return false;
                } else {
                    apilar(B);
                }
                break;
            case aPilarC:
                if (esta(C)) {
                    return false;
                } else {
                    apilar(C);
                }
                break;

        }
        return movimientoValido;
    }

    private boolean esta(int valor) {
        for (int i = 0; i<estado.getColumnas();i++ ) {
            if (estado.getCasilla(i, 0) == valor) {
                return true;
            }
        }
        return false;        
    }
    
    private void apilar(int valor) {
        int i =0;
        while (estado.getCasilla(i, 0) >=0) {
            i++;
        }
        estado.setNumero(i, 0, valor);
    }


    public Estado estadoInicial() {        
        Estado estadoAux = new Estado(new Dimension(3, 1));
        estadoAux.setNumero(0, 0, 2);
        estadoAux.setNumero(1, 0, 0);
        estadoAux.setNumero(2, 0, 1);
        return estadoAux;
    }

    @Override
    public boolean esPeligro(Estado status) {
        return false;

    }

    @Override
    public double getValorHeuristico(Estado estado) {
        //TODO
        int cont = 0;

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
}
