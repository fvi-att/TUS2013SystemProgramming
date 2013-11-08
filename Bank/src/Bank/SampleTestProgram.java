package Bank;

//importを用いることで他のプログラムを引用することができる
//例　数学用関数　Mathなど

public class SampleTestProgram {

	/**
	 * @param a int型 b int型　a+b のint型の結果を返す
	 */
	public static int Plus(int a, int b){
		// 修正前return a;
		return a + b;
	}
	
	/**
	 * @param a int型 b int型　a-b のint型の結果を返す
	 */
	public static int Minus(int a, int b) {
		// TODO 自動生成されたメソッド・スタブ
		//修正前return 0;
		return a - b;
	}
	
	/**
	 * @param a int型
	 * @return int型　2つの引数を掛け合わせたもの
	 * 
	 */
	public static int Multiply(int a, int b){
		return 0;
	}
	/**
	 * @param a
	 * @param b
	 * @return aをb乗した値を返す。
	 */
	public static double pow(double a,double b){
		/*
		 * java.lang.Mathクラスからn乗を行うプログラムを探す。
		 * もしくは自身でこの部分のコードを実装しても構わない
		 * javaのAPIはここから参照することができる！
		 * 
		 * http://docs.oracle.com/javase/jp/7/api/
		 */
		return 0;
	}
	
	public static double intTodouble(int a){
		/*
		 * このメソッドは引数にint型を用いている
		 * どのようにしてdouble型の同じ数値を返せばよいだろうか？
		 * 
		 */
		return (double)a;
		
	}


}
