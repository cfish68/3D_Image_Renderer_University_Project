package primitives;

import geometries.Polygon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 */
class PointTests {


    /**
     * test method for {@link primitives.Point#Point(double, double, double)}
     */
    @Test
    void testPointDoublesParam(){
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test Random point with positive values (1,2,3)
        try {
            new Point(5,6,7);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing the positive value point");
        }
        //TC02: Test Random point with negative values (-3,-4,-7);
        try {
            new Point(-3,-4,-7);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing the negative value point");
        }
        //TC03: Test Random point with mixed positive and negative values (-3,-4,-7);
        try {
            new Point(-15,7,-4);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing the mixed value point");
        }

        // =============== Boundary Values Tests ==================
        //TC11: Test the origin point (0,0,0) constructor;
        try {
            new Point(0,0,0);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing the zero point");
        }
    }

    /**
     * test method for {@link primitives.Point#Point(Double3)}
     */
    void testPointDouble3Param(){

        //TC01: Test the  constructor using Double3 with positive values;
        try {
            new Point(new Double3(5,6,7));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing point using Double3 with positive values");
        }
        //TC02: Test the  constructor using Double3 with negative values;
        try {
            new Point(new Double3(-1,-2,-3));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing point using Double3 with negative values");
        }
        //TC03: Test the Point constructor using Double3 with mixed positive and negative values;
        try {
            new Point(new Double3(-1,2,-3));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing point using Double3 with mixed positive and negative values");
        }
        // =============== Boundary Values Tests ==================
        //TC11: Test the Point constructor with Double3 using 0 values;
        try {
            new Point(new Double3(0,0,0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing point using Double3 with Zero values");
        }

    }
    /**
     * test method for {@link primitives.Point#subtract(primitives.Point)}
     */
    @Test
    void testSubtract() {
        //assertEquals() (expected, actual, message "errorrorro");

        // ============ Equivalence Partitions Tests ==============
        //TC01: Regular point subtraction with all positive values resulting in a positive vector
        assertEquals(new Vector(4,4,4),new Point(5,6,7).subtract(new Point(1,2,3)),"ERROR: positive Point - positive Point does not work properly" );
        //TC02: Regular point subtraction with negative values
        assertEquals(new Vector(-2,-2,-4),new Point(-3,-4,-7).subtract(new Point(-1,-2,-3)),"ERROR: positive Point - positive Point does not work properly" );

        // =============== Boundary Values Tests ==================
        //TC10: Same point subtraction. Should result in an exception.
        assertThrows(IllegalArgumentException.class, //
                () ->new Point(5,6,7).subtract(new Point(5,6,7)), //
                "ERROR: Points cannot be subtracted to equal the zero vector.");
        //TC11: subtraction of a point with the origin
        assertEquals(new Vector(5,6,7),new Point(5,6,7).subtract(new Point(0,0,0)),"ERROR: Point - origin Point does not work properly" );
        //TC12: subtraction from origin to a point
        assertEquals(new Vector(-5,-6,-7),new Point(0,0,0).subtract(new Point(5,6,7)),"ERROR: Point - origin Point does not work properly" );

    }

    /**
     * test method for {@link primitives.Point#add(primitives.Vector)}
     */
    @Test
    void testAdd() {
        Vector positiveVector = new Vector(3,3,3);
        Vector negativeVector = new Vector(-1,-1,-1);
        Vector mixedVector = new Vector(-1,3,-2);
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test random values for point addition.
        assertEquals(new Point(4,4,4), new Point(1,2,3).add(new Vector(3,2,1)),"ERROR: Regular point addition not working properly");
        // =============== Boundary Values Tests ==================
        //TC11: Test addition from the origin with positive vector
        assertEquals(new Point(3,3,3), new Point(0,0,0).add(positiveVector), "ERROR: Point addition from the origin with positive values not working properly");
        //TC12: Test addition from the origin with negative vector
        assertEquals(new Point(-1,-1,-1), new Point(0,0,0).add(negativeVector),"ERROR: Point addition from the origin with negative values not working properly");
    }

    /**
     * test method for {@link primitives.Point#distance(primitives.Point)}
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Distance test between two positive values.
        assertEquals(1.0, new Point(1,0,0).distance(new Point(0,0,0)), "ERROR: Point distance regular simple case does not return expected value.");
        //TC02: Distance test between two non-trivial values
        assertEquals(Math.sqrt(5.0), new Point(1,2,3).distance(new Point(3,3,3)),"ERROR: Point distance more complex case does not return expected value");
        //TC03: Distance test involving negative values
        assertEquals(Math.sqrt(56), new Point(-1,-2,-3).distance(new Point(1,2,3)), "ERROR: Point distance with negative values not working properly ");
        // =============== Boundary Values Tests ==================
        //TC11: Distance test between the origin and another point
        assertEquals(Math.sqrt(5.0), new Point(0,0,0).distance(new Point(0,1,2)),"ERROR: Point distance from the origin does not return expected value");
        //TC12: Distance test between point and the origin
        assertEquals(Math.sqrt(5.0), new Point(0,1,2).distance(new Point(0,0,0)),"ERROR: Point distance from the origin does not return expected value");
    }

    /**
     * test method for {@link primitives.Point#distanceSquared(primitives.Point)}
     */
    @Test
    void testDistanceSquared() {
        //TC01: Distance testSquared between two positive values.
        assertEquals(1.0, new Point(1,0,0).distanceSquared(new Point(0,0,0)), "ERROR: Point distance squared regular simple case does not return expected value.");
        //TC02: Distance testSquared between two non-trivial values
        assertEquals(5.0, new Point(1,2,3).distanceSquared(new Point(3,3,3)),"ERROR: Point distance squared more complex case does not return expected value");
        //TC03: Distance testSquared involving negative values
        assertEquals(56, new Point(-1,-2,-3).distanceSquared(new Point(1,2,3)), "ERROR: Point distance squared with negative values not working properly ");
        // =============== Boundary Values Tests ==================
        //TC11: DistanceSquared test between the origin and another point
        assertEquals(5.0, new Point(0,0,0).distanceSquared(new Point(0,1,2)),"ERROR: Point distance squared from the origin does not return expected value");
        //TC12: DistanceSquared test between point and the origin
        assertEquals(5.0, new Point(0,1,2).distanceSquared(new Point(0,0,0)),"ERROR: Point distance squared from the origin does not return expected value");
    }

}