package com.comp_3004.quest_cards.core;

public class ThreadLock{
	protected Object lock;
	
	public ThreadLock(Object lock) {
		this.lock = lock;
	}
	
	protected void wake() {
		synchronized(lock) {
			lock.notify();
		}
	}
	
	protected void sleepGame() {
		synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
}