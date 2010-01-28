package cf.logica;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luigi
 */
public class Trazas implements ObservadorCasinoFantasma {

    File trazas;
    FileWriter fw;


    public Trazas(){
        try {
            trazas = File.createTempFile("temporal", ".log");
            fw = new FileWriter(trazas);
        } catch (IOException ex) {
            Logger.getLogger(Trazas.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error creando log");
            System.exit(0);
        }

    }


    public File dameFicheroTrazas(){
        return trazas;
    }

    public boolean cerrarTrazas(){
        try {
            fw.close();
            return true;
        } catch (IOException ex) {

            return false;
        }
    }

    public void partidaEmpezada(Color[][] matriz) {
        try {
            fw.write("Partida casino empezada");
        } catch (IOException ex) {
            Logger.getLogger(Trazas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void partidaTerminada() {

        try {
            fw.write("Partida terminada\n");
        } catch (IOException ex) {
            Logger.getLogger(Trazas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarJuego(TableroCasillas matriz) {
       
        try {
            fw.write("Esta es la situación del casino\n");
            /***** Pon aqui el tablero ***/
            
            
        } catch (IOException ex) {
            Logger.getLogger(Trazas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void actualizarMiniJuego(TableroCasillas matriz) {
        // Que hacemos aqui?, es posible que tengamos que quitar esta operacion */
    }

    public void movimientoRealizado(Color[][] matriz) {
        throw new UnsupportedOperationException("Not supported yet.");
        // Que hacemos aqui?, pintamos el casino?/
    }

    public void mostrarInfoJuego(String info) {

        try {
            fw.write(info + "\n");
        } catch (IOException ex) {
            Logger.getLogger(Trazas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reseteaInfoJuego() {

        /** No hacemos nada. **/
    }

    public void muestraInformacionCasilla(String nombreJuego, String nombreBusqueda, int dificultad) {

        /** No hacemos nada **/
        /** Tal vez deberiamos poner en el fichero todas las casillas */
    }

    public void muestraInfo(String informacionError) {

        try {
            fw.write(informacionError);
        } catch (IOException ex) {
            Logger.getLogger(Trazas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void muestraVidasJugador(int vidas) {

        try {
            fw.write("Las vidas del jugador son: " + vidas + "\n");
        } catch (IOException ex) {
            Logger.getLogger(Trazas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
