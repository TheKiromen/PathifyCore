package com.dkrucze.PathifyCore;

import java.awt.*;
import java.util.Arrays;

public class SobelEdgeDetection {

    private int h,w;
    private int[][] xGradient,yGradient,magnitude,input;
    private double[][] angles;

    public SobelEdgeDetection(int[][] input){
        this.input=input;
        this.h=input.length;
        this.w=input[0].length;
    }

    public SobelResult findEdges(){
        int gx,gy,mag;
        double angle;
        xGradient=new int[h][w];
        yGradient=new int[h][w];
        magnitude=new int[h][w];
        angles=new double[h][w];

        //Loop through the image and calculate the derivatives and magnitude
        for(int y=1;y<h-1;y++){
            for(int x=1;x<w-1;x++){
                //Calculate x derivative
                gx=(input[y-1][x-1]&255)+(input[y][x-1]&255)*2+(input[y+1][x-1]&255)-(input[y-1][x+1]&255)-(input[y][x+1]&255)*2-(input[y+1][x+1]&255);
                xGradient[y][x]=gx;

                //Calculate y derivative
                gy=(input[y+1][x-1]&255)+(input[y+1][x]&255)*2+(input[y+1][x+1]&255)-(input[y-1][x-1]&255)-(input[y-1][x]&255)*2-(input[y-1][x+1]&255);
                yGradient[y][x]=gy;

                //Calculate magnitude
                mag=(int)Math.sqrt(gx*gx+gy*gy);
                magnitude[y][x]=mag;

                //Calculate the angle
                //atan2() returns values ranging from -PI to PI
                angle=Math.atan2(gy,gx);
                //Adding PI (180 degrees) if angle is negative to change the range to 0-PI
                //This will reduce if statements usage in Canny.
                if(angle<0){
                    angle+=Math.PI;
                }
                //Converting to degrees for convenience.
                angle=Math.toDegrees(angle);
                angles[y][x]=angle;

            }
        }

        //Mapping magnitude to avoid very thick edges
        mapMagnitude();

        //setting the edges to black to simplify future processing
        int black = Color.BLACK.getRGB();
        //Horizontal edges
        for(int x=0;x<w;x++){
            magnitude[0][x]=black;
            xGradient[0][x]=0;
            yGradient[0][x]=0;
            angles[0][x]=0;
            magnitude[h-1][x]=black;
            xGradient[h-1][x]=0;
            yGradient[h-1][x]=0;
            angles[h-1][x]=0;
        }
        //Vertical edges
        for(int y=0;y<h;y++){
            magnitude[y][0]=black;
            xGradient[y][0]=0;
            yGradient[y][0]=0;
            angles[y][0]=0;
            magnitude[y][w-1]=black;
            xGradient[y][w-1]=0;
            yGradient[y][w-1]=0;
            angles[y][w-1]=0;
        }

        return new SobelResult(xGradient, yGradient, magnitude, angles);
    }

    private void mapMagnitude(){
        int max = Arrays.stream(magnitude).flatMapToInt(Arrays::stream).max().getAsInt();
        int newMag;
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                newMag=(magnitude[y][x]*255/max);
                magnitude[y][x]=(newMag << 16 | newMag << 8 | newMag)-16777216;
            }
        }
    }
}
