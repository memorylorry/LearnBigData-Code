package com.dectfix.memorylorry.hbaseDemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

public class HBaseReadDemo {
    public static void main(String[] args) throws IOException {
//        Configuration config = HBaseConfiguration.create();
//        Job job = new Job(config,"ExampleRead");
//        job.setJarByClass(HBaseReadDemo.class);     // class that contains mapper
//
//        Scan scan = new Scan();
//        scan.setCaching(500);        // 1 is the default in Scan, which will be bad for MapReduce jobs
//        scan.setCacheBlocks(false);  // don't set to true for MR jobs
//
//        TableMapReduceUtil.initTableMapperJob(
//                tableName,        // input HBase table name
//                scan,             // Scan instance to control CF and attribute selection
//                MyMapper.class,   // mapper
//                null,             // mapper output key
//                null,             // mapper output value
//                job);
//        job.setOutputFormatClass(NullOutputFormat.class);   // b
    }
}