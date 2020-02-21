package designPatterns;

import consts.MyConstants;
import consts.Tools;

import java.util.ArrayList;

/*
the design pattern revolves around one idea (interface) that will be
implemented by many different concrete classes. Then the concrete classes
will have their own factories that will create instances of those
concrete classes that implement the interface (idea). All these factories will
be together in ONE factory (using an interface that will produce the given concrete object from
the correct factory)
 */
public class AbstractFactory extends DesignPatternObj{
    private String mainInterfaceName;
    private int totalFuncs;
    private String abstFactoryName;
    private String abstFactoryMethodName;
    private ArrayList<String> abstractFactoryParams;

    // get the information for the super class and sub classes
    public AbstractFactory(){
        // order of params: main interface, function amount, amount of subclasses and names of subclasses
        abstractFactoryParams = Tools.getParamsForPattern("abstract factory");
        parseDesignPatternParams(abstractFactoryParams);
        // make the directory
        MyConstants.createDir(this.mainInterfaceName);

        // to have a name for the create function and abstract factory
        this.abstFactoryName = mainInterfaceName+"AbstractFactory";
        this.abstFactoryMethodName = "create"+ mainInterfaceName;
    }

    public void createAbstractFactory(){
        // create interface for the product we are going to mass produce
        Container mainInterface = new Container("interface", mainInterfaceName, "",totalFuncs);
        mainInterface.setDirName(mainInterfaceName);
        MyConstants.createContainerStub(mainInterface);

        // adding the function stubs
        for(int i = 0; i < mainInterface.functionAmount; i++){
            mainInterface.text += String.format(MyConstants.InterfaceFunctionGenericSig,i);
        }

        // ready to create main interface file
        MyConstants.createFile(mainInterface);
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
        String name = "";
        // 2 because of the position of the names starts at 2 in the abstractFactoryParams list
        for(int i = 2; i < abstractFactoryParams.size(); i++){
            ArrayList<String> subClassParams = new ArrayList<>();
            name = abstractFactoryParams.get(i);
            Container subClass = new Container("regular class",name,mainInterfaceName,totalFuncs);
            subClass.setImplement(true);
            subClass.setDirName(mainInterfaceName);

            MyConstants.createContainerStub(subClass);
            // will need the override because implementing an interface
            for(int j = 0; j < this.totalFuncs; j++){
                subClass.text += MyConstants.OverrideRegularFunctionGenericSig;
                subClassParams.add(String.valueOf(j));
            }
            // now finalize this subclass
            subClassParams.add(subClass.name);
            subClass.text += MyConstants.ConstructorSig;
            subClass.formatTextTest(subClassParams);

            MyConstants.createFile(subClass);
        }
    }

    /*
    creates the factory that returns an instance of the main interface
     */
    private void createFactoryInterface(){
        Container factoryInterface = new Container("interface",abstFactoryName,"",1);
        ArrayList<String> params = new ArrayList<>();
        params.add(mainInterfaceName);
        params.add(abstFactoryMethodName);
        factoryInterface.setDirName(mainInterfaceName);

        //now make the text for this interface and create the file
        MyConstants.createContainerStub(factoryInterface);
        factoryInterface.text += MyConstants.InterfaceFuncWReturnAndNameSig;
        factoryInterface.formatTextTest(params);
        MyConstants.createFile(factoryInterface);
    }

    /*
    create factory interfaces based on the concrete classes made
    and implement the factory interface
     */
    private void createFactoryForConcreteClasses(){
        // 2 because of the position of the names starts at 2 in the abstractFactoryParams list
        for(int i = 2; i < abstractFactoryParams.size(); i++){
            Container subFactory = new Container("regular class",abstractFactoryParams.get(i) + "Factory",abstFactoryName,1);
            subFactory.setImplement(true);
            subFactory.setDirName(mainInterfaceName);

            MyConstants.createContainerStub(subFactory);
            subFactory.text += String.format(MyConstants.OverrideFunctionWReturnAndNameSig,mainInterfaceName,abstFactoryMethodName);
            subFactory.text += String.format(MyConstants.ReturnNewStub,abstractFactoryParams.get(i));
            // now make constructor
            subFactory.text += String.format(MyConstants.ConstructorSig,subFactory.name);
            MyConstants.createFile(subFactory);
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

        MyConstants.createContainerStub(mainFactory);
        //create function stub
        mainFactory.text += "\t"+ MyConstants.PublicStatic + mainInterfaceName + " " + abstFactoryMethodName;
        mainFactory.text += String.format("(%s %s){\n\treturn %s.%s();\n\t}\n",abstFactoryName,"af","af",abstFactoryMethodName);
        MyConstants.createFile(mainFactory);
    }

    @Override
    public void parseDesignPatternParams(ArrayList<String> paramList) {
        this.mainInterfaceName = abstractFactoryParams.get(0);
        this.totalFuncs = Integer.parseInt(abstractFactoryParams.get(1));
    }
}