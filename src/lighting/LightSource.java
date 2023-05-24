package lighting;


import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

/**
 * public interface which is the light soruce
 */
public interface LightSource {


    /**
     * method to get intensity of the light source
     * @param p
     * @return
     */
    public Color getIntensity(Point p);

    /**
     * method to get the vector of the light source
     * @param p
     * @return
     */
    public Vector getL(Point p);


    /**
     *gets the distance between a point and the light source
     * @param point
     * @return
     */
    //public double getDistance(Double3 point);
}
