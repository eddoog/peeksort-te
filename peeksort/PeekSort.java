package peeksort;

public class PeekSort {
    /* Taken from: https://github.com/sebawild/nearly-optimal-mergesort-code/blob/master/src/wildinter/net/mergesort/PeekSort.java 
     * with slight modifications to fit the criteria of the experiment
    */

	public PeekSort() {}

	public static void sort(final int[] a, final int left, final int right) {
		int n = right - left + 1;
		peeksort(a, left, right, left, right, new int[n]);
	}

	public static void peeksort(int[] A, int left, int right, int leftRunEnd, int rightRunStart, final int[] B) {	
		if (leftRunEnd == right || rightRunStart == left) return; // Algorithm is presumably sorted already

		int mid = left + ((right - left) >> 1); // Find the mid point = floor(right-left/2)
		if (mid <= leftRunEnd) {
			// |XXXXXXXX|XX     X|
			peeksort(A, leftRunEnd+1, right, leftRunEnd+1,rightRunStart, B);
			mergeRuns(A, left, leftRunEnd+1, right, B);
		} else if (mid >= rightRunStart) {
			// |XX     X|XXXXXXXX|
			peeksort(A, left, rightRunStart-1, leftRunEnd, rightRunStart-1, B);
			mergeRuns(A, left, rightRunStart, right, B);
		} else {
			// find middle run

			int i = extendWeaklyIncreasingRunLeft(A, mid, left == leftRunEnd ? left : leftRunEnd+1);
			int j = extendWeaklyIncreasingRunRight(A, mid, right == rightRunStart ? right : rightRunStart-1) ;
			if (i == left && j == right) return;
			if (mid - i < j - mid) {
				// |XX     x|xxxx   X|
				peeksort(A, left, i-1, leftRunEnd, i-1, B);
				peeksort(A, i, right, j, rightRunStart, B);
				mergeRuns(A,left, i, right, B);
			} else {
				// |XX   xxx|x      X|
				peeksort(A, left, j, leftRunEnd, i, B);
				peeksort(A, j+1, right, j+1, rightRunStart, B);
				mergeRuns(A,left, j+1, right, B);
			}
		}
	}

    public static void moreOptimalPeekSort(int[] A, int left, int right, int leftRunEnd, int rightRunStart, final int[] B) {
		if (leftRunEnd == right || rightRunStart == left) return;

		int mid = left + ((right - left) >> 1); // Find the mid point = floor(right-left/2)
		if (mid <= leftRunEnd) {
			// |XXXXXXXX|XX     X|
			peeksort(A, leftRunEnd+1, right, leftRunEnd+1,rightRunStart, B);
			mergeRuns(A, left, leftRunEnd+1, right, B);
		} else if (mid >= rightRunStart) {
			// |XX     X|XXXXXXXX|
			peeksort(A, left, rightRunStart-1, leftRunEnd, rightRunStart-1, B);
			mergeRuns(A, left, rightRunStart, right, B);
		} else {
			// find middle run

			final int i, j;
			if (A[mid] <= A[mid+1]) {
				i = extendWeaklyIncreasingRunLeft(A, mid, left == leftRunEnd ? left : leftRunEnd+1);
				j = mid+1 == rightRunStart ? mid : extendWeaklyIncreasingRunRight(A, mid+1, right == rightRunStart ? right : rightRunStart-1);
			} else {
				i = extendStrictlyDecreasingRunLeft(A, mid, left == leftRunEnd ? left : leftRunEnd+1);
				j = mid+1 == rightRunStart ? mid : extendStrictlyDecreasingRunRight(A, mid+1,right == rightRunStart ? right : rightRunStart-1);
				reverseRange(A, i, j);
			}

			if (i == left && j == right) return;
			if (mid - i < j - mid) {
				// |XX     x|xxxx   X|
				peeksort(A, left, i-1, leftRunEnd, i-1, B);
				peeksort(A, i, right, j, rightRunStart, B);
				mergeRuns(A,left, i, right, B);
			} else {
				// |XX   xxx|x      X|
				peeksort(A, left, j, leftRunEnd, i, B);
				peeksort(A, j+1, right, j+1, rightRunStart, B);
				mergeRuns(A,left, j+1, right, B);
			}
		}
	}
	
	/**
	 * Merges runs A[l..m-1] and A[m..r] in-place into A[l..r]
	 * with Sedgewick's bitonic merge (Program 8.2 in Algorithms in C++)
	 * using B as temporary storage.
	 * B.length must be at least r+1.
	 */

    private static void mergeRuns(int[] A, int l, int m, int r, int[] B) {
		--m;// mismatch in convention with Sedgewick
		int i, j;
		assert B.length >= r+1;

        for (i = m+1; i > l; --i) B[i-1] = A[i-1];
		for (j = m; j < r; ++j) B[r+m-j] = A[j+1];
		for (int k = l; k <= r; ++k)
			A[k] = B[j] < B[i] ? B[j--] : B[i++];
	}

    /**
	 * Reverse the specified range of the specified array.
	 *
	 * @param a  the array in which a range is to be reversed
	 * @param lo the index of the first element in the range to be
	 *           reversed
	 * @param hi the index of the last element in the range to be
	 *           reversed
	 */
	private static void reverseRange(int[] a, int lo, int hi) {
		while (lo < hi) {
			int t = a[lo]; a[lo++] = a[hi]; a[hi--] = t;
		}
	}

	private static int extendWeaklyIncreasingRunLeft(final int[] A, int i, final int left) {
		while (i > left && A[i-1] <= A[i]) --i;
		return i;
	}

	private static int extendWeaklyIncreasingRunRight(final int[] A, int i, final int right) {
		while (i < right && A[i+1] >= A[i]) ++i;
		return i;
	}

	private static int extendStrictlyDecreasingRunLeft(final int[] A, int i, final int left) {
		while (i > left && A[i-1] > A[i]) --i;
		return i;
	}

	private static int extendStrictlyDecreasingRunRight(final int[] A, int i, final int right) {
		while (i < right && A[i+1] < A[i]) ++i;
		return i;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
