package designPatterns;

import consts.Constants;

import java.util.ArrayList;
import java.util.Scanner;

public class AbstractFactory{
    private String designPatternType;
    private String mainInterfaceName;
    private int totalSubClasses;
    private int totalFuncs;
    private String abstFactoryName;
    private String abstFactoryMethodName;
    private ArrayList<Container> concreteClassList;

    // get the information for the super class and sub classes
    public AbstractFactory(){
        Scanner input = new Scanner(System.in);
        this.designPatternType = "abstract factory";
        Constants.printInstructions("interface");
        this.mainInterfaceName = input.nextLine();

        Constants.printInstructions("function amount");
        this.totalFuncs = Integer.parseInt(input.nextLine());
        Constants.printInstructions("amount of subclasses");
        this.totalSubClasses = Integer.parseInt(input.nextLine());

        // make the directory
        Constants.createDir(this.mainInterfaceName);

        // used to keep track of the concrete classes that implement the interface
        this.concreteClassList = new ArrayList<>();

        // to have a name for the create function and abstract factory
        this.abstFactoryName = mainInterfaceName+"AbstractFactory";
        this.abstFactoryMethodName = "create"+ mainInterfaceName;
    }

    public void createAbstractFactory(){
        // create interface for the product we are going to mass produce
        Container mainInterface = new Container("interface", mainInterfaceName, "",totalFuncs);
        mainInterface.setDirName(mainInterfaceName);
        Constants.createContainerStub(mainInterface);

        // adding the function stubs
        for(int i = 0; i < mainInterface.functionAmount; i++){
            mainInterface.text += String.format(Constants.makeFuncStubs(false,"interface function gen"),i);
        }

        // ready to create main interface file
        Constants.createFile(mainInterface);
        //now create the subclasses
        createFactorySubclasses();
        //create factory interface
        createFactoryInterface();
        // create the factories for the concrete subclasses classes
        createFactoryForConcreteClasses();
        //create the main factory that will create the products from the different factories
        createInterfaceFactoryMaker();
    }

    /*
    creates the sub classes (products) created by factory and the interface that will be implemented by all factories
     */
    private void createFactorySubclasses(){
        Scanner input = new Scanner(System.in);
        String name = "";
        for(int i = 0; i < totalSubClasses; i++){
            Constants.printInstructions("regular class");
            name = input.nextLine();
            Container subClass = new Container("regular class",name,mainInterfaceName,totalFuncs);
            subClass.setImplement(true);
            subClass.setDirName(mainInterfaceName);
            Constants.createSubClass(subClass);
            Constants.createFile(subClass);

            // now add the new concrete class into the list
            concreteClassList.add(subClass);
        }
    }

    /*
    creates the factory that returns an instance of the main interface
     */
    private void createFactoryInterface(){
        Container factoryInterface = new Container("interface",abstFactoryName,"",1);
        factoryInterface.setDirName(mainInterfaceName);

        //now make the text for this interface
        Constants.createContainerStub(factoryInterface);
        factoryInterface.text += String.format(Constants.makeFuncStubs(false,"interface function with return and name"),mainInterfaceName,abstFactoryMethodName);
        Constants.createFile(factoryInterface);
    }

    /*
    create factory interfaces based on the concrete classes made
    and implement the factory interface
     */
    private void createFactoryForConcreteClasses(){
        for(int i = 0; i < totalSubClasses; i++){
            Container subFactory = new Container("regular class",concreteClassList.get(i).name + "Factory",abstFactoryName,1);
            subFactory.setImplement(true);
            subFactory.setDirName(mainInterfaceName);

            Constants.createContainerStub(subFactory);
            subFactory.text += String.format(Constants.makeFuncStubs(true,"function with return and name"),mainInterfaceName,abstFactoryMethodName,concreteClassList.get(i).name);
            // now make constructor
            subFactory.text += String.format(Constants.makeFuncStubs(false,"constructor"), subFactory.name);
            Constants.createFile(subFactory);
        }
    }

    /*
    creates the factory that makes the abstract factories
    that will create an instance of a product of type main interface
    will only have 1 public static method that will create a product
    from the given factory
     */
    private void createInterfaceFactoryMaker(){
        Container mainFactory = new Container("regular class",mainInterfaceName+"Factory","",1);
        // implements and extends are to be set to false since this is going to be a regular class
        mainFactory.setDirName(mainInterfaceName);

        Constants.createContainerStub(mainFactory);
        //create function stub
        mainFactory.text += "\t"+ Constants.PublicStatic + mainInterfaceName + " " + abstFactoryMethodName;
        mainFactory.text += String.format("(%s %s){\n\treturn %s.%s();\n\t}\n",abstFactoryName,"af","af",abstFactoryMethodName);
        Constants.createFile(mainFactory);
    }
}