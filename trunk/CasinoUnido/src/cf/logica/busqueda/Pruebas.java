/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cf.logica.busqueda;

import cf.logica.estados.Estado;
import cf.util.Dimension;
import cf.util.Posicion;

/**
 *
 * @author luigi
 */
public class Pruebas {



    public static void main (String args[]){


        Estado es1 = new Estado(new Dimension(2,2));
        Estado es2 = (Estado) es1.clone();
        es2.setNumero(new Posicion(0,0), 1);
        es2.setNumero(new Posicion(1,1), 2);
        System.out.println(es1);
        System.out.println(es2);

    }
}
