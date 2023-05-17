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

    @Override
    public Color getIntensity(Point p) {
        return null;
    }

    /**
     * getter for l
     * @param p
     * @return
     */
    @Override
    public Vector getL(Point p) {
        return null;
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
