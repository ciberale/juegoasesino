/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cf;

import cf.gui.interfaz;
import cf.matrizCasillas.logica.ControladorMatrizColores;
import cf.matrizCasillas.logica.MatrizColoresPartida;


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
       ControladorMatrizColores controladorMatrizColores;
       MatrizColoresPartida matrizColoresPartida = new MatrizColoresPartida();
       controladorMatrizColores = new ControladorMatrizColores(matrizColoresPartida);
       matrizColoresPartida.addObserver(GUI);
       GUI.setControladorMatrizColores(controladorMatrizColores);
       GUI.setVisible(true);
    }

}
