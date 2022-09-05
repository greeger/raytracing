package com.company.math;

import com.company.model.Ray;
import com.company.model.Surface;

public class JavaMathTools implements MathTools {
    @Override
    public double[][] matMul(double[][] a, double[][] b){
        int x=a.length;
        int y=b.length;
        int z=b[0].length;
        double[][] rez=new double[x][z];
        for(int i=0; i<x; i++)
            for(int j=0; j<z; j++)
                for(int k=0; k<y; k++)
                    rez[i][j]+=a[i][k]*b[k][j];
        return rez;
    }

    @Override
    public double[][] matSum(double[][] a, double[][] b){
        int x=a.length;
        int y=a[0].length;
        double[][] rez=new double[x][y];
        for(int i=0; i<x; i++)
            for(int j=0; j<y; j++)
                rez[i][j]=a[i][j]+b[i][j];
        return rez;
    }

    @Override
    public double[][] scalarMul(double[][] a, double b){
        int x=a.length;
        int y=a[0].length;
        double[][] rez=new double[x][y];
        for(int i=0; i<x; i++)
            for(int j=0; j<y; j++)
                rez[i][j]=a[i][j]*b;
        return rez;
    }

    @Override
    public double[][] inversion(double[][] a){
        int n=a.length;
        double temp;

        double[][] E = new double[n][n];

        double[][] A1 = new double[n][n];
        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                A1[i][j]=a[i][j];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                E[i][j] = 0f;
                if (i == j)
                    E[i][j] = 1f;
            }

        for (int k = 0; k < n; k++) {
            temp = A1[k][k];
            for (int j = 0; j < n; j++) {
                A1[k][j] /= temp;
                E[k][j] /= temp;
            }

            for (int i = k + 1; i < n; i++) {
                temp = A1[i][k];
                for (int j = 0; j < n; j++) {
                    A1[i][j] -= A1[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        for (int k = n - 1; k > 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                temp = A1[i][k];
                for (int j = 0; j < n; j++) {
                    A1[i][j] -= A1[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }

        return E;
    }

    @Override
    public double getNorm(Coord n) {
        return Math.sqrt((n.getX()*n.getX()+n.getY()*n.getY()+n.getZ()*n.getZ()));
    }

    @Override
    public Coord normalize(Coord n){
        double norm = getNorm(n);
        return new Coord(n.getX()/norm, n.getY()/norm, n.getZ()/norm);
    }

    @Override
    public double dotProduct(Coord n, Coord v){
        return n.getX()*v.getX() + n.getY()*v.getY() + n.getZ()*v.getZ();
    }

    @Override
    public double normDotProduct(Coord n, Coord v){
        return dotProduct(n, v)/(getNorm(n)*getNorm(v));
    }

    @Override
    public Coord linearCombination(Coord a, double m, Coord b, double n){
        return new Coord(a.getX()*m + b.getX()*n, a.getY()*m + b.getY()*n, a.getZ()*m + b.getZ()*n);
    }

    @Override
    public Ray getReflectedRay(Ray ray, Surface surface){
        Coord e = ray.getE();
        Coord n = surface.getNormal(ray);
        if (e==null || n==null) return null;
        return new Ray(surface.getIntersection(ray), normalize(linearCombination(e, 1, n, -2*dotProduct(e, n))));
    }

    @Override
    public Ray getRefractedRay(Ray ray, Surface surface, double n1, double n2){
        Coord e = ray.getE();
        Coord n = surface.getNormal(ray);
        if (e==null || n==null) return null;
        double en = dotProduct(e, n);
        Coord newE = linearCombination(e, n1/n2, n, -en*n1/n2*(1-Math.sqrt((n2*n2-n1*n1)/(en*en*n1*n1)+1)));
        return new Ray(surface.getIntersection(ray), normalize(newE));
    }
}
