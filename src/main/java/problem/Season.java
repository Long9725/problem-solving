package problem;

import java.util.*;
import java.lang.*;
import java.io.*;

enum Season {
    SPRING,
    SUMMER
}
class Main {
    public static void main(String[] args) {
        Season season = Season.SPRING;

    }
    
    public  static void handler(Season season) {
        switch (season) {
            case SPRING:
                System.out.println(1);
                break;
            case SUMMER:
                System.out.println(2);
        }
    }
}