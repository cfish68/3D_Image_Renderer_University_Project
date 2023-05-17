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
     * initializer for Light with param intensity color which sets the intesity
     *
     * @param intensity
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p) {
        return this.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return this.direction;
    }
}
