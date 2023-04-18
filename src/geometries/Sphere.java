package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
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
    public List<Point> findIntersections(Ray ray) {

        Vector u = center.subtract(ray.getP0());
        double t = ray.getDir().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared() - t*t);
        //if d is greater than the radius then there are no intersection points and we return null
        if(d>=radius) {
            return null;
        }
        double th = Math.sqrt(radius*radius - d*d);
        List<Point> result = new ArrayList<Point>();
        double t1 = t + th;
        double t2 = t - th;
        if(t1>0){
            result.add(ray.getP0().add(ray.getDir().scale(t1)));
        }
        if(t2>0){
            result.add(ray.getP0().add(ray.getDir().scale(t2)));
        }
        return result;
        }

}
