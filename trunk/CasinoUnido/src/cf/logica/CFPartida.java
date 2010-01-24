package cf.logica;

import cf.ParserXML;
import cf.logica.busqueda.BusquedaAnchura;
import cf.logica.minijuegos.Garrafas;
import cf.util.Dimension;
import cf.util.Posicion;
import java.awt.Color;
import java.util.Vector;


public class CFPartida {
    
    private Tablero tablero;
    private Color Resultado[][];

    private Posicion posJugador;

    Dimension dimension;
    int numColores;
    protected Vector <ObservadorCasinoFantasma> Observers;
    ParserXML parserXML;



    public void comenzarPartida(){


         for (int i = 0; i < dimension.getFilas();i++)
            for (int j = 0; j < dimension.getColumnas();j++)
                Resultado[j][i] = Color.BLACK;

        /*for (int i = 0; i < Observers.size();i++)
            Observers.elementAt(i).partidaEmpezada(dameTablero()); */
    }

     public void inicializarPartida(Dimension dimension,int numColores){

       /* tablero = new Tablero();
        Resultado = new Color[dimension.getColumnas()][dimension.getFilas()];

        for (int i = 0; i < dimension.getFilas();i++)
            for (int j = 0; j < dimension.getColumnas();j++)
                Resultado[j][i] = Color.BLACK;
        this.numColores = numColores;
        this.dimension = dimension;

                for (int i = 0; i < Observers.size();i++)
            Observers.elementAt(i).partidaEmpezada(dameTablero());  */


     }

    
    public CFPartida(){
        
        Observers = new Vector<ObservadorCasinoFantasma>();
       
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
        * Aqui abrimos el fichero XML.
        */

       parserXML = new ParserXML(pathFichero);

       parserXML.parseaTablero();
       parserXML.parseaLaberinto();
       posJugador = parserXML.damePosicionJugador();

          Tablero matriz = parserXML.parseaLaberinto();
         
          tablero = parserXML.parseaTablero();

        for (int i = 0; i < Observers.size();i++){
            Observers.elementAt(i).actualizarJuego(tablero);
            Observers.elementAt(i).actualizarMiniJuego(matriz);
        }

    }

    public void insertaMovimiento(Movimientos mov) {

        Posicion aux = new Posicion(posJugador.getEjeX(),posJugador.getEjeY());

        switch(mov){

            case arriba: aux.setEjeY(aux.getEjeY() - 1); break;
            case abajo: aux.setEjeY(aux.getEjeY() + 1); break;
            case derecha: aux.setEjeX(aux.getEjeX() + 1); break;
            case izquierda:aux.setEjeX(aux.getEjeX() - 1); break;
            case noreste: aux.setEjeY(aux.getEjeY() - 1); aux.setEjeX(aux.getEjeX() + 1); break;
            case noroeste: aux.setEjeY(aux.getEjeY() - 1); aux.setEjeX(aux.getEjeX() - 1); break;
            case sudoeste: aux.setEjeY(aux.getEjeY() + 1); aux.setEjeX(aux.getEjeX() - 1); break;
            case sudeste: aux.setEjeY(aux.getEjeY() + 1); aux.setEjeX(aux.getEjeX() + 1); break;

        }

        /** Donde esta el jugador, ponemos un blanco, y luego situamos de nuevo al jugador **/

        /** Si el jugador esta en el rango del tablero, es decir, no se ha salido con el movimiento **/
        if (tablero.enRango(aux)){


            /*** Antes debemos resolver el mini-juego **/

            /// Todos igual //

            Garrafas juego = new Garrafas();
            BusquedaAnchura busqueda = new BusquedaAnchura(juego);
            busqueda.setObservers(Observers);
            busqueda.busca();




            tablero.setColor(posJugador, Color.WHITE);
            tablero.setColor(aux,Color.RED);
            posJugador = aux;


            /** Y las acciones que falten... **/

        }
        else{
            // lo que sea, no dejamos avanzar....
        }


        for (int i = 0; i < Observers.size();i++)
            Observers.elementAt(i).actualizarJuego(tablero);

    }

    public void seguirPartida() {



        
    }

}
