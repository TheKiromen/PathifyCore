package com.dkrucze.PathifyCore;

import java.awt.*;
import java.util.ArrayList;

public class CannyEdgeDetection {

    private int[][] sobelMag,result;
    private double[][] angles;
    private ArrayList<Point> candidates;
    private int highThreshold=200, lowThreshold=50;
    private int w,h;

    CannyEdgeDetection(SobelResult sobel){
        sobelMag = sobel.getMagnitude();
        angles = sobel.getAngles();
        candidates = new ArrayList();
        this.h=sobelMag.length;
        this.w=sobelMag[0].length;
        result=new int[h][w];
        //Fill the result with black pixels;
        int black = Color.BLACK.getRGB();
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                result[y][x]=black;
            }
        }
    }

    public int[][] detect(){
        int white = Color.WHITE.getRGB();
        double angle;
        int currentVal;

        for(int y=1;y<h-1;y++){
            for(int x=1;x<w-1;x++){
                    currentVal=sobelMag[y][x]&255;

                if(currentVal>lowThreshold){
                    angle=angles[y][x];

                    //FIXME Another implementation of non-maximum suppression?
                    //Check only in 3x3 area
                    //If there are pixels with bigger value in edge direction skip current iteration
                    //If there are no pixels with bigger value we have local maximum
                    //Check its value against thresholds and mark accordingly
                    //Pick direction based on edge angle
                    if(angle < 22.5 || angle > 157.5){
                        //Horizontal ---
//
                    }else if(angle >= 22.5 && angle <= 67.5){
                        //Right diagonal /

                    }else if(angle > 67.5 && angle < 112.5){
                        //Vertical |

                    }else{//Angle between 112.5 and 157.5
                        //Left diagonal \

                    }

                    //Set pixel as edge or edge candidate based on its value
                    if(currentVal>highThreshold){
                        result[y][x]=white;
                    }else{
                        candidates.add(new Point(x,y));
                    }
                }
            }
        }

        //TODO
        //Deal with two pixel wide edges (caused by two local max's with same values?)
        //Figure out good threshold values.

        //Check all edge candidates
        for(int y=1;y<h-1;y++){
            for(int x=1;x<w-1;x++){
                //TODO
                //-Loop through all candidates
                //-Check if a candidate is next to existing "hard" edge, if yes set pixel in result to white
            }
        }


        return result;
    }

}
