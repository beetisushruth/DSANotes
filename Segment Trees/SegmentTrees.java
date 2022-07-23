import java.util.Arrays;
import java.util.Scanner;

/*
 * @author: Sushruth Beeti
 * @version: 1.0
 */

 /**
    * Segment Tree is a data structure used to solve range queries and point updates in O(log n) time.
    * It is a binary tree where each node stores a range of elements.
    * For example: Finding the sum of elements in the range [l, r] in the array A.
    * 1. The root node stores the range [0, n-1]
    * 2. Each leaf in the Segment Tree T will represent a single element A[i] such that 0 <= i <= n-1.
    * 3. The internal nodes of the Segment Tree T represents the union of elementary intervals A[i:j] for i <= j <= n-1.
    * Once the Segment Tree T is built, it's structure is fixed. We can update the values of nodes but not the structure.
    * Segment Tree provides two operations:
    * 1. Update: Update the value of a node in the Segment Tree T.
    * 2. Query: Find the sum of elements in the range [l, r] in the array A.
    * 
    * Segment Tree is a data structure used to solve range queries and point updates in O(log n) time.
    * It can be built using recursion in bottom-up manner.
  */
class SegmentTree {
    public int[] arr;
    public int n;
    public int[] tree;

    public SegmentTree(int[] arr) {
        this.arr = arr;
        this.n = arr.length;
        this.tree = new int[4 * n];
        buildTree(0, 0, n - 1);
    }

    public void buildTree(int node, int start, int end) {
        if(start == end) {
            tree[node] = arr[start];
        } else {
            int mid = (start + end) / 2;
            buildTree(2*node + 1, start, mid);
            buildTree(2*node + 2, mid+1, end);
            tree[node] = tree[2*node + 1] + tree[2*node+2];
        }
    }

    public void update(int node, int start, int end, int index, int val) {
        if(start == end) {
            arr[index] += val;
            tree[node] += val;
        } else {
            int mid = (start + end) / 2;
            if(index <= mid) {
                update(2*node + 1, start, mid, index, val);
            } else {
                update(2*node+ 2, mid+1, end, index, val);
            }
            tree[node] = tree[2*node + 1] + tree[2*node+2];
        }
    }

    public int query(int node, int start, int end, int l, int r) {
        if(l > end || r < start) {
            return 0;
        }
        if(l <= start && end <= r) {
            return tree[node];
        }
        int mid = (start + end) / 2;
        int p1 = query(2*node + 1, start, mid, l, r);
        int p2 = query(2*node+ 2, mid+1, end, l, r);
        return p1 + p2;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int queries = sc.nextInt();
        int[] nums = new int[n];
        for(int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        SegmentTree st = new SegmentTree(nums);
        System.out.println(Arrays.toString(st.tree));
        for(int i=0; i<queries; i++) {
            String query = sc.next();
            if(query.equals("q")) {
                int l = sc.nextInt();
                int r = sc.nextInt();
                System.out.println(st.query(1, 0, n-1, l-1, r-1));
            } else {
                int index = sc.nextInt();
                int val = sc.nextInt();
                st.update(1, 0, n-1, index-1, val);
            }
        }
        sc.close();
    }
}