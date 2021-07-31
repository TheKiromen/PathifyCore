package com.dkrucze.PathifyCore;

public class CannyEdgeDetection {

    private int[][] sobelX,sobelY,sobelMag,result;
    private boolean[][] candidates;
    private int highThreshold=200, lowThreshold=50;
    private int w,h;

    CannyEdgeDetection(SobelResult sobel){
        sobelX = sobel.getxGradient();
        sobelY = sobel.getyGradient();
        sobelMag = sobel.getMagnitude();
        this.h=sobelMag.length;
        this.w=sobelMag[0].length;
        result=new int[h][w];
        candidates=new boolean[h][w];
    }

    public int[][] detect(){

        //Find all pixels above highThreshold
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                //TODO
                //-Calculate the angle of the pixel
                //-Pick direction based on the angle
                //-Check for local maximum
                //-If above highThreshold set pixel in result to white.
                //-If between two thresholds, mark it as a candidate
                //-If below the lowThreshold set it to black in result
            }
        }

        //Check all edge candidates
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                //TODO
                //-Loop through all candidates
                //-Check if a candidate is next to existing "hard" edge, if yes set pixel in result to white
            }
        }


        return result;
    }

}
