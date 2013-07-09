package Bank;


import java.io.IOException;
import java.net.ServerSocket;
/**
 * 
 * 
 * 
 * 
 * @since 2013/06/04
 * @author fvi
 *
 */
public class ServerSocketmanager extends Thread implements BankTransferConfiguration{
	//これは気にしなくてもいい
	@SuppressWarnings("resource")
	public void run(){
		
		ServerSocket serverSocket = null;
		
		/*
		 * もしかしたらサーバソケットを構築できないかもしれない
		 * と考えると例外処理は必要と考える
		 * 
		 */
		try {
			//SERVER_PORTはBankTransferConfigurationインターフェースは一括して通信に必要な設定を管理する。
			//これによってこのインターフェースを実装することで設定情報を一括で取得することが出来る。
			serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("[ServerManager]サーバソケットを生成しましたポート番号："+String.valueOf(SERVER_PORT)+"で待機します");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(true){
			
			try {
				
				BankServer bankServer = new BankServer(serverSocket.accept());
				new Thread(bankServer).start();
				System.out.println("[Server]接続を受けました");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
