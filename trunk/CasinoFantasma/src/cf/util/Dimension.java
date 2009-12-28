package cf.util;

public class Dimension {
	
	int columnas;
	int filas;
	
	public Dimension(int col,int fil){
		
		columnas = col;
		filas = fil;
	}

	public int getColumnas(){
		return columnas;
	}
	
	public int getFilas(){
		return filas;
	}
}
