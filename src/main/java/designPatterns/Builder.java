package designPatterns;

import consts.MyConstants;
import consts.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/*
has a main class that has certain mandatory attributes
and then some optional attributes (all private). The main class only has
getter functions for the attributes. Its constructor is
private, takes in an instance of the nested class, and only
accessible through the pubic static nested class (the builder).
The nested static class has the same mandatory AND optional
attributes and its public constructor that sets the mandatory attributes.
Has functions to set the optional attributes and returns the builder itself (this).
Has a function called build that creates the new instance of the outer class


take user input for whats needed. then make the main class stub along with the variables
then make getters and private constructor. Then make the public static nested class with the
same variables, its public constructor, setters then build function
 */
public class Builder extends DesignPatternObj{
    private String mainClassName;
    private int mandatoryAttributeAmount;
    private int optionalAttributeAmount;
    private String[] mandatoryAttributeTypes;
    private String[] optionalAttributeTypes;
    final static Logger logger = LoggerFactory.getLogger("Builder");

    public Builder(){
        this.desPatParams = Tools.getParamsForPattern("builder");
        parseDesignPatternParams(this.desPatParams);

        // make the directory
        MyConstants.createDir(this.mainClassName);
        logger.info("Builder parameters acquired successfully");
    }

    public void createBuilder(){
        //create main class
        int totalFunctionsNeeded = this.mandatoryAttributeAmount + this.optionalAttributeAmount;
        Container mainClass = new Container("regular class",this.mainClassName,"",totalFunctionsNeeded);
        makeMainClassStub(mainClass);


    }

    private void makeMainClassStub(Container mainClass){
        mainClass.setDirName(this.mainClassName);
        MyConstants.createContainerStub(mainClass);

        // add in attributes
        MyConstants.makeVariableStubs(this.mandatoryAttributeTypes,mainClass,true);
        MyConstants.makeVariableStubs(this.optionalAttributeTypes,mainClass,true);

        // make getters for attributes mandatory then optional
        int counter = 0;
        for(String s: this.mandatoryAttributeTypes){
            mainClass.text += String.format(MyConstants.GetterFuncSig,s,counter);
            mainClass.text += MyConstants.ReturnNullStub;
            counter++;
        }
        for(String s: this.optionalAttributeTypes){
            mainClass.text += String.format(MyConstants.GetterFuncSig,s,counter);
            mainClass.text += MyConstants.ReturnNullStub;
            counter++;
        }

        //private constructor
        mainClass.text += String.format(MyConstants.PrivateConstructorSig,mainClass.name);

        // now make nested static class
        Container nestedStaticClass = createNestedStaticClass();

        // merge the nested class text with the main class'
        mainClass.text += nestedStaticClass.text;
        MyConstants.createFile(mainClass);
    }

    /*
    builds the text for the nested static class
     */
    private Container createNestedStaticClass(){
        Container nestedClass = new Container("static class",this.mainClassName +"Builder","",4);
        MyConstants.createContainerStub(nestedClass);
        //add the same attributes to this nested class
        MyConstants.makeVariableStubs(this.mandatoryAttributeTypes,nestedClass,true);
        MyConstants.makeVariableStubs(this.optionalAttributeTypes,nestedClass,true);

        // setters for the optional attributes
        int counter = 0;
        for(String s: this.optionalAttributeTypes){
            nestedClass.text += String.format(MyConstants.SetterWRetOptionSig,nestedClass.name,counter,s);
            nestedClass.text += String.format(MyConstants.ReturnSomethingStub,"this",counter,s);
            counter++;
        }

        // now do build function
        nestedClass.text += String.format(MyConstants.FunctionWReturnAndNameSig,this.mainClassName,"build");
        nestedClass.text += String.format(MyConstants.ReturnSomethingStub,"new " + this.mainClassName + "(this)");

        // now public constructor
        nestedClass.text += String.format(MyConstants.ConstructorSig,nestedClass.name);

        // add the last } needed for this nested class
        nestedClass.text += "}\n";
        return nestedClass;
    }

    /*
    order or params: main class name, mandatory variable amount,
    optional amount, variable types (mandatory + optional)
     */
    @Override
    public void parseDesignPatternParams(ArrayList<String> paramList) {
        this.mainClassName = paramList.get(0);
        this.mandatoryAttributeAmount = Integer.parseInt(paramList.get(1));
        this.optionalAttributeAmount = Integer.parseInt(paramList.get(2));
        this.mandatoryAttributeTypes = new String[this.mandatoryAttributeAmount];
        this.optionalAttributeTypes = new String[this.optionalAttributeAmount];

        // get the mandatory attribute types
        int index = 3;
        for(int i = 0; i < this.mandatoryAttributeAmount; i++){
            mandatoryAttributeTypes[i] = paramList.get(index);
            index++;
        }

        // now get the optional
        for(int i = 0; i < this.optionalAttributeAmount; i++){
            this.optionalAttributeTypes[i] = paramList.get(index);
            index++;
        }
    }
}
