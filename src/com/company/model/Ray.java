package com.company.model;

import com.company.math.Coord;

// r = rho0 + e*t
// (e,e) = 1
public class Ray {
    private Coord rho0;
    private Coord e;

    public Ray() {
        rho0 = new Coord();
        e = new Coord();
    }

    public Ray(Coord rho0, Coord e) {
        this.rho0 = rho0;
        this.e = e;
    }

    public Coord getRho0() {
        return rho0;
    }

    public void setRho0(Coord rho0) {
        this.rho0 = rho0;
    }

    public Coord getE() {
        return e;
    }

    public void setE(Coord e) {
        this.e = e;
    }

    @Override
    public String toString(){
        return "r = "+rho0+" + "+e+"*t";
    }
}
