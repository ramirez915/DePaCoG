import designPatterns.Container;
import designPatterns.FactoryMethod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class FactoryMethodTest {
    public static String inputData;
    public static InputStream stdin;
    public static String[] inputParts;
    public static File dir;
    public static String path;

    @BeforeAll
    public static void setUp() {
        //**Arrange**
        inputData = "GameConsole\n2\nint\nString\n3\n1\n2\nGameBoy\nN64\n";       //2 subclasses
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
                file.delete();
            }
        }
        dir.delete();
    }

    /*
    Tests the parsing of the user inputs for Factory method.
    The parsing happens in the Factory Methods constructor

     order: abstract class name, variable amount,
     variable types (size x so positions of following will be determined later),
     abstract function amount, regular function amount
     */
    @Test
    public void factoryMethodParsingTest(){
        try {
            // **act**
            System.setIn(new ByteArrayInputStream(inputData.getBytes()));
            FactoryMethod fm = new FactoryMethod();
            path = System.getProperty("user.dir") + "/" + fm.getMainAbstractClassName();
            dir = new File(path);

            // **assert**
            Assertions.assertEquals(fm.getMainAbstractClassName(),inputParts[0]);                       //name
            Assertions.assertEquals(fm.getVariableTypes().length,Integer.parseInt(inputParts[1]));     //variable amount
            int index = 2;
            for(String s:fm.getVariableTypes()){
                Assertions.assertEquals(s,inputParts[index]);                                          // variable types
                index++;
            }
            Assertions.assertEquals(fm.getTotalAbstractMethods(),Integer.parseInt(inputParts[4]));     //abstract function amount
            Assertions.assertEquals(fm.getTotalRegularMethods(),Integer.parseInt(inputParts[5]));     //regular functions amount
            // directory created?
            Assertions.assertTrue(dir.exists());
        } finally {
            System.setIn(stdin);
        }
    }

    /*
    Tests is the subclasses are properly
    created for the Factory Method based
    on the preset inputs
     */
    @Test
    public void subClassesTest(){
        Container GameBoy = new Container("regular class","GameBoy","GameConsole",3);
        Container N64 = new Container("regular class","N64","GameConsole",3);
        ArrayList<Container> testList = new ArrayList<>();
        testList.add(GameBoy);
        testList.add(N64);
        int index = 0;
        try {
            // **act**
            System.setIn(new ByteArrayInputStream(inputData.getBytes()));
            FactoryMethod fm = new FactoryMethod();
            fm.createFactoryMethod();
            path = System.getProperty("user.dir") + "/" + fm.getMainAbstractClassName();
            dir = new File(path);

            // **assert**
            Assertions.assertEquals(fm.getSubClassList().size(),testList.size());

            for(Container subClass: fm.getSubClassList()){
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
