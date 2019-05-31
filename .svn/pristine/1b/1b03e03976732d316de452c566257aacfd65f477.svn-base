package com.talentPool.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileUtil {
	
	
	/**
	 * @param args
	 */
	public static String  convertToZipFile(String[] fileNames, String outputFileName, String outputFilePath) throws Exception {
		// These are the files to include in the ZIP file
	
		String[] filenames =fileNames;
		String outFilename=null;
		// Create a buffer for reading the files
		byte[] buf = new byte[1024];

		try {
			// Create the ZIP file
			String outFilePath=outputFilePath;			
			outFilename = outputFileName+".zip";
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputFilePath+"/"+outFilename));

			// Compress the files
			for (int i = 0; i < filenames.length; i++) {
				File f = new File(outFilePath +"/"+ filenames[i]);
				if (f.isDirectory()) {
					String fileList[] = f.list();
					for (int k = 0; k < fileList.length; k++) {
						
						FileInputStream in = new FileInputStream(f.getAbsolutePath()+ "/" + fileList[k]);
						// Add ZIP entry to output stream.
						out.putNextEntry(new ZipEntry(filenames[i]+ "/" + fileList[k]));
						// Transfer bytes from the file to the ZIP file
						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
						// Complete the entry
						out.closeEntry();
						in.close();
						
					}
				} else {
					FileInputStream in = new FileInputStream(f);

					// Add ZIP entry to output stream.
					out.putNextEntry(new ZipEntry(filenames[i]));

					// Transfer bytes from the file to the ZIP file
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					// Complete the entry
					out.closeEntry();
					in.close();
				}
			}
			// Complete the ZIP file
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outFilename;

		/* ends zip code */
	}

}

