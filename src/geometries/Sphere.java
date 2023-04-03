package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Class Sphere which represents a sphere which is a type of RadialGeometry and therefor inherits from it.
 * This Class has a center, which is the center of the sphere. This class also has a radius.
 */
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
        //no check for point on surface.
        // ( P minus center) is the normal and then normalized
        return point.subtract(this.center).normalize();
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nand sphere with center at " + this.center.toString();
    }
    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
