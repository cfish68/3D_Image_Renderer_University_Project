package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        Tube tube = new Tube(1, new Ray(new Point(1,0,0), new Vector(0,0,1)));
        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray is outside tube (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(3,0,0), new Vector(1,0,0))),
                "TC01: Ray is outside of tube, should be 0 intersections");

        //TC02: Ray crosses tube (2 points)
        List<Point> result = tube.findIntersections(new Ray(new Point(3,-1,0), new Vector(-1,1,0)));
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(1,1,0), new Point(2,0,0)), result,
                "TC02: Intersections wrong when ray crosses tube");

        //TC03: Ray starts inside tube and isn't parallel to the tubes ray (1 point)
        result = tube.findIntersections(new Ray(new Point(1.5,0,0), new Vector(1,0,0)));
        assertEquals(1, result.size(), "TC03: Wrong number of intersections, expected is 1 for ray from inside the tube");
        assertEquals(List.of(new Point(2,0,0)), result, "TC03: Intersection of ray from inside tube is wrong.");

        //TC04: Ray would cross tube but starts after tube (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(5,0,0), new Vector(1,0,0))),
                "TC04: Should be 0 intersections");

        // =============== Boundary Values Tests ==================
            // **** Group: Ray's line crosses the tube (but not the tubes' ray)
        //TC11: Ray starts at tube face and goes inside (1 point)
        result = tube.findIntersections(new Ray(new Point(1,-1,0), new Vector(0,1,0)));
        assertEquals(1, result.size(), "TC11: Wrong number of intersections, expected is 1");
        assertEquals(List.of(new Point(1,1,0)), result, "TC11: Intersection at wrong point");

        //TC12: Ray starts at tube and goes outside (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(1,-1,0), new Vector(1,-1,0))),
                "TC12: Should be 0 intersections");

            // **** Group: Ray's line goes through the tubes ray and isn't parallel to it
        //TC13: Ray starts before the tube (2 points)
        result = tube.findIntersections(new Ray(new Point(-1,0,0), new Vector(1,0,0)));
        assertEquals(2, result.size(),"TC13: Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0,0,0), new Point(2,0,0)), result,
                "TC13: Intersections wrong when ray crosses tube through tube ray");

        //TC14: Ray starts at tube and goes inside (1 points)
        result = tube.findIntersections(new Ray(new Point(1,-1,0), new Vector(0,1,0)));
        assertEquals(1, result.size(), "TC14: Wrong number of intersections, expected is 1");
        assertEquals(List.of(new Point(1,1,0)), result,
                "TC14: Ray intercepts tube at wrong point when starting at tube and going inside");

        //TC15: Ray starts inside the tube (1 points)
        result = tube.findIntersections(new Ray(new Point(0.5,0,0), new Vector(1,0,0)));
        assertEquals(1, result.size(), "TC15: Wrong number of intersections, expected is 1 for ray from inside the tube");
        assertEquals(List.of(new Point(2,0,0)), result, "TC15: Intersection of ray from inside tube is wrong.");

        //TC16: Ray starts at the base of tubes ray (1 points)
        result = tube.findIntersections(new Ray(new Point(1,0,0), new Vector(1,0,0)));
        assertEquals(1, result.size(), "TC16: Wrong number of intersections, expected is 1 for ray from inside the tube");
        assertEquals(List.of(new Point(2,0,0)), result, "TC16: Intersection of ray from base of tube is wrong.");

        //TC17: Ray starts at tube and goes outside (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(1,-1,0), new Vector(0,-1,0))),
                "TC17: Should be 0 intersections");

        //TC18: Ray starts after sphere (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(1,-2,0), new Vector(0,-1,0))),
                "TC18: Should be 0 intersections");

            // **** Group: Ray's line is tangent to the tube (all tests 0 points)
        //TC19: Ray starts before the tangent point
        assertNull(tube.findIntersections(new Ray(new Point(2,-2,0), new Vector(0,1,0))),
                "TC19: Should be 0 intersections");

        //TC20: Ray starts at the tangent point
        assertNull(tube.findIntersections(new Ray(new Point(2,0,0), new Vector(0,1,0))),
                "TC20: Should be 0 intersections");

        //TC21: Ray starts after the tangent point
        assertNull(tube.findIntersections(new Ray(new Point(2,2,0), new Vector(0,1,0))),
                "TC21: Should be 0 intersections");

        //TC22: Ray is parallel to tubes' ray and starts at radius of tube
        assertNull(tube.findIntersections(new Ray(new Point(2,0,0), new Vector(0,0,1))),
                "TC22: Should be 0 intersections");

        //TC23: Ray is same as tubes' ray
        assertNull(tube.findIntersections(new Ray(new Point(1,0,0), new Vector(0,0,1))),
                "TC23: Should be 0 intersections");

            // **** Group: Special cases
        //TC24: Ray's line is outside, ray's start is where the axisRay is orthogonal to ray (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-1,0,0), new Vector(0,1,0))));

        //TC25: Ray is parallel to Tubes ray and starts inside (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(0.5,0,0), new Vector(0,0,1))),
                "TC25: Should be 0 intersections");

        //TC26: Ray is parallel to tubes ray and starts outside (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(3,3,0), new Vector(0,0,1))),
                "TC26: Should be 0 intersections");

        //TC27: If p0 of axisRay is same as p0 of ray
        result = tube.findIntersections(new Ray(new Point(1,0,0), new Vector(1,0,0)));
        assertEquals(1, result.size(), "TC27: Wrong number of intersections, expected is 1 for ray from inside the tube");
        assertEquals(List.of(new Point(2,0,0)), result, "TC27: Intersection of ray from inside tube is wrong.");


    }
}