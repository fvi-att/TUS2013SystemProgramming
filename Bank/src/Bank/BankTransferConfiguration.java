package Bank;

public interface BankTransferConfiguration {
	
	final public int SERVER_PORT = 15000;
	final public String establish_connectionWord ="bankSystemConnection";
	
	//最初のコネクションも含めて5つと定義
	final public int message_size = 5;
	
	
	//振込メッセージの内容
	//内容に問題がなく次のコマンドを求めるときに送信するコマンド
	final public String OK = "OK";
	//予期しないエラーが発生したため強制的に終了させるときに送信するコマンドabort バルスコマンド
	final public String ABORT = "abort";
	//正常に取引を終了させるときに送信するコマンド
	final public String QUIT = "quit";
	
	

}
