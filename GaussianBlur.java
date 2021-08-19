package com.dkrucze.PathifyCore;


public class GaussianBlur {
    //Variables
    private int[][] kernel;
    private int kernelSize,offset,h,w,weight=159;
    private int[][] input;

    public GaussianBlur(int[][] input){
        this.input=input;
        h=input.length; w=input[0].length;
        kernelSize=5;
        offset=kernelSize/2;
        kernel=new int[][]{
                {2,4,5,4,2},
                {4,9,12,9,4},
                {5,12,15,12,5},
                {4,9,12,9,4},
                {2,4,5,4,2}};
    }


    public int[][] blur(){
        double avg;
        int[][] result = new int[h][w];
        int sampleX,sampleY;

        //Calculate new value for each pixel
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                avg=0;
                //Loop through the kernel
                for(int kx=0;kx<kernelSize;kx++){
                    for(int ky=0;ky<kernelSize;ky++){
                        sampleX = x+kx-offset;
                        sampleY = y+ky-offset;
                        //Check for edges
                        if(sampleX<0){sampleX*=-1;}
                        if(sampleY<0){sampleY*=-1;}
                        if(sampleX>w-1){sampleX=w-1-(sampleX-(w-1));}
                        if(sampleY>h-1){sampleY=h-1-(sampleY-(h-1));}
                        //Calculate the average value of the pixel
                        avg+=(input[sampleY][sampleX]&255)*kernel[ky][kx];
                    }
                }
                avg/=weight;
                result[y][x]=((int)avg << 16 | (int)avg << 8 | (int)avg)-16777216;
            }
        }
        return result;
    }
}
