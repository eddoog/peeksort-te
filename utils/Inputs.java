package utils;

import java.util.Random;

/**
 * Methods to create (random) inputs.
 *
 * Taken from: https://github.com/sebawild/nearly-optimal-mergesort-code/blob/master/src/wildinter/net/mergesort/Inputs.java
 */
public class Inputs {
	/** return new array filled with random permutation of [1..n] */
	public static int[] randomPermutation(final int len, Random random) {
		int[] res = new int[len];
		for (int i = 1; i <= len; ++i) res[i - 1] = i;
		for (int i = len; i > 1; i--)
			swap(res, i - 1, random.nextInt(i));
		return res;
	}

	/** return new array filled with iid uniform numbers in [1..u] */
	public static int[] randomUaryArray(final int u, final int len, Random random) {
		int res[] = new int[len];
		for (int i = 0; i < res.length; i++) {
			res[i] = random.nextInt(u)+1;
		}
		return res;
	}

    private static void swap(int[] a, int i, int j) {
		final int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
}
