package consts;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import designPatterns.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class MyConstants {
    static Config consts = ConfigFactory.load("javaWay.conf");
    final static Logger logger = LoggerFactory.getLogger("MyConstants");

    // prompts to user
    public static String AbstractClassPrompt = consts.getString("AbstractClassPrompt");
    public static String RegularClassPrompt = consts.getString("RegularClassPrompt");
    public static String AbstractFunctionAmountPrompt = consts.getString("AbstractFunctionAmountPrompt");
    public static String FunctionAmountPrompt = consts.getString("FunctionAmountPrompt");
    public static String AmountSubclassesPrompt = consts.getString("AmountSubclassesPrompt");
    public static String InterfacePrompt = consts.getString("InterfacePrompt");
    public static String VariableTypePrompt = consts.getString("VariableTypePrompt");
    public static String VariableAmountPrompt = consts.getString("VariableAmountPrompt");
    public static String MandatoryAttributeAmountPrompt = consts.getString("MandatoryAttributeAmountPrompt");
    public static String OptionalAttributeAmountPrompt = consts.getString("OptionalAttributeAmountPrompt");
    public static String AbsoluteProcessAmountPrompt = consts.getString("AbsoluteProcessAmountPrompt");
    public static String AmountOfFunctionsInAbsoluteProcess = consts.getString("AmountOfFunctionsInAbsoluteProcess");

    // signatures for the classes, functions, and interfaces
    public static String AbstractClassSig = consts.getString("ABSTRACT CLASS");
    public static String RegularClassSig = consts.getString("CLASS");
    public static String StaticClassSig = consts.getString("STATIC CLASS");
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
    public static String ReturnSomethingStub = consts.getString("RETURN SOMETHING");
    public static String GenericFunctionCall = consts.getString("GENERIC FUNCTION CALL");
    public static String FinalFunctionGenericSig = consts.getString("FINAL FUNCTION GEN");

    public static String InterfaceSig = consts.getString("INTERFACE");
    public static String ClassImplementsSig = consts.getString("CLASS IMPLEMENTS");
    public static String ClassExtendsSig = consts.getString("CLASS EXTENDS");
    public static String ConstructorSig = consts.getString("CONSTRUCTOR");
    public static String PrivateConstructorSig = consts.getString("PRIVATE CONSTRUCTOR");

    public static String VarDeclaration = consts.getString("VAR DECLARATION");
    public static String PrivateVarDeclaration = consts.getString("PRIVATE VAR");
    public static String GetterFuncSig = consts.getString("GETTER");
    public static String SetterWRetOptionSig = consts.getString("SETTER W RET OPTION");

    //switch
    public static String SwitchBeginStub = consts.getString("SWITCH");
    public static String CaseReturnNewStub = consts.getString("CASE RETURN NEW");
    public static String DefaultCaseStub = consts.getString("DEFAULT CASE");

    /*
    verifies if the desired design pattern is implemented
    or not in the config file
     */
    public static boolean verifyImplementation(String desPat){
        return consts.getBoolean(desPat.toUpperCase());
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
            logger.info("**{}** directory successfully made",name);
        } catch(Exception e){
            logger.error("**{}** directory could not be made",name);
        }
    }

    /*
    make variable stubs for the amount of variables in the array
     */
    public static void makeVariableStubs(String[] varTypeList,Container c,Boolean isPrivate){
        int counter = 0;
        if(!isPrivate){
            for(String s: varTypeList){
                c.text += String.format(VarDeclaration,s,counter);
                counter++;
            }
        }
        else{
            for(String s: varTypeList){
                c.text += String.format(PrivateVarDeclaration,s,counter);
                counter++;
            }
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
//            System.out.println("*****file written successfully*****");
            logger.info("{} file written successfully",f.getName());
        } catch (Exception e) {
            //log error
            logger.error("Failed to write to {} file",f.getName());
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
        else if(c.type.toUpperCase().compareTo("ABSTRACT CLASS") == 0){
            c.text += String.format(AbstractClassSig,c.name);
        }
        else if(c.type.toUpperCase().compareTo("STATIC CLASS") == 0){
            c.text += String.format(StaticClassSig,c.name);
        }
        // regular class
        else{
            c.text += String.format(RegularClassSig,c.name);
        }
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
        String dir = System.getProperty("user.dir") + "/" + c.dirName;
        File newFile = new File(dir,c.name + ".java");
        try {
            if (newFile.createNewFile()) {
                logger.info("File {} successfully created",newFile.getName());

                writeToFile(newFile,c.text);
            } else {
                logger.warn("Unable to create File **{}**, it already exists",newFile.getName());
            }
        } catch (Exception e) {
            logger.error("Some error occurred while creating the file {}",newFile.getName());
        }
    }
}
