package com.dkrucze.PathifyCore;

public class SobelResult {

    private int[][] magnitude,xGradient,yGradient;

    public SobelResult(){}
    public SobelResult(int[][] xGradient,int[][] yGradient,int[][] magnitude){
        this.magnitude=magnitude;
        this.xGradient=xGradient;
        this.yGradient=yGradient;
    }

    //Getters and setters
    public int[][] getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(int[][] magnitude) {
        this.magnitude = magnitude;
    }

    public int[][] getxGradient() {
        return xGradient;
    }

    public void setxGradient(int[][] xGradient) {
        this.xGradient = xGradient;
    }

    public int[][] getyGradient() {
        return yGradient;
    }

    public void setyGradient(int[][] yGradient) {
        this.yGradient = yGradient;
    }
}
