package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Class to test Geometries class
 */
public class GeometriesTests {

    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}
     */
    @Test
    void testFindIntersections() {
        //Geometry collection:
        Plane plane = new Plane(new Point(4,0,0), new Vector(-1,0,0));
        Tube tube = new Tube(1, new Ray(new Point(2,0,0), new Vector(0,0,1)));
        Sphere sphere = new Sphere(1,new Point(-2,0,0));
        Triangle triangle = new Triangle(new Point(-4,-4,-1), new Point(-4,4,-1), new Point(-4,0,4));

        //Lists:
        Geometries all = new Geometries(plane, tube, sphere, triangle);
        Geometries empty = new Geometries();

        //To sort Points of intersections
        class sortByX implements Comparator<Point> {
            @Override
            public int compare(Point p1, Point p2) {
                return (int)(p1.getX() - p2.getX());
            }
        }

        // ============ Equivalence Partitions Tests ==============
        //TC01: Several (but not all) geometries intersect
        List<Point> result = all.findIntersections(new Ray(new Point(0.5,0,0), new Vector(1,0,0)));
        result.sort(new sortByX());
        assertEquals(List.of(new Point(1,0,0), new Point(3,0,0), new Point(4,0,0)), result,
                "TC01: Interactions wrong when crossing tube and plane");

        // =============== Boundary Values Tests ==================
        //TC11: Empty geometries collection
        assertNull(empty.findIntersections(new Ray(new Point(0.5,0,0), new Vector(1,0,0))),
                "TC11: No geometries, should be no intersections");

        //TC12: No intersection points
        assertNull(all.findIntersections(new Ray(new Point(10,0,0), new Vector(1,0,0))),
                "TC12: Should be no intersection points");

        //TC13: Only 1 geometry intersects
        result = all.findIntersections(new Ray(new Point(-2,0,0), new Vector(0,0,1)));
        assertEquals(1, result.size(),
                "TC13: Wrong number of intersections, expected is 1 from inside sphere upwards");
        assertEquals(List.of(new Point(-2,0,1)), result,
                "TC13: Intersection from inside Sphere wrong");

        //TC14: All geometries intersect
        result = all.findIntersections(new Ray(new Point(-10,0,0), new Vector(1,0,0)));
        result.sort(new sortByX());
        assertEquals(6, result.size(),
                "TC14: Wrong number of intersections, expected is 6 when passing through all objects");
        assertEquals(List.of(new Point(-4,0,0), new Point(-3,0,0), new Point(-1,0,0),
                new Point(1,0,0), new Point(3,0,0), new Point(4,0,0)), result,
                "TC14: Intersections when intersecting all geometries is wrong");

    }
}