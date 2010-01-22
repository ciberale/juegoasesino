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
	
	public void partidaTerminada(boolean acierto);

        public void actualizarJuego(Tablero matriz); /// Esto le pasará una matriz de colores.

        public void actualizarMiniJuego(Tablero matriz); /// Esto le pasará una matriz de colores.
	
	public void movimientoRealizado(Color[][] matriz);
    
    
}
