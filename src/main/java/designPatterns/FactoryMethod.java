package designPatterns;


import consts.MyConstants;
import consts.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/*
the design pattern has ONE abstract class that will have subclasses
that extend it. The factory is in charge of returning an instance of the
desired class that extends the abstract class. Will have switch case to determine
which subclass to generate in the factory itself.

--------------------------- idea
* create abstract class with its one abstract method and how ever many
methods needed (regular or abstract) along with variables.

* create x amount of subclasses with the corresponding
overridden abstract functions (ask for variables and functions???)

* create factory with switch to return the created subclasses
---------------------------
 */
public class FactoryMethod extends DesignPatternObj{
    private String mainAbstractClassName;
    private int totalAbstractMethods;
    private int totalRegularMethods;
    private String[] variableTypes;
    private int namesStartIndex;
    private String mainFactoryName;
    final static Logger logger = LoggerFactory.getLogger("Factory Method");


    public FactoryMethod(){
        // order or params: abstract class name, variable amount,
        // variable types (x amount) abstract function amount, regular function amount
        this.desPatParams = new ArrayList<>();
        desPatParams = Tools.getParamsForPattern("factory method");
        parseDesignPatternParams(this.desPatParams);
        this.mainFactoryName = this.mainAbstractClassName + "Factory";
        this.subClassList = new ArrayList<>();

        // make the directory
        MyConstants.createDir(this.mainAbstractClassName);
        logger.info("Factory method parameters acquired successfully");
    }

    /*
    overall main method that will create the factory method design pattern code
     */
    public void createFactoryMethod(){
        //create the container for the abstract class and the stub
        Container mainAbstractClass = new Container("abstract class",this.mainAbstractClassName,"",this.totalFuncs);
        mainAbstractClass.setDirName(mainAbstractClassName);
        MyConstants.createContainerStub(mainAbstractClass);

        // add variables first
        MyConstants.makeVariableStubs(this.variableTypes,mainAbstractClass,false);
        // now add the abstract methods
        for(int i = 0; i < this.totalAbstractMethods; i++){
            mainAbstractClass.text += String.format(MyConstants.AbstractFunctionGenericSig,i);
        }
        // add regular functions
        for(int i = 0; i < this.totalRegularMethods; i++){
            mainAbstractClass.text += String.format(MyConstants.RegularFunctionGenericSig,i);
        }

        // now create the file for this container
        MyConstants.createFile(mainAbstractClass);
        //create subclasses
        createFactorySubClasses();
        //main factory
        createMainFactory();
        logger.info("Factory method for {} successfully created",this.mainAbstractClassName);
    }

    private void createFactorySubClasses(){
        for(int i = this.namesStartIndex; i < this.desPatParams.size(); i++){
            // for these sub classes get the total abstract methods when creating the container
            ArrayList<String> subClassParams = new ArrayList<>();
            Container subClass = new Container("regular class",this.desPatParams.get(i),this.mainAbstractClassName,this.totalAbstractMethods);
            subClass.setExtend(true);
            subClass.setDirName(this.mainAbstractClassName);

            //now ready to make the container stub
            MyConstants.createContainerStub(subClass);
            //now add in the function stubs starting with the abstract ones
            for(int j = 0; j < this.totalAbstractMethods; j++){
                subClass.text += MyConstants.OverrideAbstractFunctionGenericSig;
                subClassParams.add(String.valueOf(j));
            }

            subClassParams.add(subClass.name);
            subClass.text += MyConstants.ConstructorSig;
            subClass.formatTextTest(subClassParams);
            this.subClassList.add(subClass);
            MyConstants.createFile(subClass);
        }
    }

    /*
    creates the factory code that contains the switch statements
    for the creation of the corresponding class
     */
    private void createMainFactory(){
        Container mainFactory = new Container("regular class",this.mainFactoryName,"",1);
        mainFactory.setDirName(this.mainAbstractClassName);
        MyConstants.createContainerStub(mainFactory);

        // create function stub that will return a mainAbstractClass type and take in a string as param
        String functionName = "get"+ this.mainAbstractClassName;
        mainFactory.text += String.format(MyConstants.FunctionWReturnAndNameSig,this.mainAbstractClassName,functionName);
        MyConstants.createSwitchCase(mainFactory,this.subClassList);
        mainFactory.text += MyConstants.ReturnNullStub;
        MyConstants.createFile(mainFactory);
    }

    @Override
    public void parseDesignPatternParams(ArrayList<String> paramList) {
        this.mainAbstractClassName = paramList.get(0);

        int totalVariables = Integer.parseInt(paramList.get(1));
        this.variableTypes = new String[totalVariables];
        int index = 2;
        // keep asking the variable types for the total number of variables
        for(int i = 0; i < totalVariables; i++){
            this.variableTypes[i] = paramList.get(index);
            index++;
        }
        // index is up to where it is needed to get the correct next parameters
        this.totalAbstractMethods = Integer.parseInt(paramList.get(index));
        this.totalRegularMethods = Integer.parseInt(paramList.get(index+1));
        // from here on out all that remains are the names of the sub classes
        this.namesStartIndex = index + 2;
    }

    public String getMainAbstractClassName(){ return this.mainAbstractClassName;}
    public int getTotalAbstractMethods(){return this.totalAbstractMethods;}
    public int getTotalRegularMethods(){return this.totalRegularMethods;}
    public String[] getVariableTypes(){return this.variableTypes;}
}
