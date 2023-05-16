package geometries;
import primitives.Color;
import primitives.Vector;
import primitives.Point;

/**
 * this interface is the "parent" of all geometries it forces all geometries to
 * implement the getNormal method.
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    public abstract Vector getNormal(Point point);

}
