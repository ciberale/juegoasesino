package cf.logica.estados;

import cf.util.Dimension;
import cf.util.Posicion;

/**
 *
 * @author luigi
 */
public class Estado {

    int casillas [][];
    int numColumnas;
    int numFilas;



    public Estado(Dimension dim){

        casillas = new int[dim.getColumnas()][dim.getFilas()];
        numColumnas = dim.getColumnas();
        numFilas = dim.getFilas();

    }

    public int getCasilla(int columna, int fila) {
        return casillas[columna][fila];
    }
    
    public int getCasilla(Posicion pos) {
        return casillas[pos.getEjeX()][pos.getEjeY()];
    }
    
    public int getFilas(){
       return numFilas;
    }

    public int getColumnas(){
        return numColumnas;
    }

    public int[][] getEstado(){
        return casillas;
    }

    public void setNumero(Posicion pos,int numero){
        try {
        casillas[pos.getEjeX()][pos.getEjeY()] = numero;
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Te has salido tio");
        }
    }
    
    public void setNumero(int col, int fila ,int numero){
        try {
            casillas[col][fila] = numero;
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Te has salido tio");
        }
    }


    public boolean equals(Estado estado){

        boolean igual = true;
        int i = 0;
        while (igual && i < numColumnas){
             int j = 0;
             while(igual && j < numFilas){
                igual = getCasilla(i,j) == estado.getCasilla(i, j);
                j++;
            }
           i++;
        }
        return igual;

        /**
         *  Hacer test para comprobar si esto está bien.
         */
    }

    public boolean enRango(Posicion pos) {

        return pos.getEjeX() < getFilas() && pos.getEjeY() < getColumnas();
    }

    


}
