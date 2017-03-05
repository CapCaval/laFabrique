package org.capcaval.lafabrique.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.capcaval.lafabrique.thread._test.ThreadStateEvent;

public class CcThread implements ThreadStateEvent{
	static AtomicInteger threadCounter = new AtomicInteger(0);
	
	AtomicReference<Runnable> runnableRef = new AtomicReference<Runnable>();
	AtomicBoolean isOver = new AtomicBoolean(false);
	Thread thread;
	String name;
	List<ThreadStateEvent> observerList = new ArrayList<>();
	
	public CcThread(Runnable runnable) {
		this.init("Thread#" + threadCounter.getAndIncrement(), runnable);
	}

	public CcThread(String name, Runnable runnable) {
		this.init(name, runnable);
	}

	protected void init(String name, Runnable runnable) {
		this.runnableRef.set(runnable);
		this.name = name;

		this.thread = new Thread(this.newMainRunnable(), this.name);
	}

	Runnable newMainRunnable() {
		Runnable mainRunnable = new Runnable() {
			@Override
			public void run() {
				notifyThreadStarted();
				try {
					while(isOver.get() == false){
						runnableRef.get().run();
						// give the hand if needed
						Thread.yield();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				notifyThreadStopped();
			}
		};

		return mainRunnable;
	}

	public void start() {
		this.isOver.set(false);
		this.thread.start();
	}
	
	public void stop() {
		this.isOver.set(true);
	}

	public void subscribeThreadStateEvent(ThreadStateEvent observer) {
		this.observerList.add(observer);
	}

	@Override
	public void notifyThreadStarted() {
		for(ThreadStateEvent observer : this.observerList){
			observer.notifyThreadStarted();
		}
	}

	@Override
	public void notifyThreadStopped() {
		for(ThreadStateEvent observer : this.observerList){
			observer.notifyThreadStopped();
		}
	}
}
