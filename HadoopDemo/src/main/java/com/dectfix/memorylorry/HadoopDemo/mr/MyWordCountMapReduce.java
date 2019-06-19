package com.dectfix.memorylorry.HadoopDemo.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class MyWordCountMapReduce {

    private static String INPUT_PATH = "/wc";
    private static String OUTPUT_PATH = "/MyWordCount";

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration(true);
        /**
         *Job这个类用于配置job、提交job、控制执行、查询job状态。Job中的方法只在job提交前有效，在提交后job对象的方法时，会直接抛出IllegalStateException异常。
         * 通常用户都是通过Job类来创建、描述job的各种配置的。配置好后，将job提交然后监听job的执行。
         */
        Job job = Job.getInstance(conf);

        /**
         * 设置jar执行的主类
         */
        job.setJarByClass(MyWordCountMapReduce.class);

        job.setJobName("MyWordCountMapReduce");

        /**
         * @class InputFormat用于描述 Map-Reduce job的输入配置，Map-Reduce框架依赖于job的输入方式去：
         * 1. 验证job的输入配置
         * 2. 切分输入文件并存成逻辑的分片(logical InputSplits),每个切片将赋予给一个单独的 Mapper
         * 3. 提供RecordReader用于收集被Mapper处理后的输入记录。
         */
        FileInputFormat.addInputPath(job,new Path(INPUT_PATH));

        /**
         * @class OutputFormat用于描述 Map-Reduce job的输出配置，Map-Reduce框架依赖于job的输出方式去：
         * 1. 验证job的输入配置（例如，检查输出目录是否已存在）
         * 2. 提供RecordWriter用于写出job的输出文件，输出文件存储在文件系统上。
         */
        Path output = new Path(OUTPUT_PATH);
        FileOutputFormat.setOutputPath(job,output);

        //清理结果目录
        FileSystem fs = output.getFileSystem(conf);
        if(fs.exists(output)){
            fs.delete(output,true);
        }

        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(MyReducer.class);

        // 提交job，并等到job结束
        job.waitForCompletion(true);
    }

}