package tool.Level;

import java.io.*;
import java.util.ArrayList;

public class LevelApproximation {
    ArrayList<Point> points = new ArrayList<>();
    ArrayList<SurveyValue> surveyValues = new ArrayList<>();

    /**
     * 从文件获取数据
     */
    public LevelApproximation() {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("/home/ubd/data/level.txt"));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int pointNum = 0;
            int knownPointNum = 0;
            int eachLengthNum = 0;
            String temp = bufferedReader.readLine();
            if (temp != null) {
                String[] ones = temp.split(",");
                pointNum = Integer.parseInt(ones[0]);
                knownPointNum = Integer.parseInt(ones[1]);
                eachLengthNum = Integer.parseInt(ones[2]);
            }
            //设置点
            for (int i = 0; i < pointNum; i++) {
                if (i < knownPointNum) {
                    temp = bufferedReader.readLine();
                    String[] nexts = temp.split(",");
                    Point tempPoint = new Point(nexts[0],Double.parseDouble(nexts[1]),true);
                    points.add(tempPoint);
                } else {
                    Point tempPoint = new Point(null,-9999, false);
                    points.add(tempPoint);
                }
            }
            //设置每段数据
            for (int i = 0; i < eachLengthNum; i++) {
                temp = bufferedReader.readLine();
                String[] nexts = temp.split(",");
                SurveyValue surveyValue = new SurveyValue(nexts[0],nexts[1],Double.parseDouble(nexts[2]),
                        Double.parseDouble(nexts[3]),0,0);
                surveyValues.add(surveyValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 近似平差计算
     */
    public void Calculation() {
        double sumLength = 0;
        double sumHeight = 0;
        for (SurveyValue temp:
             surveyValues) {
            sumLength += temp.eachLength;
            sumHeight += temp.eachHeight;
        }
        //闭合差
        double deltaHeightSum = (points.get(1).height - points.get(0).height) - sumHeight;
        if (deltaHeightSum > 1) {
            System.out.println("闭合差超限!");
            System.exit(1);
        }
        //每段高差改正数及改正后高差
        for (int i = 0; i < surveyValues.size(); i++) {
            double eachDeltaHeight = deltaHeightSum * surveyValues.get(i).eachLength / sumLength;
            surveyValues.get(i).deltaHeight = eachDeltaHeight;
            surveyValues.get(i).eachHeightAfterCorrect = surveyValues.get(i).eachHeight + eachDeltaHeight;
        }
        //依据surveyValues计算每个点的实际高程
        for (int i = 0; i < surveyValues.size() - 1; i++) {
            int begin = searchPointByName(surveyValues.get(i).begin);
            int end = searchPointByName(surveyValues.get(i).end);
            points.get(end).height = points.get(begin).height + surveyValues.get(i).eachHeightAfterCorrect;
        }
        int a = 0;
        int c = a + 1;
    }

    private int searchPointByName(String name) {
        int result = -1;
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).name != null) {
                if (points.get(i).name.equals(name)) {
                    result = i;
                    break;
                }
            } else {
                points.get(i).name = name;
                result = i;
                break;
            }
        }
        return result;
    }
}
