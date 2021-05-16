package com.dkrucze.PathifyCore;

import java.awt.*;
import java.awt.image.BufferedImage;

//Facade Class
public class ImageToPathConverter {

    //TODO Find better data structure to hold image?
    private Color[][] inputImage;
    private PathifiedImage result;

    //Specify image to be converted
    public ImageToPathConverter(BufferedImage image){
        //Copy image into an array for easier processing
        copyImage(image);
    }

    //Convert the image
    public PathifiedImage convert(){
        //Convert only once
        if(result==null){
            result=new PathifiedImage();
        }

        return result;
    }

    //Method for copying an image
    private void copyImage(BufferedImage image){
        //Get image dimensions and transparency
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
