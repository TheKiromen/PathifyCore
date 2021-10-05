package com.dkrucze.PathifyCore;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class CannyEdgeDetection {

    private int[][] sobelMag,result;
    private double[][] angles;
    private ArrayList<Point> candidates;
    private int highThreshold=80, lowThreshold=25;
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

                    //Pick direction based on edge angle
                    if(angle < 22.5 || angle > 157.5){
                        //Horizontal ---
                        if((sobelMag[y][x-1]&255)>currentVal || (sobelMag[y][x+1]&255)>currentVal)
                            continue;
                    }else if(angle >= 22.5 && angle <= 67.5){
                        //Right diagonal /
                        if((sobelMag[y-1][x+1]&255)>currentVal || (sobelMag[y+1][x-1]&255)>currentVal)
                            continue;
                    }else if(angle > 67.5 && angle < 112.5){
                        //Vertical |
                        if((sobelMag[y-1][x]&255)>currentVal || (sobelMag[y+1][x]&255)>currentVal)
                            continue;
                    }else{//Angle between 112.5 and 157.5
                        //Left diagonal \
                        if((sobelMag[y-1][x-1]&255)>currentVal || (sobelMag[y+1][x+1]&255)>currentVal)
                            continue;
                    }

                    //Set pixel as edge or edge candidate based on its value
                    int neighbours = numOfNeighbours(x,y);
                    if(currentVal>highThreshold){
                        if(neighbours<3){
                            result[y][x]=white;
                        }
                    }else{
                        if(neighbours > 0 && neighbours < 3){
                            result[y][x]=white;
                        }else{
                            candidates.add(new Point(x,y));
                        }
                    }
                }
            }
        }

        
        //Complete the edges using hysteresis
        int counter;
        ArrayList<Point> tmp;
        do{
            counter=0;
            tmp = new ArrayList<>();
            //Loop through all the candidates for edge
            for(Point c : candidates){
                //If the candidate has between 1 and 2 adjacent edge pixels, mark it as edge
                if(numOfNeighbours(c.x,c.y)<3 && numOfNeighbours(c.x,c.y)>0){
                    result[c.y][c.x]=white;
                    tmp.add(c);
                    counter++;
                }
            }
            //Remove all candidates changed to edges
            candidates.removeAll(tmp);
        //Repeat until no more candidates can be converted into edges
        }while(counter>0);

        return result;
    }


    private int numOfNeighbours(int x, int y){
        int counter=0,white=Color.WHITE.getRGB();
        //Check area around given point
        for(int yOffset=-1;yOffset<=1;yOffset++){
            for(int xOffset=-1;xOffset<=1;xOffset++){
                //If there is strong edge next to our point, increase the counter
                if(result[y+yOffset][x+xOffset]==white)
                    counter++;
            }
        }
        return counter;
    }

}
