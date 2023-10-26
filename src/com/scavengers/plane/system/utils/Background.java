package com.scavengers.plane.system.utils;

import javax.swing.*;
import java.awt.*;

/**
 * @Project: ticketSystem
 * @Date: 2023/10/21 18:49
 * @author: Scavengers
 * @Description: 设置背景
 */
public class Background {
    //设置背景
    public static void setBackgroundPicture(JFrame jFrame, ImageIcon picture) {
        JLabel lbl_image = new JLabel(picture);
        jFrame.getLayeredPane().add(lbl_image, JLayeredPane.FRAME_CONTENT_LAYER);
        lbl_image.setSize(picture.getIconWidth(), picture.getIconHeight());
        Container cp = jFrame.getContentPane();
        cp.setLayout(new BorderLayout());
        //设成透明
        ((JPanel) cp).setOpaque(false);
        jFrame.setSize(picture.getIconWidth(), picture.getIconHeight());
    }
}
