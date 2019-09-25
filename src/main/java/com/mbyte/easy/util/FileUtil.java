package com.mbyte.easy.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @ClassName: FileUtil
 * @Author : 申劭明
 * @Description: 文件上传工具类
 * @Version 2.0
 **/
@Slf4j
public class FileUtil {
    /**
     * 文件上传路径前缀(在application中设置)
     */
    public static String uploadSuffixPath;
    /**
     * 本地磁盘目录(在application中设置)
     */
    public static String uploadLocalPath;

    /**
     * 上传图片支持尾缀
     */
    public static final String[] IMAGE_FILES = {"jpg","jpeg","png","ico","gif"};

    /**
     * 上传音频支持尾缀
     */
    public static final String[] AUDIO_FILES = {"mp3","wav"};

    /**
     * 上传视频支持尾缀
     */
    public static final String[] VIDEO_FILES = {"avi","mkv","mp4"};

    /**
     * @Title: uploadFile
     * @Author : 申劭明
     * @Description: 单文件上传到本地磁盘
     * @param: multipartFile
     * @return: 如果文件上传成功,返回文件的网络访问路径;上传失败返回空指针
     */
    public static String uploadFile(MultipartFile multipartFile){
        if(multipartFile == null){
            return null;
        }
        //获取文件相对路径
        String fileName = getUploadFileName(multipartFile.getOriginalFilename());
        String dateDir = DateUtil.format(null,DateUtil.PATTERN_yyyyMMdd);
        File destFileDir = new File(uploadLocalPath + File.separator + dateDir);
        if(!destFileDir.exists()){
            destFileDir.mkdirs();
        }
        try {
            File destFile = new File(destFileDir.getAbsoluteFile()+File.separator+fileName);
            multipartFile.transferTo(destFile);
            log.info("文件【"+multipartFile.getOriginalFilename()+"】上传成功");
            return uploadSuffixPath + "/" + dateDir+"/"+fileName;
        } catch (IOException e) {
            log.error("文件上传异常："+multipartFile.getOriginalFilename(),e);
            return null;
        }
    }

    /**
     * @Description : 上传图片文件,支持的文件格式包含在 IMAGE_FILES 数组中
     *
     * @param multipartFile
     * @return : 文件在数据库中的存储路径
     * @author : 申劭明
     * @date : 2019/9/2 20:49
     */
    public static String uploadImage(MultipartFile multipartFile){
        return inStringArray(IMAGE_FILES,getFileSuffix(multipartFile))? uploadFile(multipartFile) : null;
    }

    /**
     * @Description : 上传音频文件,支持的文件格式包含在 AUDIO_FILES 数组中
     *
     * @return : 文件在数据库中的存储路径
     * @author : 申劭明
     * @date : 2019/9/2 20:51
     */
    public static String uploadAudio(MultipartFile multipartFile){
        return inStringArray(AUDIO_FILES,getFileSuffix(multipartFile))? uploadFile(multipartFile) : null;
    }

    /**
     * @Description : 上传视频文件,支持的文件格式包含在 VIDEO_FILES 数组中
     *
     * @param multipartFile
     * @return : 文件在数据库中的存储路径
     * @author : 申劭明
     * @date : 2019/9/2 20:57
     */
    public static String uploadVideo(MultipartFile multipartFile){
        return inStringArray(VIDEO_FILES,getFileSuffix(multipartFile))? uploadFile(multipartFile) : null;
    }

    /**
     * @Description : 获取文件的后缀
     *
     * @param multipartFile
     * @return : 文件后缀
     * @author : 申劭明
     * @date : 2019/9/2 20:55
     */
    private static String getFileSuffix(MultipartFile multipartFile){
        if(multipartFile == null){
            return null;
        }
        File file = new File(uploadLocalPath + "\\test.txt");
        try {
            //将mutipartFile以输入流的形式暂时存储在file文件中,用于方法的输入参数
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),file);
            //获得文件的尾缀
            String suffix = FileType.getFileSuffix(file);
            return suffix;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Title: getUploadFilePath
     * @Description: 获取上传后的文件相对路径  --数据库存储该路径
     * @Author: 申劭明
     * @param: fileName
     * @return: 数据中存储的路径名称,eg:D:\home\lxt\Desktop\0729\test\20190821,D为项目所在的文件夹路径
     * @throws:
     */
    public static String getUploadFileName(String fileName){
        return new StringBuilder()
                .append(DateUtil.format(null, DateUtil.PATTERN_yyyyMMddHHmmssSSS))
                .append("_").append(Utility.getRandomStrByNum(6))
                .append(".").append(FilenameUtils.getExtension(fileName))
                .toString();
    }

    /**
     * @Title: isFileBySuffix
     * @Author: 申劭明
     * @Description: 通过后缀名判断是否是某种文件
     * @param: fileName 文件名称
     * @param: suffix 后缀名
     * @return: 如果满足条件返回true,否则返回false
     * @throws:
     */
    public static boolean isFileBySuffix(String fileName,String suffix){

        if(StringUtils.isNoneBlank(fileName) && StringUtils.isNoneBlank(suffix)){
            return fileName.endsWith(suffix.toLowerCase()) || fileName.endsWith(suffix.toUpperCase());
        }
        return false;
    }

    /**
     * @Description : 上传音频文件的同时获取该音频的时间,并返回该时间字段
     *
     * 该方法需要引入 it.sauronsoftware.jave.Encoder;
     * 和 it.sauronsoftware.jave.MultimediaInfo;
     * @param videoMultipart 音频文件
     * @return : videoMultipart 音频文件的时间
     * @author : 申劭明
     * @date : 2019/8/28 16:11
     */
//    public static String getVideoLength(MultipartFile videoMultipart){
//        String filePath = FileUtil.uploadFile(videoMultipart);
//        return getVideoLength(filePath);
//    }

    /**
     * @Description : 通过filePath获取该文件的时间长短
     * 该方法需要引入 it.sauronsoftware.jave.Encoder;
     * 和 it.sauronsoftware.jave.MultimediaInfo;
     *
     * @param filePath 音频文件的路径
     * @return : 该音频文件播放的时间,eg:3分24秒
     * @author : 申劭明
     * @date : 2019/8/28 16:10
     */
//    public static String getVideoLength(String filePath){
//
//        File source = new File(uploadLocalPath+filePath.replace(uploadSuffixPath,""));
//        Encoder encoder = new Encoder();
//        long ls = 0;
//        MultimediaInfo m;
//        String time = "";
//        try {
//            m = encoder.getInfo(source);
//            ls = m.getDuration();
//            long l = ls/1000;
//            time = ls/60000+"分"+(ls%60000)/1000+"秒";
//            System.out.println();
//        } catch (Exception e) {
//            System.out.println("获取音频时长有误：" + e.getMessage());
//        }
//        return time;
//    }

    /**
     * @Description : 判断array数组中是否包含target对象
     *
     * @param array 字符串数组
     * @param target 目标字符串
     * @return : 包含 -> true,否则 -> false
     * @author : 申劭明
     * @date : 2019/9/2 20:38
     */
    private static boolean inStringArray(String[] array,String target){
        return Arrays.asList(array).contains(target);
    }

}
