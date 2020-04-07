package com.zjstudio.happly_birthday.tools.common;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author 添柴灬少年
 * @date 2018/12/15
 */
public class ImgUtil {
    /**
     * 基础宽度
     */
    private static final int ICONWIDTH = 480;
    /**
     * 基础高度
     */
    private static final int ICONHEIGHT = 320;
    /**
     * 图片格式
     */
    private static final String[] SUFFIX = {"JPG","JPEG","BMP","PNG"};

    /**
     * 获取压缩图片
     * @param inFile 输入文件
     * @param outPath 输出文件
     * @param scale 大小
     * @param outputQuality 质量
     * @return 压缩后文件
     * @throws Exception
     */
    private static File scaleImg(InputStream inFile, String outPath, double scale, double outputQuality) throws Exception{
        // 判断路径是否存在
        File outFile = new File(outPath);
        if(!outFile.getParentFile().exists()){
            outFile.getParentFile().mkdirs();
        }

        // 压缩图片
        Thumbnails.of(inFile).scale(scale).outputQuality(outputQuality).toFile(outFile);
        return outFile;
    }


    /**
     * 获取压缩图片
     * @param inFile 输入文件
     * @param outPath 输出文件
     * @param scale 大小
     * @param outputQuality 质量
     * @return 压缩后文件
     * @throws Exception
     */
    private static File scaleImg(File inFile, String outPath, double scale, double outputQuality) throws Exception{
        // 判断路径是否存在
        File outFile = new File(outPath);
        if(!outFile.getParentFile().exists()){
            outFile.getParentFile().mkdirs();
        }
        // 压缩图片
        Thumbnails.of(inFile).scale(scale).outputQuality(outputQuality).toFile(outFile);
        return outFile;
    }

    /**
     * 获取icon缩放大小
     * @param inFile
     * @param outPath
     * @param outputQuality
     * @return
     * @throws Exception
     */
    private static File getIcon(File inFile, String outPath, double outputQuality) throws Exception{
        // 判断路径是否存在
        File outFile = new File(outPath);
        if(!outFile.getParentFile().exists()){
            outFile.getParentFile().mkdirs();
        }
        Thumbnails.of(inFile).size(ICONWIDTH, ICONHEIGHT).outputQuality(outputQuality).toFile(outFile);
        return outFile;
    }

    /**
     * 判断图片格式
     * @param fileName
     * @return
     */
    public static boolean judgeImg(String fileName){
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < SUFFIX.length ; i++) {
            if(SUFFIX[i].equalsIgnoreCase(suffix)){
                return true;
            }
            continue;
        }
        return false;
    }

    /**
     * 获取压缩图片，大小1，质量0.5
     * @param inFile
     * @param outPath
     * @throws Exception
     */
    public static File handlerImg(InputStream inFile, String outPath) throws Exception{
        // 压缩图片
        return scaleImg(inFile,outPath,1,0.5);
    }

    /**
     * 获取压缩图片，
     * @param inFile
     * @param outPath
     * @throws Exception
     */
    public static File handlerIcon(File inFile, String outPath) throws Exception{

        // 压缩图片
        return getIcon(inFile,outPath,0.5);
    }

    /**
     * 获取压缩图片，大小0.5，质量0.5
     * @param inFile
     * @param outPath
     * @param iconPath
     * @throws Exception
     */
    public static void handlerImgAndIcon(InputStream inFile, String outPath, String iconPath) throws Exception{
        // 压缩图片
        File outFile = scaleImg(inFile,outPath,1,0.5);
        // 再压成icon
        File iconFile = getIcon(outFile,iconPath,1);
    }
}
