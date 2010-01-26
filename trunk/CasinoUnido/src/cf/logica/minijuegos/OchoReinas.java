package cf.logica.minijuegos;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.Vector;

public class OchoReinas extends Minijuego {

    private final int size = 8;
    private int columnaActual;
     boolean elegidos [] = new boolean[size];



    public OchoReinas(){

         estado = new Estado(new Dimension(8,1));

         /***
          * Los movimientos posibles son algo que cambian en función del estado.
          */
    }



    public boolean estadoObjetivo() {

        /**
         * El estado es objetivo si las reinas no se atacan entre si.
         */

        if (columnaActual < size -1)
            return false;
        else {
        int numAtaques = 0;
        for (int i = 0; i < estado.getColumnas();i++)
            numAtaques = numAtaques + numeroDeAtaquesEnEstaPosicion(new Posicion(i,estado.getCasilla(i, 0)));

        return numAtaques == 0;
        }
    }

    public boolean hazMovimiento(int movimiento) {

        /**
         *
         * Comprobar que no se comen las reinas..
         *
         *
         * vamos a hacerlo asi, recibimos un numero entre 11 y 88
         *
         * la primera cifra es la columna y la segunda es la fila en la que queremos poner
         * la reina, como tiene que haber una reina en cada columna (y en cada fila), cambiamos la fila de la
         * reina de la columna que le indiquemos.
         *
         */
        /* movimientos = new Vector<Integer>();
         for (int i = 0; i < size;i++)
             for (int j=0; j < size;j++)
                 movimientos.add(i*10 + j);*/


/*
         int columna = movimiento / 10;
         int fila = movimiento % 10;

         if (fila > 7){

             System.out.println("Este movimiento es ilegal");
         }*/


        boolean NoFaltaPorElegir = true;
        int i = 0;
        while (i < size && NoFaltaPorElegir){
           NoFaltaPorElegir = NoFaltaPorElegir && elegidos[i];
           i++;
        }
        /**
         * En este caso, ya se han elegido todos los movimientos, estamos en un nodo terminal.
         */
        if (NoFaltaPorElegir)
            return false;

        if (!elegidos[movimiento]){
            estado.setNumero(new Posicion(columnaActual,0),movimiento);
            return true;
        }//[columna] = fila;
        else return false;
         /*** Habria que actualizar los estados o algo para el resto de busquedas ? **/

    }

    @Override
    public void setEstado(Estado estado){

        this.estado = estado;
        /** Calculamos los posibles movimientos*/
        /** Controlamos estados "cabrones"?,de momento no. **/

        /**
         * Lo malo es que funciona de izquierda a derecha y no al reves.
         * ni de ninguna otra forma.
         */

        elegidos = new boolean[size];
        for (int i = 0; i < elegidos.length;i++)
            elegidos[i] = false;


        for (int i = 0; i < estado.getColumnas(); i++)
                if (estado.getCasilla(i, 0) != -1)
                    elegidos[estado.getCasilla(i, 0)] = true;
                


        /**
         * Listamos los nuevos movimientos.
         */
        movimientos = new Vector<Integer>();
        for (int i = 0; i < elegidos.length;i++){
            if (!elegidos[i])
                movimientos.add(i);
        }

        for (int i = 0; i<estado.getColumnas();i++){

            if (estado.getCasilla(i,0) == -1){
                columnaActual = i;
                break;
            }
        }


    }



    public int numeroDeAtaquesEnEstaPosicion(Posicion pos) {

		int x = pos.getEjeX();
		int y = pos.getEjeY();
		return ataquesEnHorizontal(x, y)
				+ ataquesEnVertical(x, y)
				+ ataquesEnDiagonal(x, y);
	}

	private int ataquesEnHorizontal(int x, int y) {

		int retVal = 0;
		for (int i = 0; i < 8; i++) {
			if (hayReina(new Posicion(i,y))) {
				if (i != x)
					retVal++;
			}
		}
		return retVal;
	}

	private int ataquesEnVertical(int x, int y) {

		int retVal = 0;
		for (int j = 0; j < size; j++) {
			if ((hayReina(new Posicion(x,j)))) {
				if (j != y)
					retVal++;
			}
		}
		return retVal;
	}

	private int ataquesEnDiagonal(int x, int y) {

		int retVal = 0;

		int i;
		int j;
		// forward up diagonal
		for (i = (x + 1), j = (y - 1); (i < size && (j > -1)); i++, j--) {
			if (hayReina(new Posicion(i,j))) {
				retVal++;
			}
		}
		// forward down diagonal
		for (i = (x + 1), j = (y + 1); ((i < size) && (j < size)); i++, j++) {
			if (hayReina(new Posicion(i,j))) {
				retVal++;
			}
		}
		// backward up diagonal
		for (i = (x - 1), j = (y - 1); ((i > -1) && (j > -1)); i--, j--) {
			if (hayReina(new Posicion(i,j))) {
				retVal++;
			}
		}

		// backward down diagonal
		for (i = (x - 1), j = (y + 1); ((i > -1) && (j < size)); i--, j++) {
			if (hayReina(new Posicion(i,j))) {
				retVal++;
			}
		}

		return retVal;
	}

       // hayReina(new Posicion(i,j)

        public boolean hayReina(Posicion pos){

            return estado.getCasilla(pos.getEjeX(),0) == pos.getEjeY();

        }

    @Override
    public double getValorHeuristico(Estado estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean esPeligro(Estado status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getCosteMovimiento(int movimiento, Estado estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getExplicacionEstado() {

        return "Cada número indica la altura de la fila donde está situada la reina en cada una de las columnas";
    }



}
