package tool.matrix;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 矩阵
 * 1.定义
 * 2.方法:从文件获取矩阵,从键盘输入获取矩阵,加减乘,转置,求逆,打印矩阵
 * 3.使用泛型通配
 */
public class Matrix {
    /**
     * 嵌套LISDouble表示矩阵
     */
    private ArrayList<ArrayList<Double>> _matrix = new ArrayList<>();
    private int rows, columns;

    /**
     * 构造全零矩阵
     * @param rows 行
     * @param columns 列
     */
    private Matrix(int rows, int columns) {
        for (int i = 0; i < rows; i++) {
            ArrayList<Double> tempArray = new ArrayList<>();
            for (int j = 0; j < columns; j++) {
                tempArray.add(0.0);
            }
            _matrix.add(tempArray);
        }
        this.rows = rows;
        this.columns = columns;
    }

    /**
     * 构造单位矩阵
     * @param rows 行列相等
     */
    private Matrix(int rows) {
        for (int i = 0; i < rows; i++) {
            ArrayList<Double> tempArray = new ArrayList<>();
            for (int j = 0; j < rows; j++) {
                if (i != j)
                    tempArray.add(0.0);
                else
                    tempArray.add(1.0);
            }
            _matrix.add(tempArray);
        }
        this.rows = rows;
        this.columns = rows;
    }

    /**
     * 通过已知数据构造矩阵
     * @param _matrix 外部传入的嵌套列表
     */
    public Matrix(ArrayList<ArrayList<Double>> _matrix) {
        this._matrix = _matrix;
        rows = _matrix.size();
        columns = _matrix.get(0).size();
    }

    /**
     * 返回某一位置的矩阵元素
     * @param rowBeginZero 行
     * @param columnBeginZero 列
     * @return 返回值
     */
    private Double getElementByRC(int rowBeginZero, int columnBeginZero) {
        return _matrix.get(rowBeginZero).get(columnBeginZero);
    }

    /**
     * 改变某一矩阵元素
     * @param rowBeginZero 行
     * @param columnBeginZero 列
     * @param data 用以改变的值
     */
    private void setElementAtRC(int rowBeginZero, int columnBeginZero, Double data) {
        _matrix.get(rowBeginZero).set(columnBeginZero, data);
    }

    /**
     * 返回矩阵行列数
     * @return 返回值
     */
    private int [] getRC() {
        return new int [] {rows, columns};
    }

    /**
     * 打印矩阵
     */
    public void printMatrix() {
        for (ArrayList<Double> rowArray:
             _matrix) {
            for (Double element:
                 rowArray) {
                System.out.print(element+", ");
            }
            System.out.print("\n");
        }
        System.out.println("**************");
    }

