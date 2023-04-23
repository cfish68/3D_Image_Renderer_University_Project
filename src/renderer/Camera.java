package renderer;

import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    private Point location;
    private Vector to;
    private Vector up;
    private Vector right;

    Camera(Point location, Vector up, Vector to){
        if(!isZero(up.dotProduct(to))){
            throw new IllegalArgumentException("ERROR: Camera vectors up and to must be perpendicular");
        }
        right = up.crossProduct(to).normalize();
        this.to = to.normalize();
        this.up = up.normalize();

    }

    /**
     * getter for location
     * @return
     */
    public Point getLocation() {
        return location;
    }

    /**
     * getter for vector to
     * @return
     */
    public Vector getTo() {
        return to;
    }

    /**
     * getter for vector up
     * @return
     */
    public Vector getUp() {
        return up;
    }

    /**
     * getter for vector right
     * @return
     */
    public Vector getRight() {
        return right;
    }



}
