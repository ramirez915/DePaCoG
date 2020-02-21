package designPatterns;

import java.util.ArrayList;

public abstract class DesignPatternObj {
    protected int totalFuncs;
    protected ArrayList<Container> subClassList;
    protected ArrayList<String> desPatParams;

    /*
     will parse out the parameters gotten from the user
     for the corresponding design pattern
     */
    public abstract void parseDesignPatternParams(ArrayList<String> paramList);

}
