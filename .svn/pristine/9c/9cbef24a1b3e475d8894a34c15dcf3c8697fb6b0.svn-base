package com.zw.rule.jeval.tools;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageCut {
    public ImageCut() {
    }

    public static void imgCut(String srcImageFile, int x, int y, int desWidth, int desHeight) {
        try {
            BufferedImage bi = ImageIO.read(new File(srcImageFile + "_src.jpg"));
            int srcWidth = bi.getWidth();
            int srcHeight = bi.getHeight();
            if(srcWidth >= desWidth && srcHeight >= desHeight) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, 1);
                CropImageFilter cropFilter = new CropImageFilter(x, y, desWidth, desHeight);
                Image e = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(desWidth, desHeight, 1);
                Graphics g = tag.getGraphics();
                g.drawImage(e, 0, 0, (ImageObserver)null);
                g.dispose();
                ImageIO.write(tag, "JPEG", new File(srcImageFile + "_cut.jpg"));
            }
        } catch (Exception var13) {
            var13.printStackTrace();
        }

    }
}
