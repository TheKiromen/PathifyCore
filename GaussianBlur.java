package com.dkrucze.PathifyCore;

/**
 * Class used to blur images, based on Gaussian blur.
 * Automatically calculates kernel size as well as variance based on the image size.
 * Weights in kernel are calculated using Gaussian function (normal distribution) and normalized.
 */
public class GaussianBlur {
    //Variables
    private double[] kernel;
    private int kernelSize,offset,h,w;
    private int[][] input;
    private double sigma;

    /**
     * Calculates kernel size and variance based on image size,
     * populates the kernel with values from Gaussian function and normalizes it.
     * @param input Image to be blurred.
     */
    public GaussianBlur(int[][] input){
        this.input=input;
        h=input.length; w=input[0].length;

        
//        int longerSide,shorterSide;
//        if(h>w){
//            longerSide=h;
//            shorterSide=w;
//        }else{
//            longerSide=w;
//            shorterSide=h;
//        }
//        //Create blur kernel
//        kernelSize=3;
//        kernelSize=(kernelSize>shorterSide)? shorterSide : kernelSize;
//        kernel=new double[kernelSize];
//        offset=kernelSize/2;
//        sigma=3;
//
//        double summation=0;
//        //Compute kernel values
//        for(int x=0;x<kernelSize;x++){
//            kernel[x]=gaussFunction(x-offset);
//            summation+=kernel[x];
//        }
//
//        //Normalize kernel
//        for(int x=0;x<kernelSize;x++){
//            kernel[x]/=summation;
//        }
    }


    /**
     * Blurs the image by using simplified form of Gaussian Blur.
     * Passes two times over image with one dimensional kernel, one time horizontally and one time vertically.
     * @return Blurred image
     */
    public int[][] blur(){
        int[][] result = new int[h][w];
//        double avgR,avgG,avgB;
//        int[][] result = new int[h][w];
//        int[][] tmp = new int[h][w];
//
//        //Horizontal pass
//        //Loop through the image
//        for(int y=0;y<h;y++) {
//            for (int x = 0; x < w; x++) {
//                avgR = 0;avgG = 0;avgB = 0;
//
//                //Loop through the kernel
//                for(int kx = 0; kx<kernelSize;kx++){
//                    int sampleX = x+kx-offset;
//                    if(sampleX<0){sampleX*=-1;}
//                    if(sampleX>w-1){sampleX=w-1-(sampleX-(w-1));}
//
//                    avgR+=((input[y][sampleX] >> 16) & 255)*kernel[kx];
//                    avgG+=((input[y][sampleX] >> 8) & 255)*kernel[kx];
//                    avgB+=(input[y][sampleX] & 255)*kernel[kx];
//
//                }
//
//                //Save new temporary pixel value
//                tmp[y][x] = ((int)avgR << 16 | (int)avgG << 8 | (int)avgB)-16777216;
//            }
//        }
//
//
//        //Vertical pass
//        //Loop through the image
//        for(int y=0;y<h;y++) {
//            for (int x = 0; x < w; x++) {
//                avgR = 0;avgG = 0;avgB = 0;
//
//                //Loop through the kernel
//                for(int ky = 0; ky<kernelSize;ky++){
//                    int sampleY = y+ky-offset;
//                    if(sampleY<0){sampleY*=-1;}
//                    if(sampleY>h-1){sampleY=h-1-(sampleY-(h-1));}
//
//                    avgR+=((tmp[sampleY][x] >> 16) & 255)*kernel[ky];
//                    avgG+=((tmp[sampleY][x] >> 8) & 255)*kernel[ky];
//                    avgB+=(tmp[sampleY][x] & 255)*kernel[ky];
//
//                }
//
//                //Save new final pixel value
//                result[y][x] = ((int)avgR << 16 | (int)avgG << 8 | (int)avgB)-16777216;
//            }
//        }

        return result;
    }

//    /**
//     * Calculates value of Gaussian function at given x.
//     * @param x Coordinate
//     * @return Value of given coordinate
//     */
//    private double gaussFunction(double x){
//        return (
//                (Math.exp(-(Math.pow(x,2))/(2*Math.pow(sigma,2))))
//                                        /
//                        (sigma*Math.sqrt(2*Math.PI))
//        );
//    }
}
