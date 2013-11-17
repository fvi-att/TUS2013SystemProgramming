package test;

import junit.framework.TestCase;

public class TestBankTransferParser extends TestCase {
	
	int depositAmount;
	String command    = null;
	String message    = null;
	String accountID  = null;
	
	
	/**
	 * 
	 * テストクラスのコンストラクタ
	 * @param name
	 */
	public TestBankTransferParser(String name) {
		super(name);
		
		command = "send";
		message = "testComment";
		accountID = "sampleID";
	}
	/**
	 * 
	 * 実際にテストを始める前の前準備
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
	}
	/**
	 * 
	 * テスト終了後の後片付け処理
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testBankTransferParserOK() {
		/*
		 * サーバはクライアントとの接続が確立すると、
		 * クライアントから取引開始のキーワードを受信する
		 * その後受け取るメッセージからサーバは解釈を行い、
		 * 様々な処理を行うようにする。
		 * 
		 * 
		 */
	}

	public void testSetFrom() {
		fail("まだ実装されていません");
	}

	public void testPerseInputMessage() {
		fail("まだ実装されていません");
	}

	

}
