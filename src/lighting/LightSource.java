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
    public double getDistance(Point point);

    /**
     * Returns the radius of the lightsource
     * Radius and how large the light is affecting soft shadows.
     * @return
     */
    public double getRadius();

    /**
     * Sets the radius of the lightsource.
     * Radius and how large the light is affecting soft shadows.
     * @param radius
     */
    public LightSource setRadius(double radius);
}
