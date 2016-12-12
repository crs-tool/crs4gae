package br.uece.crs4gae;

import java.util.concurrent.ThreadFactory;
import com.google.appengine.api.ThreadManager;

/**  
* CRS4GAEWritingFiles.java - a class for refactor legacy code that make use of the threads and than enable this code to run in the Google App Engine.  
* @author Marcos Borges
* @version 1.0 
*/ 

public class CRS4GAEThread {
	private static ThreadFactory factory = null;
	
	/**  
	* Retrieve the ThreadFactory instance used in this class.  
	* @return A instance of the ThreadFactory class.
	*/ 
	public static ThreadFactory getFactory() {
		return factory;
	}
	
	/**  
	* Update the ThreadFactory instance used in this class.  
	* @return A instance of the ThreadFactory class.
	*/ 
	public static void setFactory(ThreadFactory factory) {
		CRS4GAEThread.factory = factory;
	}
	
	/**  
	* Retrieve a ThreadFactory instance from the runtime environment of the Google App Engine.  
	* @return A instance of the ThreadFactory class.
	*/ 
	public static ThreadFactory getThreadFactoryCloud() {
		setFactory(ThreadManager.currentRequestThreadFactory());
		return getFactory();
	}
	
	/**  
	* Execute a runnable instance in the runtime environment of the Google App Engine.  
	* @parameter A instance of aRunnable class.
	* @return The instance of the Thread class executed in the runtime environment of the Google App Engine.
	*/ 
	public static Thread newCloudThread(Runnable run) {
		return getThreadFactoryCloud().newThread(run);
	}
}
