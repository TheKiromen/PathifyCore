package com.dkrucze.PathifyCore;

import java.awt.*;

public class CannyEdgeDetection {

    private int[][] sobelX,sobelY,sobelMag,result;
    private boolean[][] candidates;
    private int highThreshold=200, lowThreshold=50;
    private int w,h;

    CannyEdgeDetection(SobelResult sobel){
        sobelX = sobel.getxGradient();
        sobelY = sobel.getyGradient();
        sobelMag = sobel.getMagnitude();
        this.h=sobelMag.length;
        this.w=sobelMag[0].length;
        candidates=new boolean[h][w];
        result=new int[h][w];
        //Fill the result with black pixels;
        int black = Color.BLACK.getRGB();
        for(int y=0;y<h;y++){
            for(int x=0;x<w;x++){
                result[y][x]=black;
            }
        }
    }

    public int[][] detect(){
        double angle;
        int black = Color.BLACK.getRGB();
        int white = Color.WHITE.getRGB();

        //Find all pixels above highThreshold
        for(int y=1;y<h-1;y++){
            for(int x=1;x<w-1;x++){
                //atan() returns value from -PI/2 to PI/2, so I am adding PI/2,
                //and converting to degrees for simplicity.
                angle=Math.atan(sobelY[y][x]/sobelX[y][x])+Math.PI/2;
                //New angle range is from 0 to 180 degrees (semicircle)
                angle=Math.toDegrees(angle);
                //Variables for finding local maximum
                int xOffset=1,yOffset=1;
                //Coordinates and value of the local maximum
                int maxX=x, maxY=y, maxVal=sobelMag[y][x]&255;

                //Possible edge directions:
                if(angle < 22.5 || angle > 157.5){
                    //Horizontal ---

                    //Check right side
                    while(sobelMag[y][x+xOffset]!=black){
                        if((sobelMag[y][x+xOffset]&255)>maxVal){
                            maxX=x+xOffset;
                            maxVal=sobelMag[y][x+xOffset]&255;
                        }
                        xOffset++;
                    }
                    xOffset=1;

                    //Check left side
                    while(sobelMag[y][x-xOffset]!=black){
                        if((sobelMag[y][x-xOffset]&255)>maxVal){
                            maxX=x-xOffset;
                            maxVal=sobelMag[y][x-xOffset]&255;
                        }
                        xOffset++;
                    }

                    //Mark a local maximum as a edge or edge candidate based on its value
                    if(maxVal>highThreshold){
                        result[maxY][maxX]=white;
                    }else if(maxVal>lowThreshold){
                        candidates[y][x]=true;
                    }

                }else if(angle >= 22.5 && angle <= 67.5){
                    //Right diagonal /

                }else if(angle > 67.5 && angle < 112.5){
                    //Vertical |

                }else{//Angle between 112.5 and 157.5
                    //Left diagonal \

                }

                //TODO
                //-Check for local maximum
                //-If above highThreshold set pixel in result to white.
                //-If between two thresholds, mark it as a candidate
                //-If below the lowThreshold set it to black in result
            }
        }

        //Check all edge candidates
        for(int y=1;y<h-1;y++){
            for(int x=1;x<w-1;x++){
                //TODO
                //-Loop through all candidates
                //-Check if a candidate is next to existing "hard" edge, if yes set pixel in result to white
            }
        }


        return result;
    }

}
