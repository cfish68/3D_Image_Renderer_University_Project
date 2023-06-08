package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Spotlight class which models point light source with direction.
 */
public class SpotLight extends PointLight{
    private Vector direction;
    /**
     * initializer for Light with param intensity color which sets the intensity
     * @param intensity
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    /**
     * Returns the colour intensity at point p
     * @param p
     * @return
     */
    @Override
    public Color getIntensity(Point p) {
        double distance = this.position.distance(p);
        return this.getIntensity().scale(Math.max(0, direction.normalize().dotProduct(super.getL(p))))
                .scale(1/(kC + kL * distance + kQ * distance*distance));
        //formula according to slideshow 6, slide 35
    }


}
