package com.crs4gae;

import java.io.OutputStream;

public abstract class CRSFileOutputStream extends OutputStream {
	public abstract void write(int b);
	public abstract void write(byte[] b);
	public abstract void write(byte[] b, int off, int len);
	public abstract void close();
}
