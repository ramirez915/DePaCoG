import designPatterns.Container;
import designPatterns.Template;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class TemplateTest {
    public static String inputData;
    public static InputStream stdin;
    public static String[] inputParts;
    public static File dir;
    public static String path;

    @BeforeAll
    public static void setUp() {
        /*
        **Arrange**
        input data order: name of abstract class, abstract function amount,
        regular function amount, absolute process amount,
        amount of functions in process i,subclass amount (x),names (x amount)
         */
        inputData = "ManualCar\n2\n0\n3\n6\n3\n2\n3\nEvo\nSi\nMustang\n";
        inputParts = inputData.split("\n");
        stdin = System.in;
    }

    @AfterEach
    void tearDown() {
        File[] files = dir.listFiles();
        if(files != null && files.length > 0){
            for(File file: files){
                file.delete();
            }
        }
        dir.delete();
    }

    /*
    testing the parsing for Template Method.
    Parsing happens in the constructor.
    For the template method everything was done
    in the parsing, the subclasses were created in
    the parsing function
    */

    @Test
    public void templateParsingTest(){
        //**Arrange**
        Container Evo = new Container("regular class","Evo","ManualCar",2);
        Container Si = new Container("regular class","Si","ManualCar",2);
        Container Mustang = new Container("regular class","Mustang","ManualCar",2);
        ArrayList<Container> testList = new ArrayList<>();
        int index = 4;          // where the total functions in each process starts
        int subclassIndex = 0;
        try {
            // **act**
            System.setIn(new ByteArrayInputStream(inputData.getBytes()));
            Template t = new Template();
            path = System.getProperty("user.dir") + "/" + t.getMainAbstractClassName();
            dir = new File(path);
            testList.add(Evo);
            testList.add(Si);
            testList.add(Mustang);

            // **assert**
            Assertions.assertEquals(t.getMainAbstractClassName(),inputParts[0]);
            Assertions.assertEquals(t.getTotalAbstractFunctions(),Integer.parseInt(inputParts[1]));
            Assertions.assertEquals(t.getDesPatParams().get(2),inputParts[2]);
            Assertions.assertEquals(t.getTotalAbsoluteProcesses(),Integer.parseInt(inputParts[3]));
            for(int i: t.getFunctionsInEachProcess()){
                Assertions.assertEquals(i,Integer.parseInt(inputParts[index]));
                index++;
            }
            // now check subclasses
            Assertions.assertEquals(t.getSubClassList().size(),testList.size());
            for(Container subclass: t.getSubClassList()){
                Assertions.assertEquals(subclass.type,testList.get(subclassIndex).type);
                Assertions.assertEquals(subclass.name,testList.get(subclassIndex).name);
                Assertions.assertEquals(subclass.partOf,testList.get(subclassIndex).partOf);
                Assertions.assertEquals(subclass.functionAmount,testList.get(subclassIndex).functionAmount);
                subclassIndex++;
            }
            Assertions.assertTrue(dir.exists());
        } finally {
            System.setIn(stdin);
        }
    }
}
