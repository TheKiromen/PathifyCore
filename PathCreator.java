package com.dkrucze.PathifyCore;

import java.awt.*;
import java.util.ArrayList;

public class PathCreator {

    private int input[][];
    private int w,h,white,black;
    private ArrayList<ArrayList<Point>> curves = new ArrayList();

    PathCreator(int[][] input){
        this.input=input;
        this.h=input.length;
        this.w=input[0].length;
        white=Color.WHITE.getRGB();
        black=Color.BLACK.getRGB();
    }


    public ArrayList<Point> calculatePath(){
        ArrayList<Point> result = new ArrayList();
        int neighbours;

        //Loop through the image
        for(int y=1;y<h-1;y++){
            for(int x=1;x<w-1;x++){

                //If we found edge, check its neighbours
                if(input[y][x]==white){
                    neighbours=numOfNeighbours(x,y);

                    //Remove all pixels with more than 2 neighbours
                    //This forces all edges to be exactly one pixel wide
                    if(neighbours>3){
                        input[y][x]=black;
                    }
                    //Finding all edges with clear beginning and end, from now on called "curves"
                    //If we found end of a curve, traverse it, save as a sub-path, and remove from input.
                    else if(neighbours==2){
                        int lineX=x,lineY=y;
                        ArrayList<Point> curve = new ArrayList();
                        curve.add(new Point(x,y));

                        //Remove starting pixel from input
                        input[y][x]=black;

                        //Traverse the curve until its end
                        do{
                            //Find where curve continues from 8 possible directions
                            if(input[y-1][x-1]==white){
                                //Up left
                                lineY--;
                                lineX--;
                            }else if(input[y-1][x]==white){
                                //Above
                                lineY--;
                            }else if(input[y-1][x+1]==white){
                                //Up right
                                lineY--;
                                lineX++;
                            }else if(input[y][x-1]==white){
                                //Left
                                lineX--;
                            }else if(input[y][x+1]==white){
                                //Right
                                lineX++;
                            }else if(input[y+1][x-1]==white){
                                //Down left
                                lineY++;
                                lineX--;
                            }else if(input[y+1][x]==white){
                                //Below
                                lineY++;
                            }else if(input[y+1][x+1]==white){
                                //Down right
                                lineY++;
                                lineX++;
                            }

                            //Remove next pixel in probed curve from input and add ii to array
                            input[lineY][lineX]=black;
                            curve.add(new Point(lineX,lineY));

                            //FIXME infinite loop, refactor checking for neighbours?
                        //Repeat until end of the curve
                        }while(numOfNeighbours(lineX,lineY)!=0);

                        //After we have whole curve, save it for later steps
                        curves.add(curve);
                    }
                }
            }
        }


        //Find all loops
        //After finding all curves, we are only left with fully enclosed edges, from now on called "loops"
        //TODO


        //Connect all the curves and loops together to form one path
        //TODO

        return result;
    }


    private int numOfNeighbours(int x, int y){
        int counter=0,white=Color.WHITE.getRGB();
        //Check area around given point
        for(int yOffset=-1;yOffset<=1;yOffset++){
            for(int xOffset=-1;xOffset<=1;xOffset++){
                //If there is strong edge next to our point, increase the counter
                if(input[y+yOffset][x+xOffset]==white)
                    counter++;
            }
        }
        return counter;
    }
}
