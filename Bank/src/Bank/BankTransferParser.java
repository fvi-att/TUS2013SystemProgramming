package Bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * BankServerから受け取った通信情報を解釈して処理を実際に行う
 * @author fvi@author Iwai_7
 * @version 1.1
 */
public class BankTransferParser implements BankTransferConfiguration {

	/**
	 * @param args
	 */
	String[] order = null;
	int at_num = 0;
	
	String from ="どこか";

	public boolean Put(String put) {
		try {
			order[at_num] = put;
		} catch (Exception err) {
			return false;
		}

		at_num++;
		return true;

	}
	
	public void setFrom(String from){
		this.from = from;
	}

	public String Perse() {
		System.out.println("解釈開始");
		//接続確立メッセージが存在しない、正しくない場合は不正な取引と判定して取引を中止する
		if (!BankTransferConfiguration.establish_connectionWord.matches(order[0])) {
			return "abort";
		} else if (order[1] == null) {
			return "OK";
		}

		if (order[1].matches("send")) {
			if (order[2] == null) {
				//OKを送信し、次の値を取得するようにする
				return BankTransferConfiguration.OK;
			} else if (order[2] != null) {
				//金額情報文字列を取得する 文字列から数値に変更できない場合はABORTする。
				try{
					Integer.valueOf(order[2]);
				}catch(NumberFormatException num_err){
					return BankTransferConfiguration.ABORT;
				}
				if (order[3] == null) {
					return BankTransferConfiguration.OK;
				} else if (order[3] != null) {
					//振込処理を実行してみる

					System.out.println("振込処理実行開始");
					//以下振込処理
					
					if(MyBank.Deposit(Integer.parseInt(order[2]))){
						String[] messageToView = {"振込処理受信",from+"から振込処理を受信し振り込まれました.内容は以下の通りです\n"+
													"[振込金額："+order[2]+",メッセージ："+order[3]+"]"};
						EventManager.fireEvent("ATMView", messageToView);
						return BankTransferConfiguration.QUIT;
					}
					//正規の通信ではない場合はABORTを返信する

						return BankTransferConfiguration.ABORT;

				}

			}
		}
		return BankTransferConfiguration.ABORT;

	}

	public BankTransferParser() {
		//これで4つのメッセージを受信することができるString型の配列のインスタンスを作成した。
		order = new String[BankTransferConfiguration.message_size];
	}

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("このクラスはbankシステムの振込にて受け取ったメッセージを解釈し処理の実行、返答の決定を行うクラスです。");
		BankTransferParser perser = new BankTransferParser();
		int cnt = 0;

		while (true) {
			System.out.println("第" + String.valueOf(cnt) + "入力");
			BufferedReader stdReader = new BufferedReader(new InputStreamReader(System.in));

			try {
				perser.Put(stdReader.readLine());
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				break;
			}
			String result = perser.Perse();
			System.out.println("result:" + result);
			if (result.matches(BankTransferConfiguration.ABORT) || result.matches(BankTransferConfiguration.QUIT)) {
				break;
			}
			cnt++;

		}

	}

}
