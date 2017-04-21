package com.crs4gae;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

public class CRS4GAEFile extends CRSFile {

	private static final boolean LOCAL_SERVER = false;
	private static String bucketName = (LOCAL_SERVER) ? "MyBucket" : "sincere-axon-148921.appspot.com"; // Local
	private final static GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	private GcsFilename gcsFileName;
	private GcsOutputChannel outputChannel;
	private GcsInputChannel inputChannel;

	public static String getBucketName() {
		return bucketName;
	}

	public static void setBucketName(String bucketName) {
		CRS4GAEFile.bucketName = bucketName;
	}

	public static boolean isLocalServer() {
		return LOCAL_SERVER;
	}

	public GcsOutputChannel getOutputChannel() {
		return outputChannel;
	}

	public void setOutputChannel(GcsOutputChannel outputChannel) {
		this.outputChannel = outputChannel;
	}

	public GcsInputChannel getInputChannel() {
		return inputChannel;
	}

	public void setInputChannel(GcsInputChannel inputChannel) {
		this.inputChannel = inputChannel;
	}

	public static GcsService getGcsService() {
		return gcsService;
	}

	public GcsFilename getGcsFileName() {
		return gcsFileName;
	}

	public void setGcsFileName(GcsFilename gcsFileName) {
		this.gcsFileName = gcsFileName;
	}

	public CRS4GAEFile(String pathName) {
		this.setGcsFileName(new GcsFilename(bucketName,
				CRSUtils.isDirectory(pathName) ? CRSUtils.asDirectoryGAE(pathName) : pathName));
	}

	public CRS4GAEFile(String parent, String child) {
		this(CRSUtils.asDirectoryGAE(parent) + child);
	}

	public CRS4GAEFile(File parent, String child) {
		this(CRSUtils.asDirectoryGAE(parent.getPath()) + child);
	}

	public CRS4GAEFile(CRSFile fileCloud, String name) {
		this(fileCloud.getPath() + name);
	}

	public GcsOutputChannel create() {
		try {
			return getGcsService().createOrReplace(this.getGcsFileName(), GcsFileOptions.getDefaultInstance());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public GcsInputChannel read() {
		try {
			return getGcsService().openReadChannel(this.getGcsFileName(), 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void close() {
		try {
			if (this.getOutputChannel() != null)
				this.getOutputChannel().close();
			if (this.getInputChannel() != null)
				this.getInputChannel().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getPath() {
		return this.getGcsFileName().getObjectName();
	}

	@Override
	public boolean exists() {

		boolean exists = false;
		try {
			exists = (getGcsService().getMetadata(this.getGcsFileName()) != null) ? true : false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return exists;
	}

	@Override
	public String getAbsolutePath() {
		return this.getPath();
	}

	@Override
	public boolean mkdirs() {

		boolean wasCreated = false;
		this.setOutputChannel(this.create());
		this.close();
		if (this.exists()) {
			wasCreated = true;
		}
		return wasCreated;
	}

	@Override
	public CRS4GAEFile[] listFiles() {
		try {
			ListResult listCloud = getGcsService().list(bucketName,
					new ListOptions.Builder().setPrefix(this.getPath()).build());
			ArrayList<CRS4GAEFile> gcs = new ArrayList<>();
			while (listCloud.hasNext()) {
				ListItem item = listCloud.next();
				gcs.add(new CRS4GAEFile(bucketName, item.getName()));
			}
			CRS4GAEFile[] crs4gaeFiles = gcs.toArray(new CRS4GAEFile[gcs.size()]);
			return crs4gaeFiles;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean isDirectory() {

		String[] arrayPath = new String[this.getPath().split("/").length];
		arrayPath = this.getPath().split("/");
		return (!arrayPath[arrayPath.length - 1].contains(".")) ? true : false;
	}

	public String getName() {

		String[] arrayPath = new String[this.getPath().split("/").length];
		arrayPath = this.getPath().split("/");
		return arrayPath[arrayPath.length - 1];
	}

	@Override
	public boolean delete() {
		boolean exists = false;
		try {
			exists = (getGcsService().delete(this.getGcsFileName()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return exists;
	}

	@Override
	public CRS4GAEFile[] listFiles(CRSFilenameFilter filter) {
		CRS4GAEFile ss[] = listFiles();
		if (ss == null)
			return null;
		ArrayList<CRS4GAEFile> files = new ArrayList<>();
		for (CRS4GAEFile s : ss)
			if ((filter == null) || filter.accept(this, s.getName())) {
				files.add(new CRS4GAEFile(s.getAbsolutePath()));
			}
		return files.toArray(new CRS4GAEFile[files.size()]);
	}

	@Override
	public long length() {
		try {
			return getGcsService().getMetadata(this.getGcsFileName()).getLength();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String getParent() {
		String[] arrayPath = new String[this.getPath().split("/").length];
		arrayPath = this.getPath().split("/");
		return arrayPath[arrayPath.length - 1];
	}

	@Override
	public boolean renameTo(CRSFile file) {
		CRS4GAEFile fileAux = (CRS4GAEFile) file;
		boolean renamed = false;
		try {
			getGcsService().copy(this.getGcsFileName(), fileAux.getGcsFileName());
			renamed = getGcsService().delete(this.getGcsFileName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return renamed;
	}

	@Override
	public boolean createNewFile() {

		boolean wasCreated = false;
		this.setOutputChannel(this.create());
		this.close();
		if (this.exists()) {
			wasCreated = true;
		}
		return wasCreated;
	}

	@Override
	public long lastModified() {

		try {
			return getGcsService().getMetadata(this.getGcsFileName()).getLastModified().getTime();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}

	@Override
	public boolean mkdir() {
		// TODO Auto-generated method stub
		return this.mkdirs();
	}

	@Override
	public boolean isFile() {
		// TODO Auto-generated method stub
		return !this.isDirectory();
	}

	@Override
	public CRS4GAEFile getParentFile() {
		return new CRS4GAEFile(getParent());
	}

	@Override
	public CRSFile getCanonicalFile() {
		// TODO Auto-generated method stub
		return new CRS4GAEFile(getAbsolutePath());
	}

	@Override
	public boolean canRead() {
		return this.exists() && this.isFile();
	}
}
