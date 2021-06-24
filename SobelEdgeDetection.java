package com.dkrucze.PathifyCore;

public class SobelEdgeDetection {

    private int threshold;
    private SobelResult result;
    private int[][] xGradient,yGradient,magnitude,input;

    public SobelEdgeDetection(int threshold){
        this.threshold=threshold;
    }


    public SobelResult findEdges(int[][] input){
        this.input=input;
        int h=input.length, w=input[0].length;
        int sumx,sumy;
        xGradient=new int[h][w];
        yGradient=new int[h][w];
        magnitude=new int[h][w];

        for(int y=1;y<h-1;y++){
            for(int x=1;x<w-1;x++){
                //FIXME fix value calculation
                //TODO get one of the RGB values (all are the same because grayscale)
                //TODO calculate new color value based on sobel operator
                //TODO set R G and B to the new value
                //TODO normalize or map the result so it is in range of 0-255?
                
//                //Calculate xGradient
//                sumx=(int)(
//                        input[y+1][x+1]*0.25+input[y][x+1]*0.5+input[y-1][x+1]*0.25
//                       -input[y+1][x-1]*0.25-input[y][x-1]*0.5-input[y-1][x-1]*0.25
//                );
//                xGradient[y][x]=sumx;
//
//                //Calculate yGradient
//                sumy=(int)(
//                        input[y+1][x-1]*0.25+input[y+1][x]*0.5+input[y+1][x+1]*0.25
//                       -input[y-1][x-1]*0.25-input[y-1][x]*0.5-input[y-1][x+1]*0.25
//                );
//                yGradient[y][x]=sumy;
//
//                //Calculate magnitude
//                magnitude[y][x]=(int)Math.sqrt(sumx*sumx+sumy*sumy);
            }
        }

        result=new SobelResult(xGradient,yGradient,magnitude);
        return result;
    }
}
