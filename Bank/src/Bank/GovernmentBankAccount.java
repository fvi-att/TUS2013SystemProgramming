package Bank;

public class GovernmentBankAccount extends AbstractAccount {

	public GovernmentBankAccount(String accountId, int init_cash) {
		super(accountId, init_cash);
		// TODO 自動生成されたコンストラクター・スタブ
		
		super.setAccountType("government");
	}

	@Override
	public boolean Withdraw(int withdraw_cash) {
		// TODO 自動生成されたメソッド・スタブ
		super.cashAmount -= withdraw_cash;
		return true;
	}

	@Override
	public boolean Withdraw(int withdraw_cash, String message) {
		// TODO 自動生成されたメソッド・スタブ
		
		
		return true;
	}

	@Override
	public boolean Deposit(int deposit_cash) {
		// TODO 自動生成されたメソッド・スタブ
		super.cashAmount += deposit_cash;
		return true;
	}

	@Override
	public boolean Deposit(int deposit_cash, String message) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

}
