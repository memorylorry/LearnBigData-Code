package com.dectfix.memorylorry.HadoopDemo.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @class Reducer
 * Reducer将一系列的中间值转化成一个key和一堆更小规模的值。
 * 实现Reducer的子类可以通过job访问配置清单(调用JobContext.getConfiguration()方法)
 *
 * Reducer有3个主要的阶段(phases)
 *
 * 1. 洗牌(Shuffle)
 * Reducer通过HTTP访问网络，去从每个Mapper那边拷贝已经排好序的输出。
 *
 * 2. 排序(Sort)
 * 框架根据key去做归并排序(因为不同的Mapper会输出相同的key).
 * 洗牌阶段(shuffle)和排序阶段(sort)是同时进行的，例如当收到Mapper的输出时，这些输出就会拿去合并。
 *
 * 第二次排序(Secondary Sort)
 * 为了实现在给迭代器返回的数据做一个再排序，应用应该extend the key with the secondary key和定义一个分组比较器。
 * 这些keys将会通过整个key去做排序，但会用分组比较器去决定哪些keys和values会发送给同一个去进行reduce。这个分组比较器可以通过Job去指定(setGroupingComparatorClass(Class))。
 * 排的顺序是由Job.setSortComparatorClass(Class)控制。
 * 例如你想找出重复的页面和标签。你可以这样设置job：
 * Map Input Key: url
 * Map Input Value: document
 * Map Output Key: document checksum, url pagerank
 * Map Output Value: url
 * Partitioner: by checksum
 * OutputKeyComparator: by checksum and then decreasing pagerank
 * OutputValueGroupingComparator: by checksum
 *
 * 3. Reduce
 * 在这个阶段，每个排好序的<key, (collection of values)>输入(sorted inputs)都会调用一次reduce方法。
 * reduce任务的输出通常会调用Reducer.Context.write(Object, Object)方法写入到一个RecordWriter。
 * Reducer的输出没有进行重新排序。
 */
public class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }
        result.set(sum);
        context.write(key, result);
    }
}