package com.crs4gae;

import java.io.Writer;

public abstract class CRSFileWriter extends Writer {
	public abstract void write(char[] cbuf); 
	public abstract void write(char[] cbuf, int off, int len);
	public abstract void close();
}
