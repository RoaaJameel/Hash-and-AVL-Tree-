package application;

public class HeapSort {
    // Function to build a Max-Heap from the Array
    static void buildHeap(Martyr[] arr, int N) {
        // Index of last non-leaf node
        int startIdx = (N / 2) - 1;
        for (int i = startIdx; i >= 0; i--) {
            heapify(arr, N, i);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    static void heapify(Martyr[] arr, int N, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        // If left child is larger than root
        if (l < N && arr[l].getAge() > arr[largest].getAge())
            largest = l;

        // If right child is larger than largest so far
        if (r < N && arr[r].getAge() > arr[largest].getAge())
            largest = r;

        // If largest is not root
        if (largest != i) {
            Martyr swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, N, largest);
        }
    }

    public static void heapSort(Martyr[] arr) {
        int N = arr.length;
        buildHeap(arr, N);

        for (int i = N - 1; i >= 0; i--) {
            Martyr temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // Heapify the root element to get the highest element at root again
            heapify(arr, i, 0);
        }
    }
}
