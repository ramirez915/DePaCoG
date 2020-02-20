package designPatterns;

import java.util.ArrayList;

public abstract class DesignPatternObj {
    protected String designPatternType;
    protected String mainInterfaceName;                 // maybe make this exclusive to abstract factory
    protected int totalFuncs;
    protected String abstFactoryName;                   // maybe make this exclusive to abstract factory
    protected String abstFactoryMethodName;             // this too
    protected ArrayList<Container> subClassList;

    /*
     will parse out the parameters gotten from the user
     for the corresponding design pattern
     */
    public abstract void parseDesignPatternParams(ArrayList<String> paramList);

}
