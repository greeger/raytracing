package com.company;

import com.company.math.Coord;
import com.company.math.JavaMathTools;
import com.company.math.MathTools;
import com.company.model.Ellipse;
import com.company.model.Ray;
import com.company.model.Sphere;
import com.company.model.Surface;
import com.company.picture.Color;
import com.company.picture.Picture;
import com.company.picture.PictureUtils;

import java.io.IOException;

public class Main {
    private static void printMatrix(double[][] a){
        for(int i=0; i<a.length; i++){
            for (int j=0; j<a[0].length; j++)
                System.out.print(a[i][j]+" ");
            System.out.println();
        }
        System.out.println();
    }
    public static void main(String[] args) {
        MathTools tools = new JavaMathTools();

        Coord r0 = new Coord(-4, -1.2, 3);
        Coord e = new Coord(1, 0, 0);
        e = tools.normalize(e);
        Ray ray = new Ray(r0, e);
        System.out.println(ray);

        double R = 2;
        Coord rho0 = new Coord(0, 0, 2);
        Surface sphere = new Sphere(rho0, R);
        System.out.println(sphere);

        Surface ellipse = new Ellipse(rho0, 2, 2, 2);
        System.out.println(ellipse);

        Ray reflectedRay = tools.getRefractedRay(ray, ellipse, 1, 3);
        System.out.println(reflectedRay);


        int w = 100;
        int h = 100;
        double scale = 0.05;
        double brightness;
        Ray currRay;
        Coord light = new Coord(1, 1, -1);
        Picture picture=new Picture(w,h);
        for(int i=0; i<w; i++)
            for(int j=0; j<h; j++){
                currRay = new Ray(new Coord((i-w/2)*scale, (j-h/2)*scale, 10), tools.normalize(new Coord(0, 0, -1)));
                currRay = tools.getReflectedRay(currRay, ellipse);
                if(currRay==null) brightness=0;
                else
                    brightness = -tools.normDotProduct(currRay.getE(), light);
                if(brightness<0) brightness=0;
                PictureUtils.drawPixel(picture, i, j, new Color((int)Math.round(255*brightness)));
            }

        double rayLength = 3;
        int x0 = (int)Math.round(ray.getRho0().getX()/scale);
        int y0 = (int)Math.round(ray.getRho0().getY()/scale);
        int x1 = (int)Math.round(reflectedRay.getRho0().getX()/scale);
        int y1 = (int)Math.round(reflectedRay.getRho0().getY()/scale);
        PictureUtils.drawLine(picture, x0+w/2, y0+h/2, x1+w/2, y1+h/2, new Color(255));
        int x2 = (int)Math.round(tools.linearCombination(reflectedRay.getRho0(), 1, reflectedRay.getE(), rayLength).getX()/scale);
        int y2 = (int)Math.round(tools.linearCombination(reflectedRay.getRho0(), 1, reflectedRay.getE(), rayLength).getY()/scale);
        PictureUtils.drawLine(picture, x2+w/2, y2+h/2, x1+w/2, y1+h/2, new Color(255));

        try {
            PictureUtils.savePicture(picture, "pictures/refraction.png");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}