package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{

    private Point center;

    /**
     * Constructor which takes in a radius and center point and makes a sphere.
     * @param radius
     * @param center
     */
    Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the normal at a given point.
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + " and sphere with center: " + this.center;
    }
}
