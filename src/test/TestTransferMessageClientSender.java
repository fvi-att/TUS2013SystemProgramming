/**
 * システムプログラミング第6回
 * 課題通過テストプログラム
 * 
 * 
 * 
 * TransferMessageSenderクラスをテストが通過するように記述すること
 * 
 * @version 1.0
 * @author  fvi@
 * 
 * 
 */
package test;

import junit.framework.TestCase;
import Bank.BankTransferConfiguration;
import Bank.TransferMessageClientSender;

public class TestTransferMessageClientSender extends TestCase {

	TransferMessageClientSender sender;
	String account = "sampleID";
	int amount = 10000;
	String comment = "学籍番号:氏名";

	/**
	 * テストクラスのコンストラクタメソッド
	 * 
	 * @param name
	 */
	public TestTransferMessageClientSender(String name) {
		super(name);
		// 引数に送信するコマンド、金額、コメントを入れる
		sender = new TransferMessageClientSender("send", account, amount,
				comment);
	}

	/**
	 * テスト開始前の準備操作
	 * 
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * テスト終了後の後片付け
	 * 
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * 通常の通信処理を行う際に適切な入力に対して適切な返答を行うことができるかのテスト
	 */
	public void testOKTransfer() {
		// まず初めにサーバに送信する内容は接続開始メッセージであるが
		// それはBankTransferConfiguration.javaに記述されてある
		super.assertEquals(BankTransferConfiguration.establish_connectionWord,
				sender.OutputMessage());

		// 問題がなければサーバから"OK"が返答される
		sender.InputMessage(BankTransferConfiguration.OK);

		// 次に返すコマンドは送金命令の"send"となるはずである
		super.assertEquals("send", sender.OutputMessage());

		// 問題がなければサーバから"OK"が返答される
		sender.InputMessage(BankTransferConfiguration.OK);

		// 次に返すコマンドはアカウント名の"sampleID"（変数:account）となるはずである
		super.assertEquals(account, sender.OutputMessage());

		// 問題がなければサーバから"OK"が返答される
		sender.InputMessage(BankTransferConfiguration.OK);

		// 次に返すコマンドは金額を文字列情報に変換したデータ
		super.assertEquals(String.valueOf(amount), sender.OutputMessage());

		// 問題がなければサーバから"OK"が返答される
		sender.InputMessage(BankTransferConfiguration.OK);

		// 次にコメントを出力するようにする
		super.assertEquals(comment, sender.OutputMessage());
	}

	/**
	 * 
	 * サーバから abort [異常発生]が帰ってきた時
	 */

	public void testNGTransfer01() {
		// 初めに正常なコマンドを送信する
		super.assertEquals(BankTransferConfiguration.establish_connectionWord,
				sender.OutputMessage());

		// サーバからの返答でBankTransferConfiguration.ABORTの文字列情報"abort"が返ってきたとする
		sender.InputMessage(BankTransferConfiguration.ABORT);

		// これ以降どのような正常なコマンドが返答されてもquitのみ返すようにする
		super.assertEquals(BankTransferConfiguration.QUIT,
				sender.OutputMessage());

		// 仮に正常な返答が帰ってきてもquitしか返さないようにする
		sender.InputMessage("OK");
		super.assertEquals(BankTransferConfiguration.QUIT,
				sender.OutputMessage());

	}

	/**
	 * 異常な通信が発生した場合
	 * 
	 */
	public void testNGTransfer02() {
		// 返信として OK abort　以外の予想外の返答が来たときは　quitを送信して通信を終了させる

		// まず初めにサーバに送信する内容は接続開始メッセージであるが
		// それはBankTransferConfiguration.javaに記述されてある
		super.assertEquals(BankTransferConfiguration.establish_connectionWord,
				sender.OutputMessage());

		// ここで予想外の返答”HELLO\"が返ってくる
		sender.InputMessage("HELLO");

		// これ以降どのような正常なコマンドが返答されてもquitのみ返すようにする
		super.assertEquals(BankTransferConfiguration.QUIT,
				sender.OutputMessage());

		// 仮に正常な返答が帰ってきてもquitしか返さない
		sender.InputMessage("OK");
		super.assertEquals(BankTransferConfiguration.QUIT,
				sender.OutputMessage());

	}

}
