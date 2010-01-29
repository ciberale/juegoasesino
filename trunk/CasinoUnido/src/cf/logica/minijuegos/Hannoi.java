/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cf.logica.minijuegos;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.Vector;
import movimientos.MovimientosHannoi;

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
        movimientos = new Vector<Integer>();
            for (int i = 0; i < MovimientosHannoi.values().length;i++)
                movimientos.add(MovimientosHannoi.values()[i].ordinal());
        for (int i = 1;i< torres;i++) {
            ocupacion[i]=0;
        }


    }
    @Override
    public boolean estadoObjetivo() {
        return estado.getCasilla(torres -1,discos-1)>0;
    }

        @Override
    public String getExplicacionEstado() {

        return "explicacion";
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
        MovimientosHannoi mov = MovimientosHannoi.values()[movimiento];
       switch (mov) {
           case pasar0a1:
               valido = moverDisco(0,1);
               break;
           case pasar1a2:
               valido = moverDisco(1,2);               
               break;
           case pasar2a1:
               valido = moverDisco(2,1);
               break;
           case pasar1a0:
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
        //TODO Esto no vale!!!
        //while (cont <discos && newValue >=0){

            if (ocupacion[ultimoMovimiento] <2) return false;
            int parteDerecha = ocupacion[ultimoMovimiento]-1;
            newValue  = estado.getCasilla(ultimoMovimiento, parteDerecha);
            lastValue = estado.getCasilla(ultimoMovimiento, parteDerecha-1);
            if (newValue >=0 && newValue < lastValue ) {
                return true;
            }
            //cont++;
            //lastValue = newValue;
        
        return false;
    }

    private int buscarOcupacion(Estado estado, int i) {
        if (i>3 ) return 0;
        int cont=0;
        try {
        while(cont <8 &&estado.getCasilla(i, cont)>=0){
            cont++;
        }
        }catch (Exception e) {
           System.out.print("El estado es "+ estado);
           System.out.print("cont es "+ cont);
           System.out.print("i es "+ i);
        }
        return cont;
    }

    private Estado estadoInicial() {
        Estado estadoAux = new Estado(new Dimension(torres, discos));
        // El numero indica el tamaño de disco.
        for (int i = 0; i<discos;i++) {
            estadoAux.setNumero(new Posicion (0,i), 7-i);
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
        int posOrigen = ocupacion[origen];
        int disco = estado.getCasilla(origen, posOrigen>0?posOrigen-1:0);
        int reversMovimiento = ultimoMovimiento;
        boolean valido = false;
               if (disco>=0) {
                   estado.setNumero(destino,ocupacion[destino], disco);
                   ultimoMovimiento = destino;
                   if (!esPeligro(estado)) {
                       ocupacion[destino]++;
                       int posOrigen2 = ocupacion[origen];
                       estado.setNumero(origen, posOrigen2>0?posOrigen2-1:0, -1);
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

    @Override
    public double getValorHeuristico(Estado estado) {
        int valor = 0;
        ;
        valor += buscarOcupacion(estado,2) *1;
        valor += buscarOcupacion(estado,1) *2;
        valor += buscarOcupacion(estado,0) *3;
        return valor;
    }

    @Override
    public double getCosteMovimiento(int movimiento, Estado estado) {
        return 1;
    }

}
