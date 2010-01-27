
package cf;

import cf.logica.TipoBusquedas;
import cf.logica.Casilla;
import cf.logica.MatrizColores;
import cf.logica.TipoJuegos;
import cf.logica.TableroCasillas;
import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.*;
import org.jdom.input.*;


public class ParserXML {

    private SAXBuilder builder;
    private Document doc;
    private Element raiz;
    Posicion posJugador;
    private int numVidasJugador;
    private Estado entradaLaberinto;
    private Estado salidaLaberinto;



    public ParserXML(String filePath){
        try {
            builder = new SAXBuilder(false);
            //usar el parser Xerces y no queremos
            //que valide el documento
            doc = builder.build(filePath);
            //construyo el arbol en memoria desde el fichero
            // que se lo pasaré por parametro.
            raiz = doc.getRootElement();
        } catch (JDOMException ex) {
            Logger.getLogger(ParserXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParserXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



    public Estado parseaLaberinto(){

       
        List equipos=raiz.getChildren("minijuego");
        Iterator iter = equipos.iterator();
        Estado maze= null;

        while (iter.hasNext()){
            Element e= (Element)iter.next();
            /**
             *  Parseamos el laberinto.
             */

            if(e.getAttributeValue("tipo").equalsIgnoreCase("laberinto")){

                Element entrada = e.getChild("entrada");
                Element salida = e.getChild("salida");

                entradaLaberinto = new Estado(new Dimension(2,1));
                entradaLaberinto.setNumero(0,0, Integer.parseInt(entrada.getAttributeValue("columna")));
                entradaLaberinto.setNumero(1,0, Integer.parseInt(entrada.getAttributeValue("fila")));


                salidaLaberinto =  new Estado(new Dimension(2,1));
                salidaLaberinto.setNumero(0,0, Integer.parseInt(salida.getAttributeValue("columna")));
                salidaLaberinto.setNumero(1,0, Integer.parseInt(salida.getAttributeValue("fila")));

                Element laberinto = e.getChild("laberinto");

                List filas = laberinto.getChildren("fila");
                for (int j = 0; j < filas.size();j++)
                    System.out.println(((Element)filas.get(j)).getText());

                /** Chapuzilla **/
                maze = new Estado(new Dimension(filas.size(),((Element)filas.get(0)).getText().length()));

                for (int i= 0;i< filas.size();i++){
                    String fila = ((Element)filas.get(i)).getText();
                    for (int j = 0; j < fila.length();j++){
                                maze.setNumero(j,i,Integer.parseInt(Character.toString(fila.charAt(j))));

                    }
                }

            }
        }
        return maze;
 }


    public Estado parsea8PuzzleInicial(){
        
       /** Al construir el estado, habria que comprobar que el tamaño especificado es correcto
         * de columnas y de filas
         */
        List listaMiniJuegos=raiz.getChildren("minijuego");
        Iterator iter = listaMiniJuegos.iterator();
        Estado estado = new Estado(new Dimension(3,3));
        while (iter.hasNext()){
            Element e= (Element)iter.next();
            /**
             *  Parseamos el mini-juego Casillas vecinas.
             *  Habria que comprobar que los valores estan entre cero y uno.
             */

            if(e.getAttributeValue("tipo").equalsIgnoreCase("OchoPuzzle")){

                Element objetivo = (Element)e.getChild("estadoInicial");

                List filas = objetivo.getChildren("fila");
                for (int i= 0;i< filas.size();i++){
                    String fila = ((Element)filas.get(i)).getText();
                    for (int j = 0; j < fila.length();j++){
                                estado.setNumero(j,i,Integer.parseInt(Character.toString(fila.charAt(j))));

                    }
                }

            }

        }
        return estado;
    }


    public Estado parseaCasillasVecinasInicial(){

          /** Al construir el estado, habria que comprobar que el tamaño especificado es correcto
         * de columnas y de filas
         */
        List listaMiniJuegos=raiz.getChildren("minijuego");
        Iterator iter = listaMiniJuegos.iterator();
        Estado estado = new Estado(new Dimension(3,3));
        while (iter.hasNext()){
            Element e= (Element)iter.next();
            /**
             *  Parseamos el mini-juego Casillas vecinas.
             *  Habria que comprobar que los valores estan entre cero y uno.
             */

            if(e.getAttributeValue("tipo").equalsIgnoreCase("CasillasVecinas")){

                Element objetivo = (Element)e.getChild("estadoInicial");

                List filas = objetivo.getChildren("fila");
                for (int i= 0;i< filas.size();i++){
                    String fila = ((Element)filas.get(i)).getText();
                    for (int j = 0; j < fila.length();j++){
                                estado.setNumero(j,i,Integer.parseInt(Character.toString(fila.charAt(j))));

                    }
                }

            }

        }
        return estado;



    }




     public TableroCasillas parseaTablero() {

        TableroCasillas tablero = null;

        Element tab = raiz.getChild("tablero");
        Element entrada = tab.getChild("entrada");
        Posicion ent = new Posicion(Integer.parseInt(entrada.getAttributeValue("columna")),Integer.parseInt(entrada.getAttributeValue("fila")));

        posJugador = ent;


        Element dimensiones = tab.getChild("dimensiones");
        Dimension dim = new Dimension(Integer.parseInt(dimensiones.getAttributeValue("numColumnas")),Integer.parseInt(dimensiones.getAttributeValue("numFilas")));
        List<Element>salidas = tab.getChildren("salida");

        Vector<Posicion> sal = new Vector<Posicion>();

        for (int i = 0; i < salidas.size();i++)
            sal.add(new Posicion(Integer.parseInt(salidas.get(i).getAttributeValue("columna")),Integer.parseInt(salidas.get(i).getAttributeValue("fila"))));
        tablero = new TableroCasillas(dim);


        Element jugador = tab.getChild("jugador");
        numVidasJugador = Integer.parseInt(jugador.getAttributeValue("numVidas"));

        
           /*for (int i= 0;i< dim.getColumnas();i++)
                    for (int j= 0; j < dim.getFilas();j++)
                         tablero.setColor(j,i,Color.WHITE);*/

      //  tablero.setColor(ent.getEjeX(),ent.getEjeY(),Color.BLUE);

        /**
         * Ponemos las salidas.
         */
        /*
        for (int i = 0; i < sal.size();i++)
             tablero.setColor(sal.elementAt(i).getEjeX(),sal.elementAt(i).getEjeY(),Color.GREEN);
        */

        /*** Aqui leemos las combinaciones de juegos y de estrategia que vamos a aplicar **/

        List<Element>casillasXML = tab.getChildren("casilla");

        //Vector<Posicion> casillas = new Vector<Posicion>();

        for (int i = 0; i < casillasXML.size();i++){

            Casilla casilla = new Casilla();
            casilla.setBusqueda(TipoBusquedas.valueOf(casillasXML.get(i).getAttributeValue("metodoBusqueda")));
            casilla.setDificultad(Integer.parseInt(casillasXML.get(i).getAttributeValue("dificultad")));
            casilla.setJuego(TipoJuegos.valueOf(casillasXML.get(i).getAttributeValue("juego")));

            System.out.println(casilla.toString());

            /** Seteamos la casilla **/
            int columna = Integer.parseInt(casillasXML.get(i).getAttributeValue("columna"));
            int fila = Integer.parseInt(casillasXML.get(i).getAttributeValue("fila"));
            tablero.setCasilla(new Posicion(columna,fila), casilla);

            //casillas.add(new Posicion(Integer.parseInt(casillasXML.get(i).getAttributeValue("columna")),Integer.parseInt(casillasXML.get(i).getAttributeValue("fila"))));

        }

        tablero.setEntrada(ent);
        tablero.setSalidas(sal);
        tablero.setJugador(posJugador);



         return tablero;
    }

    public Posicion damePosicionJugador() {

            return posJugador;
    }

    public int dameVidasJugador(){

        return numVidasJugador;
    }





  public static void main(String[] args) {
     /*try {
        SAXBuilder builder=new SAXBuilder(false);
        //usar el parser Xerces y no queremos
        //que valide el documento
        Document doc=builder.build("/home/luigi/Escritorio/liga.xml");
        //construyo el arbol en memoria desde el fichero
        // que se lo pasaré por parametro.
        Element raiz=doc.getRootElement();
        
        //cojo el elemento raiz
        /*System.out.println("La liga es de tipo:"+
                    raiz.getAttributeValue("tipo"));*/
        //todos los hijos que tengan como nombre plantilla
       /* List equipos=raiz.getChildren("minijuego");
        System.out.println("Formada por:"+equipos.size()+" minijuegos");
        Iterator i = equipos.iterator();


        while (i.hasNext()){
            Element e= (Element)i.next();
            /**
             *  Parseamos el laberinto.
             */

           /* if(e.getAttributeValue("tipo").equalsIgnoreCase("laberinto")){

                List casillas=e.getChildren("casillas");
                Element entrada = e.getChild("entrada");
                Element salida = e.getChild("salida");

                Posicion ent = new Posicion(Integer.parseInt(entrada.getAttributeValue("columna")),Integer.parseInt(entrada.getAttributeValue("fila")));
                Posicion sal = new Posicion(Integer.parseInt(salida.getAttributeValue("columna")),Integer.parseInt(salida.getAttributeValue("fila")));

                System.out.println(ent);
                System.out.println(sal);


                Element laberinto = e.getChild("laberinto");
                
                List filas = laberinto.getChildren("fila");
                for (int j = 0; j < filas.size();j++)
                    System.out.println(((Element)filas.get(j)).getText());





            }




            else{
            //primer hijo que tenga como nombre club
            System.out.println(e.getAttributeValue("tipo"));
            Element club =e.getChild("club");
            List plantilla=e.getChildren("plantilla");
            System.out.println
                          (club.getText()+":"+"valoracion="+
                           club.getAttributeValue("valoracion")+","+
                           "ciudad="+club.getAttributeValue("ciudad")+","+
                           "formada por:"+plantilla.size()+"jugadores");
             if (e.getChildren("conextranjeros").size()==0)
              System.out.println("No tiene extranjeros");
             else  System.out.println("Tiene extranjeros");
            }
        }





        // Dejamos de mano del lector el sacar el nombre
        //de los arbitros, animate!!
     }catch (Exception e){
        e.printStackTrace();
     }*/

      ParserXML parser = new ParserXML("/home/luigi/casino.xml");
      System.out.println(parser.parsea8PuzzleInicial().toString());


  }

    public Estado getEntradaLaberinto() {

            return entradaLaberinto;

    }

    public Estado getSalidaLaberinto() {

             return salidaLaberinto;
    }

    public Estado parseaCasillasVecinasObjetivo() {

        /** Al construir el estado, habria que comprobar que el tamaño especificado es correcto
         * de columnas y de filas
         */
        List listaMiniJuegos=raiz.getChildren("minijuego");
        Iterator iter = listaMiniJuegos.iterator();
        Estado estado = new Estado(new Dimension(3,3));
        while (iter.hasNext()){
            Element e= (Element)iter.next();
            /**
             *  Parseamos el mini-juego Casillas vecinas.
             *  Habria que comprobar que los valores estan entre cero y uno.
             */

            if(e.getAttributeValue("tipo").equalsIgnoreCase("CasillasVecinas")){

                Element objetivo = (Element)e.getChild("estadoObjetivo");

                List filas = objetivo.getChildren("fila");
                for (int i= 0;i< filas.size();i++){
                    String fila = ((Element)filas.get(i)).getText();
                    for (int j = 0; j < fila.length();j++){
                                estado.setNumero(j,i,Integer.parseInt(Character.toString(fila.charAt(j))));

                    }
                }

            }

        }
        return estado;



    }

    public Estado parsea8PuzzleObjetivo() {


        /** Al construir el estado, habria que comprobar que el tamaño especificado es correcto
         * de columnas y de filas
         */
        List listaMiniJuegos=raiz.getChildren("minijuego");
        Iterator iter = listaMiniJuegos.iterator();
        Estado estado = new Estado(new Dimension(3,3));
        while (iter.hasNext()){
            Element e= (Element)iter.next();
            /**
             *  Parseamos el mini-juego Casillas vecinas.
             *  Habria que comprobar que los valores estan entre cero y uno.
             */

            if(e.getAttributeValue("tipo").equalsIgnoreCase("OchoPuzzle")){

                Element objetivo = (Element)e.getChild("estadoObjetivo");

                List filas = objetivo.getChildren("fila");
                for (int i= 0;i< filas.size();i++){
                    String fila = ((Element)filas.get(i)).getText();
                    for (int j = 0; j < fila.length();j++){
                                estado.setNumero(j,i,Integer.parseInt(Character.toString(fila.charAt(j))));

                    }
                }

            }

        }
        return estado;
    }

   
}
