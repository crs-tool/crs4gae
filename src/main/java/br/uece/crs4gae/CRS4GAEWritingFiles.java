package br.uece.crs4gae;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;

import com.google.appengine.repackaged.com.google.common.io.ByteStreams;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;


public class CRS4GAEWritingFiles {
	private static String nameBucket = "MyBucket";
	private final static GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	private static GcsOutputChannel outputChannel;
	
	public static GcsOutputChannel getOutputChannel() {
		return CRS4GAEWritingFiles.outputChannel;
	}
	public static void setOutputChannel(GcsOutputChannel outputChannel) {
		CRS4GAEWritingFiles.outputChannel = outputChannel;
	}
	public static GcsService getGcsService() {
		return gcsService;
	}
	public static GcsFilename newCloudFile(String nameFile) {
		return new GcsFilename(CRS4GAEWritingFiles.nameBucket, nameFile);
	}
	public static GcsOutputChannel create(GcsFilename fileName) throws IOException {
		return getGcsService().createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
	}
	public static void write(byte[] bytes) {
		try {
			getOutputChannel().write(ByteBuffer.wrap(bytes));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void close() throws IOException  {
		getOutputChannel().close();
	}
	public static byte[] convertFileToByteArray(InputStream fs) throws FileNotFoundException, IOException {
		byte[] bytes = ByteStreams.toByteArray(fs);
		return bytes;
	}
}
