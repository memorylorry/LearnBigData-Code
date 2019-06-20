package com.dectfix.memorylorry.HadoopDemo.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @class Mapper  映射key/value对成1个中间的key/value对；
 * Map是将输入的记录转换成一对对中间的key/value的独立的任务;
 * 转换后的中间记录并不需要和输入记录的类型相同;
 * 一个给定的输入键值对可能映射成0个或多个输出键值对；
 *
 * Hadoop的Mapper-Reduce框架会通过InputFormat类为每个切片(InputSplit)生成1个map任务，
 * Mapper的实现可以通过JobContext.getConfiguration()访问job的配置清单(Configuration);
 * 框架首先会调用setup(Mapper.Context)，接着用map(Object, Object, Mapper.Context)
 * 把每个切片中的键值对映射成中间键值对，最后调用cleanup(Mapper.Context)。
 *
 * 所有以给定的输出键(given output key)为分组的中间值，其内部是有序的；接着这些中间键值对，会传递到1个Reducer去决定最后的输出；
 * 用户可以通过制定2个关键的RawComparator去控制排序和分组；
 * Mapper的输出为每个Reducer做了分区，用户可以通过实现1个自顶一个Partitioner类去控制每个键(包括记录)分到哪个Reducer;
 * 用户也可以选择性地通过Job.setCombinerClass(Class)指定combiner，这个可以用于对中间的输出执行本地化的聚合操作，这样可以有效降低从Mapper传输到Reducer的记录数。
 *
 * 应用可以指定配置清单(Configuration)是否或则如何去压缩中间值，以及指定用什么CompressionCodecs压缩编码器。
 * 如果job有0个reduce，那么Mapper的输出会直接写入到输出(OutputFormat),此过程中没有排序操作.
 */
public class MyMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    /**
     * Mapper.<map>函数
     * 每读切片(input split)的一行key/value，就调用一次map函数。大多数应用应该重写这个函数，但默认是特征函数。
     */
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        StringTokenizer itr = new StringTokenizer(value.toString());
        while (itr.hasMoreTokens()) {
            word.set(itr.nextToken());
            //输出1个key/value键值对
            context.write(word, one);
        }
    }
}
