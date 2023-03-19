package geometries;

import primitives.Point;

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
        vertices.add(point1);
        vertices.add(point2);
        vertices.add(point3);

    }
    @Override
    public String toString() {
        String points = "";
        for(Point point: vertices){
            points += point.toString() + ' ';
        }
        return super.toString() + " and a triangle with point 3 points: " + points;
    }
}
