package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
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
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Constructor for sphere with opposite parameter order
     * @param center
     * @param radius
     */
    public Sphere(Point center, double radius) {
        this(radius, center);
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

    /**
     * Finds and returns a list of GeoPoint intersections between the ray and the geometry
     * @param ray
     * @return
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        Vector u;
        try {
            u = center.subtract(ray.getP0());
        }catch(IllegalArgumentException i) {//p0 is the same as center
            return List.of(new GeoPoint(this, ray.getP0().add(ray.getDir().normalize().scale(radius))));
        }
        double t = ray.getDir().normalize().dotProduct(u);

        double tSquared = t*t;
        double uLengthSquared = u.lengthSquared();
        double d;
        if(uLengthSquared-tSquared < 0) {// in this case p0 is inside the sphere
            d = Math.sqrt(tSquared - uLengthSquared);
            if (d >= radius) {
                return null;
            }
            return List.of(new GeoPoint(this, ray.getP0().add(ray.getDir().normalize().scale(-t))));
        }
        else
            d = Math.sqrt(uLengthSquared - tSquared);
        //if d is greater than the radius then there are no intersection points then we return null
        if (d >= radius) {
            return null;
        }
        double th = Math.sqrt(radius * radius - d * d);

        double t1 = (t + th)/ray.getDir().length();
        double t2 = (t - th)/ray.getDir().length();
        if(Util.isZero(t1))
            t1 = 0;
        if(Util.isZero(t2))
            t2=0;
        //if t1 is greater than zero then we have a point
        if (t1 > 0) {
            //if t2 is greater than zero then we have 2 points
            if (t2 > 0) {
                return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
            } else {//t2 is not greater than zero hence we only have t1
                return List.of(new GeoPoint(this, ray.getPoint(t1)));
            }
        } else if(t2 > 0) {//t1 is not greater than zero hence we only have t2
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        }
        return null;
    }


    @Override
    public BoundingRegion setBoundingRegion() {
        boundingRegion.min = new Point(center.getX() - radius, center.getY() - radius, center.getZ() - radius);
        boundingRegion.max = new Point(center.getX() + radius, center.getY() + radius, center.getZ() + radius);
        return boundingRegion;
    }
}
