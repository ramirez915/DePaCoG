package code;

import consts.MyConstants;
import designPatterns.AbstractFactory;
import designPatterns.Builder;
import designPatterns.FactoryMethod;
import designPatterns.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Scanner;

public class DePaCoGMain {
    final static Logger logger = LoggerFactory.getLogger("DePaCoGMain");
    public static void main(String[] args){
        // maps the design pattern to a number
        HashMap<Integer,String> desPatMap = new HashMap<>();
        desPatMap.put(1,"Abstract Factory");
        desPatMap.put(2,"Builder");
        desPatMap.put(3,"Factory Method");
        desPatMap.put(4,"Facade");
        desPatMap.put(5,"Chain of Responsibility");
        desPatMap.put(6,"Mediator");
        desPatMap.put(7,"Visitor");
        desPatMap.put(8,"Template");

        // keeps track of the design pattern called for
        int desiredDesPat = -1;

        // allows user input
        Scanner userInput = new Scanner(System.in);
        while(desiredDesPat != 0){
             printMainInstructions();
             desiredDesPat = Integer.parseInt(userInput.nextLine());

            // main way that the program is going to work
            // first check if the desired design pattern is marked as not implemented in configuration
            try{
                //successful exit
                if(desiredDesPat == 0){
                    logger.info("Exited safely");
                    return;
                }
                // invalid option
                else if(desiredDesPat > 8 || desiredDesPat < 0){
                    logger.info("Invalid option **{}** was selected",desiredDesPat);
                }
                else if(!MyConstants.verifyImplementation(desPatMap.get(desiredDesPat))){
                    System.out.println("\n****************************IS NOT IMPLEMENTED****************************\n");
                    logger.info("User wanted to create the **{}** design pattern that is not yet implemented",desPatMap.get(desiredDesPat));
                }
                else{
                    switch(desiredDesPat){
                        case 1:
                            new AbstractFactory().createAbstractFactory();
                            break;
                        case 2:
                            new Builder().createBuilder();
                            break;
                        case 3:
                            new FactoryMethod().createFactoryMethod();
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            new Template().createTemplate();
                            break;
                        // something unusual. This is when then design pattern is in the config file and true but not in the switch...
                        default:
                            logger.warn("Tried running a design pattern with number {} that is in the config file as true but not in switch statements",desiredDesPat);
                    }
                }
            }catch(Exception e){
                logger.error("Failed to create **{}**",desPatMap.get(desiredDesPat));
            }
        }
    }

    public static void printMainInstructions(){
        System.out.println("\n***Select a Design Patterns***\n");
        System.out.printf("%-15d Abstract factory\n%-15d Builder\n%-15d Factory Method\n%-15d Facade %n",1,2,3,4);
        System.out.printf("%-15d Chain of Responsibility\n%-15d Mediator\n%-15d Visitor\n%-15d Template %n",5,6,7,8);
        System.out.println("Enter the design pattern number you want, type 0 then enter to quit");
    }
}
