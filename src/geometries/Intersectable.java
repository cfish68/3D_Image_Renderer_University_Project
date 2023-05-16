package geometries;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Interface Intersectables is anything that can be intersected by a ray.
 */
public abstract class Intersectable {


    /**
     * static class which has a geometry and a point
     */
    public static class GeoPoint{
        public Geometry geometry;
        public Double3 point;

        /**
         * constructor for GeoPoint.
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Double3 point){
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

    /**
     * This function takes in a ray and returns a list of points that the ray intersects
     * @param ray
     * @return A list of points which the ray intersects
     */
    public abstract List<Point> findIntersections(Ray ray);
}
