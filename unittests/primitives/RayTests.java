package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for primitives.Ray class
 */
public class RayTests {

    /**
     * test method for {@link primitives.Ray#findClosestPoint(List)}
     */
    @Test
    public void findClosestPointTest(){
        Ray ray = new Ray(new Point(0,0,0), new Vector(1,0,0));

        //TC01: equivalence tests(1)
        List<Point> points = List.of(new Point(1,2,3), new Point(5,5,5), new Point(1,0,0), new Point(7,7,7));
        assertEquals(new Point(1,0,0), ray.findClosestPoint(points), "TC01: point in the middle of list is closest is not found");
        //boundary value tests (3)
        //TC11: an empty list
        points = null;
        assertNull(ray.findClosestPoint(points), "an empty list doesn't have a closest point should return null");

        //TC12: the closest point is the first point
        points = List.of(new Point(1,0,0), new Point(1,2,3), new Point(5,5,5), new Point(7,7,7));
        assertEquals(new Point(1,0,0), ray.findClosestPoint(points), "TC02: point in the beginning of list is closest is not found");

        //TC13: the closest point is at the end of the list
        points = List.of(new Point(1,2,3), new Point(5,5,5), new Point(7,7,7),new Point(1,0,0));
        assertEquals(new Point(1,0,0), ray.findClosestPoint(points), "TC02: point in the beginning of list is closest is not found");

    }
}
