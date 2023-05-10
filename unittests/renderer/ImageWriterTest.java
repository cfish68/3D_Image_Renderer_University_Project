package renderer;
import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Class for testing ImageWriter
 */
public class ImageWriterTest {

    /**
     * Test method for {@link renderer.ImageWriter#ImageWriter(String, int, int)}
     */
    @Test
    void writeImageTest(){
    ImageWriter writer;
    try {
        writer = new ImageWriter("first_image", 500, 800);

        Color color = new Color(255,255,0);
        for(int i = 0; i< 500; i++){
            for(int j = 0; j < 800; j++){
                writer.writePixel(i,j, color);
            }
        }
        Color orange = new Color(255,165,0);
        for(int i = 0; i<500; i+=50)
            for(int j = 0; j < 800; j++){
                writer.writePixel(i,j, orange);
            }
        for(int j = 0; j<800; j+=50)
            for(int i = 0; i < 500; i++){
                writer.writePixel(i,j, orange);
            }
        writer.writeToImage();

    }catch (IllegalArgumentException e) {
            fail("Failed constructing first image");
    }


}
}
