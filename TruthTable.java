
import java.util.*;

public class TruthTable {
	public static void main(String[] args) {
		/*
		 * first line is number of variables
		 * second line is expression in postfix notation
		 * only accepts !, |, and & as operators
		 * 
		 * example input: 
		 * 3
		 * A B | C &
		 * 
		 */
		Scanner sc = new Scanner(System.in);
		Stack<Integer> stk = new Stack<Integer>();
		int vars = sc.nextInt();
		sc.nextLine();
		String s = sc.nextLine();
		s = s.toUpperCase().trim();
		int n = s.length();
		ArrayList<Integer> ret[] = new ArrayList[1 << vars];
		for (int i = 0; i < (1 << vars); i++)
			ret[i] = new ArrayList<Integer>();

		for (int mask = 0; mask < (1 << vars); mask++) {
			for (int i = 0; i < vars; i++) {
				ret[mask].add(((1 << i) & mask) != 0 ? 1 : 0);
			}
			for (int i = 0; i < n; i++) {
				char c = s.charAt(i);
				if (c == ' ')
					continue;
				if (c == '!') {
					int x = stk.pop();
					stk.push((x == 0) ? 1 : 0);
					ret[mask].add((x == 0) ? 1 : 0);
				} else if (c == '|' || c == '&') {
					int x = stk.pop();
					int y = stk.pop();
					if (c == '|') {
						stk.push(x | y);
						ret[mask].add(x | y);
					} else {
						stk.push(x & y);
						ret[mask].add(x & y);
					}
				} else if (c >= 'A' && c <= 'Z') {
					int x = c - 'A';
					stk.push(((1 << x) & mask) != 0 ? 1 : 0);
				} else {
					System.out.println("error " + i + " " + s.charAt(i));
				}
			}
			assert (stk.size() == 1);
			stk.clear();
		}

		//TODO: ugly spacing
		//TODO: change output format to infix
		for (int i = 0; i < vars; i++) {
			System.out.print((char) ('A' + i) + " ");
		}
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			if (c == '!' || c == '|' || c == '&')
				System.out.print(s.substring(0, i + 1)+" ");
		}
		System.out.println();
		for (int i = 0; i < (1 << vars); i++) {
			for (int x : ret[i])
				System.out.print((x==0?"F":"T")+ "  ");
			System.out.println();
		}
	}
}
