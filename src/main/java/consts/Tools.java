package consts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class Tools {
    final static Logger logger = LoggerFactory.getLogger("Tools");
    /*
    prints out the correct prompts in order for the design patterns
     */
    public static ArrayList<String> getParamsForPattern(String designPattern){
        Scanner input = new Scanner(System.in);
        ArrayList<String> designPatterParams = new ArrayList<>();
        boolean validEntries;

        switch(designPattern.toUpperCase()){
            case "ABSTRACT FACTORY":
                validEntries = abstractFactoryPrompts(designPatterParams,input);
                if(!validEntries){
                    return null;
                }
                break;
            case "BUILDER":
                validEntries = builderPrompts(designPatterParams,input);
                if(!validEntries){
                    return null;
                }
                break;
            case "FACTORY METHOD":
                validEntries = factoryMethodPrompts(designPatterParams,input);
                if(!validEntries){
                    return null;
                }
                break;
            case "TEMPLATE":
                validEntries = templatePrompt(designPatterParams,input);
                if(!validEntries){
                    return null;
                }
                break;
            default:
                System.out.println("Unknown design pattern");
        }
        return designPatterParams;
    }

    /*
    gets the amount of the subclasses and the names
    adds the names to the list of parameters
     */
    private static boolean getSubClassesName(ArrayList<String> paramsList,Scanner input){
        int totSubclasses = -1;
        try{
            while(totSubclasses < 0){
                System.out.println(MyConstants.AmountSubclassesPrompt);
                totSubclasses = Integer.parseInt(input.nextLine());
            }
        }catch (Exception e){
            logger.error("User did not input a number for amount of subclasses");
            return false;
        }

        //get the names of the subclasses
        for(int i = 0; i < totSubclasses; i++){
            System.out.println(MyConstants.RegularClassPrompt);
            paramsList.add(input.nextLine());
        }
        return true;
    }

    /*
    order: the main interface, amount of functions,
    amount of subclasses, name of subclasses
     */
    private static boolean abstractFactoryPrompts(ArrayList<String> paramList,Scanner input){
        System.out.println(MyConstants.InterfacePrompt);
        paramList.add(input.nextLine());

        int functionAmount = -1;
        try{
            while(functionAmount < 0){
                System.out.println(MyConstants.FunctionAmountPrompt);
                functionAmount = Integer.parseInt(input.nextLine());
            }
        }catch(Exception e){
            logger.error("User did not input a number for the amount of functions for the Abstract Factory's interface");
            return false;
        }

        paramList.add(String.valueOf(functionAmount));

        //get the names of the subclasses
        return getSubClassesName(paramList,input);
    }

    /*
     order: abstract class name, variable amount,
     variable types (size x so positions of following will be determined later),
     abstract function amount, regular function amount
     */
    private static boolean factoryMethodPrompts(ArrayList<String> paramList,Scanner input){
        System.out.println(MyConstants.AbstractClassPrompt);
        paramList.add(input.nextLine());

        int totVariables = -1;
        try{
            while(totVariables < 1){
                System.out.println(MyConstants.VariableAmountPrompt);
                totVariables = Integer.parseInt(input.nextLine());
            }
            paramList.add(String.valueOf(totVariables));
        }catch(Exception e){
            logger.error("User did not enter a number for variable amount for Factory Method");
            return false;
        }
        // keep asking the variable types for the total number of variables
        for(int i = 0; i < totVariables; i++){
            System.out.println(MyConstants.VariableTypePrompt);
            paramList.add(input.nextLine());
        }

        // error checks that the user puts in at least one abstract method
        int abstractFuncAmount = 0;
        try{
            while (abstractFuncAmount < 1){
                System.out.println(MyConstants.AbstractFunctionAmountPrompt);
                abstractFuncAmount = Integer.parseInt(input.nextLine());
            }
        }catch(Exception e){
            logger.error("User did not enter a number for abstract function amount for Factory Method");
            return false;
        }
        paramList.add(String.valueOf(abstractFuncAmount));

        int totFunctions = -1;
        try{
            while(totFunctions < 0){
                System.out.println(MyConstants.FunctionAmountPrompt);
                totFunctions = Integer.parseInt(input.nextLine());
            }
        }catch(Exception e){
            logger.error("User did not input a number for the number of functions for Factory Method");
            return false;
        }
        paramList.add(String.valueOf(totFunctions));

        return getSubClassesName(paramList,input);
    }

    /*
    order of params: main class name, mandatory variable amount,
    optional amount, variable types (mandatory + optional)
     */
    private static boolean builderPrompts(ArrayList<String> paramList,Scanner input){
        System.out.println(MyConstants.RegularClassPrompt);
        paramList.add(input.nextLine());

        // error checks that the user puts in at least one mandatory attribute
        int mandatoryAttAmount = 0;
        int optionalAttAmount = 0;
        try{
            while (mandatoryAttAmount < 1){
                System.out.println("***Mandatory attributes***");
                System.out.println(MyConstants.MandatoryAttributeAmountPrompt);
                mandatoryAttAmount = Integer.parseInt(input.nextLine());
            }

            // error checks that the user puts in at least one optional attribute
            while (optionalAttAmount < 1){
                System.out.println("***Optional attributes***");
                System.out.println(MyConstants.OptionalAttributeAmountPrompt);
                optionalAttAmount = Integer.parseInt(input.nextLine());
            }
        }catch(Exception e){
            logger.error("User did not put in a number for amount of mandatory or optional attributes for Builder");
            return false;
        }
        paramList.add(String.valueOf(mandatoryAttAmount));
        paramList.add(String.valueOf(optionalAttAmount));

        // get the mandatory attributes type
        System.out.println("***Mandatory attributes***");
        for(int i = 0; i < mandatoryAttAmount; i++){
            System.out.println(MyConstants.VariableTypePrompt);
            paramList.add(input.nextLine());
        }

        // get the optional attribute type
        System.out.println("***Optional attributes***");
        for(int i = 0; i < optionalAttAmount; i++){
            System.out.println(MyConstants.VariableTypePrompt);
            paramList.add(input.nextLine());
        }
        return true;
    }

    /*
    order: main abstract class, abstract function amount, regular function amount,
    amount of absolute processes, amount of functions in each process, subclasses names
     */
    private static boolean templatePrompt(ArrayList<String> paramList,Scanner input){
        System.out.println(MyConstants.AbstractClassPrompt);
        paramList.add(input.nextLine());

        int abstractFunctionAmount = 0;
        int absoluteProcessAmount = 0;
        int totalRegularFunctions = -1;
        try{
            // error checks that the user puts in at least one abstract method
            while (abstractFunctionAmount < 1){
                System.out.println(MyConstants.AbstractFunctionAmountPrompt);
                abstractFunctionAmount = Integer.parseInt(input.nextLine());
            }
            // checks that the user puts in a number  0 <=
            while(totalRegularFunctions < 0){
                System.out.println(MyConstants.FunctionAmountPrompt);
                totalRegularFunctions = Integer.parseInt(input.nextLine());
            }
            // error checks that the user puts at least one absolute process
            while (absoluteProcessAmount < 1){
                System.out.println(MyConstants.AbsoluteProcessAmountPrompt);
                absoluteProcessAmount = Integer.parseInt(input.nextLine());
            }
        }catch (Exception e){
            logger.error("User did not input a number somewhere when entering number of abstract functions,regular functions, absolute processes amount");
            return false;
        }
        paramList.add(String.valueOf(abstractFunctionAmount));
        paramList.add(String.valueOf(totalRegularFunctions));
        paramList.add(String.valueOf(absoluteProcessAmount));

        int functionNumInEachProcess = 0;
        try{
            for(int i = 0; i < absoluteProcessAmount; i++){
                while(functionNumInEachProcess < 1){
                    System.out.println(String.format(MyConstants.AmountOfFunctionsInAbsoluteProcess,i+1));
                    functionNumInEachProcess = Integer.parseInt(input.nextLine());
                }
                paramList.add(String.valueOf(functionNumInEachProcess));
                functionNumInEachProcess = 0;
            }
        }catch (Exception e){
            logger.error("User did not input a number when entering the amount of functions in an absolute process");
            return false;
        }

        return getSubClassesName(paramList, input);
    }
}

