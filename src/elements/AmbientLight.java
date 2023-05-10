package elements;

import primitives.Color;
import primitives.Double3;

/**
 * Class for the Ambient Light.
 */
public class AmbientLight {
    private Color intensity;
    //static const NONE (AmbientLight type) initialized to Color.BLACK and Double3.ZERO;

    /**
     * Constructor for AmbientLight, receives a Color intensity and
     * a Double3 for the attenuation factor and makes the Ambient Light for that
     * @param intensity
     * @param attenuationFactor
     */
    public AmbientLight(Color intensity, Double3 attenuationFactor){
        this.intensity = intensity.scale(attenuationFactor);
    }

    /**
     * Empty constructor that sets the intensity to black
     */
    public AmbientLight(){
        intensity = Color.BLACK;
    }

    /**
     * Returns the value of the intensity (the color)
     * of the ambient light
     * @return
     */
    public Color getIntensity() {
        return intensity;
    }
}
