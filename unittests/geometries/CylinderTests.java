package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class
 */
class CylinderTests {

    /**
     * Test method for {@link geometries.Cylinder#Cylinder(double, primitives.Ray, double)}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: Correct Cylinder
        try {
            new Cylinder(3, new Ray(new Point(0,0,0), new Vector(1,1,1)), 3);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct Cylinder");
        }

        //TC02: Negative radius
        assertThrows(IllegalArgumentException.class,
                () -> new Cylinder(-3, new Ray(new Point(0,0,0), new Vector(1,1,1)), 3),
                "Constructed cylinder with negative radius");

        //TC03: Negative height
        assertThrows(IllegalArgumentException.class,
                () -> new Cylinder(3, new Ray(new Point(0,0,0), new Vector(1,1,1)), -3),
                "Constructed cylinder with negative height");

        // =============== Boundary Values Tests ==================

        //TC11: 0 radius
        assertThrows(IllegalArgumentException.class,
                () -> new Cylinder(0, new Ray(new Point(0,0,0), new Vector(1,1,1)), 3),
                "Constructed cylinder with 0 radius");

        //TC12: 0 height
        assertThrows(IllegalArgumentException.class,
                () -> new Cylinder(1, new Ray(new Point(0,0,0), new Vector(1,1,1)), 0),
                "Constructed cylinder with 0 height");

        //TC13: 0 vector as ray. commented out because can't create 0 vector
        /*assertThrows(IllegalArgumentException.class,
                () -> new Cylinder(1, new Ray(new Point(0,0,0), new Vector(0,0,0)), 3),
                "Constructed cylinder with 0 vector as ray");*/

    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        //Create test cylinder, vertical cylinder of radius 1, height 2
        Cylinder cylinder = new Cylinder(1,new Ray(new Point(0,0,0),new Vector(0,0,1)),2);

        // ============ Equivalence Partitions Tests ==============
        //TC01: On round surface
        // ensure no exceptions
        assertDoesNotThrow(() -> cylinder.getNormal(new Point(0,1,1)));
        //generate test result
        Vector result = cylinder.getNormal(new Point(0,1,1));
        //Ensure |result| = 1
        assertEquals(1, result.length(),0.00000001, "Cylinder's normal is not a unit vector");
        //Ensure result is expected vector
        assertEquals(new Vector(0,1,0),result,"Cylinder's normal to round surface is wrong");

        //TC02: On base at origin (slightly further to x-axis)
        // ensure no exceptions
        assertDoesNotThrow(() -> cylinder.getNormal(new Point(0.5,0,0)));
        //generate test result
        result = cylinder.getNormal(new Point(0.5,0,0));
        //Ensure |result| = 1
        assertEquals(1, result.length(),0.00000001, "Normal to base of cylinder is not a unit vector");
        //Ensure result is expected vector (opposite ray of Cylinder)
        assertEquals(new Vector(0,0,-1),result,"Normal to base of cylinder is wrong");

        //TC03: On base at top of cylinder (slightly further to x-axis)
        // ensure no exceptions
        assertDoesNotThrow(() -> cylinder.getNormal(new Point(0.5,0,2)));
        //generate test result
        result = cylinder.getNormal(new Point(0.5,0,2));
        //Ensure |result| = 1
        assertEquals(1, result.length(),0.00000001, "Normal to top of cylinder is not a unit vector");
        //Ensure result is expected vector (same as ray of Cylinder)
        assertEquals(new Vector(0,0,1),result,"Normal to top of cylinder is wrong");

        // =============== Boundary Values Tests ==================
        //TC11: On base edge at bottom
        // ensure no exceptions
        assertDoesNotThrow(() -> cylinder.getNormal(new Point(1,0,0)));
        //generate test result
        result = cylinder.getNormal(new Point(1,0,0));
        //Ensure |result| = 1
        assertEquals(1, result.length(),0.00000001,
                "Normal to base edge at bottom of cylinder is not a unit vector");
        //Ensure result is expected vector (Which will be opposite to ray,
        //not the normal of round surface)
        assertEquals(new Vector(0,0,-1),result,"Normal to bottom edge of cylinder is wrong");

        //TC12: On base edge at top
        // ensure no exceptions
        assertDoesNotThrow(() -> cylinder.getNormal(new Point(1,0,2)));
        //generate test result
        result = cylinder.getNormal(new Point(1,0,2));
        //Ensure |result| = 1
        assertEquals(1, result.length(),0.00000001,
                "Normal to base edge at top of cylinder is not a unit vector");
        //Ensure result is expected vector (Which will be the ray,
        //not the normal of round surface)
        assertEquals(new Vector(0,0,1),result,"Normal to top edge of cylinder is wrong");
    }

    /**
     * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
    }
}