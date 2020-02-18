package designPatterns;

/*
will contain the information for containers such as a class or an interface
 */
public class Container {
    public String type;         // interface, regular class, abstract class
    public String name;
    public String partOf;       // what this container is part of... extends a class or implements an interface
    public String dirName;
    public int functionAmount;
    public boolean implement;
    public boolean extend;
    public String text;         // whats going into the file

    public Container(String type, String name, String partOf, int functionAmount){
        this.type = type;
        this.name = name;
        this.partOf = partOf;
        this.dirName = "";
        this.functionAmount = functionAmount;
        this.implement = false;
        this.extend = false;
        this.text = "";
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public void setImplement(boolean tf){ implement = tf;}
    public void setExtend(boolean tf){ extend = tf;}
}
