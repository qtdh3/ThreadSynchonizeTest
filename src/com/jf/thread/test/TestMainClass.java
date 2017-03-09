package com.jf.thread.test;

import java.util.concurrent.atomic.AtomicInteger;

public class TestMainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("主程序入口");
		AtomicInteger countNum=new AtomicInteger(1);
		Object synObjA=new Object();
		Object synObjB=new Object();
		Object synObjC=new Object();
		
		PrintThread threadA=new PrintThread(synObjA, synObjB, "线程A ", countNum);
		threadA.start();
//		try {
//			Thread.sleep(30);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		PrintThread threadB=new PrintThread(synObjB, synObjC, "线程B ", countNum);
		threadB.start();
//		try {
//			Thread.sleep(30);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		PrintThread threadC=new PrintThread(synObjC, synObjA, "线程C ", countNum);
		threadC.start();
		try {
			// 让三个程序先跑，先wait();
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (synObjA) {
			// 唤醒A ，让其最新带节奏
			synObjA.notify();
		}
		
		
		System.out.println("主线程结束");

	}

}
