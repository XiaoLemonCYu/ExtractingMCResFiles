package cn.xxy.EMCF;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    public static final int width = 500;
    public static final int height = 487;
    public static final String icon = "/img/MainIcon.png";
    private JButton open;
    private JButton save;
    private JButton start;
    private String openMods;
    private String savePath;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    public MainWindow() throws HeadlessException {
        super("ExtractingMCFiles");
        save = new JButton("保存到指定文件夹");
        open = new JButton("打开mods文件夹");
        start = new JButton("开始导出");
        label1 = new JLabel("无可用的文件夹");
        label2 = new JLabel("没有指定路径");
        label3 = new JLabel("不可导出");
        save.setBounds(180,0,180,20);
        open.setBounds(0,0,150,20);
        start.setBounds( 0,40,150,20);
        label1.setBounds(200,20,180,20);
        label2.setBounds(20,20,150,20);
        label3.setBounds(20,60,180,20);
        label1.setForeground(new Color(255, 0, 0));
        label2.setForeground(new Color(255, 0, 0));
        label3.setForeground(new Color(255, 0, 0));
        this.setResizable(false);
        ImageIcon imageIcon  = new ImageIcon(this.getClass().getResource(icon));
        Image image = imageIcon.getImage();
        ((JPanel)this.getContentPane()).setOpaque(false);
        this.setIconImage(image);
        this.add(open);
        this.add(save);
        this.add(start);
        this.add(label1);
        this.add(label2);
        this.add(label3);
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = jfc.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();
                    if(file.exists()) {
                        openMods = file.getPath();
                        getLabel2().setForeground(new Color(0, 255, 4));
                        getLabel2().setText("OK");
                    }
                }
                if (savePath != null) {
                    getLabel1().setForeground(new Color(0, 255, 4));
                    getLabel1().setText("OK");
                }if (openMods != null && savePath != null) {
                    getLabel3().setForeground(new Color(0, 255, 4));
                    getLabel3().setText("OK");
                }
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = jfc.showSaveDialog(null);
                if(result == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();
                    if(file.exists()) {
                        savePath = file.getPath();
                        getLabel1().setForeground(new Color(0, 255, 4));
                        getLabel1().setText("OK");
                    }

                }
                if (openMods != null) {
                    getLabel2().setForeground(new Color(0, 255, 4));
                    getLabel2().setText("OK");
                }if (openMods != null && savePath != null) {
                    getLabel3().setForeground(new Color(0, 255, 4));
                    getLabel3().setText("OK");
                }
            }
        });
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (openMods != null &&savePath != null) {
                    getLabel3().setText("ok");
                    getLabel3().setForeground(new Color(1,225,4));
                    ExtractingFile ef = new ExtractingFile(openMods,savePath);
                    ArrayList<File> modFiles = ef.readMainFolder();
                    CopyFileThread cft = new CopyFileThread(openMods,savePath,modFiles);
                    cft.run();
                    JOptionPane.showMessageDialog(null,"导出完毕","完成",JOptionPane.WARNING_MESSAGE);
                    setOpenMods(null);
                    setSavePath(null);
                    getLabel2().setForeground(new Color(255, 0, 0));
                    getLabel3().setForeground(new Color(255, 0, 0));
                    getLabel1().setForeground(new Color(255, 0, 0));
                    getLabel1().setText("无可用的文件夹");
                    getLabel2().setText("没有指定路径");
                    getLabel3().setText("不可导出");
                }
            }
        });
        this.setLayout(null);


    }

    public JLabel getLabel1() {
        return label1;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public JLabel getLabel3() {
        return label3;
    }

    public void setOpenMods(String openMods) {
        this.openMods = openMods;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
