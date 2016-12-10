package br.uece.crs4gae;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadFactory;
import com.google.appengine.api.ThreadManager;

public class CRS4GAEThread {
	private static ThreadFactory factory = null;

	public static ThreadFactory getFactory() {
		return factory;
	}

	public static void setFactory(ThreadFactory factory) {
		CRS4GAEThread.factory = factory;
	}

	public static ThreadFactory getThreadFactoryCloud() {
		setFactory(ThreadManager.currentRequestThreadFactory());
		return getFactory();
	}

	public static Thread newCloudThread(Runnable run) {
		return getThreadFactoryCloud().newThread(run);
	}
}
