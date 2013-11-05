package Bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**このクラスは口座を操作するクラスです。どんな種類の口座でも扱うことができるようにするためには
 * どのようにすればいいかを学びます。
 * 
 * 独自のイベントリスナの作り方を勉強するとより効率的に学習することができます。
 * {@link}  http://techbooster.jpn.org/andriod/application/9054/
 * 
 * 
 * 
 * @author fvi@
 * 
 * @version 0.5
 * 
 * @since 2013/05
 * 
 * 
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
	 * [問題]なぜこうすると便利なのか？
	 * 
	 * myAccountはシングルトン構造になっている。
	 */

	private static AbstractAccount myAccount;
	private static BankServer server;
	/**
	 * 
	 * 
	 * 
	 * @param 
	 */
	public static void initBankAccount(AbstractAccount account) {
		// TODO 自動生成されたメソッド・スタブ
		

		myAccount = account;
		
		

	}

	/**
	 * 
	 * 
	 * @return 口座オブジェクト自身を返す
	 */

	private static AbstractAccount getAccount() {
		return myAccount;
	}

	/**
	 * 入金、出金後に領収書の発行をおこなうメソッド
	 * 
	 * @param String subject 取引事項
	 * @param in     amount  取引金額
	 * 
	 * 
	 */
	private static void PrintRecipt(String subject, int amount,String comment) {
		Recipt recipt = new Recipt();
		recipt.addTransaction(subject, amount);
		recipt.setComment(comment);
		recipt.Print();
	}

	/**振り込みを行うメソッド。振り込みの仕込みはBankSocketクラスを参照すること
	 * 
	 * @param dst_ip 送信先
	 * @param money 金額
	 * @param message 送信メッセージ
	 * @return 送金成功時 true その他 false <BR>
	 * 
	 */

	static boolean TransfarTo(String dst_ip, int transfer_money, String message) {
		//振込の定義を行う
		if (myAccount.Withdraw(transfer_money)) {
			//引き出しが成功した場合は引き出しを試みる
			new BankSocket(dst_ip, transfer_money, message);
			
			String comment = message;
			if(message == null){
				comment ="";
			}

			//作業が終了したら領収書を作成する
			PrintRecipt("振込", transfer_money,comment);

			return true;
		}

		System.out.println("振込処理に失敗しました。取引を中止します。");

		return false;

	}

	/**
	 * withdrawメソッドは口座からamountの文だけ出金を試みます
	 * 
	 * 
	 * 
	 * @param amount　入金額
	 * @return boolean　振り込み成功、失敗判定
	 */
	static boolean Withdraw(int amount) {
		if (getAccount().Withdraw(amount)) {
			PrintRecipt("引き出し", amount,null);
			return true;
		} else {
			return false;
		}

	}

	static boolean Deposit(int amount) {
		if (getAccount().Deposit(amount)) {
			PrintRecipt("入金", amount,null);
			return true;
		} else {
			return false;
		}

	}

	static String getAccountType() {
		return getAccount().getAccountType();
	}

	static String getAccountID() {
		return getAccount().getAccountID();
	}

	static int getAccountAmount() {
		return getAccount().getCashAmount();
	}

	/**loopMenuはCUI環境上で入出金がテスト的に実行することが出来る
	 * 
	 * 
	 * @return void
	 * @throws IOException
	 */
	static void loopMenu() throws IOException {
		BufferedReader stdReader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("-------menu------------\n" +
					"deposit::入金を行います\n" +
					"withdraw::引き出しを行います^\n" +
					"status::今の講座情報を取得します\n" +
					"transfer::振込み作業を行います\n" +
					"exit::プログラムを終了させます");
			String mes = stdReader.readLine();

			try {
				switch (mes) {
				case "deposit":
					System.out.println("金額を指定してください");
					if (MyBank.getAccount().Deposit(Integer.parseInt(stdReader.readLine()))) {
						System.out.println("[MESSAGE]入金しました");
					} else {
						System.out.println("[ERROR]失敗しました。");
					}
					break;

				case "withdraw":
					System.out.println("金額を指定してください");
					if (MyBank.getAccount().Withdraw(Integer.parseInt(stdReader.readLine()))) {
						System.out.println("[MESSAGE]出金しました");
					} else {
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

					MyBank.TransfarTo(dst_ip, amount, message);

					break;

				case "status":
					System.out.println("現在のアカウントの状況");
					AbstractAccount myaccount = MyBank.getAccount();

					System.out.println("口座番号:" + myaccount.getAccountID() + "\n" +
							"講座の種類：" + myaccount.getAccountType() + "\n" +
							"残高:" + myaccount.getCashAmount());
					break;

				case "exit":
					System.out.println("銀行システムを終了させます");

					if (MyBank.server != null) {
						MyBank.server.FinishServer();
					}

					System.exit(0);//正常終了
					break;

				}
				//switch 終了
				
				
			} catch (java.lang.NumberFormatException format_err) {
				System.out.println("数値以外が入力されています。");
				continue;
			}

		}
	}

	/**
	 * 
	 * mainメソッドでは、新しい新規の口座を作成し基本的な動作をCUI環境上で行うことができます
	 * 試験的な運用をCUI環境上で行えるようにしてあります。
	 * 
	 * 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("こんにちは.こちらはTCBC-Bankシステム（CUIコンソール）です");
		MyBank.initBankAccount(new GovernmentBankAccount("sampleID", 400000));
		MyBank.loopMenu();
	}

	/**
	 * 
	 * [応用]
	 * GUI上で終了処理を行ったなどの終了処理を行うときに
	 * 通知インターフェースを用いてソケットの終了処理を行う
	 * @throws IOException 
	 * 
	 * 
	 */
	@Override
	public void whenCloseSystem() throws IOException {
		// TODO Auto-generated method stub
		//終了時にソケットの後始末を行う2013/08/19
		server.FinishServer();

	}

}
