import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EagerTestRest {
    private class Example{
        private final int x;
        private final int y;
        private final int z;

        private Example(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }
    @Test
    public void Eager()
    {
        Example ex = new Example(1,2,3);
        assertEquals(ex.getX(),1);
        assertEquals(ex.getY(),2);
        assertEquals(ex.getZ(),3);
    }
}
