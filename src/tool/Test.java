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

        System.out.println("PF:");
        A.inversion().printMatrix();
        System.out.println("GS:");
        A.inversion1().printMatrix();

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


/*
        c++与java类中只需对应native结果即可,其余随意

        设置jni.h 与jni_md.h的搜索路径
        #include <jni.h>
        非必须,只是一个好习惯,避免重复定义
        #ifndef _Included_m
        #define _Included_m
        必须,似乎JNI由C实现
        #ifdef __cplusplus
        extern "C" {
        #endif
        JNIEXPORT jdouble JNICALL Java_Test_circle (JNIEnv *, jobject, jdouble);
        #ifdef __cplusplus
        }
        #endif
        #endif
        ifndef 若果没有定义
        define 定义
        ifdef 如果定义
        endif与ifndef ifdef 条件分支对应
*/

/*
public static void loadLibrary() {
        String libName = "libTest.so";
        try {
            File file = new File(System.getProperty("user.dir") + File.separator + System.getProperty("java.class.path"));
            JarFile jarFile = new JarFile(file);
            JarInputStream jarInputStream = new JarInputStream(new BufferedInputStream(new FileInputStream(file)));
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                if (!jarEntry.isDirectory()) {
                    if (jarEntry.getRealName().equals(libName)) {
                        BufferedInputStream jarInputStream1 = new BufferedInputStream(jarFile.getInputStream(jarEntry));
                        byte [] temp = new byte[1024];
                        File tempFile = File.createTempFile("lib",".so");
                        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                        int read = -1;
                        while ((read = jarInputStream1.read(temp) )!= -1) {
                            fileOutputStream.write(temp,0,read);
                            fileOutputStream.flush();
                        }
                        fileOutputStream.close();
                        jarInputStream1.close();
                        jarInputStream.close();
                        System.load(tempFile.getAbsolutePath());
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 */