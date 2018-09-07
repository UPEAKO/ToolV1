package tool.tool;

import java.io.*;
import java.util.ArrayList;

public class Tool {
    /**
     * 从文件获取矩阵
     * @param filePath 文件路径
     * @return 返回矩阵
     */
    public static ArrayList<ArrayList<Double>> getMatrixByFilePath(String filePath) {
        ArrayList<ArrayList<Double>> _matrix = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("文价不存在!");
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String rowText = null;
                while ((rowText = bufferedReader.readLine()) != null) {
                    String [] rowTexts = rowText.split(",");
                    ArrayList<Double> rows = new ArrayList<>();
                    for (String tempS:
                            rowTexts) {
                        Double tempD = Double.parseDouble(tempS);
                        rows.add(tempD);
                    }
                    _matrix.add(rows);
                }
                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();
            } catch (IOException e) {
                System.out.println("文件打开失败!");
                e.printStackTrace();
            }
        }
        return _matrix;
    }

    /**
     * 角度转弧度
     * @param angle 角度
     * @return 返回弧度值
     * eg. 35.053126
     */
    public static double angleToRadian(double angle) {
        int degree = (int)angle;
        double temp = (angle - degree) * 100;
        int minute = (int)temp;
        double second = (temp - minute) * 100;
        return (degree * 60 * 60 + minute * 60 + second) / (180 * 60 * 60) * Math.PI;
    }

    /**
     * 弧度转角度
     * @param radian 弧度
     * @return 返回角度
     * eg. 0.6124232354436288
     */
    public static double radianToAngle(double radian) {
        double allSecond = (radian / Math.PI) * (180 * 60 * 60);
        int degree = (int) allSecond/(60 * 60);
        double temp = allSecond - degree * 60 * 60;
        int minute = (int) temp / 60;
        double second = (temp - minute * 60) * 100;
        return degree + (double) minute / 100 + second / 1000000;
    }
}
