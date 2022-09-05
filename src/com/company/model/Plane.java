package com.company.model;

import com.company.math.Coord;
import com.company.math.JavaMathTools;
import com.company.math.MathTools;

// (n,(r-r0)) = 0
public class Plane implements Surface {
    private Coord n;
    private Coord r0;
    private MathTools tools;

    public Plane() {
        n = new Coord();
        r0 = new Coord();
        tools = new JavaMathTools();
    }

    public Plane(Coord n, Coord r0) {
        this.n = n;
        this.r0 = r0;
        tools = new JavaMathTools();
    }

    public Coord getN() {
        return n;
    }

    public void setN(Coord n) {
        this.n = n;
    }

    public Coord getR0() {
        return r0;
    }

    public void setR0(Coord r0) {
        this.r0 = r0;
    }

    @Override
    public Coord getIntersection(Ray ray){
        double t = tools.dotProduct(n, tools.linearCombination(r0, 1, ray.getRho0(), -1))
                /tools.dotProduct(n, ray.getE());
        return tools.linearCombination(ray.getRho0(), 1, ray.getE(), t);
    }

    @Override
    public Coord getNormal(Ray ray){
        return tools.normalize(n);
    }

    @Override
    public String toString(){
        return "("+n+", (r - "+r0+")) = 0";
    }
}
