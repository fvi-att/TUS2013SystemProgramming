package test02;

import junit.framework.TestCase;
import Bank.SampleTestProgram;

public class ExampleTestCase02_02 extends TestCase {

	public ExampleTestCase02_02(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	/*
	 * ここからテストケース
	 * 
	 */

	public void testMultiply() {
		int a = 5,b =10;
		super.assertEquals(a * b,SampleTestProgram.Multiply(a, b));
		//aとbの値を変えてもプログラムが正しく機能するかを確認します。
		
		a= 50;b = 20;
		super.assertEquals(a * b,SampleTestProgram.Multiply(a, b));
		
		a = 200;b = -40;
		super.assertEquals(a * b,SampleTestProgram.Multiply(a, b));
		
		a= 0; b = 1;
		super.assertEquals(a * b,SampleTestProgram.Multiply(a, b));
	}

	public void testPow() {
	
		double a = 1,b = 3;
		super.assertEquals(1.0,SampleTestProgram.pow(a, b));
		
		a = 2;b = 4;
		super.assertEquals(16.0,SampleTestProgram.pow(a, b));
		
		a= 5;b = 3;
		super.assertEquals(125.0, SampleTestProgram.pow(a, b));
	}

	public void testIntTodouble() {
		//10 <- int型　10.0　<- double型　と別の型となるので注意する
		//int型で入力したデータをdouble型で取得するには？
		int test = 125;
		super.assertEquals(125.0,SampleTestProgram.intTodouble(test));
		test  =10;
		super.assertEquals(10.0,SampleTestProgram.intTodouble(test));
	}

}
