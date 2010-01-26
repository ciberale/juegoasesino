package cf.util;

import cf.logica.busqueda.Nodo;
import java.util.Vector;

/**
 *
 * @author luigi
 */
public class ColaOrdenadaNodos {

    Vector<Nodo> listaNodos;


    public ColaOrdenadaNodos(){

        listaNodos = new Vector<Nodo>();

    }


    public Nodo damePrimero(){

        try{
        return listaNodos.elementAt(0);

        }
        catch(ArrayIndexOutOfBoundsException ex){
            return null;
        }

    }

    public void quitaPrimero(){

        listaNodos.remove(0);
    }

    public int size(){
        return listaNodos.size();
    }

    public void removeFirst(){

        listaNodos.remove(0);
    }

    public void aniade(Nodo nodo){

        boolean llegadoAlfinal = true;

        for (int i = 0; i < listaNodos.size();i++){

            /*** Es decir, es mas prioritario **/
            if (listaNodos.elementAt(i).compareTo(nodo) == 1){
                listaNodos.add(i, nodo);
                /*** Si llegamos al final del vector sin situarlo ***/
                llegadoAlfinal = (i == listaNodos.size() - 1);
                break;
            }
        }

       if (llegadoAlfinal)
           listaNodos.add(nodo);
    }

    public Nodo elementAt(int i) {

        try{
        return listaNodos.elementAt(i);
        }
        catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }


}
