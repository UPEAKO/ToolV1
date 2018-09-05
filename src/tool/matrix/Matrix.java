package tool.matrix;

import java.io.File;
import java.util.ArrayList;

/**
 * 矩阵
 * 1.定义
 * 2.方法:从文件获取矩阵,从键盘输入获取矩阵,加减乘,转置,求逆,打印矩阵
 * 3.使用泛型通配
 */
public class Matrix<T> {
    /**
     * 嵌套LIST表示矩阵
     */
    private ArrayList<ArrayList<T>> _matrix = new ArrayList<>();

    /**
     * 返回某一位置的矩阵元素
     * @param row 行
     * @param column 列
     * @return 返回值
     */
    private T getElementByRC(int row, int column) {
        return _matrix.get(row).get(column);
    }

    /**
     * 改变某一矩阵元素
     * @param row 行
     * @param column 列
     * @param data 用以改变的值
     * @return 返回旧值
     */
    private T setElementAtRC(int row, int column, T data) {
        return _matrix.get(row - 1).set(column - 1, data);
    }

    /**
     * 返回矩阵行列数
     * @return 返回值
     */
    private int [] getRC() {
        int rows = _matrix.size();
        int columns = _matrix.get(0).size();
        return new int [] {rows, columns};
    }

    /**
     * 通过文件获取矩阵
     * @param file 文件
     * @param temp 暂时
     */
    public void getMatrixByFile(File file, T temp) {
        //暂时防止_matrix报错
        ArrayList<T> rowArray = new ArrayList<>();
        rowArray.add(temp);
        _matrix.add(rowArray);
    }

    /**
     * 打印矩阵
     */
    public void printMatix() {
        for (ArrayList<T> rowArray:
             _matrix) {
            for (T element:
                 rowArray) {
                System.out.print(element+", ");
            }
            System.out.print("\n");
        }
    }
}
