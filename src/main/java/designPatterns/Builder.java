package designPatterns;

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

 */
public class Builder extends DesignPatternObj{


    public void createBuilder(){

    }

    @Override
    public void parseDesignPatternParams(ArrayList<String> paramList) {

    }
}
