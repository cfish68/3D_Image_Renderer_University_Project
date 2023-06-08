package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

/**
 * Class for a point light, a light in every direction, e.g. a light bulb
 */
public class PointLight extends Light implements LightSource{


    protected Point position;

    //attenuation factor
    protected double kC=1,kL=0,kQ=0;

    //Radius and how large the light is affecting soft shadows.
    private double radius = 0;

    /**
     * initializer for Light with param intensity color which sets the intesity
     *
     * @param intensity
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Returns the colour intensity at point p
     * @param p
     * @return
     */
    @Override
    public Color getIntensity(Point p) {
        double distance = this.position.distance(p);
        return this.getIntensity().scale(1/kC + kL * distance + kQ * distance*distance);
        //formula according to slideshow 6, slide 33
    }

    /**
     * getter for direction vector for vector between the source and Point p
     * @param p
     * @return
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
        //return this.position.subtract(p).normalize();
    }

    /**
     * Returns the distancce of the point from the light source
     * @param point
     * @return
     */
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }

    /**
     * Returns the radius of the light source that affects soft shadows
     * @return
     */
    @Override
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the light source that affects soft shadows
     * @param radius
     */
    @Override
    public PointLight setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    /**
     * setter for kC
     * @param kC
     * @return
     */
    public PointLight setKc(double kC){
        this.kC = kC;
        return this;
    }

    /**
     * setter for kL
     * @param kL
     * @return
     */
    public PointLight setKl(double kL){
        this.kL = kL;
        return this;
    }

    /**
     * setter for kC
     * @param kC
     * @return
     */
    public PointLight setKq(double kC){
        this.kQ = kQ;
        return this;
    }
}
