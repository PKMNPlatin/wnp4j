package de.pkmnplatin.wnp4j.util;

public class MathUtil {

    public static double clamp(double num, double min, double max) {
        return num < min ? min : (num > max ? max : num);
    }

}
