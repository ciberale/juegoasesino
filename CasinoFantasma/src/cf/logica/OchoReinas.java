package cf.logica;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;

public class OchoReinas extends Minijuego {

    int size = 8;

    public OchoReinas(){

         estado = new Estado(new Dimension(8,1));
        
    }



    public boolean estadoObjetivo() {

        /**
         * El estado es objetivo si las reinas no se atacan entre si.
         */

        int numAtaques = 0;
        for (int i = 0; i < estado.getColumnas();i++)
            numAtaques = numAtaques + numeroDeAtaquesEnEstaPosicion(new Posicion(i,estado.getCasilla(i, 1)));

        return numAtaques == 0;
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


         int columna = movimiento / 10;
         int fila = movimiento % 10;

         estado.setNumero(new Posicion(columna,0),fila);//[columna] = fila;

        return true;
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



}
