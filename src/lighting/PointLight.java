package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class for a point light, a light in every direction, e.g. a light bulb
 */
public class PointLight extends Light implements LightSource{


    private Point position;

    //attenuation factor
    private double kC=1,kL=0,kQ=0;

    /**
     * initializer for Light with param intensity color which sets the intesity
     *
     * @param intensity
     */
    protected PointLight(Color intensity, Point position) {
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
        return this.position.subtract(p);
    }

    /**
     * setter for kC
     * @param kC
     * @return
     */

    public PointLight setKC(double kC){
        this.kC = kC;
        return this;
    }

    /**
     * setter for kL
     * @param kL
     * @return
     */
    public PointLight setKL(double kL){
        this.kL = kL;
        return this;
    }

    /**
     * setter for kC
     * @param kC
     * @return
     */
    public PointLight setKQ(double kC){
        this.kQ = kQ;
        return this;
    }
}
