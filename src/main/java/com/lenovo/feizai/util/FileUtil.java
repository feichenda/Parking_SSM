package com.lenovo.feizai.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author feizai
 * @date 2021/4/1 0001 下午 2:36:24
 * @annotation
 */
public class FileUtil {

    private final static String realPath = "D:" + File.separator + "Parking" + File.separator;
//    private final static String realPath = "H:\\IdeaProject\\Parking\\";
    private final static String orderDicr = "order";
    private final static String avatarDicr = "avatar";
    private final static String imageDicr = "image";
    private final static String imageChangeDicr = "imageChange";

    /**
     * @param file  文件
     * @param order 订单号
     * @return
     */
    public static String getOrderFilePath(MultipartFile file, String order) throws IOException {
        //创建文件路径
        File file1 = new File(realPath + File.separator + orderDicr);
        if (!file1.exists()) {
            //文件路径不存在时，自动创建
            file1.mkdirs();
        }
        String ofname = file.getOriginalFilename();
        String fileName = order + "." + ofname.substring(ofname.lastIndexOf(".") + 1);
        //根据路径和文件名创建文件
        File newFile = new File(file1, fileName);
        file.transferTo(newFile);
        return orderDicr + "/" + fileName;
    }

    /**
     * @param file     文件
     * @param username 用户名
     * @return
     */
    public static String getAvatarFilePath(MultipartFile file, String username) throws IOException {
        //创建文件路径
        File file1 = new File(realPath + File.separator + avatarDicr + File.separator + username);
        if (!file1.exists()) {
            //文件路径不存在时，自动创建
            file1.mkdirs();
        }
        String ofname = file.getOriginalFilename();
        String fileName = "avatar" + "." + ofname.substring(ofname.lastIndexOf(".") + 1);
        //根据路径和文件名创建文件
        File newFile = new File(file1, fileName);
//        if (!newFile.exists()) {
//            newFile.mkdirs();
//        }
//        file.transferTo(newFile);
        byte[] bytes = file.getBytes();
        OutputStream out = new FileOutputStream(newFile);
        out.write(bytes);
        out.close();
        return avatarDicr + "/" + username + "/" + fileName;
    }

    /**
     * @param file 文件
     * @param name 停车场名
     * @param mark 标记，区分是执照还是停车场照片
     * @return
     */
    public static String getParkingFilePath(MultipartFile file, String name, String mark) throws IOException {
        //创建文件路径
        File file1 = new File(realPath + File.separator + imageDicr + File.separator + name);
        if (!file1.exists()) {
            //文件路径不存在时，自动创建
            file1.mkdirs();
        }
        String ofname = file.getOriginalFilename();
        String fileName = mark + "." + ofname.substring(ofname.lastIndexOf(".") + 1);
        //根据路径和文件名创建文件
        File newFile = new File(file1, fileName);
        file.transferTo(newFile);
        return imageDicr + "/" + name + "/" + fileName;
    }

    /**
     * @param file 文件
     * @param name 停车场名
     * @param mark 标记，区分是执照还是停车场照片
     * @return
     */
    public static String getChangeParkingFilePath(MultipartFile file, String name, String mark) throws IOException {
        //创建文件路径
        File file1 = new File(realPath + File.separator + imageChangeDicr + File.separator + name);
        if (!file1.exists()) {
            //文件路径不存在时，自动创建
            file1.mkdirs();
        }
        String ofname = file.getOriginalFilename();
        String fileName = mark + "." + ofname.substring(ofname.lastIndexOf(".") + 1);
        //根据路径和文件名创建文件
        File newFile = new File(file1, fileName);
        file.transferTo(newFile);
        return imageChangeDicr + "/" + name + "/" + fileName;
    }

    public static void copy(String srcPathStr, String desPathStr) throws IOException {
        //获取源文件的名称
        srcPathStr = realPath + srcPathStr;
        desPathStr = realPath + imageDicr + File.separator + desPathStr;
        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("\\") + 1); //目标文件地址
        desPathStr = desPathStr + File.separator + newFileName; //源文件地址
        FileInputStream fis = new FileInputStream(srcPathStr);//创建输入流对象
        FileOutputStream fos = new FileOutputStream(desPathStr); //创建输出流对象
        byte datas[] = new byte[1024 * 8];//创建搬运工具
        int len = 0;//创建长度
        while ((len = fis.read(datas)) != -1) {//循环读取数据
            fos.write(datas, 0, len);
        }
        fis.close();//释放资源
        fos.close();//释放资源
    }

    public static void rename(String src, String des) throws IOException {
        src = realPath + imageDicr + File.separator + src;
        des = realPath + imageDicr + File.separator + des;
        //想命名的原文件夹的路径
        File file = new File(src);
        //将原文件夹更改为A，其中路径是必要的。注意
        file.renameTo(new File(des));
    }

    public static void renameAvatar(String src, String des) throws IOException {
        src = realPath + avatarDicr + File.separator + src;
        des = realPath + avatarDicr + File.separator + des;
        //想命名的原文件夹的路径
        File file = new File(src);
        //将原文件夹更改为A，其中路径是必要的。注意
        if (file.exists())
            file.renameTo(new File(des));
    }

    public static void deleteLicenseFile(String path) throws IOException {
        //获取目录名
        path = realPath + imageDicr + File.separator + path;
        File rootfile = new File(path);
        File[] files = rootfile.listFiles();
        //遍历删除文件
        for (File file : files) {
            if (file.getName().contains("certificate"))
                file.delete();
        }
    }

    public static Boolean deleteImageFile(String pathName) {
        String path = realPath + imageDicr + File.separator + pathName;
        File file = new File(path);
        return deleteDir(file);
    }

    public static Boolean deleteChangeFile(String pathName) {
        String path = realPath + imageChangeDicr + File.separator + pathName;
        File file = new File(path);
        return deleteDir(file);
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}

