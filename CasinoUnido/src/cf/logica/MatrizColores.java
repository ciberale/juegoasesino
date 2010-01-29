package cf.logica;

import cf.util.Dimension;
import cf.util.Posicion;
import java.awt.Color;

/**
 *
 * @author luigi
 */
public class MatrizColores {

    Color casillasColor [][];
    int numColumnas;
    int numFilas;



    public MatrizColores(Dimension dim){

        casillasColor = new Color[dim.getColumnas()][dim.getFilas()];
        numColumnas = dim.getColumnas();
        numFilas = dim.getFilas();

    }

    public Color getCasilla(int columna, int fila) {
        return casillasColor[columna][fila];
    }

    public int getFilas(){
       return numFilas;
    }

    public int getColumnas(){
        return numColumnas;
    }

    public Color[][] getMatrizColores(){
        return casillasColor;
    }

    public void setColor(Posicion pos,Color color){
        
        casillasColor[pos.getEjeX()][pos.getEjeY()] = color;
        
    }

    public void setMatriz(Color [][]matriz){
        casillasColor = matriz;
    }

  public void setColor(int j, int i, Color color) {
        casillasColor[j][i] = color;
    }

}
