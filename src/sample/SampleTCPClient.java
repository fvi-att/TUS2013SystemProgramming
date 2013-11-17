package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import Bank.BankTransferConfiguration;

public class SampleTCPClient {
	static Socket soc = null;
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自動生成されたメソッド・スタブ
		BufferedReader stdReader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("IPアドレスを入力してください");
		String ip_addr = stdReader.readLine();
		
		System.out.println("ポート番号を入力してください BankSystem:15000");
		int port_num = Integer.parseInt(stdReader.readLine());
		
		soc = new Socket(ip_addr,port_num);
		
		
		
		/* 準備：データ入力ストリームの定義--ソケットからデータを
	       取ってくる．sok → in  */  
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				soc.getInputStream()));
		
		/* 準備：データ出力ストリームの定義--ソケットにデータを
	       書き込む．  sok ← out */

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				soc.
				getOutputStream()));
		System.out.println("サーバに接続しました。メッセージを送信してください");
		writer.write(stdReader.readLine());
		writer.newLine();
		writer.flush();
		
		while(true){
			
			// 送信先からの戻り値を受信
			System.out.println("メッセージ受信中");
			// 一応待機
			String response = reader.readLine();

			System.out.println("受信したメッセージ： " + response);
			
			
			System.out.println("メッセージを送信してください");
			String q_command = stdReader.readLine();
			
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
		
		//ここまでのコードを変更すること
		//以降、通信処理の後片付け
		System.out.println("SampleTCP通信システムを終了します");

		reader.close();
		writer.close();

		soc.close();

	}

}
