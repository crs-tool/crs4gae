package com.crs4gae;

import java.util.concurrent.ThreadFactory;

import com.google.appengine.api.ThreadManager;

public class CRS4GAEThread extends CRSThread {

	/**
	 * Auxiliary Variable for Thread execution
	 */
	public Thread thread;
	
	/**
	 * Auxiliary Variable for ThreadFactory
	 */
	public ThreadFactory factory = null;
	
	
	/**
	 * Prepare a thread instance in the runtime environment of the Google App
	 * Engine.
	 * 
	 * @parameter A instance of a Runnable class.
	 */
	public CRS4GAEThread(Runnable run) {
		this.thread = this.getThreadFactoryCloud().newThread(run);
	}
	
	/**
	 * Retrieve the ThreadFactory instance used in this class.
	 * 
	 * @return A instance of the ThreadFactory class.
	 */
	public ThreadFactory getFactory() {
		return factory;
	}

	/**
	 * Update the ThreadFactory instance used in this class.
	 * 
	 * @return A instance of the ThreadFactory class.
	 */
	public void setFactory(ThreadFactory factory) {
		this.factory = factory;
	}

	/**
	 * Retrieve a ThreadFactory instance from the runtime environment of the
	 * Google App Engine.
	 * 
	 * @return A instance of the ThreadFactory class.
	 */
	public ThreadFactory getThreadFactoryCloud() {
		setFactory(ThreadManager.currentRequestThreadFactory());
		return getFactory();
	}
	
	/**
	 * Execute a runnable instance in the runtime environment of the Google App
	 * Engine.
	 * 
	 * @return The instance of the Thread class executed in the runtime
	 *         environment of the Google App Engine.
	 */
	
	
	@Override
	public void start() {
		thread.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		thread.start();
	}

	@Override
	public void sleep(long millis) throws InterruptedException {
		// TODO Auto-generated method stub
		thread.sleep(millis);

	}

	@Override
	public void sleep(long millis, int nanos) throws InterruptedException {
		// TODO Auto-generated method stub
		thread.sleep(millis, nanos);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return thread.toString();
	}

	@Override
	public void yield() {
		thread.yield();
	}

	@Override
	public void join(long millis) {
		try {
			thread.join(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
