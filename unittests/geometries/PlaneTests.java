package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 */
class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#Plane(Point, Point, Point)}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Correct Plane
        try {
            new Plane(new Point(1,0,0),new Point(0,1,0),new Point(0,0,0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct Plane");
        }

        //TC02: Plane without 3 unique points.
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0,0,1),new Point(0,0,1),new Point(1,1,1)),
                "Constructed plane with 3 non-unique points.");

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
        Plane plane = new Plane(new Point(0,0,0),new Point(1,0,0),new Point(0,1,0));
        // ensure no exceptions
        assertDoesNotThrow(() -> plane.getNormal(new Point(1,1,0)));
        //generate test result
        Vector result = plane.getNormal(new Point(1,1,0));
        //Ensure |result| = 1
        assertEquals(1, result.length(),0.00000001, "PLane's normal is not a unit vector");
        //Ensure result is expected vector (assuming positive for now, not sure what to do if negative normal (0,0,-1)
        assertTrue(result.equals(new Vector(0,0,1)) || result.equals(new Vector(0,0,-1)),
                "x,y plane's normal at (1,1,0) is wrong (not (0,0,1/-1))");
        // =============== Boundary Values Tests ==================
        //No edge cases

    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(new Point(0,0,0), new Vector(1,0,0), new Vector(0,1,0));
        // ============ Equivalence Partitions Tests ==============
        //**** Group: The ray must be neither orthogonal nor parallel to the plane
        //TC01: Ray intersect the plane
        List<Point> result = plane.findIntersections(new Ray(new Point(0,0,-1), new Vector(1,0,1)));
        assertEquals(1, (result).size(), "There should only be 1 point intersected in plane not orthogonal nor parallel");
        assertEquals(List.of(new Point(1,0,0)), result, "Ray crosses plane in wrong point not orthogonal nor parallel");
        //TC02: Ray does not intersect the plane
        assertNull(plane.findIntersections(new Ray(new Point(0,0,1), new Vector(1,0,1)))," 0 points intersected in plane Ray pointing in other direction not orthogonal nor parallel");

        // =============== Boundary Values Tests ==================
        //**** Group: Ray is parallel to the plane
        //TC11: The ray is included in the plane (ray lies in plane)
        assertNull(plane.findIntersections(new Ray(new Point(1,0,0), new Vector(1,1,0))),"No intersection points if Ray is parallel and in the plane");



        //TC12: The ray is not included in the plane (ray is not in plane at all)
        assertNull(plane.findIntersections(new Ray(new Point(0,0,1), new Vector(1,1,0))),"No intersection points if Ray is parallel and not in the plane");

        //**** Group: Ray is orthogonal to the plane
        //TC13: Head (p0) is before the plane
        result = plane.findIntersections(new Ray(new Point(0,0,-1), new Vector(0,0,1)));
        assertEquals(1, (result).size(), "Head before the plane and orthogonal should be 1 intersection point");
        assertEquals(List.of(new Point(0,0,0)), result, "Ray crosses plane in wrong point, orthogonal to the plane");
        //TC14: Head (p0) is in the plane
        assertNull(plane.findIntersections(new Ray(new Point(0,0,0), new Vector(0,0,1))),"Head in the plane and orthogonal should be 0 intersection points");

        //TC15: head (p0) is after the plane
        assertNull(plane.findIntersections(new Ray(new Point(0,0,1), new Vector(0,0,1))),"Head after the plane and orthogonal should be 0 intersection points");
        //**** Group: Ray is neither orthogonal nor parallel to and begins at the plane (p0 is in the plane, but not the ray)
        //TC16: Ray begins at the plane
        assertNull(plane.findIntersections(new Ray(new Point(1,1,0), new Vector(1,1,1))),"Head in the plane and not orthogonal or parallel should be 0 intersection points");

        //**** Group: Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane(Q)
        //TC17: Ray begins at planes reference point
        assertNull(plane.findIntersections(new Ray(new Point(0,0,0), new Vector(1,1,1))),"Head begins at reference point and goes out should be 0 points of intersection");

    }
}