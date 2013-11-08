package test04;

public class AnswerTestBankAccount_04_01 {
	
	public int cashAmount;
	private String accountID;
	private String accountType;
	
	

	public AnswerTestBankAccount_04_01(String init_accountID, int init_amount) {
		// TODO 自動生成されたコンストラクター・スタブ
		
		//以下解答
		accountID  = init_accountID;
		cashAmount = init_amount;
		
		
		accountType = "general";//null;
		//以上解答
	}

	public boolean Withdraw(int amount) {
		// TODO 自動生成されたメソッド・スタブ
		
		//以下解答
		if(amount <= 0){
			return false;
		}
		
		if(cashAmount < amount) {
			return false;
		}
		
		
		cashAmount -= amount;
		
		
		return true;
	}

	public boolean Deposit(int amount) {
		// TODO 自動生成されたメソッド・スタブ
		//以下解答
		if(amount <= 0){
			return false;
		}
	
		cashAmount += amount;
		
		
		return true;
		//return false;
	}

	public String getAccountType() {
		// TODO 自動生成されたメソッド・スタブ
		return accountType;
		//return null;
	}

	public int getCashAmount() {
		// TODO 自動生成されたメソッド・スタブ
		
		//以下解答
		return cashAmount;
		//return 0;
	}

	public String getAccountID() {
		// TODO 自動生成されたメソッド・スタブ
		return accountID;
		//return null;
	}

}
