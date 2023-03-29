package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Sphere class
 */
class SphereTests {

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
        assertDoesNotThrow(() -> sphere.getNormal(new Point(3,0,0)), "ERROR: get Normal throws an excpetion.");
        //Generate test result.
        Vector result = sphere.getNormal(new Point(3,0,0));
        //Ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Sphere normal is not a unit vector");
        //Ensure result is expected vector
        assertEquals(new Vector(1,0,0), result, "Sphere's normal is wrong");
        // =============== Boundary Values Tests ==================
    }   // There are no Boundary Values Tests.
}