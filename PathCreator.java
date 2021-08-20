package com.dkrucze.PathifyCore;

import java.awt.*;
import java.util.ArrayList;

public class PathCreator {

    private int input[][];
    private int w,h,white,black;

    PathCreator(int[][] input){
        this.input=input;
        this.h=input.length;
        this.w=input[0].length;
        white=Color.WHITE.getRGB();
        black=Color.BLACK.getRGB();
    }


    public ArrayList<Point> calculatePath(){
        ArrayList<Point> result = new ArrayList();

        //Remove all pixels with more than 2 neighbours
        for(int y=1;y<h-1;y++){
            for(int x=1;x<w-1;x++){
                //FIXME add more conditions
                //if numOfNeighbours=1, save it as end of line
                //if numOfNeighbours>2, remove it
                if(input[y][x]==white && numOfNeighbours(x,y)>2){
                    input[y][x]=black;
                }
            }
        }

        //Find all lines
        //TODO

        //Find all loops
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
