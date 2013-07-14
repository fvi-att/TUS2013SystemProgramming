package Bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;



/**
 * 
 * @author fvi@
 * @version 0.51
 * 
 * BankSocketクラスは振込などの外部にデータを送信する際に呼び出されるクラスです。
 * @param SERVER_PORT TCP/IPソケット通信上で用いる統一ポート番号 15000<BR>
 * 
 * 
 */
public class BankSocket implements BankTransferConfiguration {


	Socket socket;

	/**
	 * 
	 * BankSocketクラスはBankServerクラスに対してTCP/IP通信を試みます。
	 * 各種コマンドはBankServerクラス参照のこと
	 * @param message 
	 * 
	 * @see BankServer
	 * 
	 */
	public BankSocket(String dst_ip, int money, String message) {
		//送信メッセージのデータ構造を作成今回はところてん方式でデータの出し入れが出来るキューを用いる
		MessageQueue mes_q = new MessageQueue("send",money,message);
		
		
		
		System.out.println("接続開始");
		try {
			socket = new Socket(dst_ip, SERVER_PORT);
			
			
		    /* 準備：データ入力ストリームの定義--ソケットからデータを
		       取ってくる．sok → in  */  
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			
			/* 準備：データ出力ストリームの定義--ソケットにデータを
		       書き込む．  sok ← out */

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			
			
			writer.write(BankTransferConfiguration.establish_connectionWord);
			writer.newLine();
			writer.flush();

			System.out.println("送信完了");
			
			
			while (true) {

				// 送信先からの戻り値を受信
				System.out.println("メッセージ受信中");
				// 一応待機
				// while (!reader.ready());
				String response = reader.readLine();

				System.out.println("受信したメッセージ： " + response);
				
				//終了処理 
				if (response.equals(BankTransferConfiguration.ABORT)) {
					System.out.println("転送失敗　払い戻し処理が必要");
					break;
				}
				//STUB:今はOKが帰って来た場合を想定
				String q_command = mes_q.poll();
				
				if(q_command == null){
					writer.write(BankTransferConfiguration.QUIT);
					writer.newLine();
					writer.flush();
					break;
				}
				writer.write(q_command);
				writer.newLine();
				writer.flush();
				
				
			}
			System.out.println("[Socket]転送システムを終了します");

			reader.close();
			writer.close();

			socket.close();
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("通信ソケットの確立に失敗しました。FWの設定or すでにポートが専有されているかも");
			e.printStackTrace();
		}

	}

	/**
	 * main ではlocalhost =127.0.0.1にアクセスします。
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//localhostに引数指定した金額の送信を行う
		new BankSocket("localhost", Integer.parseInt(args[1]),"test送金処理from localhost");

	}

}
