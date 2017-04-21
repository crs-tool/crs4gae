package com.crs4gae;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class CRS4GAEAuxiliary {

	public static InputStream inputStreamChannel(CRS4GAEFile fileName) {
		return Channels.newInputStream(fileName.read());
	}

	public static Reader reader(CRS4GAEFile fileName) {
		return CRS4GAEAuxiliary.reader(fileName, "UTF8");
	}

	public static Reader reader(CRS4GAEFile fileName, String codification) {
		return Channels.newReader(fileName.read(), codification);
	}
	
	public static FileChannel getChannel(FileInputStream input) {
		return input.getChannel();
	}
	
}
