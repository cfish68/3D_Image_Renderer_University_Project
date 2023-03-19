package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {
    protected Ray axisRay;

    /**
     * Constructor which takes in a radius and a ray to create a tube.
     * @param radius
     * @param ray
     */
    Tube(double radius, Ray ray) {
        super(radius);
        axisRay = ray;
    }

    /**
     * Returns the normal at a given point.
     * (for now returns null)
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nand tube with axis " + this.axisRay.toString();
    }
}
