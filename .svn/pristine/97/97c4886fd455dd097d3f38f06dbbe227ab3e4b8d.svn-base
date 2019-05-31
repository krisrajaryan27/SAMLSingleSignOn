package com.talentPool.repository;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class DocumentQueue {
	private volatile BlockingQueue<TPDocument> queue = new ArrayBlockingQueue<TPDocument>(10000);
	public volatile boolean continueProducing = Boolean.TRUE;
	
	public void put(TPDocument doc) throws InterruptedException{
		queue.put(doc);
	}
	
	public TPDocument get() throws InterruptedException{
		return this.queue.poll(1,TimeUnit.SECONDS);
	}
}
