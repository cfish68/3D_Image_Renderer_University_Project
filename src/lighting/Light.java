package lighting;

import primitives.Color;

/**
 * public abtract class light has an attribute intensity of type Color (private) and protected constructor
 */
public abstract class Light {
    private Color intensity;


    /**
     * initializer for Light with param intensity color which sets the intesity
     * @param intensity
     */
    protected Light(Color intensity){
        this.intensity = intensity;
    }

    /**
     * method to get intensity color.
     * @return
     */
    public Color getIntensity() {
        return intensity;
    }
}
