package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * this class, Plane represents a plain with a point and normal vector of the plane
 */
public class Plane implements Geometry{

    Point q0;
    Vector normal;

    /**
     * constructor for Plane taking in 3 points and not taking in a normal vector.
     * (here the normal vector needs to be calculated)
     * @param point1
     * @param point2
     * @param point3
     */
    Plane(Point point1, Point point2, Point point3){
        if (point1.equals(point2) || point1.equals(point3) || point2.equals(point3)){
            throw new IllegalArgumentException("Plane must be 3 unique points.");
        }
        this.q0 = point1;
        //calculate the normal
        Vector a = point2.subtract(point1);
        Vector b = point3.subtract(point1);
        Vector normalNormalized = (a.crossProduct(b)).normalize();
        //save the normalized normal
        this.normal = normalNormalized;
    }

    /**
     * constructor for Plane taking in 1 point and a normal vector
     * @param p0
     * @param normal
     */
    Plane(Point p0, Vector normal){
        this.q0 = p0;
        this.normal = normal.normalize();
    }

    /**
     * the method returns the normal vector from a given point which needs to be calculated.
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point point) {
        return this.normal;
    }

    /**
     * this method returns the normal already calculated
     * @return returns the normal
     */
    public Vector getNormal() {
        return normal;
    }
}
