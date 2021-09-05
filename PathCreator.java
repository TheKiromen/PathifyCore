package com.dkrucze.PathifyCore;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.IntStream;

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

        //Add initial curve
        result.addAll(curves.get(0));
        curves.remove(0);

        while(curves.size()>0 || loops.size()>0){
            Point endOfPath = result.get(result.size()-1);
            double distance = Double.MAX_VALUE;
            ArrayList<Point> tmp=null;
            int index=0;
            boolean reversed = false,loop=false;

            for(ArrayList<Point> curve : curves){
                Point curveStart = curve.get(0);
                Point curveEnd = curve.get(curve.size()-1);

                if(distance(endOfPath,curveStart)<distance){
                    distance=distance(endOfPath,curveStart);
                    tmp=curve;
                    index=curves.indexOf(curve);
                    reversed=false;
                }

                if(distance(endOfPath,curveEnd)<distance){
                    distance=distance(endOfPath,curveEnd);
                    index=curves.indexOf(curve);
                    reversed=true;
                }
            }

            for(ArrayList<Point> l : loops){
                if(distance(endOfPath,l.get(0))<distance){
                    distance=distance(endOfPath,l.get(0));
                    tmp=l;
                    index=loops.indexOf(l);
                    loop=true;
                }
            }

            //Get two points for function calculation
            int x1=endOfPath.x,y1=endOfPath.y;
            if(reversed){
                Collections.reverse(tmp);
            }
            int x2=tmp.get(0).x,y2=tmp.get(0).y;

            //Check for vertical line, connect the ends
            if(x1==x2){
                if(y1>y2){
                    //Use map to reverse stream
                    IntStream.range(y2,y1).map(i -> y2-i + y1-1).skip(1).forEach(y -> result.add(new Point(x1,y)));
                }else{
                    IntStream.range(y1,y2).skip(1).forEach(y -> result.add(new Point(x1,y)));
                }


            }
            //Connect end of path to closest sub-path using linear function
            else{
                //Calculate function connecting two points
                //Integer division returns int, so we need to cast one of the values to double.
                //This forces the result to be double.
                double a=((double)y2-y1)/(x2-x1);
                double b=(((double)y1*x2)-(y2*x1))/(x2-x1);

                if(x1>x2){
                    //Use map to reverse stream
                    IntStream.range(x2,x1).map(i -> x2-i + x1-1).skip(1).forEach(x -> result.add(new Point(x,(int)(a*x+b))));
                }else{
                    IntStream.range(x1,x2).skip(1).forEach(x -> result.add(new Point(x,(int)(a*x+b))));
                }
            }

            //Append sub-path to the main path
            result.addAll(tmp);
            if(loop){
                loops.remove(tmp);
            }else{
                curves.remove(tmp);
            }
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


    private double distance(Point p1, Point p2){
        return Point2D.distance(p1.x,p1.y,p2.x,p2.y);
    }
}
