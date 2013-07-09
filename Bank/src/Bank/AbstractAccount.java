package Bank;

import java.util.Arrays;

public abstract class AbstractAccount {
	//継承先では書き換えができるようにする。
	//protectedは継承先ではアクセスすることが出来る。アクセス修飾子無記の場合よりも強く、privateよりも弱い制限力を持つ
	protected int cashAmount;
	private String accountID;
	private String accountType;
	
	//Getter 一覧　一応オブジェクトの排他的処理を行う。
	int getCashAmount() {
		return cashAmount;
	}
	 String getAccountID() {
		return accountID;
	}
	 String getAccountType() {
		return accountType;
	}
	 
	 //銀行講座の種類変更メソッド
	protected boolean setAccountType(String type){
		 //銀行口座の種類は以下の３つに限定する
		 String[] bank_type = {"general","special","other"};
		 
		 if(Arrays.asList(bank_type).contains(type)){
			 accountType = type;
			 return true;
		 }
			 
		 return false;
		 
	 }
	
	/**
	 * 抽象メソッド群 お金のやり取り
	 * @param withdraw_cash
	 * @return
	 */
	public abstract boolean Withdraw(int withdraw_cash);
	public abstract boolean Withdraw(int withdraw_cash,String message);
	/**
	 * 抽象メソッド群
	 * @param deposit_cash
	 * @return
	 */
	public abstract boolean Deposit(int deposit_cash);
	public abstract boolean Deposit(int deposit_cash,String message);
	
	@Override
	public String toString() {
		return "userID:"+ getAccountID()+", 残高:"+getCashAmount()+"\\";
	}
	
	
	/**
	 * コンストラクタ
	 * @param accountId
	 * @param init_cash
	 */
	public AbstractAccount(String accountId,int init_cash){
		this.accountID  = accountId;
		this.cashAmount = init_cash;
		
		accountType = "normal";
		
		
	}


}
