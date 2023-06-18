package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * this class, Plane represents a plain with a point and normal vector of the plane
 */
public class Plane extends Geometry{

    Point q0;
    Vector normal;

    /**
     * constructor for Plane taking in 3 points and not taking in a normal vector.
     * (here the normal vector needs to be calculated)
     * @param point1
     * @param point2
     * @param point3
     */
    public Plane(Point point1, Point point2, Point point3){
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



    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if(this.q0.equals(ray.getP0())){
            return null;
        }
        double denom = this.getNormal().dotProduct(ray.getDir().normalize());
        if (isZero(denom)) {
            return null;//this means the direction of the vector is parallel to the plane. and therefore there are no intersections
        }
        double numerator = this.getNormal().dotProduct(this.q0.subtract(ray.getP0()));
        if (isZero(numerator)) {
            return null;//p0 lies in the plane and therefore there are no intersections
        }
        double t = (alignZero(numerator / denom))/ray.getDir().length();
        Point p0PlusTV = ray.getP0().add(ray.getDir().normalize());
        if(this.q0.equals(p0PlusTV)){//the ray intersects the plane at point P0
            return List.of(new GeoPoint(this, this.q0));
        }
        else if(t>0){

            return List.of(new GeoPoint(this, ray.getPoint(t)));
        }
        return null;
    }

    @Override
    public BoundingRegion setBoundingRegion() {
        boundingRegion.min = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
        boundingRegion.max = new Point(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
        return boundingRegion;
    }
}
