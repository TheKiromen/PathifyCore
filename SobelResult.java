package com.dkrucze.PathifyCore;

public class SobelResult {

    private int[][] magnitude,xGradient,yGradient;
    private double[][] angles;

    public SobelResult(int[][] xGradient, int[][] yGradient, int[][] magnitude, double[][] angles){
        this.magnitude=magnitude;
        this.xGradient=xGradient;
        this.yGradient=yGradient;
        this.angles=angles;
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

    public double[][] getAngles() {
        return angles;
    }

    public void setAngles(double[][] angles) {
        this.angles = angles;
    }
}
