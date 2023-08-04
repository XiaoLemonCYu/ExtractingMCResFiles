package cn.xxy.EMCF;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ExtractingFile {
    private String MainFolder;//此为mods文件的路径
    private String saveFolder;
    public ExtractingFile(String mainFolder,String saveFolder) {
        MainFolder = mainFolder;
        this.saveFolder = saveFolder;
    }
    //先获取mods文件夹下的mod文件的绝对路径
    public ArrayList<File> readMainFolder() {
        File mainFolder = new File(MainFolder);
        if(mainFolder.isDirectory()) {
            ArrayList<File> f = new ArrayList<>();
            String[] temp = mainFolder.list(new JarFileAccept());
            if (temp != null) {
                for(int i = 0;i<temp.length;i++) {
                    temp[i] = mainFolder.getPath() + "\\" + temp[i];
                    f.add(i,new File(temp[i]));
                }
            }else return null;
            return f;
        }else{
            return null;
        }
    }
    public static void copyFileInJarToFolder(String jarPath,String saveFolder) {
        File savePath = new File(saveFolder);
        if(!savePath.exists()) {
            savePath.mkdir();
        }
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jarPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration<JarEntry> text = jarFile.entries();
        //存储需要复制的文件，不是目录
        ArrayList<JarEntry> jarEntries = new ArrayList<>();
        //需要复制的文件
        ArrayList<String> createFolder = new ArrayList<>();
        //需要创建的目录
        ArrayList<String> copyFile = new ArrayList<>();
        JarEntry tmp;
        while(text.hasMoreElements()) {
            tmp = text.nextElement();
            if (tmp.getName().length()>=6&&tmp.getName().startsWith("assets")) {
                if (tmp.getName().endsWith("/")&&!(tmp.getName().equals("assets/"))&&tmp.isDirectory()) {
                    createFolder.add(tmp.getName().substring(6).replace("/",getPathSeparator()));
                }
                if (!tmp.getName().equals("assets/")&&!tmp.isDirectory()) {
                    copyFile.add(tmp.getName().substring(6).replace("/",getPathSeparator()));
                    jarEntries.add(tmp);
                }
            }
        }
        //创建目录
        for (int i1 = 0;i1<createFolder.size();i1++) {
            File folder = new File(saveFolder+createFolder.get(i1));
            if (!folder.exists()) {
                folder.mkdir();
                folder.setWritable(true);
            }
        }
        //创建文件
        ArrayList<File> files = new ArrayList<>();
        if (!files.isEmpty()) {
            for (int i2 = 0;i2<copyFile.size();i2++) {
                if (i2!=0) {
                    File file = new File(saveFolder+copyFile.get(i2));
                    File parent = new File(files.get(i2).getParent());
                    if (!parent.exists()) {
                        parent.mkdir();
                    }
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("cant create file"+file.getName());
                        }
                    }
                    files.add(file);
                }
            }
        }

        if (!jarEntries.isEmpty()&&!files.isEmpty()) {
            jarEntries.remove(0);
            //已经遍历了assets的目录了
            for (int i = 0;i<jarEntries.size();i++) {
                InputStream in = null;
                try {
                    in = jarFile.getInputStream(jarEntries.get(i));
                    if (!jarEntries.get(i).isDirectory() && !files.get(i).isDirectory()) {
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(files.get(i).getPath()));
                        while(true) {
                            byte[] bytes = new byte[1024];
                            int len = in.read(bytes);
                            if (len == -1) {
                                break;
                            }
                            bos.write(bytes,0,len);
                            bos.flush();
                        }
                        //已经获取到文件的内容了
                        in.close();
                        bos.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("file:" + files.get(i));
                    System.out.println("copyFile:" +jarEntries.get(i).getName());
                }

            }
        }


    }
    public static String getPathSeparator(){
        return new Properties(System.getProperties()).getProperty("file.separator");
    }
}
