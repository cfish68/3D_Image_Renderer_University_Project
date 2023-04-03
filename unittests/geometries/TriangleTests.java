package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle
 */
class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#Triangle(primitives.Point, primitives.Point, primitives.Point)}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Correct Cylinder
        try {
            new Triangle(new Point(0,0,0), new Point(2,0,0), new Point(0,2,0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct triangle");
        }

        // =============== Boundary Values Tests ==================

        //TC11: 0 area triangle
        Point p = new Point(0,0,0);
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(p,p,p),
                "Constructed triangle with 0 area");
    }

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Any point in triangle
        //Create test triangle
        Triangle triangle = new Triangle(new Point(0,0,0), new Point(3,0,0), new Point(0,3,0));
        //Ensure no exceptions
        assertDoesNotThrow(() -> triangle.getNormal(new Point(1,1,0)));
        //generate test result
        Vector result = triangle.getNormal(new Point(1,1,0));
        //Ensure |result| = 1
        assertEquals(1, result.length(),0.00000001, "Triangle's normal is not a unit vector");
        //Ensure result is expected vector (same as z-axis) + or -
        assertTrue ( result.equals(new Vector(0,0,1)) || result.scale(-1).equals(new Vector(0,0,1)),"Normal to triangle is wrong");
        // =============== Boundary Values Tests ==================
        //No Boundary tests for Triangle
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(new Point(0,0,0), new Point(0,2,0), new Point(0,1,2));
        // ============ Equivalence Partitions Tests ==============
        //TC01: Inside Triangle
        List<Point> result = triangle.findIntersections(new Ray(new Point(1,1,1), new Vector(-1, 0,0)));
        assertEquals(1, result.size(),
                "Wrong number of points. Expected intersections is 1 for ray which goes through the triangle.");
        assertEquals(List.of(new Point(0,1,1)), result,
                "Ray crosses triangle at wrong point");
        //TC02: Outside against edge
        result = triangle.findIntersections(new Ray(new Point(1,0,1), new Vector(-1, 0,0)));
        assertEquals(0, result.size(),
                "Wrong number of points. Expected intersections is 0 for ray which is outside against edge.");
        //TC03: Outside against vertex
        result = triangle.findIntersections(new Ray(new Point(1,-1,-1), new Vector(-1, 0,0)));
        assertEquals(0, result.size(),
                "Wrong number of points. Expected intersections is 0 for ray which is outside against vertex.");
        // =============== Boundary Values Tests ==================
        //TC11: On edge
        result = triangle.findIntersections(new Ray(new Point(-1,-1,0), new Vector(1, 0,0)));
        assertEquals(0, result.size(),
                "Wrong number of points, 0 expected when on edge");
        //TC12: In vertex
        result = triangle.findIntersections(new Ray(new Point(-1,0,0), new Vector(1, 0,0)));
        assertEquals(0, result.size(),
                "Wrong number of points. 0 expected when in vertex.");
        //TC13: On edge's continuation
        result = triangle.findIntersections(new Ray(new Point(1,3,0), new Vector(1, 0,0)));
        assertEquals(0, result.size(),
                "Wrong number of points. 0 expected on edge's continuation.");


    }



}