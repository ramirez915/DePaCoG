package designPatterns;

//import consts.Constants;
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

        this.concreteClassList = new ArrayList<>();
        this.abstFactoryName = mainInterfaceName+"AbstractFactory";
        this.abstFactoryMethodName = "create"+ mainInterfaceName;
    }

    public void createAbstractFactory(){
        // create interface for the product we are going to mass produce
        Container mainInterface = new Container("interface", mainInterfaceName, "",totalFuncs);
        mainInterface.setDirName(mainInterfaceName);
        String mainInterfaceText = Constants.createContainerStub(mainInterface);

        // adding the function stubs
        for(int i = 0; i < mainInterface.functionAmount; i++){
            mainInterfaceText += Constants.makeFuncStubs(false,"regular function gen") + i + "();\n";
        }

        // ready to create main interface file
        Constants.createFile(mainInterface,mainInterfaceText);
        //now create the subclasses
        createFactorySubclasses();
        //create factory interface
        createFactoryInterface();
        // create the factories for the concrete subclasses classes
        createFactoryForConcreteClasses();
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
            String subClassText = Constants.createSubClass(subClass);
            Constants.createFile(subClass,subClassText);

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
        String factoryInterfaceText = Constants.createContainerStub(factoryInterface);
        factoryInterfaceText += Constants.makeFuncStubs(false,"regular function") + mainInterfaceName + " " + abstFactoryMethodName + "();\n";
        Constants.createFile(factoryInterface,factoryInterfaceText);
    }

    /*
    create factory interfaces based on the concrete classes made
    and implement the factory interface
     */
    private void createFactoryForConcreteClasses(){
        for(int i = 0; i < totalSubClasses; i++){
            Container subClass = new Container("regular class",concreteClassList.get(i).name + "Factory",abstFactoryName,1);
            subClass.setImplement(true);
            subClass.setDirName(mainInterfaceName);

            String subClassText = Constants.createContainerStub(subClass);
            subClassText += Constants.makeFuncStubs(true,"regular function") + mainInterfaceName + " " + abstFactoryMethodName + "(){\n\t}\n";
            Constants.createFile(subClass,subClassText);
        }
    }
}