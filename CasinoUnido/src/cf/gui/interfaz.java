package cf.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JOptionPane;
import cf.logica.ControladorCasinoFantasma;
import cf.logica.Tablero;
import cf.logica.ObservadorCasinoFantasma;
import java.awt.Rectangle;
import javax.swing.JFileChooser;


/**
 *
 * @author luigi
 */
public class interfaz extends javax.swing.JFrame implements ObservadorCasinoFantasma {



    /*************************** Variables de MemoCasillas************************/
    private Tablero matrizColores;
    private Rectangle2D rectangulos [][];
    private float tamanioBase;
    private float tamanioAltura;
    private ControladorCasinoFantasma controladorMatrizColores;



    /** Creates new form interfaz */
    public interfaz() {
        initComponents();
        this.setTitle("Casino Fantasma");
          Dimension dim = getToolkit().getScreenSize();
        Rectangle abounds = getBounds();
        setLocation((dim.width - abounds.width) / 2,
      (dim.height - abounds.height) / 2);


      //  jPanel9.paint(dameGraficos(jPanel9.getGraphics()));
        matrizColores = new Tablero(new cf.util.Dimension(5,5));


    }

    /************************* Metodos de MemoCasillas***************************/

   public void pinta (Graphics g) {

      try {
  Graphics2D g2 = (Graphics2D)g;
  AlphaComposite ac;
  rectangulos = new Rectangle2D[matrizColores.getColumnas()][matrizColores.getFilas()];

  Dimension dim = jPanel9.getSize();
  tamanioBase = (float) (dim.getWidth() / matrizColores.getColumnas());
  tamanioAltura = (float) (dim.getHeight() / matrizColores.getFilas());

  for (int i = 0; i < matrizColores.getFilas(); i++){
      for (int j = 0; j < matrizColores.getColumnas();j++){
        rectangulos[j][i] =  new Rectangle2D.Float(j * tamanioBase,i * tamanioAltura,tamanioBase,tamanioAltura);
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2.setColor(matrizColores.getCasilla(j,i));
        g2.fill(rectangulos[j][i]);
      }
  }
  
     int espacio = dim.height/matrizColores.getColumnas();
     for (int i = 0; i < dim.getHeight();i= i + espacio){
          g2.setColor(Color.BLACK);
          g2.drawLine(0,i,dim.height,i);

     }

     espacio = dim.height/matrizColores.getColumnas();
     for (int i = 0; i < dim.getWidth();i= i + espacio){
          g2.setColor(Color.BLACK);
          g2.drawLine(i,0,i,dim.width);

     }

     /*   float distancia = jPanel10.getWidth()/matrizColores.getColumnas();

        for (float c = 0; c < jPanel10.getWidth(); c = c + distancia){
                Rectangle2D rectangulo =  new Rectangle2D.Float(c,0 * tamanioAltura,1,jPanel10.getHeight());
                ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
                g2.setColor(Color.BLACK);
                g2.fill(rectangulo);
               // c = c + 2;

        }*/

      

      }
      catch (Exception ex){
          System.out.println("Exception en pinta");
     }
}


    public void pintaMiniJuego (Graphics g) {

      try {
      Graphics2D g2 = (Graphics2D)g;
      AlphaComposite ac;
      rectangulos = new Rectangle2D[matrizColores.getColumnas()][matrizColores.getFilas()];

      Dimension dim = jPanel9.getSize();
      tamanioBase = (float) (dim.getWidth() / matrizColores.getColumnas());
      tamanioAltura = (float) (dim.getHeight() / matrizColores.getFilas());
     float puntoMedioX,puntoMedioY;
      for (int i = 0; i < matrizColores.getFilas(); i++){
          for (int j = 0; j < matrizColores.getColumnas();j++){

            rectangulos[j][i] =  new Rectangle2D.Float(j * tamanioBase,i * tamanioAltura,tamanioBase,tamanioAltura);
            ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            g2.setColor(matrizColores.getCasilla(j,i));
          //  g2.setComposite(ac);
            g2.fill(rectangulos[j][i]);
            puntoMedioX = j * tamanioBase + tamanioBase/2;
            puntoMedioY = i * tamanioAltura + tamanioAltura/2;
            g2.drawString("CX:"+i+"CY:"+j,puntoMedioY,puntoMedioX);
          //  g2.drawString("adios",puntoMedioX*2, puntoMedioY*2);
      }

  }
      }
      catch (Exception ex){
          System.out.println("Exception en pinta");
      }
}


   

