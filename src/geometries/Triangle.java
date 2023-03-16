package geometries;

import primitives.Point;

/**
 * Triangle class. A triangle has 3 corners and is a polygon, so it inherits from polygon.
 */
public class Triangle extends Polygon{

    /**
     * constructor for a triangle takes in 3 points and saves them to a list of vertices.
     * @param point1
     * @param point2
     * @param point3
     */
    Triangle(Point point1, Point point2, Point point3){
        vertices.add(point1);
        vertices.add(point2);
        vertices.add(point3);

    }
}
