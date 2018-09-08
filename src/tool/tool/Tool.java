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


    /*public static double radianToAngle(double radian) {
        double allSecond = (radian / Math.PI) * (180 * 60 * 60);
        //度
        int degree = (int) allSecond/(60 * 60);
        double temp = allSecond - degree * 60 * 60;
        //分
        int minute = (int) temp / 60;
        //秒
        double secondDetial = temp - minute * 60;
        int second = (int) secondDetial;
        *//*厘秒*//*
        double centisecondDetail = (secondDetial - second) * 100;
        int centisecond = (int) (centisecondDetail + 0.5);
        String secondStr = null,centisecondStr = null,minuteStr = null,degreeStr = null;
        //厘秒不进位
        if (centisecond < 100) {
             degreeStr = Integer.toString(degree);
             minuteStr = minute < 10 ? "0"+Integer.toString(minute) : Integer.toString(minute);
             secondStr = second < 10 ? "0"+Integer.toString(second) : Integer.toString(second);
             centisecondStr = centisecond < 10 ? "0"+Integer.toString(centisecond) : Integer.toString(centisecond);
        }
        //厘秒进位
        else {
            //秒进位
            if (second == 59) {
                //分进位
                if (minute == 59) {
                    degreeStr = Integer.toString(degree + 1);
                    minuteStr = "00";
                    secondStr = "00";
                    centisecondStr = "00";
                }
                //分不进位
                else {
                    degreeStr = Integer.toString(degree);
                    minuteStr = minute+1 < 10 ? "0"+Integer.toString(minute+1):Integer.toString(minute + 1);
                    secondStr = "00";
                    centisecondStr = "00";
                }
            }
            //秒不进位
            else {
                degreeStr = Integer.toString(degree);
                minuteStr = Integer.toString(minute);
                secondStr = second + 1 < 10 ? "0"+Integer.toString(second + 1):Integer.toString(second + 1);
                centisecondStr = "00";
            }
        }

        //结果
        String result = degreeStr + "." + minuteStr + secondStr + centisecondStr;
        return Double.parseDouble(result);
    }*/

    /**
     * 由坐标转方位角
     * @param x1 基点x
     * @param y1 基点y
     * @param x2 x2
     * @param y2 y2
     * @return 方位角
     */
    public static double coordinateToAzimuthAngle(double x1, double y1, double x2, double y2) {
        double result = 0;
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        //deltaX == 0 时
        if (Math.abs(deltaX) < 1.0E-9) {
            if (deltaY > 1.0E-9)
                result = Math.PI / 2;
            else if (deltaY < -1.0E-9)
                result = Math.PI * 3 / 2;
        } else {
            //数学上弧度值
            double angleInMath = Math.atan(deltaY / deltaX);
            if (deltaX > 1.0E-9) {
                if (deltaY > 1.0E-9)
                    result = angleInMath;
                else if (deltaY < -1.0E-9)
                    result = Math.PI * 2 + angleInMath;
            } else if (deltaX < -1.0E-9)
                result = Math.PI + angleInMath;
        }
        return result;
    }

    /**
     * 弧度转角度
     * @param radian 弧度(限制2*PI之内)
     * @return 返回角度 36.595925
     * eg. 36.59599959
     */
    public static double radianToAngle(double radian) {
        double result = 0;
        double allSecond = (radian / Math.PI) * (180 * 60 * 60);
        //度
        int degree = (int) allSecond/(60 * 60);
        double temp = allSecond - degree * 60 * 60;
        //分
        int minute = (int) temp / 60;
        //秒
        double secondDetial = temp - minute * 60;
        int second = (int) secondDetial;
        /*厘秒*/
        double centisecondDetail = (secondDetial - second) * 100;
        int centisecond = (int) (centisecondDetail + 0.5);
        //厘秒不进位
        if (centisecond < 100) {
            result = degree + minute / 1E2 + second / 1E4 + centisecond / 1E6;
        }
        //厘秒进位
        else {
            //秒进位
            if (second == 59) {
                //分进位
                if (minute == 59) {
                    result = degree + 1;
                }
                //分不进位
                else {
                    result = degree + (minute + 1) / 1E2;
                }
            }
            //秒不进位
            else {
                result = degree + minute / 1E2 + (second + 1) / 1E4;
            }
        }
        return result;
    }
}
