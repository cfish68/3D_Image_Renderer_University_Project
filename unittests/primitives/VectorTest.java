package primitives;

import geometries.Polygon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * try { // test zero vector
     *          new Vector(0, 0, 0);
     *          out.println("ERROR: zero vector does not throw an exception");
     *       } catch (IllegalArgumentException ignore) {} catch (Exception ignore) {
     *          out.println("ERROR: zero vector throws wrong exception");
     *       }
     *
     *       Vector v1 = new Vector(1, 2, 3);
     *       Vector v2 = new Vector(-2, -4, -6);
     *       Vector v3 = new Vector(0, 3, -2);
     *
     *       // test length..
     *       if (!isZero(v1.lengthSquared() - 14))
     *          out.println("ERROR: lengthSquared() wrong value");
     *       if (!isZero(new Vector(0, 3, 4).length() - 5))
     *          out.println("ERROR: length() wrong value");
     *
     *       // Test add & subtract
     *       try {
     *          v1.add(new Vector(-1, -2, -3));
     *          out.println("ERROR: Vector + -itself does not throw an exception");
     *       } catch (IllegalArgumentException ignore) {} catch (Exception ignore) {
     *          out.println("ERROR: Vector + itself throws wrong exception");
     *       }
     *       try {
     *          v1.subtract(v1);
     *          out.println("ERROR: Vector - itself does not throw an exception");
     *       } catch (IllegalArgumentException ignore) {} catch (Exception ignore) {
     *          out.println("ERROR: Vector + itself throws wrong exception");
     *       }
     *       if (!v1.add(v2).equals(new Vector(-1, -2, -3)))
     *          out.println("ERROR: Point - Point does not work correctly");
     *       if (!v1.subtract(v2).equals(new Vector(3, 6, 9)))
     *          out.println("ERROR: Point - Point does not work correctly");
     *
     *       // test Dot-Product
     *       if (!isZero(v1.dotProduct(v3)))
     *          out.println("ERROR: dotProduct() for orthogonal vectors is not zero");
     *       if (!isZero(v1.dotProduct(v2) + 28))
     *          out.println("ERROR: dotProduct() wrong value");
     *
     *       // test Cross-Product
     *       try { // test zero vector
     *          v1.crossProduct(v2);
     *          out.println("ERROR: crossProduct() for parallel vectors does not throw an exception");
     *       } catch (Exception e) {}
     *       Vector vr = v1.crossProduct(v3);
     *       if (!isZero(vr.length() - v1.length() * v3.length()))
     *          out.println("ERROR: crossProduct() wrong result length");
     *       if (!isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)))
     *          out.println("ERROR: crossProduct() result is not orthogonal to its operands");
     *
     *       // test vector normalization vs vector length and cross-product
     *       Vector v = new Vector(1, 2, 3);
     *       Vector u = v.normalize();
     *       if (!isZero(u.length() - 1))
     *          out.println("ERROR: the normalized vector is not a unit vector");
     *       try { // test that the vectors are co-lined
     *          v.crossProduct(u);
     *          out.println("ERROR: the normalized vector is not parallel to the original one");
     *       } catch (Exception e) {}
     *       if (v.dotProduct(u) < 0)
     *          out.println("ERROR: the normalized vector is opposite to the original one");
     *
     *       // Test operations with points and vectors
     *       Point p1 = new Point(1, 2, 3);
     *       if (!(p1.add(new Vector(-1, -2, -3)).equals(new Point(0, 0, 0))))
     *          out.println("ERROR: Point + Vector does not work correctly");
     *       if (!new Vector(1, 1, 1).equals(new Point(2, 3, 4).subtract(p1)))
     *          out.println("ERROR: Point - Point does not work correctly");
     *
     *       out.println("If there were no any other outputs - all tests succeeded!");
     */

    @Test
    void testAdd() {//assertEquals() (expected, actual, message "errorrorro");
        //Equivalence partitions.
        //TC01: Simple addition test between two vectors (5,5,5) + (2,3,4) = (7,8,9).
        assertEquals(new Vector(7,8,9), new Vector(5,5,5).add(new Vector(2,3,4)), "ERROR: Simple addition between two vectors does not work properly.");
        //Boundary values
        //TC02: Addition of two vectors addding up the zero vector which is illegal and needs to be returning an exception.
        assertThrows(IllegalArgumentException.class, //
                () ->new Vector(1,1,1).add(new Vector(-1,-1,-1)), //
                "Vectors cannot be added up to equal the zero vector.");
    }

    @Test
    void testSubtract() {
        //TC01: Simple subtraction tests between two vectors (10,10,10)-(3,2,1) = (7,8,9)
        assertEquals(new Vector(7,8,9), new Vector(10,10,10).subtract(new Vector(3,2,1)), "ERROR: Simple subtraction between two vectors does not work properly.");
        //TC02: subtraction between two equal Vectors equalling the zero vector which is supposed to throw an exception.
        assertThrows(IllegalArgumentException.class, //
                () ->new Vector(1,2,3).add(new Vector(1,2,3)), //
                "Two equal vectors cannot be subtracted.");
    }

    @Test
    void testScaling() {
        //Boundary Tests:
        //TC01: 1 scalar vector u should equal u
        assertEquals(v1, v1.scale(1), "A vector scaled by one needs to equal itself.");
    }

    @Test
    void testDotProduct() {
    }

    @Test
    void testCrossProduct() {
    }

    @Test
    void TestLengthSquared() {
    }

    @Test
    void testLength() {
    }

    @Test
    void testNormalize() {
    }
}