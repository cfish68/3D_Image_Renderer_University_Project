package lighting;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Final Picture Test Class to produce picture of a room with a desk and lamp
 * 4000*4000*3000 room, 1000 pixels represents 1m
 * Back left floor corner of room is (0,0,0)
 * Camera located at rightish back corner at "head" height
 */
public class FinalPictureTest {

    private Scene scene      = new Scene("Test scene").setSoftShadowDef(10)
            .setAmbientLight(new AmbientLight(new Color(YELLOW), 0.15));

    Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 1, 0), new Vector(1, 0, 1)) //
            .setVPSize(200, 200).setVPDistance(1000);

    Color wallColor = new Color(242, 222, 162);
    Material wallMaterial = new Material().setKd(2);

    /**
     * Rectangle:
     * 2--3
     * |  |
     * 1--4
     * Creates rectangle out of two triangles, triangle 1 = (1,2,4), triangle 2 = (2,3,4)
     * @param corner1
     * @param corner2
     * @param corner3
     * @param corner4
     */
    public void AddRectangle(Point corner1, Point corner2, Point corner3, Point corner4, Color color, Material material) {
        scene.geometries.add(
                new Triangle(corner1, corner2, corner4).setEmission(color).setMaterial(material),
                new Triangle(corner2, corner3, corner4).setEmission(color).setMaterial(material));
    }

    /** Produce a picture of two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow */
    @Test
    public void finalPicture() {

        //----------------Room-----------------
        //floor
        AddRectangle(new Point(0,0,0), new Point(0,4000,0), new Point(4000,4000,0), new Point(4000,0,0),
                new Color(64, 47, 29), new Material().setKd(2));
        //South Wall
        AddRectangle(new Point(0,0,0), new Point(0,0,3000), new Point(4000,0,3000), new Point(4000,0,0),
                wallColor, wallMaterial);
        //West Wall
        AddRectangle(new Point(0,0,0), new Point(0,0,3000), new Point(0,4000,3000), new Point(0,4000,0),
                wallColor, wallMaterial);
        //North Wall
        AddRectangle(new Point(0,4000,0), new Point(0,4000,3000), new Point(4000,4000,3000), new Point(4000,4000,0),
                wallColor, wallMaterial);
        //Ceiling
        AddRectangle(new Point(0,0,3000), new Point(0,4000,3000), new Point(4000,4000,3000), new Point(4000,0,3000),
                wallColor, wallMaterial);
        //East Wall with window Start ---
        //Bottom
        AddRectangle(new Point(4000,0,0), new Point(4000,0,1200), new Point(4000,4000,1200), new Point(4000,4000,0),
                wallColor, wallMaterial);
        //Top
        AddRectangle(new Point(4000,0,2000), new Point(4000,0,3000), new Point(4000,4000,3000), new Point(4000,4000,2000),
                wallColor, wallMaterial);
        //Close side
        AddRectangle(new Point(4000,0,0), new Point(4000,0,3000), new Point(4000,1700,3000), new Point(4000,1700,0),
                wallColor, wallMaterial);
        //Far side
        AddRectangle(new Point(4000,4000,0), new Point(4000,4000,3000), new Point(4000,2300,3000), new Point(4000,2300,0),
                wallColor, wallMaterial);


        //------------- End Room -------------

        //Sun
        scene.lights.add(new DirectionalLight(new Color(255, 249, 194), new Vector(-1,0.5,-0.5)));

        //Closer Point light
        scene.lights.add(new PointLight(new Color(700, 400, 400), new Point(-650, -500, 80)) //
                .setKl(4E-4).setKq(2E-6).setRadius(10));

        //Closer SpotLight
        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(-30,-60, 1000), new Vector(0,0,-1)) //
                .setKl(4E-3).setKq(2E-4).setRadius(5));


        scene.geometries.setBoudningBoxOn();
        ImageWriter imageWriter = new ImageWriter("finalPicture", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

}