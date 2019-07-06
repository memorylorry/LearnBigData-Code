package com.dectfix.memorylorry.basic.serializable;

import java.io.*;

public class TestIt {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String filename = "test.ser";
        File file = new File(filename);

        try {
            // 写出文件 并取回文件
            write(file);
            User user = read(file);
            System.out.println(user.getName());
        }finally {
            deleteFile(file);
        }

    }

    /**
     * 序列化对象
     */
    public static void write(File file) throws IOException {
        ObjectOutputStream oos = null;
        try {
            if(!file.exists())
                file.createNewFile();
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            User user = new User("hello world",21);
            oos.writeObject(user);
        }finally {
            if(oos!=null) {
                oos.close();
            }
        }
    }

    /**
     * 通过反序列化获取对象
     */
    public static <T> T read(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        try {
            if(file.exists()){
                ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
                T obj = (T) ois.readObject();
                return obj;
            }
        }finally {
            if(ois!=null)
                ois.close();
        }
        throw new FileNotFoundException();
    }

    private static void deleteFile(File file){
        if(file.exists())
            file.delete();
    }
}
