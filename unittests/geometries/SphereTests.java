package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 */
class SphereTests {


    /**
     * Test method for {@link geometries.Sphere#Sphere(double, Point)}
     */
    @Test
    void testSphereConstructor(){
        // ============ Equivalence Partitions Tests ==============
        //TC01: Correct Sphere from the origin
        try {
            new Sphere(2, new Point(0,0,0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct sphere");
        }
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, //
                () ->new Sphere(0, new Point(0,0,0)), //
                "ERROR: A sphere should not have a 0 radius");
    }
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        //Create test Sphere, with center at the origin, and a radius of 3.
        Sphere sphere = new Sphere(3.0, new Point(0,0,0));


        // ============ Equivalence Partitions Tests ==============
        //TC01: There is a simple single test here
        //Ensure no exceptions
        assertDoesNotThrow(() -> sphere.getNormal(new Point(3,0,0)), "ERROR: get Normal throws an exception.");
        //Generate test result.
        Vector result = sphere.getNormal(new Point(3,0,0));
        //Ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Sphere normal is not a unit vector");
        //Ensure result is expected vector
        assertEquals(new Vector(1,0,0), result, "Sphere's normal is wrong");
        // =============== Boundary Values Tests ==================
        // There are no Boundary Values Tests.
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(1d, new Point (1, 0, 0));
// ============ Equivalence Partitions Tests ==============
// TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
        "Ray's line out of sphere");
// TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, (result).size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");
// TC03: Ray starts inside the sphere (1 point)
        List<Point> resultFromInside = sphere.findIntersections(new Ray(new Point(0.5,0.5,0.5),
                new Vector(-0.5,0.5,0.5)));
        assertEquals(1, (resultFromInside).size(),
            "Wrong number of points. Expected intersections is 1, for ray from inside the sphere");
        assertEquals(List.of(new Point(0,1,1)), resultFromInside, "Ray crosses sphere in wrong point");
// TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0,0,3),
                new Vector(0,0,1))), "Ray starts after the sphere");
        //assertEquals(0, (resultFromInside).size(),
              //  "Wrong number of points. Expected intersections is 0 for ray from outside the sphere towards other direction");
// =============== Boundary Values Tests ==================
// **** Group: Ray's line crosses the sphere (but not the center)
// TC11: Ray starts at sphere and goes inside (1 point)
        result = sphere.findIntersections(new Ray(new Point(2,0,0),
                new Vector(-1,1,0)));
        assertEquals(1, (result).size(),
                "Wrong number of points. Expected intersections is 1, for ray from edge into sphere");
        assertEquals(List.of(new Point(1,1,0)), result, "Ray crosses sphere in wrong point");

// TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(2,0,0),
                new Vector(0,0,3))),"edge outward is expected to be 0 intersections and therefore null");


// **** Group: Ray's line goes through the center
// TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point(1,2,0),
                new Vector(0,-1,0)));
        assertEquals(2, (result).size(),
                "Wrong number of points. Expected intersections is 2 for ray from before sphere towards the center of the sphere");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(1,-1,0), new Point(1,1,0)), result, "Ray Does not cross the right points when Ray starts before the sphere");
// TC14: Ray starts at sphere and goes inside (1 point)
        result = sphere.findIntersections(new Ray(new Point(1,-1,0),
                new Vector(0,1,0)));
        assertEquals(1, result.size(),
                "Wrong number of points. Expected intersections is 1 for ray which starts at the edge and goes in to the center.");
        assertEquals(List.of(new Point(1,1,0)), result,
                "Ray crosses sphere in wrong point when starting at edge and going to center");
// TC15: Ray starts inside (1 point)
        result = sphere.findIntersections(new Ray(new Point(0.5,0,0), new Vector(1,0,0)));
        assertEquals(1, result.size(),
                "Wrong number of points. Expected intersections is 1 for ray which starts inside and goes through the center.");
        assertEquals(List.of(new Point(2,0,0)), result,
                "Ray crosses sphere in wrong point when starting inside and going to center");
// TC16: Ray starts at the center (1 point)
        result = sphere.findIntersections(new Ray(new Point(1,0,0), new Vector(0,1,0)));
        assertEquals(1, result.size(),
                "Wrong number of points. Expected intersections is 1 for ray which starts at the center.");
        assertEquals(List.of(new Point(1,1,0)), result,
                "Ray crosses sphere in wrong point when starting at edge and going to center");
// TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0,1,1), new Vector(0,1,0))), "Ray starts at sphere and goes outside has 0 intersection points");


// TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(0,1.1,1), new Vector(0,1,1))), "Ray which starts after the sphere has 0 intersections");

// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
// TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,1,0), new Vector(0,0,1))), "Ray which starts before the tangent point has 0 intersections");

// TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,1,1), new Vector(0,0,1))),"Ray which starts at the tangent point has 0 intersections");

// TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(new Point(0,1,1), new Vector(0,0,1))), "Ray which starts after the tangent point has 0 intersections");

// **** Group: Special cases
// TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(new Point(0,2,1), new Vector(0,0,1))),"Ray which starts after the tangent point, outside orthogonal has 0 intersections");
    }

}