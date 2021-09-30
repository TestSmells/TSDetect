import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ResourceOptimismTest {
    @Test
    public void ResourceOptimism()
    {
        File f = new File("./test.txt'");
        assertEquals(f.getName(), "test.txt");
    }
}
