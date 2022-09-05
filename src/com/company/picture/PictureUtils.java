package com.company.picture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class PictureUtils {
    public static void savePicture(Picture picture, String filename) throws IOException {
        BufferedImage png = new BufferedImage(picture.getW(), picture.getH(), TYPE_INT_RGB);
        for(int i = 0; i < picture.getW(); i++) {
            for(int j = 0; j < picture.getH(); j++) {
                Color color = picture.getColorArray()[i][j];
                png.setRGB(i, j, new java.awt.Color(color.getR(), color.getG(), color.getB()).getRGB());
            }
        }
        ImageIO.write(png, "png", new File(filename));
    }

    public static Picture loadPicture(String filename) throws IOException {
        File file = new File(filename);
        BufferedImage pic = ImageIO.read(file);
        int w=pic.getWidth();
        int h=pic.getHeight();
        Picture rez=new Picture(w,h);
        Color[][] colorArray=rez.getColorArray();
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                java.awt.Color color=new java.awt.Color(pic.getRGB(i,j));
                colorArray[i][j]=new Color(color.getRed(),color.getGreen(),color.getBlue());
            }
        }
        return rez;
    }

    public static Color getColor(Picture picture, int x, int y){
        return picture.getColorArray()[x][picture.getH()-y-1];
    }

    public static void drawPixel(Picture picture, int x, int y, Color color) {
        int h= picture.getH();
        picture.getColorArray()[x][h-y-1] = color;
    }


    public static void drawLine(Picture picture, int x0, int y0, int x1, int y1, Color color) {
        boolean steep = false;
        if (Math.abs(x0-x1)<Math.abs(y0-y1)) {
            int k=x0;
            x0=y0;
            y0=k;
            k=x1;
            x1=y1;
            y1=k;
            steep = true;
        }
        if (x0>x1) {
            int k=x0;
            x0=x1;
            x1=k;
            k=y0;
            y0=y1;
            y1=k;
        }
        int dx = x1-x0;
        int dy = y1-y0;
        double derror = Math.abs(dy/(double)(dx));
        double error = 0;
        int y = y0;

        for(int x = x0; x < x1; x++) {
            if (steep){
                if(y>=0&&y< picture.getW()&&x>=0&&x< picture.getH())
                    drawPixel(picture, y, x, color);
            }
            else
                if(x>=0&&x< picture.getW()&&y>=0&&y< picture.getH())
                    drawPixel(picture, x, y, color);
            error += derror;
            if (error>0.5) {
                y += (y1>y0?1:-1);
                error -= 1;
            }
        }
    }
}