/**
 * 
 */
package lighting;

import static java.awt.Color.*;

import geometries.Plane;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   private Scene scene = new Scene("Test scene");

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheres() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(150, 150).setVPDistance(1000);

      scene.geometries.add( //
                           new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(Double3.ONE.scale(0.3))),
                           new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add( //
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                          .setKl(0.0004).setKq(0.0000006));
      scene.geometries.setBoudningBoxOn();
      camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
      scene.geometries.setBoundingBoxOff();
   }

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheresOnMirrors() {
      Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(2500, 2500).setVPDistance(10000); //

      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

      scene.geometries.add( //
                           new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                 .setKt(new Double3(0.5, 0, 0))),
                           new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKr(Double3.ONE)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000)) //
                              .setEmission(new Color(20, 20, 20)) //
                              .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
         .setKl(0.00001).setKq(0.000005));

      ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(200, 200).setVPDistance(1000);

      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      scene.geometries.add( //
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                           new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(Double3.ONE.scale(0.6))));

      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
         .setKl(4E-5).setKq(2E-7));

      ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void ownPicture() {
      Camera camera = new Camera(new Point(-1000, -1100, 100), new Vector(1, 1, -0.1), new Vector(1, 1, 20)) //
              .setVPSize(200, 200).setVPDistance(1000);

      scene.setAmbientLight(new AmbientLight(new Color(YELLOW), 0.15));

      scene.geometries.add( //
              //Floor
              new Plane(new Point(0,0,0), new Point(1,0,0), new Point(0,1,0)).setEmission(new Color(1, 50 , 32))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
              //Background/sky
              new Plane(new Point(10000,0,0), new Point(10000,0,1), new Point(10000,1,0)).setEmission(new Color(2, 167, 222))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(10)),
              //Window
              new Triangle(new Point(0,0,-1000), new Point(0,100,120), new Point(0,-100,120)).setEmission(new Color(41, 44, 51))
                      .setMaterial(new Material().setKd(0.02).setKs(0.8).setShininess(50).setKt(Double3.ONE.scale(0.6))),
              //Ball
              new Sphere(new Point(70, 0, 30), 30d).setEmission(new Color(48, 15, 15))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(Double3.ONE.scale(0.6))),
              //Streetlight
              new Sphere(new Point(-12,-90,110), 10d).setEmission(new Color(YELLOW))
                      .setMaterial(new Material().setKd(0.2).setKs(0.3).setShininess(30).setKt(Double3.ONE.scale(0.99))),
              //small Ball
              new Sphere(new Point(20, 10, 15), 15d).setEmission(new Color(77, 60, 1))
                      .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(Double3.ONE.scale(0.6))));

      //Sun
      scene.lights.add(new DirectionalLight(new Color(YELLOW), new Vector(1,1,-1)));

      //Streetlight
      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(-12,-90,110), new Vector(0, 0, -1)) //
              .setKl(4E-15).setKq(2E-20));



      ImageWriter imageWriter = new ImageWriter("ownPicturePlaneWindowSphere", 600, 600);
      camera.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene)) //
              .renderImage() //
              .writeToImage();
   }

}
