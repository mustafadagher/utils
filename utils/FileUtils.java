package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * @author Mustafa Dagher
 */
public class FileUtils {
	/**
	 * process a given file and return its whole content as a string
	 * 
	 * @return string containing the file content
	 * @throws IOException if file is not found or if I/O Exception occurs.
	 */
	public static String getFileContentAsString(File file) throws IOException {
		FileInputStream fis = null;
		String fileAsString = "";
		
			fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			fis = null;

			fileAsString = new String(data, "UTF-8");
		
		return fileAsString;

	}

	/**
	 * 
	 * @param path
	 *            the path to the directory contains the files
	 * @param filterString
	 *            the part of the file name to filter on
	 * @param filterCriteria
	 *            the criteria to filter on, it's either
	 *            {@link FilterCriteria#Prefix}, {@link FilterCriteria#Suffix}
	 *            or {@link FilterCriteria#Any}
	 * @return an array of filtered files
	 * 
	 * @author Mustafa Dagher
	 */
	public static File[] filterDirectoryFiles(String path,
			final String filterString, final FilterCriteria filterCriteria) {
		
		FilenameFilter textFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if ((filterCriteria.equals(FilterCriteria.Prefix) && name
						.startsWith(filterString))
						|| (filterCriteria.equals(FilterCriteria.Suffix) && name
								.endsWith(filterString))
						|| (filterCriteria.equals(FilterCriteria.Any) && name
								.contains(filterString))) {
					return true;
				} else {
					return false;
				}
			}
		};

		File folder = new File(path);

		return folder.listFiles(textFilter);
	}

	public static enum FilterCriteria {	
		Prefix,
		Suffix,
		Any
	}
}
