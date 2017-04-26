package com.github.crstool.crs4gae;

import java.io.OutputStream;

/**
* CRSFileOutputStream.java - Javadoc under construction.
* @author Marcos Borges
* @version 1.0
*/
public abstract class CRSFileOutputStream extends OutputStream {
	public abstract void write(int b);
	public abstract void write(byte[] b);
	public abstract void write(byte[] b, int off, int len);
	public abstract void close();
}
