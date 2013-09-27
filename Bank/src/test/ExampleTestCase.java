package test;

import junit.framework.TestCase;

public class ExampleTestCase extends TestCase {

	public ExampleTestCase(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPlus() {
		int a = 5,b= 4;
		int actual = Bank.SampleTestProgram.Plus(a, b);
		
		assertEquals(a+b, actual);
	}

}
