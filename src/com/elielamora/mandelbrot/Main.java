package com.elielamora.mandelbrot;


import com.sun.javafx.geom.Vec2d;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by eliel on 11/27/16.
 */
public class Main {

    public static final int MAX_ITERATIONS = 128;
    public static final int WIDTH = 2048;
    public static final int HEIGHT = WIDTH;
    /*public static final Function<Complex, Pair<Complex, Complex>> rule = new Function() {
        @Override
        public Complex apply(Complex o) {
            return com
        }
    }*/
    public static final double X0 = -2.0;
    public static final double Y0 = -2.0;
    public static final double X1 = 2.0;
    public static final double Y1 = 2.0;

    //public static final

    public static class Complex{
        public double a; // re
        public double b; // im
        public Complex(){a=0; b=0;}
        public Complex(double a, double b) {this.a = a; this.b = b;}
        public Complex(Complex z){this(z.a, z.b);}
        public double re(){return a;}
        public double im(){return b;}
        // http://mathworld.wolfram.com/ComplexMultiplication.html
        public static Complex multiply(Complex x, Complex y){
            return new Complex(x.a * y.a - x.b * y.b, x.a * y.b + x.b * y.a);
            // or do with 3 mult if it reduces comp time.
        }
        //public static Complex divide
        public Complex conjegate(){
            return new Complex(this.a, - this.b);
        }
        public Complex multiply(Complex z){
            return multiply(this, z);
        }
        public double absolute(){
            return Math.sqrt(a*a + b*b);
        }
        public double arg(){
            return Math.atan2(b, a);
        }
        public Complex polar(){
            return new Complex(absolute(), arg());
        }
        public Complex add(Complex z){
            return new Complex(a + z.a, b + z.b);
        }
        public Complex sub(Complex z){
            return new Complex(a-z.a, b-z.b);
        }
        public boolean diverged(){
            if(     Double.isInfinite(a) ||
                    Double.isInfinite(b) ||
                    Double.isNaN(a) ||
                    Double.isNaN(b))
                return true;
            else
                return false;
        }
        /*public Complex e(){

        }*/
    }



    /**
     * Main Method
     */
    public static void main(String ... args){
        long startTime = System.currentTimeMillis();
        String basePath = "/Users/eliel/Desktop/test/";
        BufferedImage bi = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
        //bi.setRGB(0, 0, 28, 28, toIntArray(data), 0, 1);
        double dx = X1-X0;
        double dy = Y1-Y0;
        double intervalX = dx/WIDTH;
        double intervalY = dy/HEIGHT;
        int count = 0;
        boolean diverged = false;
        for(int i = 0; i < bi.getWidth(); i++){ // real
            for(int j = 0; j < bi.getHeight(); j++){ // imaginary
                // /** mandelbrot set */
                /*Complex c = new Complex(i * intervalX + X0, j * intervalY + Y0);
                Complex result = new Complex();//new Complex(0.0843, 0.284375);
                // figure out if point diverges and when.. in terms of max iterations
                count = 0; diverged = false;

                while(!diverged && count < MAX_ITERATIONS){
                    result = result.multiply(result).multiply(result).add(c);
                    count ++;
                    if (result.diverged())
                        diverged = true;
                }*/
                // the below is the julia set code
                Complex z = new Complex(i * intervalX + X0, j * intervalY + Y0);
                Complex c = new Complex(0.289, 0.01);//new Complex(0.0843, 0.284375);
                // figure out if point diverges and when.. in terms of max iterations
                count = 0; diverged = false;

                while(!diverged && count < MAX_ITERATIONS){
                    z = z.multiply(z).add(c);
                    count ++;
                    if (z.diverged())
                        diverged = true;
                }
                // below is the filler code
                // fill in / color pixel accordingly
                int lum = (int) (255 * (1.0 - ((double)count/MAX_ITERATIONS)));
                /*if (lum > 255){lum = 255;}
                if (lum < 0){lum = 0;}*/
                bi.setRGB(i, j, (int)
                        lum + (lum << 8) + (lum << 16)
                );
                //System.out.print(data[i][j] + "\t");
            }
            //System.out.print("\n");
        }
        long endTime = System.currentTimeMillis();

        System.out.println("seconds elapsed: " + ((endTime-startTime)/1000.0));
        try {
            ImageIO.write(bi, "PNG", new File(basePath + "test" + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
