package geometries;
import primitives.Vector;
import primitives.Point;

/**
 * this interface is the "mother" of all geomentries it forces all geometries to
 * implement the getNormal method.
 */
public interface Geometry extends Intersectable {
    public Vector getNormal(Point point);
}
