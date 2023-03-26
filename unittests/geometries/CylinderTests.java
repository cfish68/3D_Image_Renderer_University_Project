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
     * Test method for {@link geometries.Cylinder#Cylinder(double, Ray, double)}
     */
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

        //TC11:

    }

    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // =============== Boundary Values Tests ==================

    }
}