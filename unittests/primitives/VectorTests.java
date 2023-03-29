package primitives;

import geometries.Polygon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    /**
     * test method for {@link primitives.Vector#Vector(double, double, double)}
     */
    @Test
    void TestVectorConstructor(){
        // ============ Equivalence Partitions Tests ==============
        //TC01: Regular positive vector constructor
        try {
            new Vector(1,2,3);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a Vector");
        }
        //TC02: Regular vector with negative values constructor
        try {
            new Vector(-1,-2,-3);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a Vector");
        }
        // =============== Boundary Values Tests ==================
        //TC02: Trying to create a 0 vector should result in an exception
        assertThrows(IllegalArgumentException.class, //
                () ->new Vector(0,0,0), //
                "ERROR: Vectors cannot be added up to equal the zero vector.");
    }
    /**
     * test method for {@link primitives.Vector#add(Vector)}
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Simple addition test between two vectors (5,5,5) + (2,3,4) = (7,8,9).
        assertEquals(new Vector(7,8,9), new Vector(5,5,5).add(new Vector(2,3,4)), "ERROR: Simple addition between two vectors does not work properly.");
        // =============== Boundary Values Tests ==================
        //TC02: Addition of two vectors adding up the zero vector which is illegal and needs to be returning an exception.
        assertThrows(IllegalArgumentException.class, //
                () ->new Vector(1,1,1).add(new Vector(-1,-1,-1)), //
                "ERROR: Vectors cannot be added up to equal the zero vector.");

    }
    /**
     * test method for {@link primitives.Vector#subtract(Point)}
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Simple subtraction tests between two vectors (10,10,10)-(3,2,1) = (7,8,9)
        assertEquals(new Vector(7,8,9), new Vector(10,10,10).subtract(new Vector(3,2,1)), "ERROR: Simple subtraction between two vectors does not work properly.");
        // =============== Boundary Values Tests ==================
        //TC02: subtraction between two equal Vectors equalling the zero vector which is supposed to throw an exception.
        assertThrows(IllegalArgumentException.class, //
                () ->new Vector(1,2,3).subtract(new Vector(1,2,3)), //
                "ERROR: Two equal vectors cannot be subtracted.");
    }
    /**
     * test method for {@link primitives.Vector#scale(double)}
     */
    @Test
    void testScaling() {
        // =============== Boundary Values Tests ==================
        //TC01: 1 scalar vector u should equal u
        assertEquals(new Vector(1,2,3), new Vector(1,2,3).scale(1), "ERROR: A vector scaled by one needs to equal itself.");
        //TC02: -1 scalar vector should equal the negative of itself.
        assertEquals(new Vector(2,4,6), new Vector(-2,-4,-6).scale(-1), "ERROR: A vector scaled by -1 should equal negative of itself");
        //TC03: -2 scalar vector
        // ============ Equivalence Partitions Tests ==============
        //TC04: Random positive number scalar
        assertEquals(new Vector(3, 6,9), new Vector(1,2,3).scale(3), "ERROR: Simple vector scaling not working properly");
        //TC05: Random negative number scalar
        assertEquals(new Vector(-3, -6,-9), new Vector(1,2,3).scale(-3), "ERROR: Simple vector scaling not working properly");

    }
    /**
     * test method for {@link primitives.Vector#dotProduct(Vector)}
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Dot product for vectors equalling a positive number
        assertEquals(10.0, new Vector(1,2,3).dotProduct(new Vector(3,2,1)), "TC01: simple dot product equalling a positive number is not working properly");
        //TC02: Dot product for vectors equalling a negative number
        assertEquals(-5 , new Vector(1,1,4).dotProduct(new Vector(0,3,-2)), "TC02: simple dot product equalling a negative number is not working properly" );

        // =============== Boundary Values Tests ==================
        //TC11: 90 degree angle test
        assertEquals(0.0 , new Vector(1,2,3).dotProduct(new Vector(0,3,-2)), "TC11: Orthogonal dot product should equal zero" );

    }
    /**
     * test method for {@link primitives.Vector#crossProduct(Vector)}
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:
        // =============== Boundary Values Tests ==================
        //TC02: Test the zero vector
        assertThrows(IllegalArgumentException.class, //
                () ->new Vector(1,2,3).crossProduct(new Vector(-2,-4,-6)), //
                "ERROR: crossProduct() for parallel vectors does not throw an exception");
    }
    /**
     * test method for {@link Vector#lengthSquared()}
     */
    @Test
    void TestLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Length squared test for a regular vector v1.
        assertEquals(14.0, new Vector(1,2,3).lengthSquared(), "ERROR: simple lengthSquared returning the wrong value.");
        // =============== Boundary Values Tests ==================
    }
    /**
     * test method for {@link Vector#length()}
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Length test for a regular vector (0,3,4) equalling 5.
        assertEquals(5.0, new Vector(0,3,4).length(), "ERROR: Simple vector length returning the wrong value");
        // =============== Boundary Values Tests ==================
        //TC02: Length test for a vector with length 1. (0,1,0)
        assertEquals(1.0, new Vector(0,1,0).length(), "ERROR: vector length for a length of 1 not returning the correct value");
    }
    /**
     * test method for {@link Vector#normalize()}
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Regular simple normalize test.
        assertEquals(new Vector(1,0,0),new Vector(15,0,0).normalize(),"ERROR: Simple Vector noramlize test does not work properly");
        //TC02: Normalize test which is a bit more complex
        assertEquals(new Vector(5/Math.sqrt(75),5/Math.sqrt(75),5/Math.sqrt(75)), new Vector(5,5,5).normalize(),"ERROR: Vector normalize for more complex vector does not work properly");
        //TC03: Normalize test with vector length less than 1
        assertEquals(new Vector(1,0,0),new Vector(0.5,0,0).normalize(),"ERROR: Vector normalize with vector length less then 1 does not work properly");
        // =============== Boundary Values Tests ==================

    }
}