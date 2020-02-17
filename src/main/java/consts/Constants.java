package consts;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import designPatterns.Container;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Constants {
    static Config consts = ConfigFactory.load("javaWay.conf");
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
    public static String createDir(String name){
        // get current working directory
        String path = System.getProperty("user.dir");
        path += "/" + name;
        try{
            File newDir = new File(path);
            newDir.mkdir();
        } catch(Exception e){
            System.out.println("could not create directory");
        }
        return path;
    }

    /*
    gives correct prompt to user
     */

    public static void printInstructions(String classType){
        String toPrint;
        switch(classType.toUpperCase()){
            case "ABSTRACT CLASS":
                toPrint = consts.getString("AbstractClassPrompt");
                break;
            case "REGULAR CLASS":
                toPrint = consts.getString("RegularClassPrompt");
                break;
            case "ABSTRACT FUNCTION AMOUNT":
                toPrint = consts.getString("AbstractFunctionAmountPrompt");
                break;
            case "FUNCTION AMOUNT":
                toPrint = consts.getString("FunctionAmountPrompt");
                break;
            case "AMOUNT OF SUBCLASSES":
                toPrint = consts.getString("AmountSubclassesPrompt");
                break;
            case "INTERFACE":
                toPrint = consts.getString("InterfacePrompt");
                break;
            default:
                toPrint = "some error";
        }
        System.out.println(toPrint);
    }

    /*
    makes the function stubs depending if needs to overridden or not
     */
    public static String makeFuncStubs(boolean isOverride, String funcType){
        String functionText = "";
        //if we are not creating functions to be overridden
        if(!isOverride){
            functionText = consts.getString(funcType.toUpperCase());
        }
        else{
            functionText = "\t@Override\n" + consts.getString(funcType.toUpperCase());
        }
        return functionText;
    }

    /*
    writes the given file once the whole string to be used is created
     */
    public static boolean writeToFile(File f,String stringToWrite) {
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
    public static String createContainerStub(Container c){
        System.out.println("creat contain");
        String textToWrite = "";

        // if the container is an interface
        if(c.type.toUpperCase().compareTo("INTERFACE") == 0){
            textToWrite += consts.getString(c.type.toUpperCase()) + c.name + "{\n\n";
        }
        // the class implements an interface
        else if(c.implement){
            textToWrite += consts.getString(c.type.toUpperCase()) + c.name + consts.getString("IMPLEMENTS") + c.partOf + "{\n\n";
        }
        // it is a class that is extending another class
        else if(c.extend){
            textToWrite += consts.getString(c.type.toUpperCase()) + c.name + consts.getString("EXTENDS") + c.partOf + "{\n\n";
        }
        // some kind of super class
        else{
            textToWrite += consts.getString(c.type.toUpperCase()) + c.name + "{\n\n";
        }
        return textToWrite;
    }

    /*
    creates the subclass string with the override and the extends
     */
    public static String createSubClass(Container c){
        String subClassText = createContainerStub(c);
        for(int i = 0; i < c.functionAmount;i++){
            subClassText += makeFuncStubs(true,"regular function") + i + "(){\n\t}\n\n";
        }

        return subClassText;
    }

    /*
    creates the .java file once the text is ready
     */
    public static void createFile(Container c, String toWrite){
        try {
            String dir = System.getProperty("user.dir") + "/" + c.dirName;
            File newFile = new File(dir,c.name + ".java");

            // try creating the new .java file
            if (newFile.createNewFile()) {
                // log the creation of the file...
                System.out.println("File created: " + newFile.getName());
                writeToFile(newFile,toWrite);
            } else {
                System.out.println("File already exists.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
