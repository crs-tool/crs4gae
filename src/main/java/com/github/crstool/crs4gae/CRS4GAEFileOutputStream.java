package com.crs4gae;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class CRS4GAEFileOutputStream extends CRSFileOutputStream {

	private OutputStream outputStream;

	public CRS4GAEFileOutputStream(CRS4GAEFile file) {
		outputStream = Channels.newOutputStream(file.create());
	}
	public CRS4GAEFileOutputStream(String pathName) {
		outputStream = Channels.newOutputStream(new CRS4GAEFile(pathName).create());
	}

	public void close() {
		try {
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public WritableByteChannel getChannel() {
			return Channels.newChannel(outputStream);
	}
	
	@Override
	public void write(int b) {
		try {
			outputStream.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void write(byte[] b) {
		try {
			outputStream.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void write(byte[] b, int off, int len) {
		try {
			outputStream.write(b, off, len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
