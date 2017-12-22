package resolver;

import java.util.Collection;
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ResolverDelegateTest {
    
    public ResolverDelegateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of support method, of class ResolverDelegate.
     */
    @Test
    public void testSupport() {
        System.out.println("support");
        Object object = null;
        ResolverDelegate instance = new ResolverDelegate();
        boolean expResult = false;
//        boolean result = instance.support(object);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resolve method, of class ResolverDelegate.
     */
    @Test
    public void testResolve() {
        System.out.println("resolve");
        Object object = null;
        ResolverDelegate instance = new ResolverDelegate();
        Optional expResult = null;
        Optional result = instance.resolve(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getResolvers method, of class ResolverDelegate.
     */
    @Test
    public void testGetResolvers() {
        System.out.println("getResolvers");
        ResolverDelegate instance = new ResolverDelegate();
//        Collection<Resolver<I, O>> expResult = null;
//        Collection<Resolver<I, O>> result = instance.getResolvers();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setResolvers method, of class ResolverDelegate.
     */
    @Test
    public void testSetResolvers() {
        System.out.println("setResolvers");
        ResolverDelegate instance = new ResolverDelegate();
        instance.setResolvers(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addResolver method, of class ResolverDelegate.
     */
    @Test
    public void testAddResolver() {
        System.out.println("addResolver");
        ResolverDelegate instance = new ResolverDelegate();
//        instance.addResolver(null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
