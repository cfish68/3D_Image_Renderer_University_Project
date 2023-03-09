package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry{

    Point q0;
    Vector normal;
    Plane(Point point1, Point point2, Point point3){
        this.q0 = point1;
        this.normal = null;
        //calculate the normal vector
    }
    Plane(Point p0, Vector normal){
        this.q0 = p0;
        this.normal = normal.normalize();
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    public Vector getNormal() {
        return normal;
    }
}
