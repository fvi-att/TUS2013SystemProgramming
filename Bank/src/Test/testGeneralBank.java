package Test;

import junit.framework.TestCase;
import Bank.GeneralBankAccount;

public class testGeneralBank extends TestCase {
	
	public testGeneralBank(){
		super();
	}
	
	
	public void test_Deposit(){
		
		assert new Bank.GeneralBankAccount("test", 30000).Withdraw(30000000) == false;
	
	}
	
	
	public void test_DepositCollect(){
		int init_cash = 5000;
		
		int deposit = 50000000;
		
		Bank.GeneralBankAccount account = new GeneralBankAccount("test",init_cash);
		
		assertEquals(false, account.Deposit(deposit));
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