    /**
     * 矩阵相加
     * @param B 相加的另一个矩阵
     * @return 返回结果矩阵
     */
    public Matrix plus(Matrix B) {
        if (!Arrays.equals(getRC(), B.getRC())) {
            System.out.println("无法相加!");
            return null;
        }
        Matrix matrix = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix.setElementAtRC(i, j, getElementByRC(i, j) + B.getElementByRC(i, j));
            }
        }
        return matrix;
    }

    /**
     * 矩阵相减
     * @param B 被减的另一个矩阵
     * @return 返回结果矩阵
     */
    public Matrix minus(Matrix B) {
        if (!Arrays.equals(getRC(), B.getRC())) {
            System.out.println("无法相减!");
            return null;
        }
        Matrix matrix = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix.setElementAtRC(i, j, getElementByRC(i, j) - B.getElementByRC(i, j));
            }
        }
        return matrix;
    }

    /**
     * 矩阵相乘
     * @param B 相乘的另一个矩阵
     * @return 返回结果矩阵
     */
    public Matrix multiply(Matrix B) {
        if (getRC()[1] != B.getRC()[0]) {
            System.out.println("无法相乘!");
            return null;
        }
        Matrix matrix = new Matrix(getRC()[0], B.getRC()[1]);
        //A行
        for (int i = 0; i < getRC()[0]; i++) {
            //B列
            for (int j = 0; j < B.getRC()[1]; j++) {
                double result = 0.0;
                //B列的每一个元素
                for (int k = 0; k < B.getRC()[0]; k++) {
                    result += getElementByRC(i, k) * B.getElementByRC(k, j);
                }
                matrix.setElementAtRC(i, j, result);
            }
        }
        return matrix;
    }

    /**
     * 矩阵求逆
     * 高斯消元法
     * 两矩阵分开进行同步操作
     * 注意被除数为0
     * @return 返回结果矩阵
     */
    public Matrix inversion1() {
        if (getRC()[0] != getRC()[1]) {
            System.out.println("无法求逆!");
            return null;
        }
        Matrix matrix = new Matrix(rows);
        /*左下角为零*/
        //rows-1个基准行
        for (int i = 0; i < rows - 1; i++) {
            //每次根据当前基准行需要消元的行数
            for (int j = i + 1; j < rows; j++) {
                //基准行为零,交换
                if (Math.abs(getElementByRC(i, i)) < 1.0E-9)
                    if (!changeBaseRow(true, i, matrix))
                        break;
                Double base = getElementByRC(j, i) / getElementByRC(i, i);
                //当前基准行下每一行需要消元的元素数量
                for (int k = 0; k < columns; k++) {
                    //原矩阵
                    setElementAtRC(j, k, getElementByRC(j, k) - getElementByRC(i, k) * base);
                    //单位阵
                    matrix.setElementAtRC(j, k, matrix.getElementByRC(j, k) - matrix.getElementByRC(i, k) * base);
                }
            }
        }
        /*右上角为零*/
        //基准行数
        for (int i = rows - 1; i > 0; i--) {
            //依据当前基准行需要消元的行数
            for (int j = i - 1; j >= 0; j--) {
                //基准行为零,交换
                if (Math.abs(getElementByRC(i, i)) < 1.0E-9)
                    if (!changeBaseRow(false, i, matrix))
                        break;
                Double base = getElementByRC(j, i) / getElementByRC(i, i);
                //当前基准行下每一行需要消元的元素数量
                for (int k = columns - 1; k >= 0 ; k--) {
                    //原矩阵
                    setElementAtRC(j, k, getElementByRC(j, k) - getElementByRC(i, k) * base);
                    //单位阵
                    matrix.setElementAtRC(j, k, matrix.getElementByRC(j, k) - matrix.getElementByRC(i, k) * base);
                }
            }
        }
        /*原矩阵->单位矩阵,单位矩阵->逆矩阵*/
        for (int i = 0; i < rows; i++) {
            //单位矩阵
            for (int j = 0; j < columns; j++) {
                matrix.setElementAtRC(i, j, matrix.getElementByRC(i, j) / getElementByRC(i, i));
            }
            //原矩阵
            setElementAtRC(i, i, 1.0);
        }
        return matrix;
    }

    /**
     * 原矩阵交换基准行,防止为零
     * @return 是否有合适基准行
     */
    private boolean changeBaseRow(boolean up, int oldBaseRow, Matrix matrix) {
        int currentBaseRow = oldBaseRow;
        if (up) {
           //基准行仍为零便换下一行
           while (currentBaseRow < rows - 1/*5行小于等于第4行时*/) {
               if (Math.abs(getElementByRC(currentBaseRow, currentBaseRow)) < 1.0E-9)
                   currentBaseRow ++;
               else
                   break;
           }
        }
        else {
            //基准行仍为零便换上一行
            while (currentBaseRow > 0/*5行小于等于第4行时*/) {
                if (Math.abs(getElementByRC(currentBaseRow, currentBaseRow)) < 1.0E-9)
                    currentBaseRow --;
                else
                    break;
            }
        }
        if (Math.abs(getElementByRC(currentBaseRow, currentBaseRow)) >= 1.0E-9) {
            //交换行
            for (int i = 0; i < columns; i++) {
                //原矩阵
                double tempOld = getElementByRC(oldBaseRow, i);
                setElementAtRC(oldBaseRow, i, getElementByRC(currentBaseRow, i));
                setElementAtRC(currentBaseRow, i, tempOld);
                //单位矩阵
                double tempNew = matrix.getElementByRC(oldBaseRow, i);
                matrix.setElementAtRC(oldBaseRow, i, matrix.getElementByRC(currentBaseRow, i));
                matrix.setElementAtRC(currentBaseRow, i, tempNew);
            }
            return true;
        } else
            return false;
    }

    /**
     * 平方根法求逆
     * @return 逆矩阵
     */
    public Matrix inversion() {
        if (rows != columns) {
            System.out.println("无法求逆");
            return null;
        }


        //取得上三角阵
        Matrix T = new Matrix(rows, columns);
        double element00 = Math.sqrt(getElementByRC(0,0));
        T.setElementAtRC(0,0, element00);
        for (int i = 1; i < columns; i++) {
            T.setElementAtRC(0,i,getElementByRC(0,i) / element00);
        }
        //行
        for (int i = 1; i < columns; i++) {
            //对角线元素
            double temp1 = 0;
            for (int j = 0; j < i; j++) {
                temp1 += T.getElementByRC(j,i) * T.getElementByRC(j,i);
            }
            double temp2 = Math.sqrt(getElementByRC(i,i) - temp1);
            T.setElementAtRC(i,i,temp2);

            //每行对角线右侧的元素遍历
            for (int j = i + 1; j < columns; j++) {
                double temp3 = 0;
                for (int k = 0; k < i; k++) {
                    temp3 += T.getElementByRC(k,i) * T.getElementByRC(k,j);
                }
                T.setElementAtRC(i,j,(getElementByRC(i,j) - temp3) / temp2);
            }
        }

        T.printMatrix();
        T.transpose().printMatrix();

        return T.inversion1().multiply(T.inversion1().transpose());
    }


    /**
     * 转置
     * @return 结果
     */
    public Matrix transpose() {
        Matrix result = new Matrix(columns,rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.setElementAtRC(j,i,getElementByRC(i,j));
            }
        }
        return result;
    }
}
