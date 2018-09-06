package tool.matrix;

import java.io.*;
import java.util.ArrayList;

public class MatrixTool {
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
}
