package Bank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * BankServerから受け取った通信情報を解釈して処理を実際に行う
 * 
 * @author fvi@author Iwai_7
 * @version 1.1
 */
public class BankTransferParser implements BankTransferConfiguration {

	/**
	 * @param args
	 */

	private String[] receiveMessage;
	private int step = 0;

	// 相手の情報を格納するための変数from
	String from = null;

	public BankTransferParser() {
		// これで5つのメッセージを受信することができるString型の配列のインスタンスを作成した。
		receiveMessage = new String[message_size];
		step = 0;
	}

	// 接続先の情報（ここではIPアドレス）を格納するためのメソッド
	public void setFrom(String from) {
		this.from = from;
	}

	public String perseInputMessage(String inMes) {
		System.out.println("解釈開始");
		switch (step) {
		case 0:
			if (inMes.equals(establish_connectionWord)) {
				receiveMessage[step] = inMes;
				step++;
				return OK;
			} else {
				return ABORT;
			}
		case 1:
			if (inMes.equals("send") || inMes.equals("receive")) {
				receiveMessage[step] = inMes;
				step++;
				return OK;
			} else {
				return ABORT;
			}
		case 2:
			if (inMes.equals(MyBank.getAccountID())) {
				receiveMessage[step] = inMes;
				step++;
				return OK;
			} else {
				return ABORT;
			}
		case 3:
			// 金額情報文字列を取得する 文字列から数値に変更できない場合はABORTする。
			try {
				Integer.valueOf(inMes);
			} catch (NumberFormatException num_err) {
				// 例外が起きた場合（数値に変換できなかった場合）の処理
				return ABORT;
			}
			receiveMessage[step] = inMes;
			step++;
			return OK;
		case 4:
			receiveMessage[step] = inMes;
			step = -1;

			if (receiveMessage[1].equals("send")) {
				System.out.println("振込処理実行");
				return processDeposit();
			}
			
			return ABORT;

		default:
			return ABORT;
		}
	}

	public String processDeposit() {
		if (MyBank.Deposit(Integer.parseInt(receiveMessage[3]))) {
			String[] messageToView = {
					"振込処理受信",
					from + "から" + receiveMessage[2]
							+ "へ振込処理を受信し振り込まれました.内容は以下の通りです\n" + "[振込金額："
							+ receiveMessage[3] + ",メッセージ：" + receiveMessage[4]
							+ "]" };
			EventManager.fireEvent("ATMView", messageToView);
			return QUIT;
		} else {
			return ABORT;
		}
	}
}