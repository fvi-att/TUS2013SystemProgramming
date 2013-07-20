

package Bank;

/**
 * BankServerクラスは相手とのデータのやり取りを実際に行うクラスオブジェクトを生成します。
 * ここからマルチスレッドの勉強なども必要になると思いますので頑張ってください。
 * @author fvi@
 * 
 * @version 0.5
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServer extends Thread {
	public final static int SERVER_PORT = 15000;

	//static ServerSocket ss ;
	private Socket act_socket = null;
	
	private MessageQueue recv_messageQue = null;
	
	private BankTransferPerser perser = null;

	/**
	 * StartCommunicationメソッドは１体１のTCP/IP通信を行うシンプルなメソッドです。
	 * ここでメッセージを受信をおこないメッセージの解析を行い様々な処理を行います。
	 * 
	 * メッセージ quit:通信を終了させます
	 * 
	 * 
	 * @see MyGeneralBank
	 * 
	 * @see BankSocket
	 * @see MessageQueue
	 */

	public BankServer(Socket accept) {
		//コンストラクタ
		// TODO Auto-generated constructor stub
		this.act_socket = accept;
		
		
		this.perser = new BankTransferPerser();
		
		perser.setFrom(accept.getInetAddress().toString());
	}

	/**ソケットの確立 1対1の通信の際はこれで十分です。
	 * 1対多になった時にどのような問題点が起きるかを考えてみましょう
	try {

		ss = new ServerSocket(SERVER_PORT);

		System.out.println("Server started port:" + SERVER_PORT
				+ "で待機しています");
		

	} catch (Exception err) {
		System.out.println("[Server]サーバーの確立に失敗しました");
		err.printStackTrace();
	}
	*/
	
	void OutputLog(String output){
		System.out.println(output);
		String[] messageToView = {"[Server]",output};
		EventManager.fireEvent("ATMView", messageToView);
	}
	
	
	public void StartCommunication() {
		

		
		try {
			//Socket socket = ss.accept();
			System.out.println("[Server]"+act_socket.getInetAddress() + "から接続を受けました");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					act_socket.getInputStream()));

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					act_socket.getOutputStream()));

			while (true) {

				String mes = null;
				// 受信するまで待機
				
				
				while ((mes = reader.readLine()) == null);
				
				
				//エンコード文字列対策
				OutputLog("[Server]getMessage:" +mes);
				//System.out.println("[Server]getMessage:" + mes);

				// 向こうから終了するようにきたら終了させる 終了コマンドは"quit"
				if (mes.matches(BankTransferConfiguration.QUIT) || mes.matches(BankTransferConfiguration.ABORT)) {
					break;
				}
				//まずデータを解釈し次にどのような動作を行うかをきめる。
				//キューにメッセージを追加
				
				perser.Put(mes);
				
				String out_mes =perser.Perse();
				writer.write(out_mes);
				writer.newLine();
				writer.flush();
				
				OutputLog(out_mes);
				//System.out.println("[Server]sendMessage:" + out_mes);

			}

			System.out.println("[Server]終了コマンドを受信しました\nサーバソケットを終了させます");
			
			
			reader.close();
			writer.close();
			act_socket.close();
			
			
						

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("[Server]メッセージ通信に失敗しました\n再度やり直してください");
			e.printStackTrace();
		}

	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		StartCommunication();

	}

	/**
	 * @param args
	 * @throws IOException 
	 */

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub デバッグ用
		ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
		new Thread(new BankServer(serverSocket.accept())).start();

	}


	public void FinishServer() throws IOException {
		System.out.println("通信を終了させます。To:"+act_socket.getInetAddress().toString());
		
	}

}
