package com.dkrucze.PathifyCore;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class PathCreator {

    private int[][] paths;
    private int w,h,white,black,neighbours;
    private LinkedList<Point> result = new LinkedList();
    private ArrayList<ArrayList<Point>> curves = new ArrayList();
    private ArrayList<ArrayList<Point>> loops = new ArrayList();


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
                        //For example to delete pixel above, it needs to have white pixel to its left or right
                        if(paths[y-1][x]==white){
                            //Pixel above
                            if(paths[y-1][x-1]==white || paths[y-1][x+1]==white)
                                paths[y-1][x]=black;
                        }else if(paths[y+1][x]==white){
                            //Pixel below
                            if(paths[y+1][x-1]==white || paths[y+1][x+1]==white)
                                paths[y+1][x]=black;
                        }else if(paths[y][x-1]==white){
                            //Right pixel
                            if(paths[y-1][x-1]==white || paths[y+1][x-1]==white)
                                paths[y][x-1]=black;
                        }else if(paths[y][x+1]==white){
                            //Left pixel
                            if(paths[y-1][x+1]==white || paths[y-1][x+1]==white)
                                paths[y][x+1]=black;
                        }
                    }
                }
            }
        }
    }


    public LinkedList<Point> calculatePath(){
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
        //There are also some individual pixels left, but they are irrelevant, so they can be ignored.
        for(int y=1;y<h-1;y++){
            for(int x=1;x<w-1;x++){
                if(paths[y][x]==white){
                    if(numOfNeighbours(x,y)==3){
                        int cx=x,cy=y;
                        ArrayList<Point> loop = new ArrayList();

                        //Traverse through the loop
                        while(numOfNeighbours(cx,cy)>0){
                            //Remove current point from image and add it to the loop
                            paths[cy][cx]=black;
                            loop.add(new Point(cx,cy));

                            //Check for next pixel
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
                        //Discard any very small curves
                        if(loop.size()>10){
                            loops.add(loop);
                        }
                    }
                }
            }
        }

        connectPaths();
        
        return result;
    }

    private void connectPaths(){
        double minDistanceStart=Double.MAX_VALUE, minDistanceEnd=Double.MAX_VALUE;
        boolean reverseStart,reverseEnd;
        int startIndex,endIndex;

        //Add initial curve
        result.addAll(curves.get(0));
        curves.remove(0);

        //Connect all the curves and loops together to form one path
        while(loops.size() > 0 && curves.size() > 0){
            //Check distance between curves, and ends of main path
            for(int i=0;i<curves.size();i++){
                ArrayList<Point> curve = curves.get(i);

                //Check for closest curve to the start of the path
                if(distance(result.get(0),curve.get(0)) < minDistanceStart){
                    minDistanceStart=distance(result.get(0),curve.get(0));
                    startIndex=i;
                    reverseStart=false;
                }else if(distance(result.get(0),curve.get(curve.size()-1)) < minDistanceStart){
                    minDistanceStart=distance(result.get(0),curve.get(curve.size()-1));
                    startIndex=i;
                    reverseStart=true;
                }

                //Check for closest curve to the end of the path
                if(distance(result.get(result.size()-1),curve.get(0)) < minDistanceStart){
                    minDistanceStart=distance(result.get(result.size()-1),curve.get(0));
                    endIndex=i;
                    reverseEnd=false;
                }else if(distance(result.get(result.size()-1),curve.get(curve.size()-1)) < minDistanceStart){
                    minDistanceStart=distance(result.get(result.size()-1),curve.get(curve.size()-1));
                    endIndex=i;
                    reverseEnd=true;
                }
            }

            //Check distance between loops, and ends of main path


        }
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

    private void appendPath(ArrayList<Point> subPath,boolean start){
        //Append sub-path to the beginning of the main path
        if(start==true){
            for(Point p : subPath)
                result.add(0,p);
        }
        //Append sub-path to the end of the main path
        else{
            result.addAll(subPath);
        }
    }

    private double distance(Point p1, Point p2){
        return Point2D.distance(p1.x,p1.y,p2.x,p2.y);
    }

    public int[][] getPaths(){
        return paths;
    }
}
