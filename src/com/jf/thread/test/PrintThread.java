package com.jf.thread.test;

import java.util.concurrent.atomic.AtomicInteger;

public class PrintThread extends Thread {
	
	Object syncObjA;
	Object syncObjB;
	String threadName;
	AtomicInteger countNum;
	
	
	
	
	public PrintThread(Object syncObjA, Object syncObjB, String threadName,
			AtomicInteger countNum) {
		super();
		this.syncObjA = syncObjA;
		this.syncObjB = syncObjB;
		this.threadName = threadName;
		this.countNum = countNum;
	}

	public void run() {
		super.run();
		System.out.println(threadName+" 开始");
		try {
			synchronized (syncObjA) {
				// 这步是为了 让大家先都睡去，配合主程序让A先启动
				syncObjA.wait();
			}
			System.out.println(threadName+" 释放锁");
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
			synchronized (syncObjA) {
				System.out.println(threadName+":拿到外锁");
				synchronized (syncObjB) {
					System.out.println(threadName+":拿到内锁");
					// 唤醒同样需要 外面套一个该对象的同步锁
					syncObjB.notify();
					if (countNum.get()>=50) {
						break;
					}
					printMessage();
					
				}
				try {
					System.out.println(threadName+":准备线程等待");
					syncObjA.wait();
					// 这行调用后 就会释放A锁，等待被唤醒
					System.out.println(threadName+":线程被唤醒");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
		
	}
	
	private void printMessage(){
//		System.out.println(threadName+" 值打印："+countNum);
		int i=countNum.getAndIncrement();
		System.out.println(threadName+" 值打印："+i);
		 i=countNum.getAndIncrement();
		System.out.println(threadName+" 值打印："+i);
		 i=countNum.getAndIncrement();
		System.out.println(threadName+" 值打印："+i);
	}

}
