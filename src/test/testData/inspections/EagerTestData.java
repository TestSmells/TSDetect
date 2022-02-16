public class EagerTestData {

    @Test
    public void Eager1()
    {
        String s = TestClass.getVal();
        int val = TestClass.add(3, 4);

        assertEquals("hello", s);
        assertEquals(7, val);
    }

    @Test
    public void Eager2()
    {
        assertEquals("hello", TestClass.getVal());
        assertEquals(7, TestClass.add(3, 4));
    }
}