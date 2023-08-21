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

    private Scene scene      = new Scene("Test scene").setSoftShadowDef(6)
            .setAmbientLight(new AmbientLight(new Color(YELLOW), 0.01));

    Camera camera = new Camera(new Point(3000, 550, 1800), new Vector(-1, 1, -0.5), new Vector(-1, 1, 4)) //
            .setVPSize(800, 800).setVPDistance(1000);


    Color wallColor = new Color(242, 222, 162);
    Material wallMaterial = new Material().setKd(0.1).setKs(0.8).setShininess(100);

    /**
     * Rectangle:
     * 2---3
     * | \ |
     * 1---4
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
                new Color(64, 47, 29), new Material().setKd(0.2).setKs(0.9).setShininess(100));
        //South Wall
        AddRectangle(new Point(0,0,0), new Point(0,0,3000), new Point(4000,0,3000), new Point(4000,0,0),
                wallColor, wallMaterial);
        //West Wall
        AddRectangle(new Point(0,0,0), new Point(0,0,3000), new Point(0,4000,3000), new Point(0,4000,0),
                wallColor, wallMaterial);
        //North Wall
        AddRectangle(new Point(0,4000,0), new Point(0,4000,3000), new Point(4000,4000,3000), new Point(4000,4000,0),
                new Color(0, 128, 128), wallMaterial);
        //Ceiling
        AddRectangle(new Point(0,0,3000), new Point(0,4000,3000), new Point(4000,4000,3000), new Point(4000,0,3000),
                wallColor, wallMaterial);

        //-------------- East Wall with window Start -----------------
        //Bottom
        AddRectangle(new Point(4000,0,0), new Point(4000,0,1000), new Point(4000,4000,1000), new Point(4000,4000,0),
                wallColor, wallMaterial);
        //Top
        AddRectangle(new Point(4000,0,2400), new Point(4000,0,3000), new Point(4000,4000,3000), new Point(4000,4000,2400),
                wallColor, wallMaterial);
        //Close side
        AddRectangle(new Point(4000,0,0), new Point(4000,0,3000), new Point(4000,1500,3000), new Point(4000,1500,0),
                wallColor, wallMaterial);
        //Far side
        AddRectangle(new Point(4000,4000,0), new Point(4000,4000,3000), new Point(4000,2500,3000), new Point(4000,2500,0),
                wallColor, wallMaterial);

        //Window horizontal beam
        AddRectangle(new Point(4000,0,1680), new Point(4000,0,1720), new Point(4000,4000,1720), new Point(4000,4000,1680),
                wallColor, wallMaterial);
        //Window Vertical beam
        AddRectangle(new Point(4000,1980,0), new Point(4000,1980,3000), new Point(4000,2020,3000), new Point(4000,2020,0),
                wallColor, wallMaterial);

        //End Window

        //------------- End Room -------------

        //Sun
        scene.lights.add(new DirectionalLight(new Color(255, 249, 194), new Vector(-4,1,-1.3)));

        //ceiling light
        scene.lights.add(new PointLight(new Color(191, 170, 128), new Point(100, 3900, 100)) //
                .setKl(3E-15).setKq(2E-30).setRadius(10));
//
//        //Closer SpotLight
//        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(-30,-60, 1000), new Vector(0,0,-1)) //
//                .setKl(4E-3).setKq(2E-4).setRadius(5));


        scene.geometries.setBoudningBoxOn();
        ImageWriter imageWriter = new ImageWriter("finalPicture", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
        scene.geometries.setBoundingBoxOff();
    }

}