package com.github.crstool.crs4gae;

/**
* CRSFile.java - Javadoc under construction.
* @author Marcos Borges
* @version 1.0
*/
public abstract class CRSFile {
	public abstract String getPath();
	public abstract boolean exists();
	public abstract String getAbsolutePath();
	public abstract boolean mkdirs();
	public abstract boolean mkdir();
	public abstract CRSFile[] listFiles();
	public abstract CRSFile[] listFiles(CRSFilenameFilter filter);
	public abstract boolean isDirectory();
	public abstract boolean isFile();
	public abstract String getName();
	public abstract boolean delete();
	public abstract long length();
	public abstract boolean renameTo(CRSFile file);
	public abstract CRSFile getParentFile();
	public abstract boolean createNewFile();
	public abstract long lastModified();
	public abstract String getParent();
	public abstract CRSFile getCanonicalFile();
	public abstract boolean canRead();
}
