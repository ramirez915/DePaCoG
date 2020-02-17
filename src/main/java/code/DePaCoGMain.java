package code;

//import consts.Constants;
import consts.Constants2;
import designPatterns.AbstractFactory;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import java.util.Scanner;

public class DePaCoGMain {
    public static void main(String[] args){
        // keeps track of the design pattern called for
        String desiredDesPat = "";

        // allows user input
        Scanner userInput = new Scanner(System.in);
        while(desiredDesPat.compareTo("#") != 0){
            System.out.println("Enter the design pattern you want, type # then enter to quit");
             desiredDesPat = userInput.nextLine().toUpperCase();

            // main way that the program is going to work
            // first check if the desired design pattern is marked as not implemented in configuration
            try{
                if(!Constants2.verifyImplementation(desiredDesPat)){
                    System.out.println("is not implemented\n");
                }
                else{
                    switch(desiredDesPat){
                        case "ABSTRACT FACTORY":
//                            AbstractFactory.createAbstractFactory();
                            new AbstractFactory().createAbstractFactory();
                            break;
                        case "BUILDER":
                            
                            break;
                        // something unusual. This is when then design pattern is in the config file and true but not in the switch...
                        default:
                            System.out.println("Something strange\n");
                    }
                }
            }catch(Exception e){
                //log an error
                System.out.println("INVALID DP\n\n");
            }
        }
    }
}
