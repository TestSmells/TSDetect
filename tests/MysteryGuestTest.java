import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MysteryGuestTest {
    @Test
    public void MysteryGuestTest() throws Exception {
        File tempFile = File.createTempFile("testtempfile-", ".txt");

        try{
            String b = "testy text for the testy file";
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            bw.write(b);

            assertEquals("test", "test");
        }finally{
            if(tempFile != null){
                tempFile.delete();
            }
        }
    }
}
