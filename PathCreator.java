package com.dkrucze.PathifyCore;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PathCreator {

    private int[][] paths;
    private int w,h,white,black;
    private ArrayList<ArrayList<Point>> curves = new ArrayList();

    PathCreator(int[][] input){
        this.h=input.length;
        this.w=input[0].length;
        white=Color.WHITE.getRGB();
        black=Color.BLACK.getRGB();
        paths= Arrays.stream(input).map(int[]::clone).toArray(int[][]::new);
    }


    public ArrayList<Point> calculatePath(){
        ArrayList<Point> result = new ArrayList();
        int neighbours;

        //Loop through the image
        for(int y=1;y<h-1;y++) {
            for (int x = 1; x < w - 1; x++) {
                if (paths[y][x] == white) {
                    neighbours = numOfNeighbours(x, y);
                    //Remove all unnecessary pixels to thin out the edges
                    if (neighbours > 4) {
                        paths[y][x] = black;
                    }
                }
            }
        }


        for(int y=1;y<h-1;y++) {
            for (int x = 1; x < w - 1; x++) {
                if (paths[y][x] == white) {
                    neighbours = numOfNeighbours(x, y);
                    //Find beginning of the curve
                    if(neighbours==2){
                        //Traverse until end of the curve
                        //TODO
                    }
                }
            }
        }

        //Find all curves
        //TODO

//                //If we found edge, check its neighbours
//                if(input[y][x]==white){
//                    neighbours=numOfNeighbours(x,y);
//
//                    //Remove all pixels with more than 2 neighbours
//                    //This forces all edges to be exactly one pixel wide
//                    if(neighbours>4){
//                        //input[y][x]=black;
//                    }
//                    //Finding all edges with clear beginning and end, from now on called "curves"
//                    //If we found end of a curve, traverse it, save as a sub-path, and remove from input.
//                    else if(neighbours==2){
//                        int cx=x,cy=y;
//                        ArrayList<Point> curve = new ArrayList();
//
//                        while(numOfNeighbours(cx,cy)>0){
//                            curve.add(new Point(cx,cy));
//                            input[cy][cx]=black;
//
//                            if(input[cy-1][cx-1]==white){
//                                //Up left
//                                cy--;
//                                cx--;
//                            }else if(input[cy-1][cx]==white){
//                                //Above
//                                cy--;
//                            }else if(input[cy-1][cx+1]==white){
//                                //Up right
//                                cy--;
//                                cx++;
//                            }else if(input[cy][cx-1]==white){
//                                //Left
//                                cx--;
//                            }else if(input[cy][cx+1]==white){
//                                //Right
//                                cx++;
//                            }else if(input[cy+1][cx-1]==white){
//                                //Down left
//                                cy++;
//                                cx--;
//                            }else if(input[cy+1][cx]==white){
//                                //Below
//                                cy++;
//                            }else if(input[cy+1][cx+1]==white){
//                                //Down right
//                                cy++;
//                                cx++;
//                            }
//                        }
//                        curves.add(curve);
//                    }
//                }
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
                if(paths[y+yOffset][x+xOffset]==white)
                    counter++;
            }
        }
        return counter;
    }

    public int[][] getPaths(){
        return paths;
    }
}
