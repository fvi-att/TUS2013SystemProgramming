package test;

import junit.framework.TestCase;


public class ExampleSampleTestCase extends TestCase {

	public ExampleSampleTestCase(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPlusMethod(){
		int a = 5,b = 4;
		int actual = Bank.SampleTestProgram.Plus(a, b);//a+bの結果が出てくると考えている 今回は9になるはずである
		
		//テスト内容　assert = ”であるべきである" a+b ＝？
		super.assertEquals(a + b, actual);
	}

}
