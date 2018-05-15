package com.zw.base.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * @author nishai
 *         获取图片的名称
 */
public class ImgUtil {
    public static String getName(String src) {
        String[] arr = src.split("/");
        return Array.get(arr, arr.length - 1).toString();
    }

    public static String[] getUrl(String urls) {
        return urls.split(";");
    }

    public static String[] mag_customer_image(String urls) {
        return urls.split(";");
    }

    public static boolean isImage(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                return false;
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
