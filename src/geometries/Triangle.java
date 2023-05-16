package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Triangle class. A triangle has 3 corners and is a polygon, so it inherits from polygon.
 */
public class Triangle extends Polygon {

    /**
     * Constructor for a triangle takes in 3 points and saves them to a list of vertices.
     *
     * @param point1
     * @param point2
     * @param point3
     */
    public Triangle(Point point1, Point point2, Point point3) {
        super(point1, point2, point3);

    }

    @Override
    public String toString() {
        String points = "";
        for (Point point : vertices) {
            points += point.toString() + ' ';
        }
        return super.toString() + " and a triangle with point 3 points: " + points;
    }



    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Plane plane = new Plane(this.vertices.get(0), this.vertices.get(1), this.vertices.get(2));
        List planeIntersection = plane.findGeoIntersectionsHelper(ray);
        if (planeIntersection == null) {
            return null;
        }
        try {
            Point A = this.vertices.get(0), B = this.vertices.get(1), C = this.vertices.get(2);
            Point p0 = ray.getP0();

            GeoPoint Q = ((GeoPoint) planeIntersection.get(0));

            Vector AB = B.subtract(A);
            Vector AC = C.subtract(A);
            Vector n = AB.crossProduct(AC);
            double areaABC = n.length() / 2;

            //calculate triangle ABP
            //Vector AB
            Vector AP = Q.point.subtract(A);
            n = AB.crossProduct(AP);
            double areaABP = n.length() / 2;

            //calculate triangle BCP
            Vector BC = C.subtract(B);
            Vector BP = Q.point.subtract(B);
            n = BC.crossProduct(BP);
            double areaBCP = n.length() / 2;

            //calculate triangle ACP
            Vector CA = A.subtract(C);
            Vector CP = Q.point.subtract(A);
            n = CA.crossProduct(CP);
            double areaCAP = n.length() / 2;


            double u = areaABP / areaABC;
            double v = areaBCP / areaABC;
            double w = areaCAP / areaABC;
            if (isZero(alignZero(u)) || isZero(alignZero(v)) || isZero(alignZero(w)))
                return null;
            if (!isZero(u + v + w - 1)) {
                return null;
            }
            return List.of(new GeoPoint(this, Q.point));


        } catch (IllegalArgumentException i) {
            return null;
        }
    }
}
