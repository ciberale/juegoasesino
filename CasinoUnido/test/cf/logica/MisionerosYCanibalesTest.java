/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cf.logica;

import cf.logica.estados.Estado;
import cf.logica.minijuegos.MisionerosYCanibales;
import cf.util.Dimension;
import cf.util.Posicion;
import movimientos.MovimientosMisioneros;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author luigi
 */
public class MisionerosYCanibalesTest {

    public MisionerosYCanibalesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of estadoObjetivo method, of class MisionerosYCanibales.
     */
    @Test
    public void testEstadoObjetivo() {
        System.out.println("estadoObjetivo");
        MisionerosYCanibales instance = new MisionerosYCanibales();
        boolean expResult = false;
        boolean result = instance.estadoObjetivo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hazMovimiento method, of class MisionerosYCanibales.
     */
    @Test
    public void testHazMovimiento() {
        System.out.println("hazMovimiento");
        int movimiento = 0;
        MisionerosYCanibales instance = new MisionerosYCanibales();
        boolean expResult = false;
        boolean result = instance.hazMovimiento(movimiento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of barcaAlaIzquierda method, of class MisionerosYCanibales.
     */
    @Test
    public void testBarcaAlaIzquierda() {
        System.out.println("barcaAlaIzquierda");
        MisionerosYCanibales instance = new MisionerosYCanibales();
        boolean expResult = false;
        boolean result = instance.barcaAlaIzquierda();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     @Test
    public void testEstados(){


        MisionerosYCanibales juego = new MisionerosYCanibales();
        Estado estado = new Estado(new Dimension(3,1));

       /**
        * Hay un monton de combinaciones...
        */

        estado.setNumero(new Posicion(0,0),3);
        estado.setNumero(new Posicion(1,0),3);
        estado.setNumero(new Posicion(2,0),1);
        juego.setEstado(estado);

        Estado estadoCorrecto = new Estado (new Dimension(3,1));
        estadoCorrecto.setNumero(new Posicion(0,0),0);
        estadoCorrecto.setNumero(new Posicion(1,0),0);
        estadoCorrecto.setNumero(new Posicion(2,0),0);

        /**
         * Solucion del juego de los canibales
         */
        juego.hazMovimiento(MovimientosMisioneros.MisioneroYCanibal.ordinal());
        juego.hazMovimiento(MovimientosMisioneros.Misionero.ordinal());
        juego.hazMovimiento(MovimientosMisioneros.DosCanibales.ordinal());
        juego.hazMovimiento(MovimientosMisioneros.Canibal.ordinal());
        juego.hazMovimiento(MovimientosMisioneros.DosMisioneros.ordinal());
        juego.hazMovimiento(MovimientosMisioneros.MisioneroYCanibal.ordinal());
        juego.hazMovimiento(MovimientosMisioneros.DosMisioneros.ordinal());
        juego.hazMovimiento(MovimientosMisioneros.Canibal.ordinal());
        juego.hazMovimiento(MovimientosMisioneros.DosCanibales.ordinal());
        juego.hazMovimiento(MovimientosMisioneros.Canibal.ordinal());
        juego.hazMovimiento(MovimientosMisioneros.DosCanibales.ordinal());






        System.out.println(juego.getEstado());
        System.out.println(estadoCorrecto.toString());
        assertTrue(juego.getEstado().equals(estadoCorrecto));
      

    }


}