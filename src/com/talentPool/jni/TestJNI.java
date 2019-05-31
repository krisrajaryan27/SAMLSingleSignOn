package com.talentPool.jni;

import java.io.File;

public class TestJNI {
	public TestJNI() {
		System.load("D:/TalentPool/jni/ToHtml1.dll");

	}

	// iterate in folder and convert all documents .doc and .rtf to .html with the same name
	public static void main(String[] args) {
		TestJNI newT = new TestJNI();
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		File srcFile = new File("c:\\distinct\\test1");
		String destPath = "c:\\distinct\\test1result\\";
		if (srcFile.isDirectory()) {
			File[] listFiles = srcFile.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				File file = listFiles[i];
				if (file.getName().endsWith(".doc") || file.getName().endsWith(".rtf")) {
					String srcPath = file.getAbsolutePath();
					//String destFile = destPath + file.getName().substring(0, file.getName().lastIndexOf(".")) + ".html";
					try {
						
						if (newT != null) {
							newT.convertWordToHtml(srcPath, destPath);
							Thread.sleep(5000);
						}
						
						// System.loadLibrary(destFile)
						// Thread.currentThread().join();
						
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				

			}
		}
		
		newT = null;
		System.gc();
	}

	public synchronized void convertWordToHtml(String srcPath, String destPath) {
	}
}
