package cf.logica;

import cf.ParserXML;
import cf.gui.ExampleFileFilter;
import cf.logica.busqueda.Busqueda;
import cf.logica.estados.Estado;
import cf.logica.minijuegos.Minijuego;
import cf.util.Dimension;
import cf.util.Posicion;
import java.io.File;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class CFPartida extends Minijuego {
    
    private TableroCasillas tablero;
    //private Color Resultado[][];
    private Posicion posJugador;
    //private Dimension dimension;

    private boolean juegoInicializado;
    private int numVidas;

    protected Vector <ObservadorCasinoFantasma> Observers;
    private ParserXML parserXML;
    private FactoriaJuegosYBusquedas factoriaJuegosYBusquedas;

    private String pathXML;
    private Busqueda busqueda;
    private Trazas trazas;


     public CFPartida(){

        Observers = new Vector<ObservadorCasinoFantasma>();
        factoriaJuegosYBusquedas = new FactoriaJuegosYBusquedas();
        juegoInicializado = false;
        movimientos = new Vector<Integer>();
        for (int i = 0; i < Movimientos.values().length;i++)
            movimientos.add(Movimientos.values()[i].ordinal());

    }


    public void comenzarPartida(){

            /** Empezamos a buscar en el tablero **/

            /** Insertamos movimientos, etc ?**/
            busqueda.busca();

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

  public void addObserver(ObservadorCasinoFantasma observer){
		
		if ((observer != null)&& (!Observers.contains(observer)))
            Observers.add(observer);
	}
	

  public void removeObserver(ObservadorCasinoFantasma observer){
		
		if (observer != null)
                    Observers.remove(observer);
	}

   public void abrirFichero(String pathFichero) {

    try{  /**
        * Aqui abrimos el fichero XML.
        */

       pathXML = pathFichero;
       parserXML = new ParserXML(pathFichero);

       parserXML.parseaTablero();
       parserXML.parseaLaberinto();
       posJugador = parserXML.damePosicionJugador();

       numVidas = parserXML.dameVidasJugador();

          //TableroCasillas matriz = parserXML.parseaLaberinto();
          tablero = parserXML.parseaTablero();

          /*for (int i = 0; i < tablero.getNumColumnas();i++)
              for (int j = 0; j < tablero.getNumFilas();j++)
                  matrizColores.setColor(new Posicion(i,j),tablero.getColorCasilla(i,j));*/


        for (int i = 0; i < Observers.size();i++){
            Observers.elementAt(i).actualizarJuego(tablero);
            Observers.elementAt(i).actualizarMiniJuego(tablero);
        }

          /*** Si no ha habido ningun problema, en caso contrario avisas a los observers de que el 
           * fichero es incorrecto 
           */
        juegoInicializado = true;

     }catch(Exception ex){

         for (int i = 0; i < Observers.size();i++)
            Observers.elementAt(i).muestraInfo("Ha habido un problema leyendo el XML");

         juegoInicializado = false;
     }

    }



    public boolean hazMovimiento(int movimiento) {

        Movimientos mov = Movimientos.values()[movimiento];
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
        /*** Y cumple una serie de condiciones, como por ejemplo tener dinero para poder apostar
         o que no haya pasado ya ?**/

        if (tablero.enRango(aux) && podemosApostar(aux) && !HemospasadoYa(aux)){


            /*** Antes debemos resolver el mini-juego **/

            /// Todos igual //

            Casilla cas = tablero.getCasilla(new Posicion(aux.getEjeX(),aux.getEjeY()));

            /*** Cogemos el metodo de busqueda **/
            TipoBusquedas tipoBusqueda = cas.getBusqueda();

            /** Y el juego de la casilla a la que se ha ido **/
            TipoJuegos tipoJuego = cas.getJuego();

             for (int i = 0; i < Observers.size();i++)
                 Observers.elementAt(i).reseteaInfoJuego();

            Minijuego miniJuego = factoriaJuegosYBusquedas.dameJuego(tipoJuego);
            Busqueda busquedaMini = factoriaJuegosYBusquedas.dameBusqueda(tipoBusqueda,miniJuego);
            busquedaMini.setObservers(Observers);
            busquedaMini.busca();

            
            tablero.setJugador(aux);
            posJugador = aux;


            /** Y las acciones que falten... **/
             for (int i = 0; i < Observers.size();i++)
                 Observers.elementAt(i).muestraVidasJugador(numVidas);
            
            for (int i = 0; i < Observers.size();i++)
                Observers.elementAt(i).actualizarJuego(tablero);

                 return true;
        }
        else{
                    return false;
        }
        
    }

    public void seguirPartida() {



        
    }

    public void dameNombreJuegoYEstrategia(int x, int y) {

    try{
        int ejeX = x/tablero.getColumnas();
        int ejeY = y/tablero.getFilas();
        
        Casilla cas = tablero.getCasilla(new Posicion(ejeX,ejeY));
        
        for (ObservadorCasinoFantasma obs:Observers)
            obs.muestraInformacionCasilla(cas.getJuego().toString(),cas.getBusqueda().toString(),cas.getDificultad());
    
    }
    catch(NullPointerException ex){

            //// No se ha inicializado la partida con el XML.
     }
    }


    /****************** Metodos que tienen los mini-juegos, y por tanto esta tambien ***/




    @Override
    public double getCosteMovimiento(int movimiento, Estado estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean estadoObjetivo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   

    @Override
    public double getValorHeuristico(Estado estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean esPeligro(Estado status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private boolean podemosApostar(Posicion aux) {

       Casilla cas = tablero.getCasilla(aux);
       return cas.getDificultad() <= numVidas;

    }

    private boolean HemospasadoYa(Posicion aux){

        //// Mirar esto, controla los bucles ***/
        return false;
    }

    @Override
    public String getExplicacionEstado() {

        return "esto es un casino";
    }

    public void salir(){
        /*** Ofrecemos guardar el fichero de trazas **/

        JFileChooser chooser= new JFileChooser();

        ExampleFileFilter filter = new ExampleFileFilter() {};
        filter.addExtension("log");
        filter.setDescription("Fichero log casino");
        chooser.setFileFilter(filter);

       if (chooser.showSaveDialog(new JPanel()) == JFileChooser.APPROVE_OPTION)
       {
          String path = (chooser.getSelectedFile().toString());


              if (!path.endsWith(".log"))
                  path = path + ".log";
                  File definitivo = new File(path);
                  getTrazas().dameFicheroTrazas().renameTo(definitivo);
                  JOptionPane.showMessageDialog(null, "Archivo guardado con exito : " + path, "Casino Fantasma 2009/2010", JOptionPane.INFORMATION_MESSAGE);
       }

        System.exit(0);
    }

    /**
     * @return the trazas
     */
    public Trazas getTrazas() {
        return trazas;
    }

    /**
     * @param trazas the trazas to set
     */
    public void setTrazas(Trazas trazas) {
        this.trazas = trazas;
    }

}
