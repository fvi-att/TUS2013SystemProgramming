package test;

import junit.framework.TestCase;
import Bank.GeneralBankAccount;

public class TestGeneralBankAccount extends TestCase {
	
	GeneralBankAccount g_account;

	public TestGeneralBankAccount(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		/*
		 * 初期設定
		 */
		
		g_account = new GeneralBankAccount("sampleID", 1000);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * 正しく入金処理が行えるかのテストを行う
	 * 
	 */
	public void testDepositCollect() {
		
		int deposit_money = 1000;
		
		g_account.Deposit(deposit_money);
		
		assertEquals(2000, 2000);
		

		
	}



}
