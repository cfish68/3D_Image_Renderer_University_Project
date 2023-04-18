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
    @Test
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
        assertEquals(1, result.length(),0.00000001, "TC01: Tube's normal is not a unit vector");
        //Ensure result is expected vector
        assertEquals(new Vector(0,1,0),result,"TC01: Tube's normal to round surface is wrong");

        // =============== Boundary Values Tests ==================
        //TC11: When projection of normal goes to the start of the ray of the tube
        // ensure no exceptions
        assertDoesNotThrow(() -> tube.getNormal(new Point(0,1,0)));
        //generate test result
        result = tube.getNormal(new Point(0,1,0));
        //Ensure |result| = 1
        assertEquals(1, result.length(),0.00000001, "TC11: Tube's normal is not a unit vector");
        //Ensure result is expected vector
        assertEquals(new Vector(0,1,0),result,"TC11: Tube's normal at origin of ray is wrong");

    }

    /**
     * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray is outside tube (0 points)

        //TC02: Ray crosses tube (2 points)

        //TC03: Ray starts inside tube and isn't parallel to the tubes ray (1 point)

        //TC04: Ray would cross tube but starts after tube (0 points)

        // =============== Boundary Values Tests ==================
            // **** Group: Ray's line crosses the sphere (but not the center)
        //TC11: Ray starts at tube face and goes inside (1 point)
        //TC12: Ray starts at sphere and goes outside (0 points)
            // **** Group: Ray's line goes through the tubes ray and isn't parralel to it
        //TC13: Ray starts before the tube (2 points)
        //TC14: Ray starts at tube and goes inside (1 points)
        //TC15: Ray starts inside the tube (1 points)
        //TC16: Ray starts at the base of tubes ray (1 points)
        //TC17: Ray starts at tube and goes outside (0 points)
        //TC18: Ray starts after sphere (0 points)
            // **** Group: Ray's line is tangent to the tube (all tests 0 points)
        //TC19: Ray starts before the tangent point
        //TC20: Ray starts at the tangent point
        //TC21: Ray starts after the tangent point
        //TC24: Ray is parallel to tubes ray and starts at radius of tube
            // **** Group: Special cases
        //TC22: Ray's line is outside, ray start is orthogonal to the line to tubes' ray start (0 points)
        //TC22: Ray is parallel to Tubes ray and starts inside (0 points)
        //TC23: Ray is parallel to tubes ray and starts outside (0 points)

    }
}