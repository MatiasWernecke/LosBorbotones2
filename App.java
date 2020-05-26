package ATM_TP;

public class App {

	public static int m2(int n) {
		if(n <= 2) {
			return 1;
		}else {
			return m2(n-1) + m2(n-2);
		}
	}
	public static void main(String [] args) {
		System.out.print(m2(5));
	}
}
