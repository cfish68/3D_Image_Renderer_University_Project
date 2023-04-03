package geometries;
import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Interface Intersectables is anything that can be intersected by a ray.
 */
public interface Intersectable {
    /**
     * This function takes in a ray and returns a list of points that the ray intersects
     * @param ray
     * @return A list of points which the ray intersects
     */
    List<Point> findIntersections(Ray ray);
}
