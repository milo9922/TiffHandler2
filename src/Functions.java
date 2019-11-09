import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

class Functions {


    void tiffToJpg(String path) throws IOException {
        // open tiff file
        File tiffFile = new File(path);
        BufferedImage image = ImageIO.read(tiffFile);

        // convert tiff to jpg (create new jpg file)
        ImageIO.write(image, "jpg", new File("output.jpg"));
    }

    void tiffToPng(String path) throws IOException {
        // open tiff file
        File tiffFile = new File(path);
        BufferedImage image = ImageIO.read(tiffFile);

        // convert tiff to jpg (create new png file)
        ImageIO.write(image, "png", new File("output.png"));
    }

    int getWidth(String path) throws IOException {
        // get width
        File tiffFile = new File(path);
        BufferedImage img = ImageIO.read(tiffFile);
        return img.getWidth();
    }

    int getHeight(String path) throws IOException {
        // get height
        File tiffFile = new File(path);
        BufferedImage img = ImageIO.read(tiffFile);
        return img.getHeight();
    }


    LinkedList<Integer> colorReader(String path) throws IOException {
        //open tiff file
        File originalImage = new File(path);
        BufferedImage img = ImageIO.read(originalImage);

        int posX1 = 1000000;
        int posX2 = 0;
        int posY1 = 1000000;
        int posY2 = 0;

        // check image colors (pixel by pixel)
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {

                //get RGB value of single pixel
                Color c = new Color(img.getRGB(j, i));
                int r = c.getRed();

//                  pixels are black or white so if pixel rgb red value = 0 then it's black
//                  then
//                  update position

                if (r == 0) {
                    if (j < posX1) {
                        posX1 = j;
                    }
                    if (j > posX2) {
                        posX2 = j;
                    }
                    if (i < posY1) {
                        posY1 = i;
                    }
                    if (i > posY2) {
                        posY2 = i;
                    }
                }
            }

        }

        // create and return list of positions
        LinkedList<Integer> measurementResultPx = new LinkedList<>();
        measurementResultPx.add(posX2 - posX1);
        measurementResultPx.add(posY2 - posY1);
        return measurementResultPx;
    }


}