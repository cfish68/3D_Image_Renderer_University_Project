package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    //Camera attributes
    private Point location;
    private Vector to;
    private Vector up;
    private Vector right;

    //View Plane attributes
    private double width;
    private double height;
    private double distance;

    /**
     * Constructor for a Camera object, taking in location and vectors 'up' and 'to'.
     * vectors 'up' and 'to' must be orthogonal. The constructor creats the vector 'right'
     * being perpendicular to 'up' and 'to'.
     * @param location
     * @param up
     * @param to
     */
    public Camera(Point location, Vector to, Vector up){
        if(!isZero(up.dotProduct(to))){
            throw new IllegalArgumentException("ERROR: Camera vectors up and to must be perpendicular");
        }
        this.location = location;
        right = to.crossProduct(up).normalize();
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

    /**
     *A set function for the View Plane size which receives two parameters -
     * width and height (in this order) and returns the camera’s object it self (this)
     * @param width
     * @param height
     * @return
     */
    public Camera setVPSize(double width, double height){
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     *A set function for the View Plane distance from the camera,
     * the function returns the camera’s object (this).
     * @param distance
     * @return
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Constructs a Ray through a pixel for creating the image on the view plane.
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        //move to the 'top left' of the viewPlane
        Point p0 = location.add(to.scale(distance));//p0 is the middle of the viewPlane
        Point topLeftCorner = p0.add(up.scale(height/2)).add(right.scale(-1*(width/2)));//NOTICE!!! this could also be the 'top right corner'. It'll still work just from the opposite direction.
        double pixelheight = height/nX;
        double pixelWidth = width/nY;
        Point centerOfPixel = topLeftCorner.add(right.scale(pixelWidth*(j+0.5))).add(up.scale(-1*pixelheight*(i+0.5)));
        return new Ray(location, centerOfPixel.subtract(location));


    }

}
