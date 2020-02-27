import designPatterns.Builder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class BuilderTest {
    public static String inputData;
    public static InputStream stdin;
    public static String[] inputParts;
    public static File dir;
    public static String path;

    @BeforeAll
    public static void setUp() {
        //**Arrange**
        inputData = "Phone\n2\n1\nString\nString\nint\n";
        inputParts = inputData.split("\n");
        stdin = System.in;
    }

    @AfterEach
    public void tearDown() {
        File[] files = dir.listFiles();
        if(files != null && files.length > 0){
            for(File file: files){
                file.delete();
            }
        }
        dir.delete();
    }

    /*
    tests the parsing of the user input.
    Builders parsing happens inside the constructor
     */
    @Test
    public void BuilderParsingTest(){
        int index = 3;      //index where the attributes start
        try {
            // **act**
            System.setIn(new ByteArrayInputStream(inputData.getBytes()));
            Builder b = new Builder();
            path = System.getProperty("user.dir") + "/" + b.getDesPatParams().get(0);
            dir = new File(path);

            // **assert**
            Assertions.assertEquals(b.getMainClassName(),inputParts[0]);                                    //name
            Assertions.assertEquals(b.getMandatoryAttributeAmount(),Integer.parseInt(inputParts[1]));       //mandatory attribute amount
            Assertions.assertEquals(b.getOptionalAttributeAmount(),Integer.parseInt(inputParts[2]));        //optional attributes amount

            for(String s: b.getMandatoryAttributeTypes()){                                                  //mandatory attributes
                Assertions.assertEquals(s,inputParts[index]);
                index++;
            }
            for(String s: b.getOptionalAttributeTypes()){                                                   //optional attributes
                Assertions.assertEquals(s,inputParts[index]);
            }
            // directory created?
            Assertions.assertTrue(dir.exists());
        } finally {
            System.setIn(stdin);
        }
    }

}
