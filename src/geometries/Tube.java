package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Class Tube which extends RadialGeometry.
 * This class has an axisRay (which is a ray through the middle.) Tube also has a radius from Radial Geometry.
 */
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
        //Direction vector of ray dot-product with the vector between point and p0
        Double t = axisRay.getDir().dotProduct(point.subtract(axisRay.getP0()));
        Point O; //The point where the normal intersects axisRay
        if (t == 0){ //If point is orthogonal to P0 (t == 0) then point O = P0
            O = axisRay.getP0();
        }else {
            //Figuring out point O, the point on the ray orthogonal to point
            O = axisRay.getP0().add(axisRay.getDir().scale(t));
        }
        //Normal is the normalized vector from point O to point P
        return (point.subtract(O)).normalize();
    }

    @Override
    public String toString() {
        return super.toString()
                + "\nand tube with axis " + this.axisRay.toString();
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
