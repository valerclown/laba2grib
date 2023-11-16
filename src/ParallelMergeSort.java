import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort {

    public static void main(String[] args) {
        int[] array = {5, 3, 1, 6, 2, 8, 10, 11, 12, 4, 7, 9};
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new MergeSortTask(array, 0, array.length - 1));
        System.out.println(Arrays.toString(array));
    }

    static class MergeSortTask extends RecursiveAction {
        private int[] array;
        private int low;
        private int high;

        MergeSortTask(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (low < high) {
                int mid = low + (high - low) / 2;

                MergeSortTask leftTask = new MergeSortTask(array, low, mid);
                MergeSortTask rightTask = new MergeSortTask(array, mid + 1, high);

                invokeAll(leftTask, rightTask);

                merge(array, low, mid, high);
            }
        }
    }

    static void merge(int[] array, int low, int mid, int high) {
        int[] left = Arrays.copyOfRange(array, low, mid + 1);
        int[] right = Arrays.copyOfRange(array, mid + 1, high + 1);

        int i = 0, j = 0, k = low;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        while (i < left.length) {
            array[k++] = left[i++];
        }

        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
}
