package consts;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import designPatterns.Container;

import java.io.File;
import java.io.FileWriter;

public class Constants {
    static Config consts = ConfigFactory.load("javaWay.conf");

    // prompts to user
    public static String AbstractClassPrompt = consts.getString("AbstractClassPrompt");
    public static String RegularClassPrompt = consts.getString("RegularClassPrompt");
    public static String AbstractFunctionAmountPrompt = consts.getString("AbstractFunctionAmountPrompt");
    public static String FunctionAmountPrompt = consts.getString("FunctionAmountPrompt");
    public static String AmountSubclassesPrompt = consts.getString("AmountSubclassesPrompt");
    public static String InterfacePrompt = consts.getString("InterfacePrompt");

    // signatures for the classes, functions, and interfaces
    public static String AbstractClassSig = consts.getString("ABSTRACT CLASS");
    public static String RegularClassSig = consts.getString("CLASS");
    public static String PublicStatic = consts.getString("STATIC");

    public static String AbstractFunctionGenericSig = consts.getString("ABSTRACT FUNCTION GEN");
    public static String RegularFunctionGenericSig = consts.getString("REGULAR FUNCTION GEN");
    public static String StaticFunctionGenericSig = consts.getString("STATIC FUNCTION GEN");
    public static String InterfaceGenericSig = consts.getString("INTERFACE FUNCTION GEN");
    public static String OverrideRegularFunctionGenericSig = consts.getString("OVERRIDE REGULAR FUNCTION GEN");
    public static String FunctionWReturnAndNameSig = consts.getString("FUNCTION WITH RETURN AND NAME");
    public static String InterfaceFuncWReturnAndNameSig = consts.getString("INTERFACE FUNCTION WITH RETURN AND NAME");

    public static String InterfaceSig = consts.getString("INTERFACE");
    public static String ClassImplementsSig = consts.getString("CLASS IMPLEMENTS");
    public static String ClassExtendsSig = consts.getString("CLASS EXTENDS");
    public static String ConstructorSig = consts.getString("CONSTRUCTOR");

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
                toPrint = AbstractClassPrompt;
                break;
            case "REGULAR CLASS":
                toPrint = RegularClassPrompt;
                break;
            case "ABSTRACT FUNCTION AMOUNT":
                toPrint = AbstractFunctionAmountPrompt;
                break;
            case "FUNCTION AMOUNT":
                toPrint = FunctionAmountPrompt;
                break;
            case "AMOUNT OF SUBCLASSES":
                toPrint = AmountSubclassesPrompt;
                break;
            case "INTERFACE":
                toPrint = InterfacePrompt;
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
    public static String createContainerStub(Container c){
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
        return c.text;

    }

    /*
    creates the subclass string with the override and the extends
     */
    public static String createSubClass(Container c){
        createContainerStub(c);
        for(int i = 0; i < c.functionAmount;i++){
            c.text += String.format(makeFuncStubs(true,"regular function gen"),i);
        }

        // lastly create the constructors
        c.text += String.format(makeFuncStubs(false,"constructor"),c.name);

        return c.text;
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
