package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 */
class PlaneTests {

    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Correct Plane
        try {
            new Plane(new Point(0,0,0),new Point(1,0,0),new Point(1,0,0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct Plane");
        }

        // =============== Boundary Values Tests ==================
        //No edge cases
    }
    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Anywhere on plane
        //Create Plane
        Plane plane = new Plane(new Point(0,0,0),new Point(1,0,0),new Point(1,0,0));
        // ensure no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(1,1,0)));
        //generate test result
        Vector result = plane.getNormal(new Point(1,1,0));
        //Ensure |result| = 1
        assertEquals(1, result.length(),0.00000001, "PLane's normal is not a unit vector");
        //Ensure result is expected vector (assuming positive for now, not sure what to do if negative normal (0,0,-1)
        assertEquals(new Vector(0,0,1),result,"x,y plane's normal at (1,1,0) is wrong (not (0,0,1))");
        // =============== Boundary Values Tests ==================
        //No edge cases
    }
}