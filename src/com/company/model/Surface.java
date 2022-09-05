package com.company.model;

import com.company.math.Coord;

public interface Surface {
    Coord getIntersection(Ray ray);
    Coord getNormal(Ray ray);
}
