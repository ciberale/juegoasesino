/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cf.logica;

import java.awt.Color;

/**
 *
 * @author luis
 */
public interface ObservadorCasinoFantasma {

        public void partidaEmpezada(Color[][] matriz);
	public void partidaTerminada();
        public void actualizarJuego(TableroCasillas matriz); /// Esto le pasará una matriz de colores.
        public void actualizarMiniJuego(TableroCasillas matriz); /// Esto le pasará una matriz de colores
	public void movimientoRealizado(Color[][] matriz);

        public void mostrarInfoJuego(String info);
        public void reseteaInfoJuego();
        public void muestraInformacionCasilla(String nombreJuego,String nombreBusqueda,int dificultad);
        public void muestraInfo(String informacionError);
        public void muestraVidasJugador(int vidas);
}
