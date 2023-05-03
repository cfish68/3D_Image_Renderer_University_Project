package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Triangle class. A triangle has 3 corners and is a polygon, so it inherits from polygon.
 */
public class Triangle extends Polygon{

    /**
     * Constructor for a triangle takes in 3 points and saves them to a list of vertices.
     * @param point1
     * @param point2
     * @param point3
     */
    public Triangle(Point point1, Point point2, Point point3){
        super(point1,point2,point3);

    }

    @Override
    public String toString() {
        String points = "";
        for(Point point: vertices){
            points += point.toString() + ' ';
        }
        return super.toString() + " and a triangle with point 3 points: " + points;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Plane plane = new Plane(this.vertices.get(0),this.vertices.get(1),this.vertices.get(2));
        List planeIntersection = plane.findIntersections(ray);
        if(planeIntersection == null){
            return null;
        }
        try{
            Point A = this.vertices.get(0), B= this.vertices.get(1), C= this.vertices.get(2);
            Point p0 = ray.getP0();

            Point Q = (Point)planeIntersection.get(0);

            Vector AB = B.subtract(A);
            Vector AC = C.subtract(A);
            Vector n = AB.crossProduct(AC);
            double areaABC = n.length()/2;

            //calculate triangle ABP
            //Vector AB
            Vector AP = Q.subtract(A);
            n = AB.crossProduct(AP);
            double areaABP = n.length()/2;

            //calculate triangle BCP
            Vector BC = C.subtract(B);
            Vector BP = Q.subtract(B);
            n = BC.crossProduct(BP);
            double areaBCP = n.length()/2;

            //calculate triangle ACP
            Vector CA = A.subtract(C);
            Vector CP = Q.subtract(A);
            n = CA.crossProduct(CP);
            double areaCAP = n.length()/2;


            double u = areaABP/areaABC;
            double v = areaBCP/areaABC;
            double w = areaCAP/areaABC;
            if(u+v+w < 0.99999999999999 || u+v+w > 1.000000000000001){
                return null;
            }
            return planeIntersection;




        }catch(IllegalArgumentException i){
            return null;
        }

    }
}
