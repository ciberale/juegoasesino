/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cf.logica;

import cf.logica.minijuegos.OchoReinas;
import cf.util.Posicion;
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
public class OchoReinasTest {

    public OchoReinasTest() {
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
     * Test of estadoObjetivo method, of class OchoReinas.
     */
    @Test
    public void testEstadoObjetivo() {
        System.out.println("estadoObjetivo");
        OchoReinas instance = new OchoReinas();
        boolean expResult = false;
        boolean result = instance.estadoObjetivo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hazMovimiento method, of class OchoReinas.
     */
    @Test
    public void testHazMovimiento() {
        System.out.println("hazMovimiento");
        int movimiento = 0;
        OchoReinas instance = new OchoReinas();
        boolean expResult = false;
        boolean result = instance.hazMovimiento(movimiento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of numeroDeAtaquesEnEstaPosicion method, of class OchoReinas.
     */
    @Test
    public void testNumeroDeAtaquesEnEstaPosicion() {
        System.out.println("numeroDeAtaquesEnEstaPosicion");
        Posicion pos = null;
        OchoReinas instance = new OchoReinas();
        int expResult = 0;
        int result = instance.numeroDeAtaquesEnEstaPosicion(pos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hayReina method, of class OchoReinas.
     */
    @Test
    public void testHayReina() {
        System.out.println("hayReina");
        Posicion pos = null;
        OchoReinas instance = new OchoReinas();
        boolean expResult = false;
        boolean result = instance.hayReina(pos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}