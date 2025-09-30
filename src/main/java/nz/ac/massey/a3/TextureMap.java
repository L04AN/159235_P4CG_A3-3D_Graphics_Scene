package nz.ac.massey.a3;

/*
    Class to implement a texture mapping procedure
 */

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class TextureMap {

    /*
    Sample the texture image at the given texel values (u,v)

    (u, v) are NORMALIZED, i.e. 0<=u<=1, 0<=v<=1

    Complete the implementation of pickColour().

    Here it just returns a default colour

     */

    //public Color pickColour(double u, double v) { return Color.BLACK;}

    private BufferedImage texture;

    public TextureMap(String filename) {
        try {
            texture = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.err.println("Could not load texture: " + filename);
            texture = null;
        }
    }

    public Color pickColour(double u, double v) {
        if (texture == null) return Color.BLACK;

        // Clamp and convert to pixel coordinates
        u = Math.max(0, Math.min(1, u));
        v = Math.max(0, Math.min(1, v));

        int x = (int)(u * (texture.getWidth() - 1));
        int y = (int)(v * (texture.getHeight() - 1));

        int rgb = texture.getRGB(x, y);
        return new Color(rgb);
    }

}
