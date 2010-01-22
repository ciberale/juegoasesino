package cf;

import cf.gui.interfaz;
import cf.logica.ControladorCasinoFantasma;
import cf.logica.CFPartida;


/**
 *
 * @author luigi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

       interfaz GUI = new interfaz();
       ControladorCasinoFantasma controladorPartida;
       CFPartida partida = new CFPartida();
       controladorPartida = new ControladorCasinoFantasma(partida);
       partida.addObserver(GUI);
       GUI.setControladorMatrizColores(controladorPartida);
       GUI.setVisible(true);
    }

}
