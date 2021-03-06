package com.dkrucze.PathifyCore;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

/**
 * Facade class to warp up all conversions for easier use
 */
public class ImageToPathConverter {

    /**
     * Java array to store pixels of the image because it
     * provides relatively fast access and modification of elements.
     * Pixels are stored as Integers in RGB format
     */
    private int[][] tmp;
    private int imageType;
    private PathifiedImage result;

    /**
     * Constructor
     * @param image Image to be converted
     */
    public ImageToPathConverter(BufferedImage image){
        if(image.getWidth()<10||image.getHeight()<10){
            throw new IllegalArgumentException("Image too small");
        }
        //Copy image into an array for easier processing
        copyImage(image);
    }

    /**
     * Controls how the image is being converted
     * Converts image only once.
     * @return Instance of PathifiedImage which contains all steps of the conversion
     */
    //Convert the image
    public PathifiedImage convert(){
        //Convert only once
        if(result==null){
            result=new PathifiedImage(imageType, tmp);

            //Convert image to the grayscale
            tmp = GrayscaleConversion.convert(tmp);
            result.setGreyscaleImage(tmp);

            //Blur the image
            GaussianBlur fgBlur = new GaussianBlur(tmp);
            tmp = fgBlur.blur();
            result.setBlurredImage(tmp);

            //Find edges using sobel edge detection
            SobelEdgeDetection sobel = new SobelEdgeDetection(tmp);
            SobelResult sobelRes = sobel.findEdges();
            result.setSobelEdges(sobelRes);

            //Enhance the edges using Canny edge detection
            CannyEdgeDetection canny = new CannyEdgeDetection(sobelRes);
            tmp = canny.detect();
            result.setCannyEdges(tmp);

            //Calculate path based on the edges
            PathCreator pc = new PathCreator(tmp);
            result.setPath(pc.calculatePath());
        }

        return result;
    }


    /**
     * Converts image pixels into RGB value using byte operations.
     * Saves them into a copy of the image.
     * @param image Image to be converted.
     */
    private void copyImage(BufferedImage image){
        //Get image dimensions, type and transparency
        imageType=image.getType();
        int h=image.getHeight(), w=image.getWidth();
        //Get image data in byte format
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        //Copy the image
        tmp=new int[h][w];
        int pxLength = 3,rgb;
        //Loop through image data
        for(int pixel=0,y=0,x=0; pixel+2<pixels.length;pixel+=pxLength){
            rgb=0;
            rgb += -16777216; //jpg has no alpha thus 255 for alpha channel
            rgb += ((int) pixels[pixel] & 255); // blue
            rgb += (((int) pixels[pixel + 1] & 255) << 8); // green
            rgb += (((int) pixels[pixel + 2] & 255) << 16); // red

            tmp[y][x]=rgb;
            x++;
            if(x==w){
                x=0;
                y++;
            }
        }
    }
}
