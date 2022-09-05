package com.company.math;

import com.company.model.Ray;
import com.company.model.Surface;

public interface MathTools {
    double[][] matMul(double[][] a, double[][] b);
    double[][] matSum(double[][] a, double[][] b);
    double[][] scalarMul(double[][] a, double b);
    double[][] inversion(double[][] a);
    double dotProduct(Coord n, Coord v);
    double normDotProduct(Coord n, Coord v);
    double getNorm(Coord n);
    Coord normalize(Coord n);
    Coord linearCombination(Coord a, double m, Coord b, double n);
    Ray getReflectedRay(Ray ray, Surface surface);
    Ray getRefractedRay(Ray ray, Surface surface, double n1, double n2);
}
