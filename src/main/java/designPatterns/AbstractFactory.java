package designPatterns;

import consts.Constants;

import java.io.File;
import java.util.Scanner;

public class AbstractFactory {
    public static void createAbstractFactory(){
        String superClassname = Constants.createClass("abstract super class","");

        // create the x amount of sub classes input by user

        Constants.createClass("regular class",superClassname);
    }

}
