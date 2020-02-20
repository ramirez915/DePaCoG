package consts;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import designPatterns.Container;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MyConstants {
    static Config consts = ConfigFactory.load("javaWay.conf");

    // prompts to user
    public static String AbstractClassPrompt = consts.getString("AbstractClassPrompt");
    public static String RegularClassPrompt = consts.getString("RegularClassPrompt");
    public static String AbstractFunctionAmountPrompt = consts.getString("AbstractFunctionAmountPrompt");
    public static String FunctionAmountPrompt = consts.getString("FunctionAmountPrompt");
    public static String AmountSubclassesPrompt = consts.getString("AmountSubclassesPrompt");
    public static String InterfacePrompt = consts.getString("InterfacePrompt");
    public static String VariableTypePrompt = consts.getString("VariableTypePrompt");
    public static String VariableAmountPrompt = consts.getString("VariableAmountPrompt");

    // signatures for the classes, functions, and interfaces
    public static String AbstractClassSig = consts.getString("ABSTRACT CLASS");
    public static String RegularClassSig = consts.getString("CLASS");
    public static String PublicStatic = consts.getString("STATIC");

    public static String AbstractFunctionGenericSig = consts.getString("ABSTRACT FUNCTION GEN");
    public static String RegularFunctionGenericSig = consts.getString("REGULAR FUNCTION GEN");
    public static String StaticFunctionGenericSig = consts.getString("STATIC FUNCTION GEN");
    public static String InterfaceFunctionGenericSig = consts.getString("INTERFACE FUNCTION GEN");
    public static String OverrideRegularFunctionGenericSig = consts.getString("OVERRIDE REGULAR FUNCTION GEN");
    public static String OverrideAbstractFunctionGenericSig = consts.getString("OVERRIDE ABSTRACT FUNCTION GEN");
    public static String FunctionWReturnAndNameSig = consts.getString("FUNCTION WITH RETURN AND NAME");
    public static String OverrideFunctionWReturnAndNameSig = consts.getString("OVERRIDE FUNCTION WITH RETURN AND NAME");
    public static String InterfaceFuncWReturnAndNameSig = consts.getString("INTERFACE FUNCTION WITH RETURN AND NAME");
    public static String ReturnNewStub = consts.getString("RETURN NEW");
    public static String ReturnNullStub = consts.getString("RETURN NULL");

    public static String InterfaceSig = consts.getString("INTERFACE");
    public static String ClassImplementsSig = consts.getString("CLASS IMPLEMENTS");
    public static String ClassExtendsSig = consts.getString("CLASS EXTENDS");
    public static String ConstructorSig = consts.getString("CONSTRUCTOR");

    public static String VarDeclaration = consts.getString("VAR DECLARATION");

    //switch
    public static String SwitchBeginStub = consts.getString("SWITCH");
    public static String CaseReturnNewStub = consts.getString("CASE RETURN NEW");
    public static String DefaultCaseStub = consts.getString("DEFAULT CASE");

    /*
    verifies if the desired design pattern is implemented
    or not in the config file
     */
    public static boolean verifyImplementation(String desPat){
        if(consts.getBoolean(desPat)){
            return true;
        }
        return false;
    }

    /*
    creates a directory for the new files
     */
    public static void createDir(String name){
        // get current working directory
        String path = System.getProperty("user.dir");
        path += "/" + name;
        try{
            File newDir = new File(path);
            newDir.mkdir();
        } catch(Exception e){
            System.out.println("could not create directory");
        }
    }

    /*
    make variable stubs for the amount of variables in the array
     */
    public static void makeVariableStubs(String[] varTypeList,Container c){
        int counter = 0;
        for(String s: varTypeList){
            c.text += String.format(VarDeclaration,s,counter);
            counter++;
        }
    }

    /*
    writes the given file once the whole string to be used is created
     */
    private static boolean writeToFile(File f,String stringToWrite) {
        try {
            // add the last } needed for the outer {
            stringToWrite += "}";

            // now write it all to the file
            FileWriter writer = new FileWriter(f);
            writer.write(stringToWrite);
            writer.close();

            // log that the string was written successfully *****************************
            System.out.println("*****file written successfully*****");
        } catch (Exception e) {
            //log error
            return false;
        }
        return true;
    }

    /*
    makes the string for the beginning of the class
     */
    public static void createContainerStub(Container c){
        // if the container is an interface
        if(c.type.toUpperCase().compareTo("INTERFACE") == 0){
            c.text += String.format(InterfaceSig, c.name);
        }
        // the class implements an interface
        else if(c.implement){
            c.text += String.format(ClassImplementsSig,c.name,c.partOf);
        }
        // it is a class that is extending another class
        else if(c.extend){
            c.text += String.format(ClassExtendsSig,c.name,c.partOf);
        }
        // some kind of super class
        else{
            c.text += String.format(RegularClassSig,c.name);
        }
    }

    /*
    puts together all the last parts that pertains to a class
    the last parameter which is the name for constructor
    and then appends the constructor to the text
    then creates the file
     */
    public static void finalizeClass(Container c,ArrayList<String> paramsList){
        paramsList.add(c.name);
        c.text += MyConstants.ConstructorSig;
        c.formatTextTest(paramsList);
    }

    /*
    creates the switch case stub
     */
    public static void createSwitchCase(Container c,ArrayList<Container> subClassList){
        c.text += SwitchBeginStub;
        for(Container subClass: subClassList){
            c.text += String.format(CaseReturnNewStub,subClass.name,subClass.name);
        }
        c.text += DefaultCaseStub;
    }

    /*
    creates the .java file once the text is ready
     */
    public static void createFile(Container c){
        try {
            String dir = System.getProperty("user.dir") + "/" + c.dirName;
            File newFile = new File(dir,c.name + ".java");

            // try creating the new .java file
            if (newFile.createNewFile()) {
                // log the creation of the file...
                System.out.println("File created: " + newFile.getName());
                writeToFile(newFile,c.text);
            } else {
                System.out.println("File already exists.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}