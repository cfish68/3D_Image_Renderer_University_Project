package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Util;

import java.util.List;
import java.util.stream.Collectors;

import static primitives.Util.alignZero;

/**
 * Interface Intersectables is anything that can be intersected by a ray.
 */
public abstract class Intersectable {


    public BoundingRegion boundingRegion = new BoundingRegion();

    public static class BoundingRegion{
        /**
         * max point for bounding region
         */
        public Point max;

        /**
         * min point for bounding region
         */
        public Point min;

    }

    /**
     * method to set the min and max bounding region point to be implemented by each geometry
     */
    public abstract BoundingRegion setBoundingRegion();



    /**
     * static class which has a geometry and a point
     */
    public static class GeoPoint{
        public Geometry geometry;
        public Point point;

        /**
         * constructor for GeoPoint.
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point){
            this.geometry = geometry;
            this.point = point;
        }


        @Override
        public boolean equals(Object object){
            if(this == object)
                return true;
            if(object instanceof GeoPoint gp){
                return(this.geometry.equals(gp.geometry) && this.point.equals(gp.point));
            }
            return false;
        }

        @Override
        public String toString() {
            return ("geometry: " + geometry.toString() + '\n' + "Point: " + point.toString());
        }
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

//    /**
//     * receives a ray and returns a List fo GeoPoints, at this point just calls findGeoIntersectionsHelper(ray)
//     * @param ray
//     * @return
//     */
//    public List<GeoPoint> findGeoIntersections(Ray ray){
//        return findGeoIntersectionsHelper(ray);
//    }

    /**
     * This function takes in a ray and returns a list of points that the ray intersects
     * @param ray
     * @return A list of points which the ray intersects
     */
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }


    /**
     * find intersections where we don't care about the distance
     * @param ray
     * @return
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * find intersections only within a certain distance. This distance might be +infinity
     * @param ray
     * @param maxDistance
     * @return
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Returns
     * @param ray
     * @param maxDistance
     * @return
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
        var geoList = findGeoIntersectionsHelper(ray);
        if(geoList == null)
            return null;
        geoList = geoList.stream().filter(gp -> Util.alignZero(ray.getP0().distance(gp.point) - maxDistance)
                <= 0).collect(Collectors.toList());
        if(geoList.isEmpty()){
            return null;
        }
        return geoList;
    }



}
