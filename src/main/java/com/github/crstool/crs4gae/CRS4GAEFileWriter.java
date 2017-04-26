package com.github.crstool.crs4gae;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.channels.Channels;

/**
* CRS4GAEFileWriter.java - Javadoc under construction.
* @author Marcos Borges
* @version 1.0
*/
public class CRS4GAEFileWriter extends CRSFileWriter {

	private Writer writer;

	public CRS4GAEFileWriter(CRS4GAEFile file) {
		writer = Channels.newWriter(file.create(), "UTF8");
	}

	public CRS4GAEFileWriter(String pathName) {
		this(new CRS4GAEFile(pathName));
	}

	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void write(char[] cbuf) {

		try {
			writer.write(cbuf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void write(char[] cbuf, int off, int len) {
		try {
			writer.write(cbuf, off, len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void flush() {
		try {
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
