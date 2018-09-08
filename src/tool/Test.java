package tool;

import tool.matrix.Matrix;
import tool.tool.Tool;

import java.io.*;
import java.util.ArrayList;

public class Test {
    public static void main(String [] args) {
        //矩阵测试
        ArrayList<ArrayList<Double>> _matrixA = Tool.getMatrixByFilePath("/home/ubd/data/matrix1.txt");
        ArrayList<ArrayList<Double>> _matrixB = Tool.getMatrixByFilePath("/home/ubd/data/matrix2.txt");
        Matrix A = new Matrix(_matrixA);
        Matrix B = new Matrix(_matrixB);
        System.out.println("A");
        A.printMatrix();
        System.out.println("B");
        B.printMatrix();
        System.out.println("加");
        A.plus(B).printMatrix();
        System.out.println("减");
        A.minus(B).printMatrix();
        System.out.println("乘");
        A.multiply(B).printMatrix();
        System.out.println("转置");
        A.inversion().printMatrix();
        //角度转换测试
        double radian = 0.0;

        File file = new File("/home/ubd/data/rad.txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s = bufferedReader.readLine();
            radian = Double.parseDouble(s);
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(Tool.angleToRadian(radian));
        System.out.println("角度:"+Tool.radianToAngle(Tool.angleToRadian(35.5959985999999)));
        //坐标方位角
        System.out.println(Tool.radianToAngle(Tool.coordinateToAzimuthAngle(-89.378,25.555,-123.456,37.125)));
    }
}
