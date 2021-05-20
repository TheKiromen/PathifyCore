package com.dkrucze.PathifyCore;


import java.awt.*;
import java.awt.image.BufferedImage;

public class PathifiedImage {

    private Color[][] initialImage,blurredImage;
    private int imageType;

    public PathifiedImage(int imageType,Color[][] initialImage){
        this.imageType=imageType;
        this.initialImage=initialImage;
    }

    public void setBlurredImage(Color[][] blurredImage) {
        this.blurredImage = blurredImage;
    }

    public BufferedImage getBlurredImage(){
        return getBufferedImage(blurredImage);
    }

    public BufferedImage getInitialImage(){
        return getBufferedImage(initialImage);
    }

    private BufferedImage getBufferedImage(Color[][] pixels) {
        BufferedImage tmp = new BufferedImage(pixels[0].length,pixels.length,imageType);
        for(int i=0; i< pixels.length;i++){
            for(int j=0; j<pixels[0].length;j++){
                tmp.setRGB(j,i,pixels[i][j].getRGB());
            }
        }
        return tmp;
    }
}
