package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 */
class TubeTests {

    /**
     * Test method for {@link geometries.Tube#Tube(double, primitives.Ray)}
     */
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Correct Tube
        try {
            new Tube(1,new Ray(new Point(0,0,0), new Vector(0,0,1)));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct tube");
        }

        //TC02: Negative radius
        assertThrows(IllegalArgumentException.class,
                () -> new Tube(-1, new Ray(new Point(0,0,0), new Vector(1,1,1))),
                "Constructed tube with negative radius");

        // =============== Boundary Values Tests ==================
        //TC11: 0 radius
        assertThrows(IllegalArgumentException.class,
                () -> new Tube(0, new Ray(new Point(0,0,0), new Vector(1,1,1))),
                "Constructed tube with 0 radius");
    }

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        //Create test cylinder, vertical cylinder of radius 1, height 2
        Tube tube = new Tube(1,new Ray(new Point(0,0,0),new Vector(0,0,1)));

        // ============ Equivalence Partitions Tests ==============
        //TC01: On surface (all points are the same)
        // ensure no exceptions
        assertDoesNotThrow(() -> tube.getNormal(new Point(0,1,1)));
        //generate test result
        Vector result = tube.getNormal(new Point(0,1,1));
        //Ensure |result| = 1
        assertEquals(1, result.length(),0.00000001, "Tube's normal is not a unit vector");
        //Ensure result is expected vector
        assertEquals(new Vector(0,1,0),result,"Tube's normal to round surface is wrong");

        // =============== Boundary Values Tests ==================
        //TC11: When projection of normal goes to the start of the ray of the tube
        // ensure no exceptions
        assertDoesNotThrow(() -> tube.getNormal(new Point(0,1,0)));
        //generate test result
        result = tube.getNormal(new Point(0,1,0));
        //Ensure |result| = 1
        assertEquals(1, result.length(),0.00000001, "Tube's normal is not a unit vector");
        //Ensure result is expected vector
        assertEquals(new Vector(0,1,0),result,"Tube's normal at origin of ray is wrong");

    }
}