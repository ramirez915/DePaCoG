package designPatterns;

//import consts.Constants;
import consts.Constants2;

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
        Constants2.printInstructions("interface");
        this.mainInterfaceName = input.nextLine();

        Constants2.printInstructions("function amount");
        this.totalFuncs = Integer.parseInt(input.nextLine());
        Constants2.printInstructions("amount of subclasses");
        this.totalSubClasses = Integer.parseInt(input.nextLine());

        // make the directory
        Constants2.createDir(this.mainInterfaceName);

        this.concreteClassList = new ArrayList<>();
        this.abstFactoryName = mainInterfaceName+"AbstractFactory";
        this.abstFactoryMethodName = "create"+ mainInterfaceName;
    }

    public void createAbstractFactory(){
        // create interface for the product we are going to mass produce
        Container mainInterface = new Container("interface", mainInterfaceName, "",totalFuncs);
        mainInterface.setDirName(mainInterfaceName);
        String mainInterfaceText = Constants2.createContainerStub(mainInterface);

        // adding the function stubs
        for(int i = 0; i < mainInterface.functionAmount; i++){
            mainInterfaceText += Constants2.makeFuncStubs(false,"regular function gen") + i + "();\n";
        }

        // ready to create main interface file
        Constants2.createFile(mainInterface,mainInterfaceText);
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
            Constants2.printInstructions("regular class");
            name = input.nextLine();
            Container subClass = new Container("regular class",name,mainInterfaceName,totalFuncs);
            subClass.setImplement(true);
            subClass.setDirName(mainInterfaceName);
            String subClassText = Constants2.createSubClass(subClass);
            Constants2.createFile(subClass,subClassText);

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
        String factoryInterfaceText = Constants2.createContainerStub(factoryInterface);
        factoryInterfaceText += Constants2.makeFuncStubs(false,"regular function") + mainInterfaceName + " " + abstFactoryMethodName + "();\n";
        Constants2.createFile(factoryInterface,factoryInterfaceText);
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

            String subClassText = Constants2.createContainerStub(subClass);
            subClassText += Constants2.makeFuncStubs(true,"regular function") + mainInterfaceName + " " + abstFactoryMethodName + "(){\n\t}\n";
            Constants2.createFile(subClass,subClassText);
        }
    }
}