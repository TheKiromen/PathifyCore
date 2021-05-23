package com.dkrucze.PathifyCore;

import java.awt.*;

public class FastGaussianBlur {
    private double[] kernel;
    private int kernelSize,offset,h,w;
    private double sigma;

    public FastGaussianBlur(Color[][] input){
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


        //Horizontal pass


        //Vertical pass


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
