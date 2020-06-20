
public class Rekurzija {

	public static void main(String[] args) {
		// System.out.println(brojRukovanja(10));

		String s = "xjoIDHFAXxx";
		q(4);
	}

	public static int fib(int position) {
		if (position <= 2)
			return 1;
		return fib(position - 1) + fib(position - 2);
	}

	public static int brojRukovanja(int brLjudi) {
		if (brLjudi <= 1)
			return 0;
		return brLjudi - 1 + brojRukovanja(brLjudi - 1);
	}

	// public static int brojRukovanjaParovi(int brParova) {
	// //u grupi od n parova samo je jedan pol u mogucnosti da se sa svima rukuje
	// if(brParova <= 1) return 1;
	// return (int) Math.pow(brParova, 2) +
	// }

	public static int brojPonavljanjaXUStringu(String s) {
		if (s.length() == 0)
			return 0;
		if (s.charAt(0) != 'x')
			return 0 + brojPonavljanjaXUStringu(s.substring(1));

		return 1 + brojPonavljanjaXUStringu(s.substring(1));
	}
	
	public static void q(int i) {
		System.out.println("*");
		if(i > 1) {
			q(i/2);
		}
		if(i>2) {
			q(i/2);
			System.out.println("*");
		}
	}

}
