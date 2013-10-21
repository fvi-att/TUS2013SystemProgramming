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

public class MyselfBank {
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

	private static TestBankAccount_04_01 myAccount;
	

	public static void initMyselfBank(TestBankAccount_04_01 account) {
		//アカウントの初期設定を行う
		myAccount = account;
	}

	/**
	 * 
	 * 
	 * @return 口座オブジェクト自身を返す
	 */

	private static TestBankAccount_04_01 getAccount() {
		return myAccount;
	}



	/**
	 * withdrawメソッドは口座からamountの文だけ出金を試みます
	 * 
	 * 
	 * 
	 * @param amount　入金額
	 * @return boolean　振り込み成功、失敗判定
	 */
	public static boolean Withdraw(int amount) {
		if (getAccount().Withdraw(amount)) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean Deposit(int amount) {
		if (getAccount().Deposit(amount)) {
			return true;
		} else {
			return false;
		}

	}

	public static String getAccountType() {
		return myAccount.getAccountType();
	}

	public static String getAccountID() {
		return myAccount.getAccountID();
	}

	public static int getAccountAmount() {
		return myAccount.getCashAmount();
	}

	/**loopMenuはCUI環境上で入出金がテスト的に実行することが出来る
	 * 
	 * 
	 * @param mybank
	 * @return void
	 * @throws IOException
	 */
	static void loopMenu(MyselfBank mybank) throws IOException {
		BufferedReader stdReader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("-------menu------------\n" +
					"deposit::入金を行います\n" +
					"withdraw::引き出しを行います^\n" +
					"status::今の講座情報を取得します\n" +
					"exit::プログラムを終了させます");
			String mes = stdReader.readLine();
			if (mes == "quit") {
				System.out.println("[MESSAGE]システムを終了します");
				break;
			}

			try {
				switch (mes) {
				case "deposit":
					System.out.println("金額を指定してください");
					if (MyselfBank.getAccount().Deposit(Integer.parseInt(stdReader.readLine()))) {
						System.out.println("[MESSAGE]入金しました");
					} else {
						System.out.println("[ERROR]失敗しました。");
					}
					break;

				case "withdraw":
					System.out.println("金額を指定してください");
					if (MyselfBank.getAccount().Withdraw(Integer.parseInt(stdReader.readLine()))) {
						System.out.println("[MESSAGE]出金しました");
					} else {
						System.out.println("[ERROR]失敗しました");
					}
					break;

				case "status":
					System.out.println("現在のアカウントの状況");
					TestBankAccount_04_01 myaccount = MyselfBank.getAccount();

					System.out.println("口座番号:" + myaccount.getAccountType() + "\n" +
							"講座の種類：" + myaccount.getAccountType() + "\n" +
							"残高:" + myaccount.getCashAmount());
					break;

				case "exit":
					System.out.println("銀行システムを終了させます");					
					System.exit(0);//正常終了
					break;
					

				}
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
		System.out.println("こんにちは.こちらはTCBC-Bankシステム（CUIコンソール,サンプル）です");
		loopMenu(new MyselfBank());
	}



}
