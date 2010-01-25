package cf.logica;

import cf.ParserXML;
import cf.logica.busqueda.Busqueda;
import cf.logica.minijuegos.Minijuego;
import cf.util.Dimension;
import cf.util.Posicion;
import java.awt.Color;
import java.util.Vector;


public class CFPartida {
    
    private TableroCasillas tablero;
    private Color Resultado[][];
    private Posicion posJugador;
    private Dimension dimension;
    private int numColores;
    protected Vector <ObservadorCasinoFantasma> Observers;
    private ParserXML parserXML;
    private FactoriaJuegosYBusquedas factoriaJuegosYBusquedas;


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
        factoriaJuegosYBusquedas = new FactoriaJuegosYBusquedas();
       
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

          //TableroCasillas matriz = parserXML.parseaLaberinto();
          tablero = parserXML.parseaTablero();

          /*for (int i = 0; i < tablero.getNumColumnas();i++)
              for (int j = 0; j < tablero.getNumFilas();j++)
                  matrizColores.setColor(new Posicion(i,j),tablero.getColorCasilla(i,j));*/


        for (int i = 0; i < Observers.size();i++){
            Observers.elementAt(i).actualizarJuego(tablero);
            Observers.elementAt(i).actualizarMiniJuego(tablero);
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

            Casilla cas = tablero.getCasilla(new Posicion(aux.getEjeX(),aux.getEjeY()));

            /*** Cogemos el metodo de busqueda **/
            TipoBusquedas tipoBusqueda = cas.getBusqueda();

            /** Y el juego de la casilla a la que se ha ido **/
            TipoJuegos tipoJuego = cas.getJuego();

            Minijuego miniJuego = factoriaJuegosYBusquedas.dameJuego(tipoJuego);
            Busqueda busqueda = factoriaJuegosYBusquedas.dameBusqueda(tipoBusqueda,miniJuego);
            busqueda.setObservers(Observers);
            busqueda.busca();

            
            tablero.setJugador(aux);
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
