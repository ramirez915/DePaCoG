import designPatterns.AbstractFactory;
import designPatterns.Container;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class AbstractFactoryTest {
    public static String inputData;
    public static InputStream stdin;
    public static String[] inputParts;
    public static File dir;
    public static String path;

    /*
    create the abstract factory that is going to be used
    throughout the tests
     */
    @BeforeAll
    public static void setUp(){
        // **Arrange**
        // input data to create the Abstract Factory
        inputData = "Car\n2\n3\nHonda\nNissan\nFord\n";
        inputParts = inputData.split("\n");
        stdin = System.in;
    }

    /*
    delete all the contents of the directory that was created
     */
    @AfterEach
    public void tearDown(){
        File[] files = dir.listFiles();
        if(files != null && files.length > 0){
            for(File file: files){
//                System.out.println("deleting file");
                file.delete();
            }
        }
        dir.delete();
//        System.out.println("directory deleted");
    }

    /*
    order: the main interface, amount of functions,
    amount of subclasses, name of subclasses

    Will test if the inputs yield the correct parts
    associated with the building of the Abstract Factory.
    The parsing happens inside the constructor
     */
    @Test
    public void parseNeededInputTest(){
        try {
            // **act**
            System.setIn(new ByteArrayInputStream(inputData.getBytes()));
            AbstractFactory abf = new AbstractFactory();
            path = System.getProperty("user.dir") + "/" + abf.getMainInterfaceName();
            dir = new File(path);

            // **assert**
            Assertions.assertEquals(abf.getMainInterfaceName(),inputParts[0]);
            Assertions.assertEquals(abf.getTotalFunctions(),Integer.parseInt(inputParts[1]));

            // subclasses names
            Assertions.assertEquals(abf.getDesPatParams().get(2),inputParts[3]);        //honda
            Assertions.assertEquals(abf.getDesPatParams().get(3),inputParts[4]);        //nissan
            Assertions.assertEquals(abf.getDesPatParams().get(4),inputParts[5]);        //ford

            // since the abstract factory does not need the number of subclasses after the names are gotten
            // that parameter can be ignored thus subtracting one from the length of the inputParts
            Assertions.assertEquals(abf.getDesPatParams().size(),inputParts.length-1);

            // directory created?
            Assertions.assertTrue(dir.exists());
        } finally {
            System.setIn(stdin);
        }
    }

    /*
    Tests the creation of the correct subclasses
    based on the preset input.
     */
    @Test
    public void subClassesTest(){
        // **Arrange**
        Container honda = new Container("regular class","Honda","Car",2);
        Container nissan = new Container("regular class","Nissan","Car",2);
        Container ford = new Container("regular class","Ford","Car",2);
        ArrayList<Container> testList = new ArrayList<>();
        testList.add(honda);
        testList.add(nissan);
        testList.add(ford);
        int index = 0;
        try {
            // **act**
            System.setIn(new ByteArrayInputStream(inputData.getBytes()));
            AbstractFactory abf = new AbstractFactory();
            abf.createAbstractFactory();
            path = System.getProperty("user.dir") + "/" + abf.getMainInterfaceName();
            dir = new File(path);

            // **assert**
            Assertions.assertEquals(abf.getSubClassList().size(),testList.size());      // same size subclasses list

            // since same size then check their attributes
            for(Container subClass: abf.getSubClassList()){
                Assertions.assertEquals(subClass.type,testList.get(index).type);
                Assertions.assertEquals(subClass.name,testList.get(index).name);
                Assertions.assertEquals(subClass.partOf,testList.get(index).partOf);
                Assertions.assertEquals(subClass.functionAmount,testList.get(index).functionAmount);
                index++;
            }

        } finally {
            System.setIn(stdin);
        }
    }
}
