package com.dkrucze.PathifyCore;

import java.awt.*;

public class SobelEdgeDetection {

    private SobelResult result;
    private int h,w;
    private int[][] xGradient,yGradient,magnitude;

    public SobelEdgeDetection(int[][] input){
        this.h=input.length;
        this.w=input[0].length;
    }

    public SobelResult findEdges(){
        int Gx,Gy,Mag;
        xGradient=new int[h][w];
        yGradient=new int[h][w];
        magnitude=new int[h][w];

        //Loop through the image and calculate the derivatives and magnitude
        for(int y=1;y<h-1;y++){
            for(int x=1;x<w-1;x++){
                //Calculate x derivative
                //TODO

                //Calculate y derivative
                //TODO

                //Calculate magnitude
                //TODO
            }
        }

        //setting the edges to black to simplify future processing
        int black = Color.BLACK.getRGB();
        for(int x=0;x<w;x++){
            magnitude[0][x]=black;
            xGradient[0][x]=black;
            yGradient[0][x]=black;
            magnitude[h-1][x]=black;
            xGradient[h-1][x]=black;
            yGradient[h-1][x]=black;
        }
        for(int y=0;y<h;y++){
            magnitude[y][0]=black;
            xGradient[y][0]=black;
            yGradient[y][0]=black;
            magnitude[y][w-1]=black;
            xGradient[y][w-1]=black;
            yGradient[y][w-1]=black;
        }

        result=new SobelResult(xGradient,yGradient,magnitude);
        return result;

//        for(int y=1;y<h-1;y++){
//            for(int x=1;x<w-1;x++){
//                //Calculate xGradient
//                sumx=(input[y+1][x+1]&255)*1+(input[y][x+1]&255)*2+(input[y-1][x+1]&255)*1-(input[y+1][x-1]&255)*1-(input[y][x-1]&255)*2-(input[y-1][x-1]&255)*1;
//                sumx=Math.abs(sumx)*255/1020;
//                if(sumx<threshold)
//                    sumx=0;
//                xGradient[y][x]=(sumx << 16 | sumx << 8 | sumx)-16777216;
//
//                //Calculate yGradient
//                sumy=(input[y+1][x-1]&255)*1+(input[y+1][x]&255)*2+(input[y+1][x+1]&255)*1-(input[y-1][x-1]&255)*1-(input[y-1][x]&255)*2-(input[y-1][x+1]&255)*1;
//                sumy=Math.abs(sumy)*255/1020;
//                if(sumy<threshold)
//                    sumy=0;
//                yGradient[y][x]=(sumy << 16 | sumy << 8 | sumy)-16777216;
//
//                //Calculate magnitude
//                mag=(int)Math.sqrt(sumx*sumx+sumy*sumy);
//                if(mag>255)
//                    mag=255;
//                magnitude[y][x]=(mag << 16 | mag << 8 | mag)-16777216;
//            }
//        }
    }
}
