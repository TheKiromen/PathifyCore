package com.dkrucze.PathifyCore;

import java.awt.*;

public class GaussianBlur {

    //TODO Make it faster, kernelLength and variance depends on img size?

    /**
     * Blurring kernel is square so its width and height are both equal to length variable.
     */
    private int kernelLength = 20;
    /**
     * Weight matrix, is square and normalized.
     */
    private double[][] kernel;
    private double variance;

    /**
     * Calculates transformation matrix on creation.
     * @param variance Defines impact of the surrounding pixels. The higher the variance, the more impact surrounding pixels have.
     */
    public GaussianBlur(double variance){
        if(variance>0.0){
            this.variance=variance;
        }else{
            throw new IllegalArgumentException("Variance must be higher than 0.");
        }
        double summation=0;
        kernel=new double[kernelLength][kernelLength];

        //Generate matrix
        for(int y=0;y<kernelLength;y++){
            for(int x=0;x<kernelLength;x++){
                //point 0,0 is in the middle of the kernel
                kernel[y][x]=gaussianFunction((x-kernelLength/2),(y-kernelLength/2));
                summation += kernel[y][x];
            }
        }

        //Normalize the kernel data
        for (int y = 0; y < kernelLength; y++) {
            for (int x = 0; x < kernelLength; x++) {
                kernel[y][x]/=summation;
            }
        }

    }

    /**
     * Blurs given array of numbers
     * @param input Array of pixels to be blurred
     * @return Array of pixels after blurring
     */
    public Color[][] blur(Color[][] input){
        //Variables
        double avgR,avgG,avgB;
        int imgWidth=input[0].length,imgHeight=input.length,offset = kernelLength/2;
        Color[][] result = new Color[imgHeight][imgWidth];

        //Blurring the image
        //Loop through image
        for(int y=0;y<imgHeight;y++){
            for(int x=0;x<imgWidth;x++){
                avgR=0;avgG=0;avgB=0;

                for(int kernelY=0;kernelY<kernelLength;kernelY++){
                    for(int kernelX=0;kernelX<kernelLength;kernelX++){
                        int sampleY = y+kernelY - offset;
                        int sampleX = x+kernelX - offset;

                        //Check for edges of the image
                        if(sampleX < 0){sampleX*=-1;}
                        if(sampleY < 0){sampleY*=-1;}
                        if(sampleX > imgWidth-1){sampleX=imgWidth-1-(sampleX-(imgWidth-1));}
                        if(sampleY > imgHeight-1){sampleY=imgHeight-1-(sampleY-(imgHeight-1));}

                        //Calculate average color values
                        avgR+=(input[sampleY][sampleX].getRed()*kernel[kernelY][kernelX]);
                        avgG+=(input[sampleY][sampleX].getGreen()*kernel[kernelY][kernelX]);
                        avgB+=(input[sampleY][sampleX].getBlue()*kernel[kernelY][kernelX]);

                    }
                }
                //Create
                result[y][x]=new Color((int)avgR,(int)avgG,(int)avgB);
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
