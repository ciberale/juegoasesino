package cf.logica.minijuegos;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;
import java.util.Vector;
import movimientos.MovimientosMisioneros;

/**
 *
 * @author luigi
 */
public class MisionerosYCanibales extends Minijuego {


    final int numMisioneros = 3;
    final int numCanibales = 3;
    private EstadoMisionerosYCanibales estadoMC;

    @Override
    public double getValorHeuristico(Estado estado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean esPeligro(Estado status) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   private class EstadoMisionerosYCanibales{

        Estado estado;

        EstadoMisionerosYCanibales(Estado est){
            estado = est;
            movimientos = new Vector<Integer>();
            for (int i = 0; i < MovimientosMisioneros.values().length;i++)
                movimientos.add(MovimientosMisioneros.values()[i].ordinal());
        }

        public void cambiaDeOrilla(){

            if (estado.getCasilla(2,0) == 0)
                estado.setNumero(new Posicion(2,0),1);
            else estado.setNumero(new Posicion(2,0),0);
        }

        /*** Metodos de los canibales ***/
        public void setCanibalesIzq(int canibales){
             estado.setNumero(new Posicion(1,0),canibales);
        }

        public int getCanibalesIzq(){

            return estado.getCasilla(1,0);
        }

        public int getCanibalesDer(){

            return numMisioneros - estado.getCasilla(1,0);
        }

        /*** Métodos de los misioneros ***/
         public void setMisionerosIzq(int misioneros){

             estado.setNumero(new Posicion(0,0),misioneros);
        }

        public int getMisionerosIzq(){

            return estado.getCasilla(0,0);

        }

        public int getMisionerosDer(){

            return numMisioneros - estado.getCasilla(0,0);
        }



    }

    public MisionerosYCanibales(){

        estado = new Estado(new Dimension(3,1));
        estado.setNumero(new Posicion(0,0),3);
        estado.setNumero(new Posicion(1,0),3);
        estado.setNumero(new Posicion(2,0),1);
        estadoMC = new EstadoMisionerosYCanibales(estado);
        /***
         *  Tenemos tres cifras, según las transparencias, solamente tenemos que almacenar
         * una de las orillas. La izquierda. El primer número son los misioneros, el segundo número
         * son los canibales y el tercer número es la situación de la barca, 1 si está a la izquierda, 0
         * a la derecha.
         *
         * Son tres misioneros, tres canibales y un bote.  Partimos con este estado (3,3,1) y tenemos que
         * llegar a este estado.
         *
         */
        estadoObjetivo = new Estado(new Dimension(3,1));
        estadoObjetivo.setNumero(new Posicion(0,0),0);
        estadoObjetivo.setNumero(new Posicion(1,0),0);
        estadoObjetivo.setNumero(new Posicion(2,0),0);


    }

    @Override
    public boolean estadoObjetivo() {

        return estado.equals(estadoObjetivo);


    }

    @Override
    public boolean hazMovimiento(int movimiento) {

        /// Según las transparencias. Los canibales pueden ir de dos en dos en la barca, o incluso
        // uno solo, por lo que tenemos 6 posibles movimientos.

        MovimientosMisioneros mov = MovimientosMisioneros.values()[movimiento];

        /**
         *
         * Hay que comprobar las situaciones de peligro.
         */

         /*int numCanibalesIzq = estado.getCasilla(1,0);
         int numCanibalesDer = numCanibales - numCanibalesIzq;
         int numMisionerosIzq = estado.getCasilla(0,0);
         int numMisionerosDer = numMisioneros - numMisionerosIzq;*/

         switch(mov){

             case Canibal:
                    // refactoriza barcaAlaIzquierda.
                    /// hay que chequear que el canibal que queremos cruzar existe.
                    if (barcaAlaIzquierda() && estadoMC.getCanibalesIzq() >=1 && (estadoMC.getMisionerosDer() == 0 || estadoMC.getCanibalesDer() + 1 <= estadoMC.getMisionerosDer())){
                        
                        estadoMC.setCanibalesIzq(estadoMC.getCanibalesIzq() - 1);
                        estadoMC.cambiaDeOrilla();
                        return true;
                    }

                    else if (!barcaAlaIzquierda() && estadoMC.getCanibalesDer() >=1 &&(estadoMC.getCanibalesIzq() + 1 <= estadoMC.getMisionerosIzq() || estadoMC.getMisionerosIzq() == 0)){

                            estadoMC.setCanibalesIzq(estadoMC.getCanibalesIzq() + 1);
                            estadoMC.cambiaDeOrilla();
                            return true;
                    }

                    else return false;

             case Misionero:

                 /** Si cuando abandonamos la orilla, quedan el mismo numero
                  * de misioneros que canibales o no queda ningún misionero.
                  */
                 if (barcaAlaIzquierda() && estadoMC.getMisionerosIzq() >= 1 && (estadoMC.getMisionerosIzq() - 1 >= estadoMC.getCanibalesIzq() || estadoMC.getMisionerosIzq() - 1 == 0) && (estadoMC.getMisionerosDer() + 1 >= estadoMC.getCanibalesDer() || estadoMC.getMisionerosDer() - 1 == 0)){

                       estadoMC.setMisionerosIzq(estadoMC.getMisionerosIzq() - 1);
                       estadoMC.cambiaDeOrilla();
                       return true;


                    }

                 else if(!barcaAlaIzquierda() && estadoMC.getMisionerosDer() >= 1 && (estadoMC.getMisionerosIzq() + 1 >=estadoMC.getCanibalesIzq() || estadoMC.getCanibalesIzq() == 0)&&(estadoMC.getMisionerosDer() - 1 >= estadoMC.getCanibalesDer() || estadoMC.getMisionerosDer() - 1 == 0)){

                        estadoMC.setMisionerosIzq(estadoMC.getMisionerosIzq() + 1);
                        estadoMC.cambiaDeOrilla();
                        return true;
                    }
                 else return false;
    


             case MisioneroYCanibal:

                   /** Si cuando abandonamos la orilla, quedan el mismo numero
                  * de misioneros que canibales o no queda ningún misionero.
                  */
                                             /** Esta es un poco trivial, si hemos llegado aqui es evidente que
                                              *  está equilibrado.
                                              */
                 /*if (barcaAlaIzquierda() && (estadoMC.getMisionerosIzq() - 1 >= estadoMC.getCanibalesIzq() - 1  || estadoMC.getMisionerosIzq() - 2 == 0)){
                    */
                 if (barcaAlaIzquierda() &&  estadoMC.getCanibalesIzq()>=1 && estadoMC.getMisionerosIzq()>=1 && estadoMC.getMisionerosDer() + 1 >= estadoMC.getCanibalesDer() + 1){
                       estadoMC.setMisionerosIzq(estadoMC.getMisionerosIzq() - 1);
                       estadoMC.setCanibalesIzq(estadoMC.getCanibalesIzq() - 1);
                       estadoMC.cambiaDeOrilla();
                       return true;


                    }

                 else if(!barcaAlaIzquierda() && estadoMC.getCanibalesDer()>=1 && estadoMC.getMisionerosDer()>=1 && estadoMC.getMisionerosIzq() + 1 >= estadoMC.getCanibalesIzq() + 1){

                        estadoMC.setMisionerosIzq(estadoMC.getMisionerosIzq() + 1);
                        estadoMC.setCanibalesIzq(estadoMC.getCanibalesIzq() + 1);
                        estadoMC.cambiaDeOrilla();
                        return true;
                    }
                 else return false;

             case DosMisioneros:

                 /** Si cuando abandonamos la orilla, quedan el mismo numero
                  * de misioneros que canibales o no queda ningún misionero.
                  */
                 if (barcaAlaIzquierda() && estadoMC.getMisionerosIzq() >=2 && (estadoMC.getMisionerosIzq() - 2 >= estadoMC.getCanibalesIzq() || estadoMC.getMisionerosIzq() - 2 == 0)){

                       estadoMC.setMisionerosIzq(estadoMC.getMisionerosIzq() - 2);
                       estadoMC.cambiaDeOrilla();
                       return true;


                    }

                 else if(!barcaAlaIzquierda() && estadoMC.getMisionerosDer() >= 2 &&(estadoMC.getMisionerosDer() - 2 >= estadoMC.getCanibalesDer() || estadoMC.getMisionerosDer() - 2 == 0)){

                        estadoMC.setMisionerosIzq(estadoMC.getMisionerosIzq() + 2);
                        estadoMC.cambiaDeOrilla();
                        return true;
                    }
                 else return false;


             case DosCanibales:

                   // refactoriza barcaAlaIzquierda.
                    /// hay que chequear que el canibal que queremos cruzar existe.
                    if (barcaAlaIzquierda() && estadoMC.getCanibalesIzq() >=2 && (estadoMC.getMisionerosDer() == 0 || estadoMC.getCanibalesDer() + 2 <= estadoMC.getMisionerosDer())){

                        estadoMC.setCanibalesIzq(estadoMC.getCanibalesIzq() - 2);
                        estadoMC.cambiaDeOrilla();
                        return true;
                    }

                    else if (!barcaAlaIzquierda() && estadoMC.getCanibalesDer() >=2 && (estadoMC.getCanibalesIzq() + 2 <= estadoMC.getMisionerosIzq() || estadoMC.getMisionerosIzq() == 0)){

                            estadoMC.setCanibalesIzq(estadoMC.getCanibalesIzq() + 2);
                            estadoMC.cambiaDeOrilla();
                            return true;
                    }

                    else return false;


             default: return false;

         }

    }



    public boolean barcaAlaIzquierda(){

            return estado.getCasilla(2,0) == 1;
    }



    @Override
    public void setEstado(Estado estado){

        this.estado = estado;
        estadoMC = new EstadoMisionerosYCanibales(estado);
    }

}
