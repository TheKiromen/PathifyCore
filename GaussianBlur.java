package com.dkrucze.PathifyCore;

import java.awt.*;

public class GaussianBlur {

    /**
     * Blurring kernel is square so its width and height are both equal to length variable
     */
    private int kernelLength = 5;
    /**
     * Weight matrix, is square and normalized.
     */
    private float[][] kernel;
    private double variance;

    public GaussianBlur(double variance){
        this.variance=variance;
        //TODO Compute the kernel from 2 dimensional gaussian function

    }

    public Color[][] blur(Color[][] input){
        //Variables
        int avgR,avgG,avgB,imgWidth=input[0].length,imgHeight=input.length;
        Color[][] result = new Color[imgHeight][imgWidth];


        //Blurring the image
        //Loop through image
        for(int y = kernelLength; y<imgHeight- kernelLength; y++){
            for(int x = kernelLength; x<imgWidth- kernelLength; x++){
                //TODO loops to calculate average rgb values

            }
        }

        //Fixing the edges

        return result;
    }

    private double gaussianFunction(int x, int y){
        return (

                (Math.exp(-(Math.pow(x,2)+Math.pow(y,2))/(2*Math.pow(variance,2))))
                                                /
                                (2*Math.PI*Math.pow(variance,2))
        );
        //return (1/(2*Math.PI*Math.pow(variance,2))*Math.exp(-(Math.pow(x,2)+Math.pow(y,2))/(2*Math.pow(variance,2))));
    }

}
