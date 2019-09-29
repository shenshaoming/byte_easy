package com.mbyte.easy.util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @Description : 读取文件的后缀名
 *
 * @author : 申劭明
 * @date : 2019/9/2 19:42
*/
public class FileType
{

    private FileType(){}

    /**
     * 该filePath指向的文件不存在时返回值为null
     * @param filePath 待获取尾缀的文件路径
     * @return
     */
    private static String getFileSuffix(String filePath) {
        return getFileSuffix(new File(filePath));
    }

    /**
     * @Description : 获取filePath对应文件的尾缀
     *
     * @param file File对象
     * @return : 文件的尾缀,如jpg等.
     * return值为空指针的情况:
     * 1.该图片文件的宽或高为0;
     * 2.该文件的后缀名不包含在FILE_TYPE_MAP中
     * 3.程序发生异常
     * @author : 申劭明
     * @date : 2019/9/2 20:15
     * @thorws : IOException
     */
    public static String getFileSuffix(File file){
        if (file.exists()){
            String result = null;
            try {
                result = getImageFileType(file);
                return result == null? getFileByFile(file) : result;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * @Description : 获取图片的尾缀
     *
     * @param f 待获取尾缀的File对象
     * @return : 如果该文件是真实的图片,返回该图片的尾缀;否则返回空指针
     * @author : 申劭明
     * @date : 2019/9/2 19:38
    */
    private static String getImageFileType(File f) throws IOException {
        if (isImage(f))
        {
                ImageInputStream iis = ImageIO.createImageInputStream(f);
                Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
                if (!iter.hasNext())
                {
                    return null;
                }
                ImageReader reader = iter.next();
                iis.close();
                return reader.getFormatName();
        }
        return null;
    }

    /**
     * @Description : 通过字节码判断文件类型.
     *
     * @param file 待判断的File对象
     * @return : 如果该文件字节码存在于map集合中,返回该文件的尾缀名;否则返回空指针
     * @author : 申劭明
     * @date : 2019/9/2 19:35
    */
    private static String getFileByFile(File file) throws IOException {
        String fileType = null;
        //读取字节文件中的前50个字节
        byte[] b = new byte[50];

        InputStream is = new FileInputStream(file);
        is.read(b);
        fileType = getFileTypeByStream(b);
        is.close();

        return fileType;
    }

    /**
     * @Description : 通过文件的字节码判断文件类型
     *
     * @param b 文件的字节码数组
     * @return : 如果该文件字节码能够在FILE_TYPE_MAP中找到,返回该key值(文件类型名);否则返回空指针
     * @author : 申劭明
     * @date : 2019/9/2 19:26
    */
    private static String getFileTypeByStream(byte[] b)
    {
        String filetypeHex = String.valueOf(getFileHexString(b));
        Map<String, String> fileTypeMap = InitMap.getMap();
        for (Entry<String, String> entry : fileTypeMap.entrySet()) {
            String fileTypeHexValue = entry.getValue();
            //如果是以该字节码为开头,则属于该类型文件
            if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * @Description : 通过读取图片的宽和高来判断是否为一个图片
     *
     * @param file 待检测的文件
     * @return : true 是 | false 否,发生异常也会导致返回值为null
     * @author : 申劭明
     * @date : 2019/9/2 19:25
    */
    public static boolean isImage(File file){
        boolean flag = false;
        try
        {
            BufferedImage bufferedImage = ImageIO.read(file);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if(width==0 || height==0){
                flag = false;
            }else {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * @Description : 获取该文件的魔术数字
     *
     * @param b 该文件的字节码数组
     * @return : java.lang.String
     * @author : 申劭明
     * @date : 2019/9/2 19:25
    */
    private static String getFileHexString(byte[] b)
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (b == null || b.length <= 0)
        {
            return null;
        }
        for (byte value : b) {
            int v = value & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * @author 申劭明
     * 通过静态内部类懒汉模式,初始化FILE_TYPE_MAP
     */
    private static class InitMap{
        /**
         * key是文件尾缀,value是文件的魔术数字
         */
        private final static Map<String, String> FILE_TYPE_MAP = new HashMap<>();

        static {
            //图片文件
            FILE_TYPE_MAP.put("jpg", "FFD8FF");
            FILE_TYPE_MAP.put("png", "89504E47");
            FILE_TYPE_MAP.put("gif", "47494638");
            FILE_TYPE_MAP.put("tif", "49492A00");
            FILE_TYPE_MAP.put("bmp", "424D");
            FILE_TYPE_MAP.put("dwg", "41433130");

            FILE_TYPE_MAP.put("html", "68746D6C3E");
            FILE_TYPE_MAP.put("rtf", "7B5C727466");
            FILE_TYPE_MAP.put("xml", "3C3F786D6C");
            FILE_TYPE_MAP.put("zip", "504B0304");
            FILE_TYPE_MAP.put("rar", "52617221");
            //Photoshop (psd)
            FILE_TYPE_MAP.put("psd", "38425053");
            //Email [thorough only] (eml)
            FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A");
            //Outlook Express (dbx)
            FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F");
            //Outlook (pst)
            FILE_TYPE_MAP.put("pst", "2142444E");
            //MS Word
            FILE_TYPE_MAP.put("xls", "D0CF11E0");
            //MS Excel 注意：word 和 excel的文件头一样
            FILE_TYPE_MAP.put("doc", "D0CF11E0");
            //MS Access (mdb)
            FILE_TYPE_MAP.put("mdb", "5374616E64617264204A");
            //WordPerfect (wpd)
            FILE_TYPE_MAP.put("wpd", "FF575043");
            FILE_TYPE_MAP.put("eps", "252150532D41646F6265");
            FILE_TYPE_MAP.put("ps", "252150532D41646F6265");
            //Adobe Acrobat (pdf)
            FILE_TYPE_MAP.put("pdf", "255044462D312E");
            //Quicken (qdf)
            FILE_TYPE_MAP.put("qdf", "AC9EBD8F");
            //Windows Password (pwl)
            FILE_TYPE_MAP.put("pwl", "E3828596");
            //音频文件
            FILE_TYPE_MAP.put("mp3", "4944330300000003");
            FILE_TYPE_MAP.put("wav", "57415645");
            //视频文件
            FILE_TYPE_MAP.put("avi", "41564920");
            FILE_TYPE_MAP.put("mp4", "0000002066747970");
            FILE_TYPE_MAP.put("mkv", "1A45DFA3A3428681");
            //Real Audio (ram)
            FILE_TYPE_MAP.put("ram", "2E7261FD");
            //Real Media (rm)
            FILE_TYPE_MAP.put("rm", "2E524D46");
            FILE_TYPE_MAP.put("mpg", "000001BA");
            FILE_TYPE_MAP.put("mov", "6D6F6F76");
            //Windows Media (asf)
            FILE_TYPE_MAP.put("asf", "3026B2758E66CF11");
            //MIDI (mid)
            FILE_TYPE_MAP.put("mid", "4D546864");
        }

        public static Map<String,String> getMap(){
            return FILE_TYPE_MAP;
        }
    }
}
