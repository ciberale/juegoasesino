/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cf.logica;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;

/**
 *
 * @author Qiang
 */
public class Hannoi extends Minijuego{
    private static final int discos = 8;
    private static final int torres = 3;
    //Indica en que columna se situo la ultima ficha.
    private int ultimoMovimiento;
    private int[] ocupacion;
    
    

    public Hannoi() {
        estado = estadoInicial();
        ocupacion = new int[torres];
        ocupacion[0]=8;
        for (int i = 1;i< torres;i++) {
            ocupacion[i]=0;
        }


    }
    @Override
    public boolean estadoObjetivo() {
        return estado.getCasilla(torres -1,discos-1)>0;
    }

    /**
     * El movimiento consiste en pasar el disco que este en la cima de una barra
     * a la otra barra:
     *  - 0: Pasar de la barra 0 a la barra 1.
     *  - 1: Pasar de la barra 1 a la barra 2.
     *  - 2: Pasar de la barra 2 a la barra 1.
     *  - 3: Pasar de la barra 1 a la barra 0.
     * @param movimiento
     * @return
     */
    
    @Override
    public boolean hazMovimiento(int movimiento) {        
        boolean valido = false;
       switch (movimiento) {
           case 0:
               valido = moverDisco(0,1);
               break;
           case 1:
               valido = moverDisco(1,2);               
               break;
           case 2:
               valido = moverDisco(2,1);
               break;
           case 3:
               valido = moverDisco(1,0);
               break;
       }
       return valido;
    }

    @Override
    public boolean esPeligro(Estado status) {
        int cont = 0;
        int newValue =0;
        int lastValue = 0;
        while (cont <discos && newValue >=0){
            newValue  = estado.getCasilla(ultimoMovimiento, discos);
            if (newValue >=0 && newValue < lastValue ) {
                return true;
            }
            lastValue = newValue;
        }
        return false;
    }

    private Estado estadoInicial() {
        Estado estadoAux = new Estado(new Dimension(torres, discos));
        // El numero indica el tamaño de disco.
        for (int i = 0; i<discos;i++) {
            estadoAux.setNumero(new Posicion (i,0), i);
        }
        // -1 es el valor para indicar que no hay disco.
        for (int cols = 1; cols< torres; cols++ ) {
            for (int filas = 0; filas< discos; filas++ ) {
                estadoAux.setNumero(new Posicion (cols,filas), -1);
            }
        }
        return estadoAux;
    }
    
    public boolean moverDisco(int origen, int destino) {
        int disco = estado.getCasilla(origen, ocupacion[origen]-1);
        int reversMovimiento = ultimoMovimiento;
        boolean valido = false;
               if (disco>=0) {
                   estado.setNumero(destino,ocupacion[destino], disco);
                   ultimoMovimiento = destino;
                   if (!esPeligro(estado)) {
                       ocupacion[destino]++;
                       estado.setNumero(origen, ocupacion[origen]-1, -1);
                       ocupacion[origen]--;
                       ultimoMovimiento = destino;
                       valido = true;
                   } else {
                       ultimoMovimiento = reversMovimiento;
                        estado.setNumero(destino,ocupacion[destino], -1);
                   }
               }
        return valido;
    }

}
