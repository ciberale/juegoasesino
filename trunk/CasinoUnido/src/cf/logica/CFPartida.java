package cf.logica;

import cf.ParserXML;
import cf.util.Dimension;
import cf.util.Posicion;
import java.awt.Color;
import java.util.Vector;


public class CFPartida {
    
    private Color Matriz[][];
    private Color Resultado[][];
    Dimension dimension;;
    int numColores;
    protected Vector <ObservadorCasinoFantasma> Observers;
    ParserXML parserXML;



    public void comenzarPartida(){


         for (int i = 0; i < dimension.getFilas();i++)
            for (int j = 0; j < dimension.getColumnas();j++)
                Resultado[j][i] = Color.BLACK;

        for (int i = 0; i < Observers.size();i++)
            Observers.elementAt(i).partidaEmpezada(dameTablero());    
    }

     public void inicializarPartida(Dimension dimension,int numColores){

        Matriz = new Color[dimension.getColumnas()][dimension.getFilas()];
        Resultado = new Color[dimension.getColumnas()][dimension.getFilas()];

        for (int i = 0; i < dimension.getFilas();i++)
            for (int j = 0; j < dimension.getColumnas();j++)
                Resultado[j][i] = Color.BLACK;
        this.numColores = numColores;
        this.dimension = dimension;

                for (int i = 0; i < Observers.size();i++)
            Observers.elementAt(i).partidaEmpezada(dameTablero());  


     }

    
    public CFPartida(){
        
        Observers = new Vector<ObservadorCasinoFantasma>();
       
    }



   public Color[][] dameTablero(){
       
       for (int i = 0; i < dimension.getFilas();i++)
           for (int j = 0; j < dimension.getColumnas();j++){

               /***
                * Debes inicializar de alguna forma.
                */
             //  Matriz[j][i] = dameColor(numColores);
           }
       
       return Matriz;
   }
  /*
private Color dameColor(int numColores){
    
    int x = (int)(Math.random() * numColores);
    Colores color = Colores.values()[x];
    switch (color){
        
        case BLANCO: return Color.white;
        case NEGRO: return Color.black;
        case AMARILLO:return Color.yellow;
        case ROJO:return Color.red;
        case VERDE:return Color.green;
        case AZUL:return Color.blue;
        default: return Color.black;
    }
}*/
   
public void verificaTablero(){
    
    boolean result = true;
    for (int i = 0; i < dimension.getFilas();i++)
        for (int j = 0; j < dimension.getColumnas();j++){
            if (Matriz[j][i] != Resultado[j][i])
                result = false;
            else result = result && true;
        }
          
    // Aqui tienes que indicar lo de los observers para decir si ha acertado  o no.
    
 for (int i = 0; i < Observers.size(); i++)
                Observers.elementAt(i).partidaTerminada(result);
        
      
}

public void insertaMovimiento(Posicion pos,Color color){

        Color coloraux = Resultado[pos.getEjeX()][pos.getEjeY()];
        Resultado[pos.getEjeX()][pos.getEjeY()] = color;
        
        for (int i = 0; i < Observers.size();i++)
            Observers.elementAt(i).movimientoRealizado(Resultado);
    
}




  public void addObserver(ObservadorCasinoFantasma observer){
		
		if ((observer != null)&& (!Observers.contains(observer)))
            Observers.add(observer);
	}
	

  public void removeObserver(ObservadorCasinoFantasma observer){
		
		if (observer != null)
		Observers.remove(observer);
	}

   public void abrirFichero(String pathFichero) {

       /**
        *
        * Aqui abrimos el fichero XML.
        */

       parserXML = new ParserXML(pathFichero);

       parserXML.parseaTablero();
       parserXML.parseaLaberinto();


          Tablero matriz = parserXML.parseaLaberinto();
          Tablero tablero = parserXML.parseaTablero();

        for (int i = 0; i < Observers.size();i++){
            Observers.elementAt(i).actualizarJuego(tablero);
            Observers.elementAt(i).actualizarMiniJuego(matriz);
        }



    }
}
