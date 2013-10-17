package Bank;

import java.util.LinkedList;
import java.util.Queue;


/**
 * FIFO(First In First Out)を実現するデータ構造をこのクラスでは定義します
 * 
 * 
 * @version 1.0
 * @author fvi@
*/

public class MessageQueue {
	//LinkedListクラスはQueueインターフェースの機能を実装する
	//<>を設定することでqオブジェクトが扱えるオブジェクトのタイプをStringに限定する
	private Queue<String> q;
	
	//キューに追加する
	public boolean Offer(String input){
		return q.offer(input);
	}
	//メッセージを取り出す　なければnullを返してくる
	public String poll(){
		return q.poll();
	}
	
	public int length(){
		return q.size();
	}
	//コンストラクタ1
	public MessageQueue(String command,int amount,String message){
		q = new LinkedList<String>();
		
		//メッセージの追加
		q.offer(command);
		q.offer(String.valueOf(amount));
		q.offer(message);

		
		
		
	}
	//コンストラクタ2
	public MessageQueue() {
		// TODO Auto-generated constructor stub
		q = new LinkedList<String>();
	}
	
}