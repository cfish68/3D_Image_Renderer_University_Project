package lighting;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Testing basic shadows
 * @author Dan */
public class SoftShadowsTests {
    private Intersectable sphere     = new Sphere(60d, new Point(0, 0, -200))                                         //
            .setEmission(new Color(BLUE))                                                                                  //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
    private Material      trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);

    private Scene         scene      = new Scene("Test scene").setSoftShadowDef(10);
    private Camera        camera     = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
            .setVPSize(200, 200).setVPDistance(1000)                                                                       //
            .setRayTracer(new RayTracerBasic(scene));

    /** Helper function for the tests in this module */
    void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
        scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7).setRadius(4));
        scene.geometries.setBoudningBoxOn();
        camera.setImageWriter(new ImageWriter(pictName, 400, 400)) //
                .renderImage() //
                .writeToImage();
        scene.geometries.setBoundingBoxOff();
    }

    /** Produce a picture of a sphere and triangle with point light and shade */
    @Test
    public void sphereTriangleInitial() {
        sphereTriangleHelper("shadowSphereTriangleInitial", //
                new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                new Point(-100, -100, 200));
    }

    /**
     * Sphere-Triangle shading - move triangle up-right
     */
    @Test
    public void sphereTriangleMove1() {
        sphereTriangleHelper("SoftShadowSphereTriangleMove2", //
                new Triangle(new Point(-54.93487,-38.07649,15.82), new Point(-35.04967,-48.9013,0), new Point(-60,-60,9.29695)), //
                new Point(-100, -100, 200));
    }

    /**
     * Sphere-Triangle shading - move triangle upper-righter
     */
    @Test
    public void sphereTriangleMove2() {
        sphereTriangleHelper("softShadowSphereTriangleMove1", //
                new Triangle(new Point(-63.62208,-19.51698,20), new Point(-17.88,-51.99654,0), new Point(-66.7836,-60.19368,0)), //
                new Point(-100, -100, 200));
    }

    /** Sphere-Triangle shading - move spot closer */
    @Test
    public void sphereTriangleSpot1() {
        sphereTriangleHelper("softShadowSphereTriangleSpot1", //
                new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                new Point(-100, -100, 125));
    }

    //   /** Sphere-Triangle shading - move spot even more close */
    @Test
    public void sphereTriangleSpot2() {
        sphereTriangleHelper("softShadowSphereTriangleSpot2", //
                new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                new Point(-75,-75,75));
    }

    /** Produce a picture of a two triangles lighted by a spot light with a Sphere
     * producing a shading */
    @Test
    public void trianglesSphere() {
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                new Sphere(30d, new Point(0, 0, -11)) //
                        .setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(700, 400, 400), new Point(40, 40, 300), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-5));

        camera.setImageWriter(new ImageWriter("softShadowTrianglesSphere", 600, 600)) //
                .renderImage() //
                .writeToImage();
    }

    /** Produce a picture of two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow */
    @Test
    public void SoftShadowOwnPicture() {
        Camera camera = new Camera(new Point(-500, -550, 100), new Vector(1, 1, -0.1), new Vector(1, 1, 20)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(YELLOW), 0.15));

        //Pyramid Material and color
        Material PyramidM = new Material().setKd(0).setKs(0);
        Color PyramidC = new Color(250, 96, 12);

        scene.geometries.add( //
                //1 Floor
                new Plane(new Point(0,0,0), new Point(1,0,0), new Point(0,1,0)).setEmission(new Color(1, 50 , 32))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                //2 Background/sky
                new Plane(new Point(10000,0,0), new Point(10000,0,1), new Point(10000,1,0)).setEmission(new Color(2, 167, 222))
                        .setMaterial(new Material()),
                //3 Window on x-axis
                new Triangle(new Point(0,0,-1000), new Point(0,100,120), new Point(0,-100,120)).setEmission(new Color(41, 44, 51))
                        .setMaterial(new Material().setKd(0.02).setKs(0.8).setShininess(50).setKt(Double3.ONE.scale(0.6))),
                //4 Ball
                new Sphere(new Point(70, 0, 30), 30d).setEmission(new Color(77, 30, 1))
                        .setMaterial(new Material().setKd(0.2).setKs(0.01).setShininess(30).setKt(Double3.ONE.scale(0.6))),
                //5 Ball Hat
                new Triangle(new Point(35,0,52), new Point(88,-25,67), new Point(88,25,67))
                        .setEmission(new Color(BLACK))
                        .setMaterial(PyramidM),
                //Pyramid Start ----------
                //6 South Side
                new Triangle(new Point(-50,-80,0), new Point(-50,-40,0), new Point(-30,-60,50))
                        .setEmission(PyramidC)
                        .setMaterial(PyramidM),
                //7 West Side
                new Triangle(new Point(-10,-40,0), new Point(-50,-40,0), new Point(-30,-60,50))
                        .setEmission(PyramidC)
                        .setMaterial(PyramidM),
                //8 North Side
                new Triangle(new Point(-10,-40,0), new Point(-10,-80,0), new Point(-30,-60,50))
                        .setEmission(PyramidC)
                        .setMaterial(PyramidM),
                //9 East Side
                new Triangle(new Point(-10,-80,0), new Point(-50,-80,0), new Point(-30,-60,50))
                        .setEmission(PyramidC)
                        .setMaterial(PyramidM),
                //Pyramid End ------------
                //10 small Ball
                new Sphere(new Point(20, 10, 15), 15d).setEmission(new Color(77, 60, 1))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(Double3.ONE.scale(0.6))));

        //Sun
        scene.lights.add(new DirectionalLight(new Color(255, 249, 194), new Vector(0,1,-0.5)));

        //Closer Point light
        scene.lights.add(new PointLight(new Color(700, 400, 400), new Point(-650, -500, 80)) //
                .setKl(4E-4).setKq(2E-6).setRadius(10));

        //Closer SpotLight
        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(-30,-60, 1000), new Vector(0,0,-1)) //
                .setKl(4E-3).setKq(2E-4).setRadius(5));


        scene.geometries.setBoudningBoxOn();
        ImageWriter imageWriter = new ImageWriter("ownPictureSoftShadows", 1200, 1200);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
        scene.geometries.setBoundingBoxOff();
    }

}
