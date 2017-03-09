package com.android.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import org.apache.log4j.Logger;

import com.android.reource.ResourceUtil;

public class FileUtils {

    private FileUtils() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 文件复制
     *
     * @param source
     * @param target
     * @throws IOException
     */
    public static void copy(String source, String target) throws IOException {
        RandomAccessFile sourceRF = null;
        RandomAccessFile targetRF = null;
        try {
            sourceRF = new RandomAccessFile(new File(source), "r");
            targetRF = new RandomAccessFile(new File(target), "rws");
            FileChannel sourceFC = sourceRF.getChannel();
            FileChannel targetFC = targetRF.getChannel();
            targetFC.transferFrom(sourceFC, 0, sourceRF.length());
        } catch (Exception ex) {
            if (sourceRF != null) {
                sourceRF.close();
            }
            if (targetRF != null) {
                targetRF.close();
            }
        }

    }

    /**
     * 删除目录
     *
     * @param dir
     */
    public static void removeDir(String dir) {
        File f = new File(dir);
        if (f.isFile()) {
            boolean res = f.delete();
            Logger.getLogger(FileUtils.class.getName()).info(
                    "remove : " + f.getAbsolutePath() + " res:" + res);
        } else {
            for (File sf : f.listFiles()) {
                removeDir(sf.getAbsolutePath());
            }
            f.delete();
        }
    }

    /**
     * 导出一个文件
     *
     * @param projectPath
     * @param fileName
     */
    public static void exportFile(String projectPath, String fileName) {
        System.out.println(fileName + "---");
        InputStream is = ResourceUtil.class.getResourceAsStream("/img/" + fileName);
        OutputStream out;
        try {
            File f = new File(projectPath.concat(File.separator).concat(fileName));
            out = new FileOutputStream(f);
            byte[] bs = new byte[1024];
            int len = 0;
            while ((len = is.read(bs)) != -1) {
                out.write(bs, 0, len);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
