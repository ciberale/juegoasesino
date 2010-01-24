package cf.logica.busqueda;

import cf.logica.ObservadorCasinoFantasma;
import cf.logica.minijuegos.Minijuego;
import java.util.Vector;


public abstract class Busqueda {

    Minijuego miniJuego;
    private Vector<ObservadorCasinoFantasma> Observers;


    public void setObservers(Vector<ObservadorCasinoFantasma> Observers) {

        this.Observers = Observers;
    }

    protected void muestraInformacion(String info){

        for (int i = 0; i < Observers.size();i++){
            Observers.elementAt(i).mostrarInfoJuego(info);
        }

    }

    /**
     * Y mas cosas.
     */
}