   public void setControladorMatrizColores(ControladorCasinoFantasma controladorMatrizColores) {
        this.controladorMatrizColores = controladorMatrizColores;
    }

    public void movimientoRealizado(Tablero matriz) {

             matrizColores = matriz;
            pinta(jPanel9.getGraphics());
            pintaMiniJuego(jPanel10.getGraphics());
    }
  
    public void partidaTerminada(boolean acierto) {

        if (acierto)
              JOptionPane.showMessageDialog(null,"Has acertado", "Resultado",JOptionPane.INFORMATION_MESSAGE);
        else   JOptionPane.showMessageDialog(null,"Has fallado", "Resultado",JOptionPane.INFORMATION_MESSAGE);
    }



    /****************************************************************************/

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel9 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel9.setPreferredSize(new java.awt.Dimension(400, 400));
        jPanel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel9MouseReleased(evt);
            }
        });
        jPanel9.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel9MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );

        jButton2.setText("Comenzar la partida");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel24.setText("¿Dejamos el boton o que empiece automaticamente?");

        jPanel10.setPreferredSize(new java.awt.Dimension(400, 400));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel10MouseReleased(evt);
            }
        });
        jPanel10.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel10MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );

        jLabel1.setText("Nombre del mini-juego:");

        jLabel2.setText("jLabel2");

        jLabel3.setText("Situación del jugador");

        jMenu1.setText("Archivo");
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem1.setText("Cargar fichero XML");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Salir");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Opciones");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(jLabel3)))
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2))
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel24))
                .addGap(96, 96, 96))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel9MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseMoved


        // Este método te va a venir bien para informar del juego que se va a ejecutar?

    //    jLabel22.setText(Integer.toString(evt.getX()) + " " + Integer.toString(evt.getY()));
        //pinta(jPanel9.getGraphics());
       // jPanel9.setBackground(Color.BLACK);
        //jPanel9.paint(jPanel9.getGraphics());
       
        //jPanel9.setIgnoreRepaint(rootPaneCheckingEnabled)
    }//GEN-LAST:event_jPanel9MouseMoved

    private void jPanel9MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel9MouseReleased

       /*  try {
        jLabel22.setText(Integer.toString(evt.getX()) + " " + Integer.toString(evt.getY()));

         int i = (int) (evt.getX() / tamanioBase);
         int j = (int) (evt.getY() / tamanioAltura);

        /* colores colorines = new colores();
         casillasColor [i][j]= colorines.dameSiguienteColor(casillasColor [i][j]);*/

        // controladorMatrizColores.insertarMovimiento(new Posicion(i,j));
        // casillasColor [i][j]= Color.getColor(colorin);//colores.getColor(colorines.toString());
                 pinta(jPanel9.getGraphics());
 
        pintaMiniJuego(jPanel10.getGraphics());
       /*  }
         catch (Exception ex){
             System.out.println("Te has salido?");
         }*/
    }//GEN-LAST:event_jPanel9MouseReleased




    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

       //   controladorMatrizColores.iniciarPartida(50);//Integer.parseInt(numCasillasBox.getSelectedItem().toString()));

       // controladorMatrizColores.iniciarPartida(Integer.parseInt(numCasillasBox.getSelectedItem().toString()),Integer.parseInt(numColoresBox.getSelectedItem().toString()));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jPanel10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel10MouseReleased

    private void jPanel10MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel10MouseMoved

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed



    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        JFileChooser chooser = new JFileChooser();
        ExampleFileFilter filter = new ExampleFileFilter() {};
        filter.addExtension("xml");
        filter.setDescription("Ficheros XML(*.xml)");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {

            controladorMatrizColores.abrirFichero(chooser.getSelectedFile().toString());
           /* control.abrirBaseDeDatos(chooser.getSelectedFile().toString());
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());*/
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed



   public void partidaEmpezada(Tablero matrizColores) {
           this.matrizColores = matrizColores;
           pinta(jPanel9.getGraphics());
           pintaMiniJuego(jPanel10.getGraphics());
    }

     @Override
    public void repaint(){
        super.repaint();
        jPanel9.repaint();
        jPanel10.repaint();


    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables

    public void actualizarJuego(Tablero matriz) {

        matrizColores = matriz;
        pinta(jPanel9.getGraphics());
    }

    public void actualizarMiniJuego(Tablero matriz) {

         matrizColores = matriz;
         pinta(jPanel10.getGraphics());
    }

    public void partidaEmpezada(Color[][] matriz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void movimientoRealizado(Color[][] matriz) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
