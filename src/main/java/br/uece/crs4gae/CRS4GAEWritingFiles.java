package br.uece.crs4gae;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.google.appengine.repackaged.com.google.common.io.ByteStreams;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

/**  
* CRS4GAEWritingFiles.java - a class for refactor legacy code that make use of the file system and than enable this code to use the service Google Cloud Storage.  
* @author Marcos Borges
* @version 1.0 
*/ 

public class CRS4GAEWritingFiles {
	private static String nameBucket = "MyBucket";
	private final static GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	private static GcsOutputChannel outputChannel;
	
	/**  
	* Retrieve the GcsOutputChannel instance used in this class to write files in Google Cloud Storage.  
	* @return A instance of the GcsOutpuChannel class.
	*/ 
	public static GcsOutputChannel getOutputChannel() {
		return CRS4GAEWritingFiles.outputChannel;
	}
	
	/**  
	* Update the GcsOutputChannel instance.  
	* @parameter A instance of the GcsOutpuChannel class.
	*/ 
	public static void setOutputChannel(GcsOutputChannel outputChannel) {
		CRS4GAEWritingFiles.outputChannel = outputChannel;
	}
	
	/**  
	* Retrieve the GcsService instance used in this class to communicate with the Google Cloud Storage.  
	* @return A instance of the GcsService class.
	*/ 
	public static GcsService getGcsService() {
		return gcsService;
	}
	
	/**  
	* Retrieve a GcsFilename instance that represents the complete name of a file in the Google Cloud Storage.  
	* @parameter A string with the file name.
	* @return A instance of the GcsFilename class.
	*/ 
	public static GcsFilename newCloudFile(String nameFile) {
		return new GcsFilename(CRS4GAEWritingFiles.nameBucket, nameFile);
	}
	
	/**  
	* Create a file in the Google Cloud Storage.  
	* @parameter A string with the file name.
	* @return A instance of the GcsOutputChannel class.
	*/ 
	public static GcsOutputChannel create(GcsFilename fileName) throws IOException {
		return getGcsService().createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
	}
	
	/**  
	* Write a array of bytes in the GcsOutputChannel instance used in this class.  
	* @parameter A array of bytes.
	*/ 
	public static void write(byte[] bytes) {
		try {
			getOutputChannel().write(ByteBuffer.wrap(bytes));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**  
	* Close the connection established through the GcsOutputChannel instance of this class.  
	*/ 
	public static void close() throws IOException  {
		getOutputChannel().close();
	}
	
	/**  
	* Convert a regular file of the file system to a byte array.
	* @parameter A InputStream instance.
	*/ 
	public static byte[] convertFileToByteArray(InputStream fs) throws FileNotFoundException, IOException {
		byte[] bytes = ByteStreams.toByteArray(fs);
		return bytes;
	}
}
