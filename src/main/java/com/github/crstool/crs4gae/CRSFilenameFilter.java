package com.crs4gae;

public interface CRSFilenameFilter {
	/**
     * Tests if a specified file should be included in a file list.
     *
     * @param   dir    the cloud directory where the file was found.
     * @param   name   the name of the file.
     * @return  <code>true</code> if and only if the name should be
     * included in the file list; <code>false</code> otherwise.
     */
	boolean accept(CRSFile dir, String name);
	
}
