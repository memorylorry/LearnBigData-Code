package com.dectfix.memorylorry.basic.reflection;

public class ChildWorker extends WorkerWrapper {
    @Override
    public void doIt(String job) {
        System.out.println("I am ChildWorker!"+job);
    }
}
