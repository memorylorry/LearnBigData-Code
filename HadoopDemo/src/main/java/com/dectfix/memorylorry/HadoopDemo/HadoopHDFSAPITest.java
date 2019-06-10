package com.dectfix.memorylorry.HadoopDemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * 此类为hdfs开发的基本开始
 * 1. 将$HADOOP_HOME/share/hadoop下的所有jar拷贝到项目中，并引入
 * 2. 将core-site/hdfs-site/mapred-site/yarn-site.xml拷贝到resources目录，配置和环境的一直
 * 3. 开始开发此类
 *
 * ！ 注意配置环境变量说明用户名（HADOOP_USER_NAME=hadoop）
 */
public class HadoopHDFSAPITest
{
    Configuration conf;
    FileSystem fs;

    String DIR_NAME = "/hc";
    String FILE_NAME = "/hadoop_info";

    /**
     * 加载配置
     */
    @Before
    public void init() throws IOException {
        conf = new Configuration(true);
        fs = FileSystem.get(conf);
    }

    @Test
    public void mkdir() throws IOException {
        Path dir = new Path(DIR_NAME);
        if(!fs.exists(dir))
            fs.mkdirs(dir );
    }

    @Test
    public void rm() throws IOException {
        if(fs.exists(new Path(DIR_NAME)))
            fs.delete(new Path(DIR_NAME),true);
    }

    @Test
    public void cat() throws IOException {
        String p = "/hadoop_info";
        Path path = new Path(p);
        if(fs.exists(path)){
            FSDataInputStream in = fs.open(path);
            System.out.println(in.readLine());
            in.seek(30);
            System.out.println(in.readLine());
        }else{
            System.out.println("Path["+p+"] is not existed!!!");
        }
    }

    @Test
    public void file() throws IOException {
        FileStatus fileStatus = fs.getFileStatus(new Path(FILE_NAME));

        //获取文件长度
        long size = fileStatus.getLen();

        //获取blockSize
        long blockSize = fileStatus.getBlockSize();

        String owner = fileStatus.getOwner();

        String group = fileStatus.getGroup();

        short replica = fileStatus.getReplication();

        FsPermission fsPermission = fileStatus.getPermission();

        //Path symlink = fileStatus.getSymlink();//非链接文件不可用

        Path name = fileStatus.getPath();

        System.out.println(String.format("Name:%s  permission:%s  blockSize:%d  Size:%d  owner:%s  group:%s  replica:%d",name.toString(),fsPermission.toString(),blockSize,size,owner,group,replica));

        // File Type (d|-|l)
        fileStatus.isDirectory();
        fileStatus.isFile();
        fileStatus.isSymlink();
    }

    @Test
    public void blk() throws IOException {
        FileStatus fileStatus = fs.getFileStatus(new Path(FILE_NAME));

        //获取文件块信息
        BlockLocation[] bls = fs.getFileBlockLocations(fileStatus,0,fileStatus.getLen());

        //获取每个block的起始偏移量
        String msg = "HOST:%s OFFSET：%s LEN:%s NAME：%s";
        for(BlockLocation bl:bls){
            System.out.println(String.format(msg,
                    Arrays.asList(bl.getHosts()).toString(),
                    bl.getOffset(),
                    bl.getLength(),
                    Arrays.asList(bl.getNames()).toString()));
        }
    }

    /**
     * 收拾现场
     */
    @After
    public void finish() throws IOException {
        fs.close();
    }

}
