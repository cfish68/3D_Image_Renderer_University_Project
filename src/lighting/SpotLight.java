package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Spotlight class which works like a stage spotlight
 */
public class SpotLight extends PointLight{
    private Vector direction;
    /**
     * initializer for Light with param intensity color which sets the intesity
     *
     * @param intensity
     */
    protected SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }
}
