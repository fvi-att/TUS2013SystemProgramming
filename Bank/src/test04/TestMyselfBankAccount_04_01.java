package test04;

import junit.framework.TestCase;
import Bank.MyselfBank;

public class TestMyselfBankAccount_04_01 extends TestCase {
	
	TestBankAccount_04_01 test_account;
	int initial_cash = 10000;
	
	String initial_BankID = "sampleID";
	
	
	/**
	 * コンストラクタ
	 * 
	 * 
	 */
	public TestMyselfBankAccount_04_01(String name) {
		super(name);
		
		//TestBankAccount_04_01は第一引数にAccountIDを第二引数に初期口座に入れる金額を与える
		test_account = new TestBankAccount_04_01("sampleID",initial_cash);
	}

	protected void setUp() throws Exception {
		super.setUp();
		//MyselfBankに口座をセットする
		MyselfBank.initMyselfBank(test_account);
		
		}
	
	
	/**
	 * 
	 * 100円出金を行う　システムが正しく処理を行っているか確認を行う
	 */
	public void testWithdraw_01() {
		int withdraw_amount = 100;
		
		//引き出しが成功していればtrueを返す
		super.assertEquals(true,MyselfBank.Withdraw(withdraw_amount));
		//初期の金額が１００００円なので残りは 9900円のはずである
		
		super.assertEquals(initial_cash - withdraw_amount, MyselfBank.getAccountAmount());
		
	}
	/**
	 *5000円出金を行う　システムが正しく処理を行っているか確認を行う
	 * 
	 * 
	 * 
	 */
	public void testWithdraw_02() {
		int withdraw_amount = 5000;
		
		//引き出しが成功していればtrueを返す
		super.assertEquals(true,MyselfBank.Withdraw(withdraw_amount));
		//初期の金額が１００００円なので残りは 9900円のはずである
		
		super.assertEquals(initial_cash - withdraw_amount, MyselfBank.getAccountAmount());
		
	}
	
	
	/**
	 * 初期入金金額は１００００円であるがここで５００００円出金を試みる
	 * 結果として処理は失敗し、口座内の残金は変化していてはいけない
	 * 
	 */
	public void testWithdraw_fusoku() {
		int withdraw_amount = 50000;
		
		//引き出しが成功していればtrueを返す.失敗していればfalseを返す
		super.assertEquals(false,MyselfBank.Withdraw(withdraw_amount));
		
		//初期の金額が１００００円なので５００００円の出金は出来ないはずである　よって取引は中止されもとの１００００円に戻っているべきである
		
		super.assertEquals(initial_cash, MyselfBank.getAccountAmount());
		
	}
	
	/**
	 * 出金額がマイナスを与えられた時、falseを返すようにすること。また処理は失敗とし口座内の残金が変化していては
	 * いけない
	 * 
	 */
	public void testWithdraw_minusWithDraw(){
		int withdraw_amount = -50000;
		
		//引き出しが成功していればtrueを返す.失敗していればfalseを返す
		super.assertEquals(false,MyselfBank.Withdraw(withdraw_amount));
		
		//取引は中止されもとの１００００円に戻っているべきである
		
		super.assertEquals(initial_cash, MyselfBank.getAccountAmount());
	}

	
	
	
	/*
	 * 入金系統のテストケース
	 * 
	 * 
	 * 
	 */
	/**
	 * 
	 * 100円出金を行う　システムが正しく処理を行っているか確認を行う
	 */
	public void testDeposit_01() {
		int deposit_amount = 100;
		
		//引き出しが成功していればtrueを返す
		super.assertEquals(true,MyselfBank.Deposit(deposit_amount));
		//初期の金額が１００００円なので残りは 9900円のはずである
		
		super.assertEquals(initial_cash + deposit_amount, MyselfBank.getAccountAmount());
		
	}
	/**
	 *5000円出金を行う　システムが正しく処理を行っているか確認を行う
	 * 
	 * 
	 * 
	 */
	public void testDeposit_02() {
		int deposit_amount = 5000;
		
		//引き出しが成功していればtrueを返す
		super.assertEquals(true,MyselfBank.Deposit(deposit_amount));
		//初期の金額が１００００円なので残りは 9900円のはずである
		
		super.assertEquals(initial_cash + deposit_amount, MyselfBank.getAccountAmount());
		
	}
	
	
	/**
	 * 出金額がマイナスを与えられた時、falseを返すようにすること。また処理は失敗とし口座内の残金が変化していては
	 * いけない
	 * 
	 */
	public void testWithdraw_minusDeposit(){
		int deposit_amount = -50000;
		
		//引き出しが成功していればtrueを返す.失敗していればfalseを返す
		super.assertEquals(false,MyselfBank.Deposit(deposit_amount));
		
		//取引は中止されもとの１００００円に戻っているべきである
		
		super.assertEquals(initial_cash, MyselfBank.getAccountAmount());
	}

	
	
	/*
	 * 
	 * そのたテストケース
	 * 
	 * 
	 * 
	 */

	public void testGetAccountType() {
		super.assertEquals("general", MyselfBank.getAccountType());
	}

	public void testGetAccountID() {
		super.assertEquals(initial_BankID, MyselfBank.getAccountID());
	}
	/**
	 * 初期に設定した金額が正しく口座の中に存在しているかのテスト
	 * 
	 * 
	 */
	public void testGetAccountAmount() {
		
		super.assertEquals(initial_cash, MyselfBank.getAccountAmount());
	}
	
	
	


}
