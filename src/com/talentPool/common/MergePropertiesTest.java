package com.talentPool.common;

import com.talentPool.common.utils.MergeProperties;

import junit.framework.TestCase;

public class MergePropertiesTest extends TestCase {
	public void testValid() {
		String args[] = new String[3];
		args[0] = "C:\\123\\1.properties";
		args[1] = "C:\\123\\2.properties";
		args[2] = "1";
		MergeProperties.main(args);
	}
	
	public static void main(String[] args) {
		MergePropertiesTest mergePropertiesTest = new MergePropertiesTest();
		mergePropertiesTest.testValid();
	}
}
