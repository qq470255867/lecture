package com.icycraft.league_lecture.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    static int flag = 1;//用来判断文件是否删除成功
    public static String downloadAllAttachment(String name,ArrayList<String> allFilePath,String filePath, HttpServletRequest request, HttpServletResponse response) throws Exception {
        File dirFile = new File(filePath) ;

        List<File> filesList = new ArrayList<>();

        File[] files=new File[allFilePath.size()];
        String path;
        for (int j = 0; j < allFilePath.size(); j++) {
            path=allFilePath.get(j);
            File file = new File(path);
            files[j]=file;
            filesList.add(file);
        }
        return	downLoadFiles(name,filesList,filePath,request,response);

    }

    public static void deleteFile(File file){
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()){
            flag = 0;
            System.out.println("文件删除失败,请检查文件路径是否正确");
            return;
        }
        //取得这个目录下的所有子文件对象
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f: files){
            //打印文件名
            String name = file.getName();
            System.out.println(name);
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()){
                deleteFile(f);
            }else {
                f.delete();
            }
        }
        //删除空文件夹  for循环已经把上一层节点的目录清空。
        file.delete();
    }


    //获取文件夹下的所有文件的路径
    public static ArrayList<String> Dir(File dirFile) throws Exception {
        ArrayList<String> dirStrArr = new ArrayList<String>();
        if (dirFile.exists()) {
            //直接取出利用listFiles()把当前路径下的所有文件夹、文件存放到一个文件数组
            File files[] = dirFile.listFiles();
            for (File file : files) {
                //如果传递过来的参数dirFile是以文件分隔符，也就是/或者\结尾，则如此构造
                if (dirFile.getPath().endsWith(File.separator)) {
                    dirStrArr.add(dirFile.getPath() + file.getName());
                } else {
                    //否则，如果没有文件分隔符，则补上一个文件分隔符，再加上文件名，才是路径
                    dirStrArr.add(dirFile.getPath() + File.separator
                            + file.getName());
                }
            }
        }
        return dirStrArr;
    }

    //下载文件
    public static String downLoadFiles(String name,List<File> files,String filePath ,HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //这里的文件你可以自定义是.rar还是.zip
            filePath = filePath+name+"青年大学习截图.zip";
            File file = new File(filePath);
            if (!file.exists()){
                file.createNewFile();
            }else{
                //如果压缩包已经存在则删除后重新打包压缩
                file.delete();
            }
            response.reset();
            //创建文件输出流
            FileOutputStream fous = new FileOutputStream(file);
            /**打包的方法用到ZipOutputStream这样一个输出流,所以这里把输出流转换一下*/
            ZipOutputStream zipOut = new ZipOutputStream(fous);
            /**这个方法接受的就是一个所要打包文件的集合，还有一个ZipOutputStream*/
            zipFiles(files, zipOut);
            zipOut.close();
            fous.close();

            return filePath;

        }catch (Exception e) {
            e.printStackTrace();
            //return "文件下载出错" ;
        }
        return "文件下载出错";
    }

    //把接受的全部文件打成压缩包
    public static void zipFiles(List files,ZipOutputStream outputStream) {

        int size = files.size();

        for(int i = 0; i < size; i++) {

            File file = (File) files.get(i);

            zipFile(file, outputStream);
        }
    }

    //将单个文件打包
    public static void zipFile(File inputFile,ZipOutputStream ouputStream) {

        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    //org.apache.tools.zip.ZipEntry
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    //关闭创建的流对象
                    bins.close();
                    IN.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
