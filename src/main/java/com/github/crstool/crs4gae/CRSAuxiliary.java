package com.crs4gae;

import java.io.FilenameFilter;

public class CRSAuxiliary {

	public static String getRegexPattern(FilenameFilter filter) {
		String regex = "";
		switch (filter.getClass().getSimpleName()) {
		case "FourDigitFilenameFilter":
			regex = "\\/\\d\\d\\d\\d\\/.*";
			break;
		case "TwoDigitFilenameFilter":
			regex = "\\/\\d\\d\\/.*";
			break;
		case "BlogEntryFilenameFilter":
			regex = "\\/\\d.*.xml";
			break;
		default:
			break;
		}
		return regex;
	}
	
	
}
