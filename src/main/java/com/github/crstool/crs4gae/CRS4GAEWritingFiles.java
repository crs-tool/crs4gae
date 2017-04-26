package com.github.crstool.crs4gae;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.appengine.repackaged.com.google.common.io.ByteStreams;
import com.google.appengine.tools.cloudstorage.GcsFileMetadata;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.ListItem;
import com.google.appengine.tools.cloudstorage.ListOptions;
import com.google.appengine.tools.cloudstorage.ListResult;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**
* CRS4GAEWritingFiles.java - Javadoc under construction.
* @author Marcos Borges
* @version 1.0
*/
public class CRS4GAEWritingFiles {

	private static final boolean LOCAL_SERVER = false;
	private static String nameBucket = (LOCAL_SERVER) ? "MyBucket" : "sincere-axon-148921.appspot.com"; // Local
																										// Server
	private final static GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	private static GcsOutputChannel outputChannel;
	private static GcsInputChannel inputChannel;

	public static GcsOutputChannel getOutputChannel() {
		return CRS4GAEWritingFiles.outputChannel;
	}

	public static GcsInputChannel getInputChannel() {
		return CRS4GAEWritingFiles.inputChannel;
	}

	public static void setOutputChannel(GcsOutputChannel outputChannel) {
		CRS4GAEWritingFiles.outputChannel = outputChannel;
	}

	public static GcsService getGcsService() {
		return gcsService;
	}

	public static GcsFilename newCloudFile(String fileName) {
		return new GcsFilename(CRS4GAEWritingFiles.nameBucket,
				CRSUtils.isDirectory(fileName) ? CRSUtils.asDirectoryGAE(fileName) : fileName);
		// return new GcsFilename(CRS4GAEWritingFiles.nameBucket, nameFile);
	}

	public static GcsFilename newCloudFile(File file) {
		return CRS4GAEWritingFiles.newCloudFile(file.getPath());
		// return new GcsFilename(CRS4GAEWritingFiles.nameBucket,
		// CRSUtils.isDirectory(file.getPath()) ?
		// CRSUtils.asDirectoryGAE(file.getPath()):file.getPath());
	}

	public static GcsFilename newCloudFile(File file, String name) {
		return CRS4GAEWritingFiles.newCloudFile(CRSUtils.asDirectoryGAE(file.getPath()) + name);
		// return new GcsFilename(CRS4GAEWritingFiles.nameBucket,
		// CRSUtils.asDirectoryGAE(file.getPath())+CRSUtils.asDirectoryGAE(name));
	}

	public static GcsFilename newCloudFile(String file, String name) {
		return CRS4GAEWritingFiles.newCloudFile(CRSUtils.asDirectoryGAE(file) + name);
		// return new GcsFilename(CRS4GAEWritingFiles.nameBucket,
		// CRSUtils.asDirectoryGAE(file)+CRSUtils.asDirectoryGAE(name));
	}

	public static GcsFilename newCloudFile(GcsFilename file, String name) {
		return CRS4GAEWritingFiles.newCloudFile(file.getObjectName() + name);
	}

	public static GcsOutputChannel create(GcsFilename fileName) {
		try {
			return getGcsService().createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static GcsInputChannel read(GcsFilename fileName) {
		try {
			return getGcsService().openReadChannel(fileName, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void write(byte[] bytes) {
		try {
			getOutputChannel().write(ByteBuffer.wrap(bytes));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Close one channel at a time
	public static void close() {
		try {
			if (getOutputChannel() != null)
				getOutputChannel().close();
			if (getInputChannel() != null)
				getInputChannel().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static byte[] convertFileToByteArray(InputStream fs) throws FileNotFoundException, IOException {
		byte[] bytes = ByteStreams.toByteArray(fs);
		return bytes;
	}

	public static boolean exists(GcsFilename filename) {
		boolean exists = false;
		try {
			exists = (getGcsService().getMetadata(filename) != null) ? true : false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exists;
	}

	public static void createVirtualDirectory(GcsFilename fileName) {
		CRS4GAEWritingFiles.setOutputChannel(CRS4GAEWritingFiles.create(fileName));
		CRS4GAEWritingFiles.close();
	}

	public static Writer writerChannel(GcsFilename fileName) {
		return CRS4GAEWritingFiles.writerChannel(fileName, "UTF8");
	}

	public static Writer writerChannel(GcsFilename fileName, String codification) {
		return Channels.newWriter(CRS4GAEWritingFiles.create(fileName), codification);
	}

	public static OutputStream ouputStreamChannel(GcsFilename fileName) {
		return Channels.newOutputStream(CRS4GAEWritingFiles.create(fileName));
	}

	public static InputStream inputStreamChannel(GcsFilename fileName) {
		return Channels.newInputStream(CRS4GAEWritingFiles.read(fileName));
	}

	public static Reader readerChannel(GcsFilename fileName) {
		return CRS4GAEWritingFiles.readerChannel(fileName, "UTF8");
	}

	public static Reader readerChannel(GcsFilename fileName, String codification) {
		return Channels.newReader(CRS4GAEWritingFiles.read(fileName), codification);
	}

	public static GcsFilename[] listFiles(GcsFilename fileName) {
		try {
			ListResult listCloud = CRS4GAEWritingFiles.getGcsService().list(CRS4GAEWritingFiles.nameBucket,
					new ListOptions.Builder().setPrefix(fileName.getObjectName()).build());
			ArrayList<GcsFilename> gcs = new ArrayList<>();
			while (listCloud.hasNext()) {
				ListItem item = listCloud.next();
				gcs.add(new GcsFilename(CRS4GAEWritingFiles.nameBucket, item.getName()));
			}
			GcsFilename[] newGcsFileNames = gcs.toArray(new GcsFilename[gcs.size()]);
			return newGcsFileNames;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static GcsFilename[] listFiles(GcsFilename[] arrayFileName, FilenameFilter filter) {

		ArrayList<GcsFilename> gcs = new ArrayList<>();

		String regexPattern = CRSAuxiliary.getRegexPattern(filter);

		for (GcsFilename fileName : arrayFileName) {
			String objectName = (String) fileName.getObjectName().toString();
			Pattern pattern = Pattern.compile(regexPattern);
			Matcher matcher = pattern.matcher(objectName);
			if (matcher.find()) {
				gcs.add(new GcsFilename(CRS4GAEWritingFiles.nameBucket, matcher.group().substring(1)));
			}
		}
		GcsFilename[] newGcsFileNames = gcs.toArray(new GcsFilename[gcs.size()]);
		return newGcsFileNames;
	}
}
