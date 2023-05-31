package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

/**
 * Class for the Ambient Light.
 */
public class AmbientLight extends Light{

    /**
     * Constructor for AmbientLight, receives a Color intensity and
     * a Double3 for the attenuation factor and makes the Ambient Light for that
     * @param intensity
     * @param attenuationFactor
     */
    public AmbientLight(Color intensity, double attenuationFactor){
        super(intensity.scale(attenuationFactor));
    }

    /**
     * Empty constructor that sets the intensity to black
     */
    public AmbientLight(){
        super(Color.BLACK);
    }

}
