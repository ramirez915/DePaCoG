//package consts;
//
//import com.typesafe.config.Config;
//import com.typesafe.config.ConfigFactory;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.util.Scanner;
//
//public class Constants {
//    static Config consts = ConfigFactory.load("javaWay.conf");
//    /*
//    verifies if the desired design pattern is implemented
//    or not in the config file
//     */
//    public static boolean verifyImplementation(String desPat){
//        if(consts.getBoolean(desPat)){
//            return true;
//        }
//        return false;
//    }
//
//    /*
//    creates a directory for the new files                                   DONEEEEEEE
//     */
//    public static String createDir(String name){
//        // get current working directory
//        String path = System.getProperty("user.dir");
//        path += "/" + name;
//        try{
//            File newDir = new File(path);
//            newDir.mkdir();
//        } catch(Exception e){
//            System.out.println("could not create directory");
//        }
//        return path;
//    }
//
//    /*
//    gives correct prompt to user                                             DONEEEEEEEE
//     */
//
//    public static void printInstructions(String classType){
//        String toPrint;
//        switch(classType.toUpperCase()){
//            case "ABSTRACT SUPER CLASS":
//                toPrint = consts.getString("AbstractSuperClassPrompt");
//                break;
//            case "REGULAR CLASS":
//                toPrint = consts.getString("RegularClassPrompt");
//                break;
//            default:
//                toPrint = "some error";
//        }
//        System.out.println(toPrint);
//    }
//
//    /*
//    evaluates which type of class signature to use                                                      DONEEEEEEEEEEEEEE
//     */
//    public static void generateClassStub(File f, String className,String classTypeSig,String superClassName){
//        try{
//            String classStub;
//            if(superClassName != ""){
//                classStub = consts.getString(classTypeSig.toUpperCase()) + className + " extends " + superClassName + "{\n\n";
//            }
//            else{
//                classStub = consts.getString(classTypeSig.toUpperCase()) + className + "{\n\n";
//            }
//            String toWrite = generateFuncStubs(classStub,superClassName);
//            writeToFile(f,toWrite);
//        }catch(Exception e){
//            //log it
//            System.out.println("could not write class stub");
//        }
//    }
//
//    /*
//     appends to the total number of function stubs to the current                        DONEEEEEEEEE
//     template string that will be used to write to file
//     */
//    public static String generateFuncStubs(String toWrite,String superClassName){
//        Scanner userInput = new Scanner(System.in);
//        int counter = 0;
//
//        // get the total number of functions that are going to be needed
//        System.out.println("Enter total num of functions");
//        int totalFuncs = Integer.parseInt(userInput.nextLine());
//        while(counter < totalFuncs) {
//            // if subclass, will need to have the @Override
//            if(superClassName != ""){
//                toWrite += "\t@Override\n\tpublic void f" + counter + "(){\n\t}\n\n";
//            }
//            else{
//                toWrite += "\tpublic void f" + counter + "(){\n\t}\n\n";
//            }
//            counter++;
//        }
//        return toWrite;
//    }
//
//    /*
//    writes the the given file once the whole string to be used is created       DONEEEEEEEEE
//     */
//    public static boolean writeToFile(File f,String stringToWrite) {
//        try {
//            // add the last } needed for the outer {
//            stringToWrite += "}";
//
//            // now write it all to the file
//            FileWriter writer = new FileWriter(f);
//            writer.write(stringToWrite);
//            writer.close();
//
//            // log that the string was written successfully *****************************
//            System.out.println("*****file written successfully*****");
//        } catch (Exception e) {
//            //log error
//            return false;
//        }
//        return true;
//    }
//
//    /*
//    creates a class depending on the flag set. Super class, abstract class, regular class that extends      DONEEEEEEEEEEEEE
//     */
//    public static String createClass(String type,String superClassName){
//        Scanner userInput = new Scanner(System.in);
//        String className;
//
//        Constants.printInstructions(type);
//        className = userInput.nextLine();
//
//        try {
//            File newFile;
//            //if it is a sub class we are making, then add the file to the super class' directory that was created
//            if(superClassName != ""){
//                String path = System.getProperty("user.dir") + "/" + superClassName;
//                newFile = new File(path,className + ".java");
//            }
//            //create directory with super class' name
//            else{
//                String path = createDir(className);
//                newFile = new File(path,className + ".java");
//            }
//
//            // try creating the new .java file
//            if (newFile.createNewFile()) {
//                // log the creation of the file...
//                System.out.println("File created: " + newFile.getName());
//                generateClassStub(newFile,className,type,superClassName);
//            } else {
//                System.out.println("File already exists.");
//            }
//        } catch (Exception e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//
//        return className;
//    }
//
//    /*
//    prompt user to input the amount of sub classes of super class to make
//     */
//    public static int howManySubClasses(){
//        Scanner input = new Scanner(System.in);
//        System.out.println("How many sub classes?");
//        return Integer.parseInt(input.nextLine());
//    }
//}
