import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VerboseTest{

    //Test that is clearly too long and complex
    @Test
    public void TooLong(){
        int a = 1;
        int b = 2;
        int c = 3;
        int d = 4;
        int e = 5;
        int f = 6;
        int g = 7;
        int h = 8;
        int i = 9;
        int j = 10;
        int k = 11;
        int l = 12;
        int m = 13;
        int n = 14;
        int o = 15;
        int p = 16;
        int q = 17;
        int r = 18;
        int s = 19;
        int t = 20;
        int u = 21;
        int v = 22;
        int x = 23;
        int y = 24;
        int z = 25;
        int u = 26;
        int t = 27;
        assertNotEquals(a, b);
    }

    //Average size test
    @Test
    public void NotTooLong(){
        int a = 2;
        int b = 2;
        assertEquals(a, b);
    }

    @Test
    public void NothingInside(){

    }
}