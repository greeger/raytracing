package com.company.model;

import com.company.math.Coord;
import com.company.math.JavaMathTools;
import com.company.math.MathTools;

// (x-rhox)^2/a^2 + (y-rhoy)^2/b^2 + (z-rhoz)^2/c^2 = 1
public class Ellipse implements Surface {
    private Coord rho0;
    private double a;
    private double b;
    private double c;
    private MathTools tools;

    public Ellipse() {
        rho0 = new Coord();
        a=0;
        b=0;
        c=0;
        tools=new JavaMathTools();
    }

    public Ellipse(Coord rho0, double a, double b, double c) {
        this.rho0 = rho0;
        this.a=a;
        this.b=b;
        this.c=c;
        tools=new JavaMathTools();
    }

    public Coord getRho0() {
        return rho0;
    }

    public void setRho0(Coord rho0) {
        this.rho0 = rho0;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    @Override
    public Coord getIntersection(Ray ray){
        double[][] Mabc = new double[][]{{b*c, 0, 0}, {0, a*c, 0}, {0, 0, a*b}};
        double[][] temp = tools.matMul(Mabc, new double[][]{{ray.getE().getX()}, {ray.getE().getY()}, {ray.getE().getZ()}});
        Coord MabcE = new Coord(temp[0][0], temp[1][0], temp[2][0]);
        Coord shift = tools.linearCombination(ray.getRho0(), 1, rho0, -1);
        temp = tools.matMul(Mabc, new double[][]{{shift.getX()}, {shift.getY()}, {shift.getZ()}});
        Coord MabcShift = new Coord(temp[0][0], temp[1][0], temp[2][0]);
        double d = Math.pow(tools.dotProduct(MabcE, MabcShift),2)
                - tools.dotProduct(MabcE, MabcE)*(tools.dotProduct(MabcShift, MabcShift)-a*a*b*b*c*c);
        if(d < 0) return null;
        double t = (-tools.dotProduct(MabcE, MabcShift) - Math.sqrt(d))/tools.dotProduct(MabcE, MabcE);
        return tools.linearCombination(ray.getRho0(), 1, ray.getE(), t);
    }

    @Override
    public Coord getNormal(Ray ray){
        return null;
    }

    @Override
    public String toString(){
        return "(x-"+rho0.getX()+")^2/"+a+"^2 + (y-"+rho0.getY()+")^2/"+b+"^2 + (z-"+rho0.getZ()+")^2/"+c+"^2 = 1";
    }
}
