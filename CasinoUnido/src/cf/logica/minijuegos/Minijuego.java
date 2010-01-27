package cf.logica.minijuegos;

import cf.ParserXML;
import cf.logica.estados.Estado;
import java.util.Vector;

/**
 *
 * @author luigi
 */
public abstract class Minijuego {
    protected ParserXML parserXML;

    
    public Estado getEstado(){
        return estado;
    }

     public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public Vector<Integer> getMovimientos(){
        return movimientos;
    }

    public String pintaEstado(){
        /** Creo que deberia ser el propio juego el que pinte el estado como crea oportuno **/
        return estado.dibujaEstado();
    }

    public abstract String getExplicacionEstado();

    public abstract double getCosteMovimiento(int movimiento, Estado estado);

    
    /**
     * Este metodo nos dice si hemos llegado a un estado objetivo.
     * Hay que sobreescribirlo en cada juego, en concreto los que tienen
     * varias posibles soluciones.
     */
    
    public abstract boolean estadoObjetivo();
    public abstract boolean hazMovimiento(int movimiento);
    public abstract double getValorHeuristico(Estado estado);
  

    protected Estado estado;
    protected Estado estadoObjetivo;
    protected Vector<Integer> movimientos;
    public abstract boolean esPeligro(Estado status);

    public void setParser(ParserXML parserXML) {
            this.setParserXML(parserXML);

    }

    /**
     * @return the parserXML
     */
    public ParserXML getParserXML() {
        return parserXML;
    }

    /**
     * @param parserXML the parserXML to set
     */
    public void setParserXML(ParserXML parserXML) {
        this.parserXML = parserXML;
    }



}
