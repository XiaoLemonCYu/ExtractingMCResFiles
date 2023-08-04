package cn.xxy.EMCF;

import java.io.File;
import java.util.ArrayList;

public class CopyFileThread implements Runnable{
    private String openMods;
    private String savePath;
    private ArrayList<File> needCopy;

    public CopyFileThread(String openMods, String savePath,ArrayList<File> files) {
        this.openMods = openMods;
        this.savePath = savePath;
        this.needCopy = files;
    }
    @Override
    public void run() {
        if (openMods != null &&savePath != null) {
            if(!needCopy.isEmpty()) {
                for (int i = 0;i<needCopy.size();i++) {
                    String path = needCopy.get(i).getPath();
                    ExtractingFile.copyFileInJarToFolder(path,savePath);
                }
            }

        }
    }
}
