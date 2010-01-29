package cf.logica;

import cf.ParserXML;
import cf.gui.ExampleFileFilter;
import cf.logica.busqueda.Busqueda;
import cf.logica.busqueda.BusquedaAEstrella;
import cf.logica.busqueda.BusquedaAnchura;
import cf.logica.busqueda.BusquedaProfundidad;
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
    private FactoriaJuegosYBusquedas factoriaJuegosYBusquedas;
    private String pathXML;
    private Busqueda busqueda,miBusqueda;
    private Trazas trazas;
    Vector<Posicion> salidas;
    private Posicion posEntrada;

    private Thread tBusqueda;



    /*** Definimos el estado del jugador en el casino ***/

    /***
     * Array de tres componentes, la primera es la columna, la segunda la fila y la tercera las vidas.
     *
     *
     */


     public CFPartida(){

        Observers = new Vector<ObservadorCasinoFantasma>();
        factoriaJuegosYBusquedas = new FactoriaJuegosYBusquedas();
        juegoInicializado = false;
        movimientos = new Vector<Integer>();
        for (int i = 0; i < Movimientos.values().length;i++)
            movimientos.add(Movimientos.values()[i].ordinal());

        tBusqueda = new Thread(new BusquedaProfundidad(this));

    }


    public void comenzarPartida(){

            /** Empezamos a buscar en el tablero **/

            /** Insertamos movimientos, etc ?**/

        if(juegoInicializado){
            /*miBusqueda = new BusquedaAnchura(this);
            miBusqueda.busca();*/
            tBusqueda.start();

        }
        else{
            for (ObservadorCasinoFantasma oc:Observers)
                oc.muestraInfo("Hay que cargar antes el ficheroXML");
        }

    }

     public void inicializarPartida(Dimension dimension,int numColores){

     }

  public void addObserver(ObservadorCasinoFantasma observer){
		
		if ((observer != null)&& (!Observers.contains(observer)))
            Observers.add(observer);
	}
	

  public void removeObserver(ObservadorCasinoFantasma observer){
		
		if (observer != null)
                    Observers.remove(observer);
	}

   public void cargarFicheroXML(String pathFichero) {

   /* try{  /**
        * Aqui abrimos el fichero XML.
        */

       pathXML = pathFichero;
       parserXML = new ParserXML(pathFichero);

       parserXML.parseaTablero();
       //parserXML.parseaLaberinto();
       posJugador = parserXML.damePosicionJugador();
       posEntrada = new Posicion(parserXML.damePosicionJugador().getEjeX(),parserXML.damePosicionJugador().getEjeY());

       numVidas = parserXML.dameVidasJugador();

          //TableroCasillas matriz = parserXML.parseaLaberinto();
          tablero = parserXML.parseaTablero();

          /*for (int i = 0; i < tablero.getNumColumnas();i++)
              for (int j = 0; j < tablero.getNumFilas();j++)
                  matrizColores.setColor(new Posicion(i,j),tablero.getColorCasilla(i,j));*/


        for (int i = 0; i < Observers.size();i++){
            Observers.elementAt(i).actualizarJuego(tablero);
           // Observers.elementAt(i).actualizarMiniJuego(tablero);
        }

          /** Tenemos que setear las salidas ***/

          salidas = parserXML.dameSalidasTablero();

          
          /*** Si no ha habido ningun problema, en caso contrario avisas a los observers de que el 
           * fichero es incorrecto 
           */
        juegoInicializado = true;

        estado = new Estado(new Dimension(3,1));
        estado.setNumero(0,0,posJugador.getEjeX());
        estado.setNumero(1,0,posJugador.getEjeY());
        estado.setNumero(2,0,numVidas);


   /*  }catch(Exception ex){

         for (int i = 0; i < Observers.size();i++)
            Observers.elementAt(i).muestraInfo("Ha habido un problema leyendo el XML");

         juegoInicializado = false;
     }*/

    }



    public boolean hazMovimiento(int movimiento) {

        Movimientos mov = Movimientos.values()[movimiento];
        Posicion aux = new Posicion(estado.getCasilla(0,0),estado.getCasilla(1,0));

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


        if (esSalida(aux)){ // si es salida...

                /// Y hacemos lo que sea...
                /// Avisamos a los observadores...

            tablero.setJugador(aux);
           
            posJugador = aux;
            estado.setNumero(0,0,aux.getEjeX());
            estado.setNumero(1,0,aux.getEjeY());


             /*** Aqui seteamos la estela y las casillas sucesoras,  **/
            tablero.setEstadosAnteriores(estado);



            /** Y las acciones que falten... **/
             for (int i = 0; i < Observers.size();i++)
                 Observers.elementAt(i).muestraVidasJugador(numVidas);

            for (int i = 0; i < Observers.size();i++){
                Observers.elementAt(i).actualizarJuego(tablero);
                Observers.elementAt(i).partidaTerminada();
            }

                 return true;
        }

        else if (tablero.enRango(aux) && podemosApostar(aux) && !HemospasadoYa(aux) && niEntradaNiSalida(aux)){


            /*** Antes debemos resolver el mini-juego **/

            /// Todos igual //

            Casilla cas = tablero.getCasilla(new Posicion(aux.getEjeX(),aux.getEjeY()));

            /*** Cogemos el metodo de busqueda **/
            TipoBusquedas tipoBusqueda = cas.getBusqueda();

            /** Y el juego de la casilla a la que se ha ido **/
            TipoJuegos tipoJuego = cas.getJuego();

            // Comprobamos si el estado es objetivo, en ese caso no tendrá juego y por lo tanto no
            // podremos jugar.
            Estado auxEstado = new Estado(new Dimension(3,1));
            auxEstado.setNumero(0,0,aux.getEjeX());
            auxEstado.setNumero(1,0,aux.getEjeY());
            if(estadoObjetivo(auxEstado))
                return true;


             for (int i = 0; i < Observers.size();i++)
                 Observers.elementAt(i).reseteaInfoJuego();

            /*** Resolvermos el mini-juego...
             *  Ponemos un try-catch por si acaso...
             */
      try{

          // Esto viene a representar la busquedas y los mini-juegos.
          
            Minijuego miniJuego = factoriaJuegosYBusquedas.dameJuego(tipoJuego,parserXML);
            miniJuego.setParser(parserXML);
            Busqueda busquedaMini = factoriaJuegosYBusquedas.dameBusqueda(tipoBusqueda,miniJuego);
            busquedaMini.setObservers(Observers);
            busquedaMini.busca();

          //// ### Justo aqui despues de la ejecucion deberias parar la hebra **/
          
         tBusqueda.suspend();



            tablero.setJugador(aux);
            posJugador = aux;
            estado.setNumero(0,0,aux.getEjeX());
            estado.setNumero(1,0,aux.getEjeY());

            /** Y las acciones que falten... **/
             for (int i = 0; i < Observers.size();i++)
                 Observers.elementAt(i).muestraVidasJugador(numVidas);
            
            for (int i = 0; i < Observers.size();i++)
                Observers.elementAt(i).actualizarJuego(tablero);

                 return true;


      }
      catch (Exception ex){

          /// devolvemos el dinero ?
          return false;
      }
        }

       

        else{
                    return false;
        }
        
    }

    public void seguirPartida() {

        /*** Continuar la ejecucion del Thread **/
       tBusqueda.resume();
    }

    public void dameNombreJuegoYEstrategia(int x, int y) {

    try{
        
        Casilla cas = tablero.getCasilla(new Posicion(x,y));
        
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
       
        /************************** Revisar ***/
        return 1;
    }

    @Override
    public boolean estadoObjetivo() {

        return estadoObjetivo(estado);

    }

    private boolean estadoObjetivo(Estado est) {

            for (Posicion p:salidas)
                   if (p.equals(new Posicion(est.getCasilla(0,0),est.getCasilla(1,0))))
                       return true;
            return false;
    }

    @Override
    public double getValorHeuristico(Estado estado) {

        return 1 / estado.getCasilla(0,0);
    }

    @Override
    public boolean esPeligro(Estado status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private boolean podemosApostar(Posicion aux) {


      /** Si es una salida, devolvemos que si, aunque no se vaya a apostar **/

      if (tablero.enRango(aux) && niEntradaNiSalida(aux)){
            Casilla cas = tablero.getCasilla(aux);
            return cas.getDificultad() <= numVidas;
      }
      else return false;
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
        
        guardarTrazas();
        System.exit(0);
    }

    public void guardarTrazas(){
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

    private boolean niEntradaNiSalida(Posicion aux) {
       
         if (posEntrada.equals(aux))
                return false;

         else return !esSalida(aux);
    }

    private boolean esSalida(Posicion aux){
        /** Si es una salida devolvemos true, para que puede avanzar.*/
        for (Posicion p:salidas)
                   if (p.equals(aux))
                       return true;

        return false;
    }


}
