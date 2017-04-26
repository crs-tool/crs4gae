package com.github.crstool.crs4gae;

import java.io.Writer;

/**
* CRSFileWriter.java - Javadoc under construction.
* @author Marcos Borges
* @version 1.0
*/
public abstract class CRSFileWriter extends Writer {
	public abstract void write(char[] cbuf);
	public abstract void write(char[] cbuf, int off, int len);
	public abstract void close();
}
