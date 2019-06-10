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
        Job job = Job.getInstance(conf);

        job.setJarByClass(MyWordCountMapReduce.class);

        // Specify various job-specific parameters
        job.setJobName("MyWordCountMapReduce");

        FileInputFormat.addInputPath(job,new Path(INPUT_PATH));

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

        // Submit the job, then poll for progress until the job is complete
        job.waitForCompletion(true);
    }

}