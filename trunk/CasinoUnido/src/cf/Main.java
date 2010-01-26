package cf;

import cf.gui.ExampleFileFilter;
import cf.gui.interfaz;
import cf.logica.ControladorCasinoFantasma;
import cf.logica.CFPartida;
import cf.logica.Trazas;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 *
 * @author luigi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    Trazas trazas = new Trazas();
    try{
       interfaz GUI = new interfaz();
       ControladorCasinoFantasma controladorPartida;
       CFPartida partida = new CFPartida();
       
       controladorPartida = new ControladorCasinoFantasma(partida);
       partida.addObserver(GUI);
       partida.addObserver(trazas);
       partida.setTrazas(trazas);
       GUI.setControladorCasino(controladorPartida);
       GUI.setVisible(true);

    }
    catch(Exception ex){

            /// Avisamos del error ///
            /// Ofrecemos un cuadro de dialogo para guardar las trazas **/
            // Guardamos las trazas. **/

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
                  trazas.dameFicheroTrazas().renameTo(definitivo);
                  JOptionPane.showMessageDialog(null, "Archivo guardado con exito : " + path, "Casino Fantasma 2009/2010", JOptionPane.INFORMATION_MESSAGE);
             }

    }

   }

}
