/*
 * @author fviAtt
 * @version 0.5  
 * 
 * 銀行システムATM画面
 */

package Bank;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//これは気にしなくていい
@SuppressWarnings("serial")
public class ATMFrameView extends JFrame implements ActionListener, WindowListener {

	private MyBank mybank = null;
	Container contentpane;

	private CloseNotification close_notification = mybank;//なぜ型が違うのに代入できるのかを考えよう

	/**
	 * GUI上の情報を更新するメソッド
	 * 
	 * 
	 * 
	 */
	public void modify_GUI_value() {
		//toString()を設定しておくと便利なことは何か？
		//this.setTitle("銀行システム    "+mybank.getAccount().toString());
		this.setTitle("銀行システム    UserID:" + mybank.getAccount().getAccountID() + "    残高："
				+ mybank.getAccount().getCashAmount());
	}

	public void showResult(boolean result, String message) {
		if (result) {
			System.out.println("処理を完了させました");
			if (message != null) {
				System.out.println("message::" + message);
			}
		}
	}

	/**
	 * Frame内でのボタンの操作が行われた時呼び出されるメソッド
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("受信ボタンコマンド:"+arg0.getActionCommand());
		String amount_string = null;
		
		switch(arg0.getActionCommand()){
		case "入金":
			 amount_string = JOptionPane.showInputDialog(this, "入金する金額を入力してください", "引き出し処理", JOptionPane.QUESTION_MESSAGE);
			
			//キャンセルボタンが押された時の回避処理
			if(amount_string == null){
				break;
			}
			
			if(mybank.getAccount().Deposit(Integer.parseInt(amount_string))){
				System.out.println("入金を行いました");
				
			}else{
				System.out.println("入金に失敗しました");
			}
		break;
		
			
		case "引き出し":
			 amount_string = JOptionPane.showInputDialog(this, "引き出す金額を入力してください", "引き出し処理", JOptionPane.QUESTION_MESSAGE);
			if(amount_string == null){
				break;
			}
			
			
			if(mybank.getAccount().Withdraw(Integer.parseInt(amount_string))){
				System.out.println("引き出しを行いました");
				
			}else{
				System.out.println("引き出しに失敗しました");
			}
			
			break;
		
		case "振込":
			String dst_ip = JOptionPane.showInputDialog(this, "送信先のipアドレスを入力してください", "振込", JOptionPane.QUESTION_MESSAGE);
			if(dst_ip == null){
				break;
			}
			
			 amount_string = JOptionPane.showInputDialog(this, "送金する金額を入力してください", "振込", JOptionPane.QUESTION_MESSAGE);
			//キャンセル時の回避処理
			if(amount_string == null){
				break;
			}
			
			Integer transfer_amount = null;
			try{
				transfer_amount = Integer.parseInt(amount_string);
			}catch(NumberFormatException formatErr){
				//文字列をInteger型に変換することが出来ない場合。　例　r385など文字が含まれる場合など
				System.err.println("不正な文字列が振込作業中に入力されました");
				break;
			}
			String transfer_message = JOptionPane.showInputDialog(this, "振込先へのメッセージ", "振込", JOptionPane.QUESTION_MESSAGE);
			mybank.TransfarTo(dst_ip, transfer_amount, transfer_message);
			
			break;
		
		case "口座確認":
			JOptionPane.showMessageDialog(contentpane, "残高:"+mybank.getAccount().getCashAmount()+"円\n"+
													   "口座ID："+mybank.getAccount().getAccountID()+"\n"+
													   	"口座タイプ:"+mybank.getAccount().getAccountType()
													   	, "取引中の口座情報",JOptionPane.QUESTION_MESSAGE);
			
			break;
			
			
			
		}
		
		modify_GUI_value();
	}

	public ATMFrameView() {
		super();

		//Accountはシングルトンなので他のATMViewからたとえ引っ張ってきても同じ
		mybank = new MyBank();

		//なぜ非推奨になっているのか？　考えてみよう
		this.setTitle("銀行システム    UserID:" + mybank.getAccount().getAccountID() + "    残高："
				+ mybank.getAccount().getCashAmount());
		this.setSize(500, 500);

		contentpane = getContentPane();
		//レイアウトを実装するとFrameのサイズを可変にした時に対応したサイズにしてくれる。
		contentpane.setLayout(null);

		JButton depositbutton = new JButton("入金");
		depositbutton.setSize(100, 100);
		depositbutton.setLocation(200, 200);
		depositbutton.addActionListener(this);

		JButton withdrawbutton = new JButton("引き出し");
		withdrawbutton.setSize(100, 100);
		withdrawbutton.setLocation(50, 50);
		withdrawbutton.addActionListener(this);

		JButton transferbutton = new JButton("振込");
		transferbutton.setSize(100, 100);
		transferbutton.setLocation(50, 160);
		transferbutton.addActionListener(this);

		JButton button2 = new JButton("口座確認");
		button2.setSize(200, 100);
		button2.setLocation(200, 350);
		button2.setVisible(true);
		button2.addActionListener(this);

		contentpane.add(depositbutton);
		contentpane.add(button2);
		contentpane.add(withdrawbutton);
		contentpane.add(transferbutton);

		this.setVisible(true);

	}

	public void closeListener(CloseNotification cnotify) {
		close_notification = cnotify;
	}

	//window status 処理
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("画面が閉じられました");
		if (close_notification != null)
			try {
				close_notification.whenCloseSystem();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * mainメソッド
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ATMFrameView();

	}

}
