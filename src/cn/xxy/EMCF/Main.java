package cn.xxy.EMCF;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
        Toolkit to = Toolkit.getDefaultToolkit();
        Dimension ds = to.getScreenSize();
        mw.setBounds((int)((ds.getWidth()-MainWindow.width)/2),(int)((ds.getHeight()-MainWindow.width)/2),500,487);
        mw.setVisible(true);
        mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
