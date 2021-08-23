package com.dkrucze.PathifyCore;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PathCreator {

    private int[][] paths;
    private int w,h,white,black,neighbours;
    private ArrayList<ArrayList<Point>> curves = new ArrayList();


    PathCreator(int[][] input){
        this.h=input.length;
        this.w=input[0].length;
        white=Color.WHITE.getRGB();
        black=Color.BLACK.getRGB();
        //Copy edges
        paths= Arrays.stream(input).map(int[]::clone).toArray(int[][]::new);

        //Remove all unnecessary pixels to thin out the edges
        //Loop through the image
        for(int y=1;y<h-1;y++) {
            for (int x = 1; x < w - 1; x++) {
                if (paths[y][x] == white) {
                    neighbours = numOfNeighbours(x, y);
                    if (neighbours > 3) {
                        //Check if it's safe to delete a pixel
                        //Check for white pixels in cardinal directions
                        //If they have a pixel next to them, then it is safe to remove it.
                        if(paths[y-1][x]==white){
                            //Pixel above
                            if(paths[y-1][x-1]==white || paths[y-1][x+1]==white)
                                paths[y-1][x]=black;
                        }else if(paths[y+1][x]==white){
                            //Pixel below
                            if(paths[y+1][x-1]==white || paths[y+1][x+1]==white)
                                paths[y+1][x]=black;
                        }else if(paths[y][x-1]==white){
                            //Pixel on the right
                            if(paths[y-1][x-1]==white || paths[y+1][x-1]==white)
                                paths[y][x-1]=black;
                        }else if(paths[y][x+1]==white){
                            //Pixel on the left
                            if(paths[y-1][x+1]==white || paths[y-1][x+1]==white)
                                paths[y][x+1]=black;
                        }
                    }
                }
            }
        }

    }


    public ArrayList<Point> calculatePath(){
        ArrayList<Point> result = new ArrayList();

        //Loop through the image and find all curves
        for(int y=1;y<h-1;y++) {
            for (int x = 1; x < w - 1; x++) {
                if (paths[y][x] == white) {
                    neighbours = numOfNeighbours(x, y);
                    //Find beginning of the curve
                    if(neighbours==2){

                        int cx=x,cy=y;
                        ArrayList<Point> curve = new ArrayList();

                        //Traverse until end of the curve
                        while(numOfNeighbours(cx,cy)>0){
                            //Remove current point from image, add it to curve.
                            curve.add(new Point(cx,cy));
                            paths[cy][cx]=black;

                            //Check for next
                            if(paths[cy-1][cx-1]==white){
                                //Up left
                                cy--;
                                cx--;
                            }else if(paths[cy-1][cx]==white){
                                //Above
                                cy--;
                            }else if(paths[cy-1][cx+1]==white){
                                //Up right
                                cy--;
                                cx++;
                            }else if(paths[cy][cx-1]==white){
                                //Left
                                cx--;
                            }else if(paths[cy][cx+1]==white){
                                //Right
                                cx++;
                            }else if(paths[cy+1][cx-1]==white){
                                //Down left
                                cy++;
                                cx--;
                            }else if(paths[cy+1][cx]==white){
                                //Below
                                cy++;
                            }else if(paths[cy+1][cx+1]==white){
                                //Down right
                                cy++;
                                cx++;
                            }
                        }
                        //Discard any "noise" curves
                        if(curve.size()>10){
                            curves.add(curve);
                        }
                    }
                }
            }
        }


        //Find all loops
        //After finding all curves, we are only left with fully enclosed edges, from now on called "loops"
        //There are also some individual pixels left but they are irrelevant so they can be ignored.
        //TODO


        //Connect all the curves and loops together to form one path
        //TODO
        // pick starting curve, find closest curve to the beginning and end of the curve
        // attach these curves (or loops) respectively, continue until no more curves left.

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
