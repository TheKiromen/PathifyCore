package com.dkrucze.PathifyCore;

public class SobelEdgeDetection {

    private int threshold;
    private SobelResult result;
    private int[][] xGradient,yGradient,magnitude;

    public SobelEdgeDetection(int threshold){
        if(threshold>=0&&threshold<=255){
            this.threshold=threshold;
        }else{
            throw new IllegalArgumentException("Threshold must be between 0 and 255.");
        }
    }


    public SobelResult findEdges(int[][] input){
        int h=input.length, w=input[0].length;
        int sumx,sumy,mag;
        xGradient=new int[h][w];
        yGradient=new int[h][w];
        magnitude=new int[h][w];

        for(int y=1;y<h-1;y++){
            for(int x=1;x<w-1;x++){
                //Calculate xGradient
                sumx=(int)(
                        (input[y+1][x+1]&255)*0.25+(input[y][x+1]&255)*0.5+(input[y-1][x+1]&255)*0.25
                       -(input[y+1][x-1]&255)*0.25-(input[y][x-1]&255)*0.5-(input[y-1][x-1]&255)*0.25
                );
                sumx=Math.abs(sumx);
                if(sumx<threshold)
                    sumx=0;
                xGradient[y][x]=(sumx << 16 | sumx << 8 | sumx)-16777216;

                //Calculate yGradient
                sumy=(int)(
                        (input[y+1][x-1]&255)*0.25+(input[y+1][x]&255)*0.5+(input[y+1][x+1]&255)*0.25
                       -(input[y-1][x-1]&255)*0.25-(input[y-1][x]&255)*0.5-(input[y-1][x+1]&255)*0.25
                );
                sumy=Math.abs(sumy);
                if(sumy<threshold)
                    sumy=0;
                yGradient[y][x]=(sumy << 16 | sumy << 8 | sumy)-16777216;

                //Calculate magnitude
                mag=(int)Math.sqrt(sumx*sumx+sumy*sumy);
                magnitude[y][x]=(mag << 16 | mag << 8 | mag)-16777216;
            }
        }

        result=new SobelResult(xGradient,yGradient,magnitude);
        return result;
    }
}
