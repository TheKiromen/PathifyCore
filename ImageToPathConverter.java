package com.dkrucze.PathifyCore;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

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

            //Blur the initial image
            FastGaussianBlur fgBlur = new FastGaussianBlur(tmp);
            tmp = fgBlur.blur();
            result.setBlurredImage(tmp);

            //Convert the blurred image to grayscale
            tmp = GrayscaleConversion.convert(tmp);
            result.setGreyscaleImage(tmp);

            //Find edges using Sobel edge detection
            SobelEdgeDetection sobel = new SobelEdgeDetection(100);
            result.setSobelEdges(sobel.findEdges(tmp));
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
