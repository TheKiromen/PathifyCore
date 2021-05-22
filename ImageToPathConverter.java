package com.dkrucze.PathifyCore;

import java.awt.*;
import java.awt.image.BufferedImage;

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
            GaussianBlur gBlur = new GaussianBlur(1.5);
            result.setBlurredImage(gBlur.blur(inputImage));

            //TODO convert the image to greyscale
        }

        return result;
    }

    //Method for copying an image
    private void copyImage(BufferedImage image){
        //Get image dimensions, type and transparency
        imageType=image.getType();
        int h=image.getHeight(), w=image.getWidth();
        boolean alpha = image.getColorModel().hasAlpha();

        //Copy the image
        inputImage=new Color[h][w];
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                inputImage[i][j]=new Color(image.getRGB(j,i),alpha);
            }
        }
    }
}
