package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Directional light class which has light from one direction from an "infinitely" far source
 * e.g. the sun.
 */
public class DirectionalLight extends Light implements LightSource{

    private Vector direction;
    /**
     * initializer for Light with param intensity color which sets the intensity
     * @param intensity
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    /**
     * Returns the intensity at the point p, which is the same as intensity at the "source"
     * as Directional light doesn't get weaker over distance
     * @param p
     * @return
     */
    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    /**
     * returns the direction L of the directional light
     * @param p
     * @return
     */
    @Override
    public Vector getL(Point p) {
        return this.direction;
    }
}
