package com.dkrucze.PathifyCore;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

//Facade Class
public class ImageToPathConverter {

    /**
     * Basic array to store pixels of the image because it
     * provides relatively fast access and modification of elements.
     */
    private Color[][] inputImage;
    private int imageType;
    private PathifiedImage result;

    //Specify image to be converted
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
            result=new PathifiedImage(imageType, inputImage);

            //Blur the image
            FastGaussianBlur fgBlur = new FastGaussianBlur(inputImage);
            result.setBlurredImage(fgBlur.blur());

            //TODO convert the image to greyscale
        }

        return result;
    }

    //Method for copying an image
    private void copyImage(BufferedImage image){
        //Get image dimensions, type and transparency
        imageType=image.getType();
        int h=image.getHeight(), w=image.getWidth();
        //Get image data in byte format
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        //Copy the image
        inputImage=new Color[h][w];
        int pxLength = 3,rgb;
        //Loop through image data
        for(int pixel=0,y=0,x=0; pixel+2<pixels.length;pixel+=pxLength){
            rgb=0;
            rgb += -16777216; //jpg has no alpha thus 255 for alpha channel
            rgb += ((int) pixels[pixel] & 255); // blue
            rgb += (((int) pixels[pixel + 1] & 255) << 8); // green
            rgb += (((int) pixels[pixel + 2] & 255) << 16); // red

            inputImage[y][x]=new Color(rgb);
            x++;
            if(x==w){
                x=0;
                y++;
            }
        }
    }
}
