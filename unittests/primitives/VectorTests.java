package primitives;

import geometries.Polygon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    @Test
    void testAdd() {//assertEquals() (expected, actual, message "errorrorro");
        // ============ Equivalence Partitions Tests ==============
        //TC01: Simple addition test between two vectors (5,5,5) + (2,3,4) = (7,8,9).
        assertEquals(new Vector(7,8,9), new Vector(5,5,5).add(new Vector(2,3,4)), "ERROR: Simple addition between two vectors does not work properly.");
        // =============== Boundary Values Tests ==================
        //TC02: Addition of two vectors addding up the zero vector which is illegal and needs to be returning an exception.
        assertThrows(IllegalArgumentException.class, //
                () ->new Vector(1,1,1).add(new Vector(-1,-1,-1)), //
                "ERROR: Vectors cannot be added up to equal the zero vector.");
    }

    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Simple subtraction tests between two vectors (10,10,10)-(3,2,1) = (7,8,9)
        assertEquals(new Vector(7,8,9), new Vector(10,10,10).subtract(new Vector(3,2,1)), "ERROR: Simple subtraction between two vectors does not work properly.");
        // =============== Boundary Values Tests ==================
        //TC02: subtraction between two equal Vectors equalling the zero vector which is supposed to throw an exception.
        assertThrows(IllegalArgumentException.class, //
                () ->new Vector(1,2,3).add(new Vector(1,2,3)), //
                "ERROR: Two equal vectors cannot be subtracted.");
    }

    @Test
    void testScaling() {
        // =============== Boundary Values Tests ==================
        //TC01: 1 scalar vector u should equal u
        assertEquals(v1, v1.scale(1), "ERROR: A vector scaled by one needs to equal itself.");
        //TC02: -1 scalar vector should equal the negative of itself.
        assertEquals(new Vector(2,4,6), v2.scale(-1), "ERROR: A vector scaled by -1 should equal negative of itself");
        //TC03: -2 scalar vector
        // ============ Equivalence Partitions Tests ==============
        //TC04: Random positive number scalar
        assertEquals(new Vector(3, 6,9), v1.scale(3), "ERROR: Simple vector scaling not working properly");
        //TC05: Random negative number scalar
        assertEquals(new Vector(-3, -6,-9), v1.scale(-3), "ERROR: Simple vector scaling not working properly")

    }

    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Dot product for vectors eqalling a positive number
        assertEquals(10.0, v1.dotProduct(new Vector(3,2,1)), "ERROR: simple dot product equalling a positive number is not working properly");
        //TC02: Dot product for vectors equalling a negative number
        assertEquals(-28.0 , v1.dotProduct(v3), "ERROR: simple dot product equalling a negative number is not working properly" );
        // =============== Boundary Values Tests ==================
        //TC01: 90 degree angle test
        assertEquals(0.0 , v1.dotProduct(v3), "ERROR: Orthogonal dot product should equal zero" );

    }

    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:
        // =============== Boundary Values Tests ==================
    }

    @Test
    void TestLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Length squared test for a regular vector v1.
        assertEquals(14.0, v1.lengthSquared(), "ERROR: simple lengthSquared returning the wrong value.");
        // =============== Boundary Values Tests ==================
    }

    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Length test for a regular vector (0,3,4) equalling 5.
        assertEquals(5.0, new Vector(0,3,4).length(), "ERROR: Simple vector length returning the wrong value");
        // =============== Boundary Values Tests ==================
        //TC02: Length test for a vector with length 1. (0,1,0)
        assertEquals(1.0, new Vector(0,1,0).length(), "ERROR: vector length for a length of 1 not returning the ocrrect value");
    }

    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        // =============== Boundary Values Tests ==================
    }
}