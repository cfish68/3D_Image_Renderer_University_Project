package elements;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    private Color intensity;

    public AmbientLight(Color color, Double3 double3){
        intensity = color.scale(double3);
    }
    public AmbientLight(){
        intensity = Color.BLACK;
    }

    public Color getIntensity() {
        return intensity;
    }
}