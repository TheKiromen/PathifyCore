package com.dkrucze.PathifyCore;

import java.awt.*;

public class FastGaussianBlur {
    private double[] kernel;
    private int kernelSize,offset,h,w;
    private Color[][] input;
    private double sigma;

    public FastGaussianBlur(Color[][] input){
        this.input=input;
        h=input.length; w=input[0].length;
        int longerSide = (h>w)? h : w;

        //Calculate Kernel size and sigma
        kernelSize=((18*longerSide/3797)+(11337/3797));
        kernel=new double[kernelSize];
        offset=kernelSize/2;
        sigma=(0.0025*longerSide+0.5);

        //Compute kernel values
        double summation=0;
        //Compute kernel array
        for(int x=0;x<kernelSize;x++){
            kernel[x]=gaussFunction(x-offset);
            summation+=kernel[x];
        }

        //Normalize kernel
        for(int x=0;x<kernelSize;x++){
            kernel[x]/=summation;
        }
    }



    public Color[][] blur(){
        double avgR,avgG,avgB;
        Color[][] result = new Color[h][w];
        Color[][] tmp = new Color[h][w];

        //Horizontal pass
        //Loop through the image
        for(int y=0;y<h;y++) {
            for (int x = 0; x < w; x++) {
                avgR = 0;avgG = 0;avgB = 0;

                //Loop through the kernel
                for(int kx = 0; kx<kernelSize;kx++){
                    int sampleX = x+kx-offset;
                    if(sampleX<0){sampleX*=-1;}
                    if(sampleX>w-1){sampleX=w-1-(sampleX-(w-1));}

                    avgR+=input[y][sampleX].getRed()*kernel[kx];
                    avgG+=input[y][sampleX].getGreen()*kernel[kx];
                    avgB+=input[y][sampleX].getBlue()*kernel[kx];

                }

                //Create new color
                tmp[y][x] = new Color((int) avgR, (int) avgG, (int) avgB);
            }
        }


        //Vertical pass
        //Loop through the image
        for(int y=0;y<h;y++) {
            for (int x = 0; x < w; x++) {
                avgR = 0;avgG = 0;avgB = 0;

                //Loop through the kernel
                for(int ky = 0; ky<kernelSize;ky++){
                    int sampleY = y+ky-offset;
                    if(sampleY<0){sampleY*=-1;}
                    if(sampleY>h-1){sampleY=h-1-(sampleY-(h-1));}

                    avgR+=tmp[sampleY][x].getRed()*kernel[ky];
                    avgG+=tmp[sampleY][x].getGreen()*kernel[ky];
                    avgB+=tmp[sampleY][x].getBlue()*kernel[ky];

                }

                //Create new color
                result[y][x] = new Color((int) avgR, (int) avgG, (int) avgB);
            }
        }

        return result;
    }


    private double gaussFunction(double x){
        //return ((Math.exp(-(Math.pow(x,2)/2*Math.pow(sigma,2))))/(sigma*Math.sqrt(2*Math.PI)));
        return (
                (Math.exp(-(Math.pow(x,2))/(2*Math.pow(sigma,2))))
                                        /
                        (sigma*Math.sqrt(2*Math.PI))
        );
    }
}
