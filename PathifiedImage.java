package com.dkrucze.PathifyCore;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Holds all the steps of conversion.
 * Provides final result of the conversion as well as the results of the intermediate steps.
 */
public class PathifiedImage {

    private int[][] initialImage,blurredImage,grayscaleImage,canny;
    private int imageType;
    private SobelResult edges;
    private LinkedList<Point> path;

    public PathifiedImage(int imageType,int[][] initialImage){
        this.imageType=imageType;
        this.initialImage=initialImage;
    }

    public void setBlurredImage(int[][] blurredImage) {
        this.blurredImage = blurredImage;
    }
    public void setGreyscaleImage(int[][] grayscaleImage){
        this.grayscaleImage=grayscaleImage;
    }
    public void setSobelEdges(SobelResult edges){
        this.edges=edges;
    }
    public void setCannyEdges(int[][] canny){
        this.canny=canny;
    }
    public void setPath(LinkedList<Point> path){
        this.path=path;
    }

    public BufferedImage getInitialImage(){
        return getBufferedImage(initialImage);
    }
    public BufferedImage getBlurredImage(){
        return getBufferedImage(blurredImage);
    }
    public BufferedImage getGreyscaleImage(){
        return getBufferedImage(grayscaleImage);
    }
    public BufferedImage getSobelXGradient(){
        return getBufferedImage(initialImage);
    }
    public BufferedImage getSobelYGradient(){
        return getBufferedImage(initialImage);
    }
    public BufferedImage getSobelAngles() {
        return getBufferedImage(initialImage);
    }
    public BufferedImage getSobelMagnitude(){
        return getBufferedImage(edges.getMagnitude());
    }
    public BufferedImage getCannyEdges(){
        return getBufferedImage(canny);
    }
    public LinkedList<Point> getPath(){
        return path;
    }

    public BufferedImage visualizePath(int[][] path){
        return getBufferedImage(path);
    }

    /**
     * Converts array of pixels stored in RGB format into ready to save file.
     * @param pixels Pixels to be converted back into image
     * @return Image object
     */
    private BufferedImage getBufferedImage(int[][] pixels) {
        BufferedImage tmp = new BufferedImage(pixels[0].length,pixels.length,imageType);
        for(int i=0; i< pixels.length;i++){
            for(int j=0; j<pixels[0].length;j++){
                tmp.setRGB(j,i,pixels[i][j]);
            }
        }
        return tmp;
    }
}
