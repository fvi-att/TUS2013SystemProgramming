package Bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @author fvi@
 * 
 * @version 0.5
 * 
 * @since 2013/05
 * 
 * このクラスは口座を操作するクラスです。どんな種類の口座でも扱うことができるようにするためには
 * どのようにすればいいかを学びます。
 * 
 * 独自のイベントリスナの作り方を勉強するとより効率的に学習することができます。
 * {@link}  http://techbooster.jpn.org/andriod/application/9054/
 * 
 */

public class MyBank implements CloseNotification {
	/**
	 * 
	 * ここでは自分自身の預金口座の定義・設定・操作を行います。
	 * つまりGeneralBankAccountは自分の銀行口座を作ることもできますし、
	 * かつ別の普通銀行口座を作ることもできるのです。
	 * 
	 * つまり　AbstractAccount = typeA_Account, AbstractAccountAccount = typeB＿Accountともできる。
	 * [問題]なんでこうすると便利なのか？
	 * 
	 * myAccountはシングルトン構造になっている。
	 */
	
	private static AbstractAccount myAccount;
	private BankServer server;
	
	public MyBank(){
		//サーバを起動して　待機状態にする
		//.start()を.run()として呼び出しては行けない理由を考えてみよう
		new Thread(new ServerSocketmanager()).start();
		/*
		 * このように
		server = new BankServer();
		server.start();
		*/
		
		//server = new BankServer();
		//アカウントの初期設定を行う
		if(myAccount == null){
			//myAccountが存在しない場合のみ作成を行う。
			//初期状態では1000円に設定
			myAccount = new GeneralBankAccount("sampleID", 1000);
			
		}
		
		
	}
	/**
	 * 
	 * @param 口座を直接操作するのはおすすめしない。
	 * @return 口座自身
	 */

	private static  AbstractAccount getAccount(){
		return myAccount;
	}
	
	private static void PrintRecipt(String subject,int amount){
		Recipt recipt = new Recipt();
		recipt.addTransaction(subject,amount);
		recipt.Print();
	}
	
	@SuppressWarnings("unused")
	private static void PrintRecipt(HashMap<String,Integer> transaction){
		Recipt recipt = new Recipt();
		recipt.addTransaction(transaction);
		recipt.Print();
		
	}
	/**
	 * 
	 * @param dst_ip 送信先
	 * @param money　金額
	 * @param message　送信メッセージ
	 * @return　送金成功時 true その他　false <BR>
	 * 
	 */
	
	public boolean TransfarTo(String dst_ip,int transfer_money,String message){
		//振込の定義を行う。
		/**この場合だとある問題を解決できない。　それはなにか？
		
		if(myAccount.Withdraw(money)){
			new BankSocket(dst_ip,money);
		}
		*/
		
		if(myAccount.Withdraw(transfer_money)){
			//引き出しが成功した場合は引き出しを試みる
			new BankSocket(dst_ip,transfer_money,message);
			
			
			
			//作業が終了したら領収書を作成する
			PrintRecipt("振込", transfer_money);
			

			return true;
		}
		
		
		System.out.println("振込処理に失敗しました。取引を中止します。");
		
		return false;
		
	}
	
	public static boolean Withdraw(int amount){
		if(getAccount().Withdraw(amount)){
			PrintRecipt("引き出し",amount);
			return true;
		}else{
			return false;
		}
		
	}
	
	public static  boolean Deposit(int amount){
		if(getAccount().Deposit(amount)){
			PrintRecipt("入金",amount);
			return true;
		}else{
			return false;
		}
		
	}
	
	public String getAccountType(){
		return getAccount().getAccountType();
	}
	
	public String getAccountID(){
		return getAccount().getAccountID();
	}
	
	public int getAccountAmount(){
		return getAccount().getCashAmount();
		}
	
	/**
	 * 
	 * 
	 * @param mybank
	 * @throws IOException
	 */
	static void loopMenu(MyBank mybank) throws IOException{
		BufferedReader stdReader = new BufferedReader(new InputStreamReader(System.in));
		while(true){
			System.out.println("-------menu------------\n"+
			           "deposit::入金を行います\n" +
						"withdraw::引き出しを行います^\n"+
			           "status::今の講座情報を取得します\n"+
						"transfer::振込み作業を行います");
		String mes = stdReader.readLine();
		if(mes == "quit"){
			System.out.println("[MESSAGE]システムを終了します");
			break;
		}
		
		try{
		switch(mes){
			case "deposit":
				System.out.println("金額を指定してください");
				if(mybank.getAccount().Deposit(Integer.parseInt(stdReader.readLine()))){
					System.out.println("[MESSAGE]入金しました");
				}else{
					System.out.println("[ERROR]失敗しました。");
				}
				break;
			
			case "withdraw":
				System.out.println("金額を指定してください");
				if(mybank.getAccount().Withdraw(Integer.parseInt(stdReader.readLine()))){
					System.out.println("[MESSAGE]出金しました");
				}else{
					System.out.println("[ERROR]失敗しました");
				}
				break;
			
			case "transfer":
				System.out.println("宛先ipアドレスを入力して下さい");
				String dst_ip = stdReader.readLine();
				System.out.println("送金金額を入力して下さい");
				int amount = Integer.parseInt(stdReader.readLine());
				System.out.println("メッセージを入力して下さい");
				String message = stdReader.readLine();
				
				mybank.TransfarTo(dst_ip,amount, message);
				
				break;
			
			case "status":
				System.out.println("現在のアカウントの状況");
				AbstractAccount myaccount = mybank.getAccount();
				
				System.out.println("口座番号:"+myaccount.getAccountID()+"\n"+
									"講座の種類："+myaccount.getAccountType()+"\n"+
									"残高:"+myaccount.getCashAmount());
				break;
			
				
				
		}
		}catch(java.lang.NumberFormatException format_err){
			System.out.println("数値以外が入力されています。");
			continue;
		}
			
			
		}
	}
	
	
	/**
	 * 
	 * mainメソッドでは、新しい新規の口座を作成し基本的な動作をCUI環境上で行うことができます
	 * @param deposit 入金<BR>
	 * @param withdraw 引き出し<br>
	 * @param status 口座情報<br>
	 * 
	 * 
	 */
	public static void main(String[] args) throws IOException{
		System.out.println("こんにちはこちらは銀行システム（デバッグ）です");
		loopMenu(new MyBank());
	}
	
	/**
	 * GUI上で終了処理を行ったなどの終了処理を行うときに
	 * 通知インターフェースを用いてソケットの終了処理を行う
	 * @throws IOException 
	 * 
	 * 
	 */
	@Override
	public void whenCloseSystem() throws IOException {
		// TODO Auto-generated method stub
		
		server.FinishServer();
		
	}
	
	

}
