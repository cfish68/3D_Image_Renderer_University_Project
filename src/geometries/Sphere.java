package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{

    private Point center;

    Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + "and center: " + this.center;
    }
}
