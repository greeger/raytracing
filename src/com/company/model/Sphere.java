package com.company.model;

import com.company.math.Coord;
import com.company.math.JavaMathTools;
import com.company.math.MathTools;

// (rho - rho0, rho - rho0) = R^2
public class Sphere implements Surface {
    private Coord rho0;

    private double R;
    private MathTools tools;

    public Sphere() {
        rho0 = new Coord();
        R = 0;
        tools = new JavaMathTools();
    }

    public Sphere(Coord rho0, double R) {
        this.rho0 = rho0;
        this.R = R;
        tools = new JavaMathTools();
    }

    public Coord getRho0() {
        return rho0;
    }

    public void setRho0(Coord rho0) {
        this.rho0 = rho0;
    }

    public double getR() {
        return R;
    }

    public void setR(double r) {
        R = r;
    }

    @Override
    public Coord getIntersection(Ray ray){
        Coord shift = tools.linearCombination(ray.getRho0(), 1, rho0, -1);
        double d = Math.pow(tools.dotProduct(shift, ray.getE()),2)
                - tools.dotProduct(shift, shift) + R*R;
        if(d < 0) return null;
        double t = -tools.dotProduct(shift, ray.getE()) - Math.sqrt(d);
        return tools.linearCombination(ray.getRho0(), 1, ray.getE(), t);
    }

    @Override
    public Coord getNormal(Ray ray){
        Coord intersection = getIntersection(ray);
        if(intersection==null) return null;
        return tools.normalize(tools.linearCombination(intersection, 1, rho0, -1));
    }

    @Override
    public String toString(){
        return "(rho - "+rho0+", rho - "+rho0+") = "+R+"^2";
    }
}
