package cf.logica;

import cf.logica.minijuegos.CasillasVecinas;
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
public class CasillasVecinasTest {

    public CasillasVecinasTest() {
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
     * Test of estadoObjetivo method, of class CasillasVecinas.
     */
    @Test
    public void testEstadoObjetivo() {
        System.out.println("estadoObjetivo");
        CasillasVecinas instance = new CasillasVecinas();
        boolean expResult = false;
        boolean result = instance.estadoObjetivo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hazMovimiento method, of class CasillasVecinas.
     */
    @Test
    public void testHazMovimiento() {
        System.out.println("hazMovimiento");
        int movimiento = 0;
        CasillasVecinas instance = new CasillasVecinas();
        boolean expResult = false;
        boolean result = instance.hazMovimiento(movimiento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}