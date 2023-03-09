package geometries;

public abstract class RadialGeometry implements Geometry {
    protected double radius;
    RadialGeometry(double radius){
        this.radius = radius;
    }
}
