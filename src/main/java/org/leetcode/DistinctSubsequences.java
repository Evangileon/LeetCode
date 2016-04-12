package org.leetcode;

public class DistinctSubsequences {

	int[][] sameCount;

	public int numDistinct(String S, String T, int Sbegin, int Tbegin) {
		if (-1 != sameCount[Sbegin][Tbegin]) {
			return sameCount[Sbegin][Tbegin];
		}

		if (Tbegin == T.length() - 1) {
			// base case
			int same = 0;
			for (int i = Sbegin; i < S.length(); i++) {
				if (S.charAt(i) == T.charAt(Tbegin)) {
					same++;
				}
			}
			sameCount[Sbegin][Tbegin] = same;
			return same;
		}

		int same = 0;
		// boolean hit = false;
		for (int i = Sbegin; i < S.length(); i++) {
			if (S.charAt(i) == T.charAt(Tbegin)) {
				same += numDistinct(S, T, i + 1, Tbegin + 1);
			}
		}
		sameCount[Sbegin][Tbegin] = same;
		return same;
	}

	public int numDistinctDP(String S, String T) {
		 for (int k = 0; k <= S.length(); k++) {
			 sameCount[T.length()][k] = 1;
		 }
		
		for (int j = S.length() - 1; j >= 0; j--) {
			for (int i = T.length() - 1; i >= 0; i--) {
				if (S.charAt(j) == T.charAt(i)) {
					sameCount[i][j] = (sameCount[i][j + 1] + sameCount[i + 1][j + 1]);
				} else {
					sameCount[i][j] = sameCount[i][j + 1];
				}
			}
		}

		return sameCount[0][0];
	}

	public int numDistinct(String S, String T) {
		sameCount = new int[T.length() + 1][S.length() + 1];
		return numDistinctDP(S, T);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String S2 = "daacaedaceacabbaabdccdaaeaebacddadcaeaacadbceaecddecdeedcebcdacdaebccdeebcbdeaccabcecbeeaadbccbaeccbbdaeadecabbbedceaddcdeabbcdaeadcddedddcececbeeabcbecaeadddeddccbdbcdcbceabcacddbbcedebbcaccac";
		String T2 = "ceadbaa";
		String S = "adfgh";
		String T = "afg";
		DistinctSubsequences distinct = new DistinctSubsequences();
		System.out.println(distinct.numDistinct(S2, T2));
	}

}
