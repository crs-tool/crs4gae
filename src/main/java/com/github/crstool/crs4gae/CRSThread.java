package com.github.crstool.crs4gae;

/**
* CRS4Thread.java - Javadoc under construction.
* @author Marcos Borges
* @version 1.0
*/
public abstract class CRSThread {
	public abstract void start();
	public abstract void stop();
	public abstract void sleep(long millis) throws InterruptedException;
	public abstract void sleep(long millis, int nanos) throws InterruptedException;
	public abstract String toString();
	public abstract void yield();
	public abstract void join(long millis);
}
