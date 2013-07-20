package Bank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class Recipt {
	
	String title = null;
	
	SimpleDateFormat timeformat = null;
	Date time = null;
	
	AbstractAccount account = null;
	
	HashMap<String ,Integer> transaction =null;
	
	String text = null;
	
	
	public Recipt(){
		title = "領収書";
		timeformat = new SimpleDateFormat("yyyy'年'MM'月'dd'日'HH'時'mm'分'ss'秒'");
		transaction = new HashMap<String,Integer>();
		
		
		time = new Date();
	
	}
	
	public void addTransaction(HashMap<String,Integer> transactions){
		transaction.putAll(transactions);
		
	}
	public void addTransaction(String name,int amount){
		
		transaction.put(name,amount);
		
		
	}
	public void setTime(Date fromTime){
		time = fromTime;
	}
	





	public void setTitle(String title) {
		this.title = title;
	}






	public void setTime(SimpleDateFormat time) {
		this.timeformat = time;
	}






	public void setAccount(AbstractAccount account) {
		this.account = account;
	}
	

	
	
	
	
	/**
	 * オブジェクトからの情報を元に領収書の文書を作成します
	 * 
	 */
	private void Construct() {
		text = new String("-------------"+title+"-----------\n");
		
		text += timeformat.format(time)+"\n\n";
		
		text += "-------------取引内容-----------\n";
		
		Iterator<String> ite = transaction.keySet().iterator();
		
		while(ite.hasNext()){
			String name = ite.next();
			
			text +="  "+name+":"+transaction.get(name)+"\n";
			
		}
		
		if(account != null){
			text +="\n\n------------------------------\n";
			text += "残高　　　:"+account.getCashAmount()+"\n";
			
		}
		
		text += "**************************備考**************************";
		
	}

	public boolean Print(){
		
		Construct();
		
		try{
			File directory = new File("./Recipt");
			 if(!directory.exists())
				 directory.mkdir();
			 
				 
			 FileWriter writer = new FileWriter(new File("./Recipt/"+timeformat.format(time)+".txt"));
			 
			 writer.write(text);
			 
			 writer.close();
			 
			}catch(IOException e){
			  System.out.println(e);
			  return false;
			  
			}
		return true;
	}






	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		
		Recipt recipt = new Recipt();
		
		recipt.addTransaction("引き出し", 3000);
		recipt.Print();

	}

}
