package com.dectfix.memorylorry.ZookeeperDemo;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class Executor {

    public static void main(String[] args) {

        final String PATH = "/dubbo";

        ZkClient zkClient = new ZkClient("localhost:2180", 1000);
        //如果节点存在则删除该节点
        if (zkClient.exists(PATH)) {
            String data = zkClient.readData(PATH);
            System.out.println(data);
            zkClient.delete(PATH);
        }else {
            String nodeName = zkClient.create(PATH, "{\"name\":\"admin\"}", CreateMode.PERSISTENT);
            System.out.println(nodeName);
        }
    }

}
