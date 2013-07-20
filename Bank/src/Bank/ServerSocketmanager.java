package Bank;


import java.io.IOException;
import java.net.ServerSocket;
/**
 * @inheritDoc BankTransferConfiguration
 * 
 * 
 * 
 * @since 2013/06/04
 * @author fvi
 * @version 1.00
 * 
 * このクラスはサーバソケットの管理を行いマルチスレッド化を行うことで一度に複数の受信に耐えられるように
 * 設計してあります
 * 
 * @see ServerSocket
 * 
 * 
 *
 */
public class ServerSocketmanager extends Thread implements BankTransferConfiguration, NotificationCenter{

	ServerSocket serverSocket = null;
	
	public ServerSocketmanager(){
		//これは難しいので無視して構わない
		EventManager.Put("server_manager", this);
	}
	public void run(){
		
		
		/*
		 * もしかしたらサーバソケットを構築できないかもしれない
		 * と考えると例外処理は必要と考える
		 * 
		 */
		try {
			/*
			SERVER_PORTはBankTransferConfigurationインターフェースは一括して通信に必要な設定を管理する。
			これによってこのインターフェースを実装することで設定情報を一括で取得することが出来る。
			*/
			serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("[ServerManager]サーバソケットを生成しましたポート番号："+String.valueOf(SERVER_PORT)+"で待機します");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//エラーメッセージをATMViewに投げる
			String[] err_messageToView = {"[エラー]","サーバソケットの初期化に失敗しました。システムの再起動を行なって下さい"};
			EventManager.fireEvent("ATMView", err_messageToView);
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
	@Override
	public void NotificationCallfired(Object[] args) {
		// TODO 自動生成されたメソッド・スタブ
		if((String.valueOf(args[0]).matches("closed"))){
			System.out.println("closing ServerSocket @ServerSocketManager");
			if(serverSocket.isClosed()){
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
			}
		}
		
	}
}
