package com.github.crstool.crs4gae;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.google.appengine.repackaged.com.google.common.io.ByteStreams;

/**
* CRSUtils.java - Javadoc under construction.
* @author Marcos Borges
* @version 1.0
*/
public final class CRSUtils {

	public static String asDirectoryGAE(String path) {
		return (path.substring(path.length() - 1).equals("/")) ? path : path + "/";
	}

	public static boolean isDirectory(String path) {

		String[] arrayPath = new String[path.split("/").length];
		arrayPath = path.split("/");
		return (!arrayPath[arrayPath.length - 1].contains(".")) ? true : false;
	}

	public static String removeLastCaracter(String path) {
		System.err.println(path+ ".."+path.substring(0, path.length()-1));
		return path.substring(0, path.length()-1);
	}

	public static byte[] convertFileToByteArray(InputStream fs) throws FileNotFoundException, IOException {
		byte[] bytes = ByteStreams.toByteArray(fs);
		return bytes;
	}

}
