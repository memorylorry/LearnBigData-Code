package com.dectfix.memorylorry.basic.reflection;

public class Tester {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        /**
         * 反射出来的对象，该调用谁的方法：
         * 1. 若父子类均有同一个方法，则根据对象目前所属类型进行调用
         * 2. 若父子类只有其中某个类有方法实现、重写，则调用当前类型下方法
         */
        Class workerClass = Class.forName("com.dectfix.memorylorry.basic.reflection.ChildWorker");
        Worker worker = (Worker) workerClass.newInstance();
        worker.doIt("whoami");
    }
}
