package consts;

import java.util.ArrayList;
import java.util.Scanner;

public class Tools {
    /*
    prints out the correct prompts in order for the design patterns
     */
    public static ArrayList<String> getParamsForPattern(String designPattern){
        Scanner input = new Scanner(System.in);
        ArrayList<String> designPatterParams = new ArrayList<>();

        switch(designPattern.toUpperCase()){
            case "ABSTRACT FACTORY":
                // order: the main interface, amount of functions, amount of subclasses, name of subclasses
                abstractFactoryPrompts(designPatterParams,input);
                break;
            case "BUILDER":
                /*
                order or params: main class name, mandatory variable amount,
                optional amount, variable types (mandatory + optional)
                 */
                builderPrompts(designPatterParams,input);
                break;
            case "FACTORY METHOD":
                // order: abstract class name, variable amount,
                // variable types (size x so positions of following will be determined later),
                // abstract function amount, regular function amount
                factoryMethodPrompts(designPatterParams,input);
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
    private static void getSubClassesName(ArrayList<String> paramsList,Scanner input){
        System.out.println(MyConstants.AmountSubclassesPrompt);
        int totSubclasses = Integer.parseInt(input.nextLine());

        //get the names of the subclasses
        for(int i = 0; i < totSubclasses; i++){
            System.out.println(MyConstants.RegularClassPrompt);
            paramsList.add(input.nextLine());
        }
    }

    private static void abstractFactoryPrompts(ArrayList<String> paramList,Scanner input){
        System.out.println(MyConstants.InterfacePrompt);
        paramList.add(input.nextLine());
        System.out.println(MyConstants.FunctionAmountPrompt);
        paramList.add(input.nextLine());

        //get the names of the subclasses
        getSubClassesName(paramList,input);
    }

    private static void factoryMethodPrompts(ArrayList<String> paramList,Scanner input){
        System.out.println(MyConstants.AbstractClassPrompt);
        paramList.add(input.nextLine());

        System.out.println(MyConstants.VariableAmountPrompt);
        int totVariables = Integer.parseInt(input.nextLine());
        paramList.add(String.valueOf(totVariables));
        // keep asking the variable types for the total number of variables
        for(int i = 0; i < totVariables; i++){
            System.out.println(MyConstants.VariableTypePrompt);
            paramList.add(input.nextLine());
        }

        // error checks that the user puts in at least one abstract method
        int abstractFuncAmount = 0;
        while (abstractFuncAmount == 0){
            System.out.println(MyConstants.AbstractFunctionAmountPrompt);
            abstractFuncAmount = Integer.parseInt(input.nextLine());
        }
        paramList.add(String.valueOf(abstractFuncAmount));

        System.out.println(MyConstants.FunctionAmountPrompt);
        paramList.add(input.nextLine());

        getSubClassesName(paramList,input);
    }

    /*
    order or params: main class name, mandatory variable amount,
    optional amount, variable types (mandatory + optional)
     */
    private static void builderPrompts(ArrayList<String> paramList,Scanner input){
        System.out.println(MyConstants.RegularClassPrompt);
        paramList.add(input.nextLine());

        // error checks that the user puts in at least one mandatory attribute
        int mandatoryAttAmount = 0;
        while (mandatoryAttAmount == 0){
            System.out.println("***Mandatory attributes***");
            System.out.println(MyConstants.MandatoryAttributeAmountPrompt);
            mandatoryAttAmount = Integer.parseInt(input.nextLine());
        }
        paramList.add(String.valueOf(mandatoryAttAmount));

        // error checks that the user puts in at least one optional attribute
        int optionalAttAmount = 0;
        while (optionalAttAmount == 0){
            System.out.println("***Optional attributes***");
            System.out.println(MyConstants.OptionalAttributeAmountPrompt);
            optionalAttAmount = Integer.parseInt(input.nextLine());
        }
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
    }
}
