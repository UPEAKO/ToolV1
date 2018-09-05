package tool.sort;


public class Sort {
    /**
     * 交换元素
     *
     * @param array   数组
     * @param target1 下标1
     * @param target2 下标2
     */
    private static <T> void swap(T [] array, int target1, int target2) {
        T temp = array[target1];
        array[target1] = array[target2];
        array[target2] = temp;
    }

    /**
     * 打印显示数组
     *
     * @param array 数组
     */
    public static <T> void printArray(T[] array) {
        for (T temp :
                array) {
            System.out.print(temp + ", ");
        }
        System.out.print("\n");
    }

    /**
     * 冒泡排序
     *
     * @param array 待排序数组
     */
    public static <T extends Comparable<T>> void bubbleSort(T [] array) {
        int length = array.length;
        if (length == 0)
            return;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (array[j].compareTo(array[j + 1]) > 0)
                    swap(array, j, j + 1);
            }
        }
    }

    /**
     * 选择排序
     *
     * @param array 待排序数组
    */
    public static <T extends Comparable<T>> void selectSort(T [] array) {
        int length = array.length;
        if (length == 0)
            return;
        for (int i = 0; i < length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < length; j++) {
                if (array[j].compareTo(array[minIndex]) > 0)
                    minIndex = j;
            }
            if (minIndex != i)
                swap(array, i, minIndex);
        }
    }

    /**
     * 插入排序
     *
     * @param array 待排序数组
     */

    public static <T extends Comparable<T>> void insertSort(T [] array) {
        int length = array.length;
        if (length == 0)
            return;
        for (int i = 1; i < length; i++) {

        }
    }
}
