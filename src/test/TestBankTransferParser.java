package test;

import junit.framework.TestCase;
import Bank.BankTransferConfiguration;
import Bank.BankTransferParser;
import Bank.GeneralBankAccount;
import Bank.MyBank;

public class TestBankTransferParser extends TestCase implements BankTransferConfiguration{
	
	
	int init_cash;
	int transfer_amount;
	String message    = null;
	String accountID  = null;
	BankTransferParser server_parser = null;
	
	
	
	/**
	 * 
	 * テストクラスのコンストラクタ
	 * @param name
	 */
	public TestBankTransferParser(String name) {
		super(name);
		
		init_cash = 10000;
		transfer_amount = 1000;
		message = "@@testComment@@";
		accountID = "sampleID";
		
		
		
	}
	/**
	 * 
	 * 実際にテストを始める前の前準備
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		/*
		 * サーバ側でクライアントから受け取ったメッセージを解釈するオブジェクト
		 * BankTransferParserを初期化する
		 */
		server_parser = new BankTransferParser();
		server_parser.setFrom("localhost");
		
		/*
		 * 今回の検証環境はlocalhostであるので、口座情報を新規に定義しておく
		 * 
		 */
		MyBank.initBankAccount(new GeneralBankAccount(accountID,init_cash ));
		
	}
	/**
	 * 
	 * テスト終了後の後片付け処理
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * サーバはクライアントとの接続が確立すると、
	 * クライアントから取引開始のキーワードを受信する
	 * その後受け取るメッセージからサーバは解釈を行い、
	 * 様々な処理を行うようにする。
	 * 
	 * 
	 */
	public void testBankTransferParserOK() {
		
		
		
		/*
		 * 最初にクライアントから受け取る文字列は establish_connectionWordであるので
		 * それがきたら・・・
		 */
		super.assertEquals(OK,server_parser.ParseMessagefromClient(establish_connectionWord));
		
		/*
		 * 次はクライアントから受け取るメッセージは振り込み処理命令なので
		 * それがきたら・・・
		 */
		super.assertEquals(OK,server_parser.ParseMessagefromClient("send"));
		
		/*
		 * 次にクライアントから来るメッセージはアカウントIDを指定する文字列より
		 * それがきたら・・・
		 * 
		 */
		super.assertEquals(OK, server_parser.ParseMessagefromClient(accountID));
		
		/*
		 * 次にクライアントからくる文字列は金額情報だからそれがきたら・・
		 * 
		 */
		super.assertEquals(OK,server_parser.ParseMessagefromClient(String.valueOf(transfer_amount)));
		
		/*
		 * 次にクライアントから文字列はコメント情報であるのでそれが来たら・・・
		 * 
		 */
		super.assertEquals(QUIT, server_parser.ParseMessagefromClient(message));
		
		/*
		 * クライアントに取引完了のメッセージを送りかつ自分の口座にも正しく
		 * メッセージ通りにお金が処理されているか確認を行う
		 * 
		 * 追加でReciptフォルダを確認して取引が正しく行われているかを追加で確認すること
		 */
		super.assertEquals(init_cash + transfer_amount, MyBank.getAccountAmount());
		
	}
	
	/**
	 * sendの他にrecieveメッセージも処理できるようにする必要があるので
	 * その実装を行いこのテストを通過させること
	 * 
	 * 
	 */
	public void testBankTransferParserOK_02(){
		/*
		 * 最初にクライアントから受け取る文字列は establish_connectionWordであるので
		 * それがきたら・・・
		 */
		super.assertEquals(OK,server_parser.ParseMessagefromClient(establish_connectionWord));
		
		/*
		 * 次はクライアントから受け取るメッセージは振り込み処理命令なので
		 * それがきたら・・・
		 */
		super.assertEquals(OK,server_parser.ParseMessagefromClient("receive"));
		
		/*
		 * 次にクライアントから来るメッセージはアカウントIDを指定する文字列より
		 * それがきたら・・・
		 * 
		 */
		super.assertEquals(OK, server_parser.ParseMessagefromClient(accountID));
		
		/*
		 * 次にクライアントからくる文字列は金額情報だからそれがきたら・・
		 * 
		 */
		super.assertEquals(OK,server_parser.ParseMessagefromClient(String.valueOf(transfer_amount)));
		
		/*
		 * 次にクライアントから文字列はコメント情報であるのでそれが来たら・・・
		 * ##先生へ
		 * なぜかquitを返すはずだと考えているのですがABORTが返ってきます。確認の方お願いします
		 */
		super.assertEquals(QUIT, server_parser.ParseMessagefromClient(message));
		
		/*
		 * クライアントに取引完了のメッセージを送りかつ自分の口座にも正しく
		 * メッセージ通りにお金が処理されているか確認を行う
		 * 
		 * 追加でReciptフォルダを確認して取引が正しく行われているかを追加で確認すること
		 */
		super.assertEquals(init_cash - transfer_amount, MyBank.getAccountAmount());
		
	}
	/**
	 *予定以外のデータが入力された場合の対処を行う
	 * 最初にクライアントから受け取る文字列は establish_connectionWordであるので
	 * なぜか”変な文字列"ような予想外な文字列がクライアントから送信されたら・・・ 
	 */
	public void testBankTransferParserNG_01(){
		/*
		 *予定以外のデータが入力された場合の対処を行う 
		 */
		
		/*
		 * 最初にクライアントから受け取る文字列は establish_connectionWordであるので
		 * なぜか”変な文字列"ような予想外な文字列がクライアントから送信されたら
		 */
		super.assertEquals(ABORT,server_parser.ParseMessagefromClient("変な文字列を送信"));
		
		//以降正しいメッセージが来てもABORTのみを返す。
		/*
		 * 次はクライアントから受け取るメッセージは振り込み処理命令なので
		 * それがきたら・・・
		 */
		super.assertEquals(ABORT,server_parser.ParseMessagefromClient("send"));
		
		/*
		 * 次にクライアントから来るメッセージはアカウントIDを指定する文字列より
		 * それがきたら・・・
		 * 
		 */
		super.assertEquals(ABORT, server_parser.ParseMessagefromClient(accountID));
		
		/*
		 * 次にクライアントからくる文字列は金額情報だからそれがきたら・・
		 * 
		 */
		super.assertEquals(ABORT,server_parser.ParseMessagefromClient(String.valueOf(transfer_amount)));
		
		/*
		 * 次にクライアントから文字列はコメント情報であるのでそれが来たら・・・
		 * 
		 */
		super.assertEquals(ABORT, server_parser.ParseMessagefromClient(message));
		
		
	}
	
	/**
	 * 
	 * サーバ上のMyBankが管理しているアカウントのIDが受け取ったアカウント文字列と合致しない場合でも
	 * ABORTを返す
	 */
	public void testBankTransferParserNG_02(){
		
		/*
		 * 最初にクライアントから受け取る文字列は establish_connectionWordであるので
		 * それがきたら・・・
		 */
		super.assertEquals(OK,server_parser.ParseMessagefromClient(establish_connectionWord));
		
		/*
		 * 次はクライアントから受け取るメッセージは振り込み処理命令なので
		 * それがきたら・・・
		 */
		super.assertEquals(OK,server_parser.ParseMessagefromClient("send"));
		
		/*
		 * 次にクライアントから来るメッセージはアカウントIDを指定する文字列より
		 * それがきたら・・・
		 * 
		 */
		super.assertEquals(ABORT, server_parser.ParseMessagefromClient("another_account"));
		
		//以降はどのように正しい情報をクライアントを受信してもABORTを返す
		/*
		 * 次にクライアントからくる文字列は金額情報だからそれがきたら・・
		 * 
		 */
		super.assertEquals(ABORT,server_parser.ParseMessagefromClient(String.valueOf(transfer_amount)));
		
		/*
		 * 次にクライアントから文字列はコメント情報であるのでそれが来たら・・・
		 * 
		 */
		super.assertEquals(ABORT, server_parser.ParseMessagefromClient(message));
		
	}
	
	
	
	/**
	 * サーバ側のMyBankが管理しているアカウントがアカウントの制約のために
	 * 振り込みに失敗した場合最終的にABORTを返すようにする
	 * 
	 * 今回はサーバ側のMyBankが管理しているアカウントはGeneralBankAccountの制約は
	 * 20万円以上入金できないことから・・・・
	 * 
	 */
	public void testBankTransferParserNG_05(){
		
		
		/*
		 * 最初にクライアントから受け取る文字列は establish_connectionWordであるので
		 * それがきたら・・・
		 */
		super.assertEquals(OK,server_parser.ParseMessagefromClient(establish_connectionWord));
		
		/*
		 * 次はクライアントから受け取るメッセージは振り込み処理命令なので
		 * それがきたら・・・
		 */
		super.assertEquals(OK,server_parser.ParseMessagefromClient("send"));
		
		/*
		 * 次にクライアントから来るメッセージはアカウントIDを指定する文字列より
		 * それがきたら・・・
		 * 
		 */
		super.assertEquals(OK, server_parser.ParseMessagefromClient(accountID));
		
		/*
		 * 次にクライアントからくる文字列は金額情報だからそれがきたら・・
		 * 今回は試験的に200万の入金を確認する
		 */
		super.assertEquals(OK,server_parser.ParseMessagefromClient(String.valueOf(2000000)));
		
		/*
		 * 次にクライアントから文字列はコメント情報であるのでそれが来たら・・・
		 * 
		 */
		super.assertEquals(ABORT, server_parser.ParseMessagefromClient(message));
		
		/*
		 * 入金が失敗した場合残金が変化していてはいけない
		 */
		super.assertEquals(init_cash, MyBank.getAccountAmount());

	}

}
