package com.dkrucze.PathifyCore;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Holds all the steps of conversion.
 * Provides final result of the conversion as well as the results of the intermediate steps.
 */
public class PathifiedImage {

    private int[][] initialImage,grayscaleImage,blurredImage,xGradient,yGradient,canny;
    private SobelResult edges;
    private LinkedList<Point> path;

    public PathifiedImage(int[][] initialImage){
        this.initialImage=initialImage;
    }

    public void setGreyscaleImage(int[][] grayscaleImage){
        this.grayscaleImage=grayscaleImage;
    }
    public void setBlurredImage(int[][] blurredImage) {
        this.blurredImage = blurredImage;
    }
    public void setSobelEdges(SobelResult edges){
        this.edges=edges;
        int w=initialImage[0].length,h=initialImage.length;
        xGradient = new int[h][w];
        yGradient = new int[h][w];

        //Get maximum and minimum vales of gradients
        int xMax,xMin,yMax,yMin,tmp,current,black;
        double angle;
        black=Color.BLACK.getRGB();
        xMax=Arrays.stream(edges.getxGradient()).flatMapToInt(Arrays::stream).max().getAsInt();
        xMin=Math.abs(Arrays.stream(edges.getxGradient()).flatMapToInt(Arrays::stream).min().getAsInt());
        yMax=Arrays.stream(edges.getyGradient()).flatMapToInt(Arrays::stream).max().getAsInt();
        yMin=Math.abs(Arrays.stream(edges.getyGradient()).flatMapToInt(Arrays::stream).min().getAsInt());

        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                //Map the gradients to 0-255
                //255.0 forces program to operate on floating point numbers
                //255 would just result in operations on integers, which would return 0.
                //x gradient
                current = edges.getxGradient()[y][x];
                tmp = (int)(255.0*(current+xMin)/(xMin+xMax));
                xGradient[y][x]=(tmp << 16 | tmp << 8 | tmp)-16777216;
                //y gradient
                current = edges.getyGradient()[y][x];
                tmp = (int)(255.0*(current+yMin)/(yMin+yMax));
                yGradient[y][x]=(tmp << 16 | tmp << 8 | tmp)-16777216;;
            }
        }
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
        return getBufferedImage(xGradient);
    }
    public BufferedImage getSobelYGradient(){
        return getBufferedImage(yGradient);
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


    /**
     * Converts array of pixels stored in RGB format into ready to save file.
     * @param pixels Pixels to be converted back into image
     * @return Image object
     */
    private BufferedImage getBufferedImage(int[][] pixels) {
        BufferedImage tmp = new BufferedImage(pixels[0].length,pixels.length,5);
        for(int i=0; i< pixels.length;i++){
            for(int j=0; j<pixels[0].length;j++){
                tmp.setRGB(j,i,pixels[i][j]);
            }
        }
        return tmp;
    }
}
