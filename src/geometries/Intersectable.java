package geometries;
import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Interface Intersectables is anything that can be intersected by a ray.
 */
public abstract class Intersectable {
    /**
     * This function takes in a ray and returns a list of points that the ray intersects
     * @param ray
     * @return A list of points which the ray intersects
     */
    public abstract List<Point> findIntersections(Ray ray);
}
