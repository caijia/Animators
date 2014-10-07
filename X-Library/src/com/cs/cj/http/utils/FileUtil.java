package com.cs.cj.http.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class FileUtil {

    /**
    * 将需要保存的数据存贮到文件中
    *
    * @param key
    *            保存到文件的键
    * @param value
    *            文件中对应的值
    * @param fileName
    *            保存文件的文件名
    * @throws Exception
    */
    public static void save(String key, String value, String fileName) throws Exception {
        File file = new File(fileName);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        Properties properties = new Properties();
        properties.put(key, value);
        properties.store(out, null);
        out.close();
    }

    /**
    * 将文件中保存的数据根据键取出来
    *
    * @param key
    *            文件保存中的键
    * @param fileName
    *            保存文件的文件名
    * @return 文件保存中的值
    * @throws Exception
    */
    public static String find(String key, String fileName) throws Exception {
        File file = new File(fileName);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        Properties properties = new Properties();
        properties.load(in);
        String value = properties.getProperty(key);
        in.close();
        file.delete();
        return value;
    }

    /**
    * 复制文件或文件夹
    *
    * @param oldpath
    *            原来的文件或文件夹路径
    * @param newpath
    *            要复制到文件或文件夹的路径
    */
    public static void copyFile(String oldpath, String newpath) {
        try {
            File file = new File(oldpath);
            if (!file.exists())
                return;
            if (file.isFile()) {
                BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
                File newDir = new File(newpath);
                if (!newDir.exists())
                    newDir.mkdirs();
                BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(newDir, file.getName())));
                writeStream(inStream, outStream, true);
            } else {
                File[] files = file.listFiles();
                if (files.length == 0) {
                    File nullfile = new File(new File(newpath), file.getName());
                    if (!nullfile.exists())
                        nullfile.mkdir();
                } else {
                    for (int i = 0; i < files.length; i++) {
                        copyFile(oldpath + File.separator + files[i].getName(), newpath + File.separator + file.getName());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
    * 删除文件夹或文件
    *
    * @param path
    *            要删除的文件或文件夹的路径
    */
    public static void delete(String path) {
        File file = new File(path);
        if (!file.exists())
            return;
        if (file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            System.out.println(111);
            for (int i = 0; i < files.length; i++) {
                delete(files[i].getAbsolutePath());
            }
            file.delete();
        }
    }

    /**
    * 得到文件夹的大小 单位是以K,M,G表示
    *
    * @param path
    *            文件的路径
    * @return 文件的大小
    */
    public static String getFileSize(String path) {
        return formatFileSize(getFileLength(path));
    }

    /**
    * 得到文件夹的大小 单位是以K,M,G表示
    *
    * @param path
    *            文件的路径
    * @return 文件的大小
    */
    public static String getFileSize(File file) {
        return formatFileSize(getFileLength(file));
    }

    public static long getFileLength(File file) {
        long fileSize = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    fileSize += getFileLength(files[i].getAbsolutePath());
                }
            }
        } else {
            fileSize += file.length();
        }
        return fileSize;
    }

    /**
    * 得到文件夹或文件的大小 单位B 字节
    *
    * @param path
    *            文件的路径
    * @return 文件夹或文件的大小
    */
    private static long getFileLength(String path) {
        File file = new File(path);
        return getFileLength(file);
    }

    /**
    * 将单位为字节的整数转化成单位为B,K,M,G的形式
    *
    * @param fileS
    *            整数
    * @return 单位为B,K,M,G的形式的字符串
    */
    private static String formatFileSize(long fileS) {// 转换文件大小
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = "1k";
        } else if (fileS < 1048576) {
            fileSizeString = fileS / 1024 + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = fileS / 1048576 + "M";
        } else {
            fileSizeString = fileS / 1073741824 + "G";
        }
        return fileSizeString;
    }

    /**
    * 得到目录里面的所有文件个数
    *
    * @param path
    *            文件夹的路径
    * @return 文件夹里面文件的个数
    */
    public static long getFileNum(String path) {
        File file = new File(path);
        if (!file.exists())
            return 0;
        long fileNum = 0;
        File[] files = file.listFiles();
        fileNum = files.length;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                fileNum += getFileNum(files[i].getAbsolutePath());
                fileNum--;
            }
        }
        return fileNum;
    }

    /**
    * 将输入流中的数据写入到输出流中
    *
    * @param inStream
    *            输入流
    * @param outStream
    *            输出流
    * @param closeStream
    *            是否要关闭流
    */
    private static void writeStream(InputStream inStream, OutputStream outStream, boolean closeStream) {
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outStream.flush();
                if (closeStream) {
                    outStream.close();
                    inStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
