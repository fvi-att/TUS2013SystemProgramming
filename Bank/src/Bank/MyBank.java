package Bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
	 * @deprecated
	 * @param 口座を直接操作するのはおすすめしない。
	 * @return 口座自身
	 */

	public static  AbstractAccount getAccount(){
		return myAccount;
	}
	/**
	 * 
	 * @param dst_ip 送信先
	 * @param money　金額
	 * @param message　送信メッセージ
	 * @return　送金成功時 true その他　false <BR>
	 * 
	 */
	
	boolean TransfarTo(String dst_ip,int transfer_money,String message){
		//振込の定義を行う。
		/**この場合だとある問題を解決できない。　それはなにか？
		
		if(myAccount.Withdraw(money)){
			new BankSocket(dst_ip,money);
		}
		*/
		
		if(myAccount.Withdraw(transfer_money)){
			//引き出しが成功した場合は引き出しを試みる
			new BankSocket(dst_ip,transfer_money,message);
		}
		
		
		
		
		return true;
		
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
			           "status::今の講座情報を取得します\n");
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
