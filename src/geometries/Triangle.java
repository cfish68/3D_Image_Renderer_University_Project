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
    Triangle(Point point1, Point point2, Point point3){
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
            Point p0 = ray.getP0();
            Vector v1 = this.vertices.get(0).subtract(p0);
            Vector v2 = this.vertices.get(1).subtract(p0);
            Vector v3 = this.vertices.get(2).subtract(p0);
            Vector vNormal = ray.getDir().normalize();
            if(vNormal.dotProduct(v1.normalize()) > 0 && vNormal.dotProduct(v2.normalize()) > 0 && vNormal.dotProduct(v3.normalize()) > 0 || vNormal.dotProduct(v1.normalize()) < 0 && vNormal.dotProduct(v2.normalize()) < 0 && vNormal.dotProduct(v3.normalize()) < 0 )
                return planeIntersection;
        }catch(IllegalArgumentException i){
            return null;
        }
        return null;
    }
}
