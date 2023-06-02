package primitives;

import geometries.Intersectable.GeoPoint;
import java.util.List;

/**
 * this class is used to represent a ray. (a vector from  a certain point)
 */
public class Ray {
    Point p0;
    Vector dir;

    private static final double DELTA = 0.1;

    /**
     * Constructor for Ray that takes in a point and vector.
     * @param point
     * @param dir
     */
    public Ray(Point point, Vector dir){
        this.p0 = point;
        this.dir = dir.normalize();
    }

    public Ray(Point p0, Vector dir, Vector normal) {
        this(p0, dir);
        double nv = normal.dotProduct(this.dir);
        if (!Util.isZero(nv)) {
            Vector delta = normal.scale(nv > 0 ? DELTA : -DELTA);
            this.p0 = p0.add(delta);
        }
    }

    /**
     * Getter for point of ray
     * @return
     */
    public Point getP0(){
        return p0;
    }

    /**
     * Getter for direction of ray
     * @return
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * Checks first the object passed is in-fact a ray so that we don't have a runtime error
     * and then checks if it's the same ray with the same attributes.
     * @param obj
     * @return true if it is the same point false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if(!(obj instanceof Ray)){
            return false;
        }
        return this.p0.equals(((Ray) obj).p0) && this.dir.equals(((Ray) obj).dir);
    }

    @Override
    public String toString() {
        return "Ray starting at " + p0.toString()
                + "\nWith " + dir.toString();
    }

    /**
     * returns the point P = p0 + dir.scale(t)
     * @param t
     * @return
     */
    public Point getPoint(double t){
        return p0.add(dir.scale(t));
    }

    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * method takes in a list of geoPoints and returns the geoPoint closest to the ray head.
     * @param geoPoints
     * @return
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints){
        if(geoPoints == null){
            return null;
        }
        double inf = Double.POSITIVE_INFINITY;

        GeoPoint closestGeoPoint = null;
        for(GeoPoint gPoint: geoPoints){
            double distance = this.p0.distance(gPoint.point);
            if(distance < inf){
                closestGeoPoint = gPoint;
                inf = distance;
            }
        }

        return closestGeoPoint;
    }
}
