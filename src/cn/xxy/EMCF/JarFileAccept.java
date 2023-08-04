package cn.xxy.EMCF;

import java.io.File;
import java.io.FilenameFilter;

public class JarFileAccept implements FilenameFilter {
    public static final String suffix = "jar";
    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(suffix);
    }
}
