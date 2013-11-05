package test;

import junit.framework.TestCase;


public class ExampleTestCase02_01 extends TestCase {

	public ExampleTestCase02_01(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	
	/*
	 * テストとは仕様と実際のプログラムとの間に差が無いかを確認するための動作である。
	 * 
	 * これにより、コンパイルが成功しても正しい出力が行えないプログラムなどを探し出すことができる。
	 * 
	 * 
	 */

	public void testPlusMethod(){
		int a = 5,b = 4;
		int actual = Bank.SampleTestProgram.Plus(a, b);//a+bの結果が出てくると考えている 今回は9になるはずである
		
		/*テスト内容　assert = ”であるべきである" a+b ＝？
		 * 
		 * super.assertEquals(有るべき値,実行結果);
		 */
		super.assertEquals(a + b, actual);
	}
	
	public void testMinusMethod(){
		int a = 10,b = 5;
		int actual = Bank.SampleTestProgram.Minus(a,b);
		
		//テスト内容　assert = ”であるべきである" a-b ＝？
		super.assertEquals(a-b, actual);
		
	}
		

	

}
