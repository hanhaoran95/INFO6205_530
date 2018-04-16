package genetics;

import java.util.NoSuchElementException;

public class IndexMaxPQ<Item extends Comparable<Item>>   {
	
	private int n;           // number of elements on PQ
    private int[] pq;        // binary heap using 1-based indexing        pq[n]=i是索引的逻辑位置
    private int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i    qp[i]=n是索引i的物理存储位置
    private Item[] items;      // keys[i] = priority of i
    
    public IndexMaxPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        n = 0;
        items = (Item[]) new Comparable[maxN + 1];    
        pq   = new int[maxN + 1];
        qp   = new int[maxN + 1];                   
        for (int i = 0; i <= maxN; i++)
            qp[i] = -1;
    }
    
    public boolean isEmpty() {
        return n == 0;
    }
    
    
    //是否存在索引为k的值
    public boolean contains(int i) {
        return qp[i] != -1;
    }
    
    public void insert(int i, Item item) {
        if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
        n++;
        qp[i] = n;      
        pq[n] = i;
        items[i] = item;
        swim(n);
    }
   
    public int maxIndex() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }
    
    public Item maxKey() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return items[pq[1]];
    }
    
    public int delMax() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        int max = pq[1];
        exch(1, n--);
        sink(1);

        assert pq[n+1] == max;
        qp[max] = -1;        // delete
        items[max] = null;    // to help with garbage collection
        pq[n+1] = -1;        // not needed
        return max;
    }
    
    
    //Returns the key associated with index i
    public Item keyOf(int i) {
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        else return items[i];
    }
    
    public void changeKey(int i, Item item) {
        if (!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        items[i] = item;
        swim(qp[i]);
        sink(qp[i]);
    }
    
    private boolean less(int i, int j) {
        return items[pq[i]].compareTo(items[pq[j]]) < 0;
    }
    
    private void exch(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }
    
    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(j, j+1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }


	

}
