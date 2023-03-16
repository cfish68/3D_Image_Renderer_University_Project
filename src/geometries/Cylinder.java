package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{

    double height;
    /**
     * constructor which takes in a radius and a ray to create the tube part of the cylinder, and a height for the cylinder
     *
     * @param radius
     * @param ray
     */
    Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        this.height = height;
    }

    /**
     * returns the normal at a given point.
     *       (for now returns null)
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
