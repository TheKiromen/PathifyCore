package com.dkrucze.PathifyCore;

public class  GrayscaleConversion {

    private GrayscaleConversion(){}

    public static int[][] convert(int[][] input){
        int h=input.length, w=input[0].length;
        int[][] result = new int[h][w];

        int r,g,b,avg;

        //Loop through the image
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                r=0;g=0;b=0;avg=0;

                //Get individual colors
                r=(input[y][x] >> 16) & 255;
                g=(input[y][x] >> 8) & 255;
                b=input[y][x] & 255;

                //Calculate grayscale value
                avg=(r+g+b)/3;

                result[y][x]=(avg << 16 | avg << 8 | avg)-16777216;

            }
        }

        return result;
    }
}
