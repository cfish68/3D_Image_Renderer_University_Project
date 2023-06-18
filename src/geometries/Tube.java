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
    public Tube(double radius, Ray ray) {
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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        //Intersection points are roots of: at^2 + bt + c = 0 where a,b,c are the following
        Vector normalizedAxis = axisRay.getDir().normalize();
        Vector v = ray.getDir();
        Vector w; //Vector of ray.p0 - axisRay.p0
        double a,b,c; //coefficients of quadratic equation with t

        a = (v.dotProduct(v)) - Math.pow(v.dotProduct(normalizedAxis),2);

        if (!ray.getP0().equals(axisRay.getP0())) {// if w != 0
            w = ray.getP0().subtract(axisRay.getP0());
            b = 2 * (v.dotProduct(w) - (v.dotProduct(normalizedAxis) * (w.dotProduct(normalizedAxis))));
            c = (w.dotProduct(w) - Math.pow(w.dotProduct(normalizedAxis), 2)) - Math.pow(radius, 2);
        }
        else {// if w = 0
            b = 0;
            c = -Math.pow(radius, 2);
        }

        double distance;
        double t1, t2; //will become scalars for the equation ray.p0 + t1*ray.dir
        Point int1, int2; //Intersection 1 and 2
        double discriminant = b*b - 4*a*c;

        //if true, line is tangent or greater than radius from axisRay
        if (discriminant <= 0)
            return null;
        else { //there are 1-2 intersections depending on where ray.p0 is
            t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
        }

        //Only return intersections with positive non-zero scalars
        //as the rest are behind the ray
        if(t1 > 0 && t2 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        else if(t1 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
        else if(t2 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        else
            return null;
    }

    @Override
    public BoundingRegion setBoundingRegion() {
        //doesn't do anything
        return null;
    }
}
