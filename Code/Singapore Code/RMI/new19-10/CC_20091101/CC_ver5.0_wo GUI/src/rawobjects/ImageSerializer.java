package rawobjects;

import java.awt.image.BufferedImage;

public class ImageSerializer {
    /**
     * Serialises a BufferedImage
     * @param bi    BufferedImage to be serialise
     * @return  serialised image in integer array
     */
    public static int[] serialiseBufferedImage(BufferedImage bi) {
        int[] serializedMapImage;
        int width = bi.getWidth();
        int height = bi.getHeight();
        serializedMapImage = new int[width * height];
        int[] tmp = bi.getRGB(0, 0, width, height, serializedMapImage, 0, width);
        return tmp;
    }

    /**
     * Convert a serialised image to BufferedImage
     * @param serializedMapImage    Serialised image
     * @param width     Width of the serialised image
     * @param height    Height of the serialised image
     * @return  image as BufferedImage
     */
    public static BufferedImage intArrayToBufferedImage(int[] serializedMapImage, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bi.setRGB(0, 0, width, height, serializedMapImage, 0, width);
        return bi;
    }
}
