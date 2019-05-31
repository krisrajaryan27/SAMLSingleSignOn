package com.talentPool.parser.regex.test;

import junit.framework.TestCase;
import com.talentPool.parser.regex.TPRegExp;

public class TPRegExpTest extends TestCase {
	
	public static void test_CPP() {

		TPRegExp dm = new TPRegExp ();
		dm.addNewRegExp("\\bC\\+\\+");
		dm.stringToBeMatched("worked in C project. also C++. Some JAVA experience.But poor in J2ee.");
		dm.match();
		int match = 1;
		boolean found = dm.matchFirst();
		System.out.println ("C++ matches:");
		while (found)
		{
			String [] matchedStrs = dm.matchedStrs();
			printStrs (matchedStrs, match);
			match ++;
			found = dm.matchNext();
		}
	}

	public static void test_C() {

		TPRegExp dm = new TPRegExp ();
		dm.addNewRegExp("\\bC\\s*\\b");
		dm.stringToBeMatched("worked in C project. also C++. Some JAVA experience.But poor in J2ee.");
		int match = 1;
		boolean found = dm.matchFirst();
		System.out.println("C matches:");
		while (found)
		{
			String [] matchedStrs = dm.matchedStrs();
			printStrs (matchedStrs, match);
			match ++;
			found = dm.matchNext();
		}
	}

	public static void test_Java() {

		TPRegExp dm = new TPRegExp ();
		dm.addNewRegExp("java|j2ee");
		dm.stringToBeMatched("worked in C project. also C++. Some JAVA experience.But poor in J2ee.");
		System.out.println ("Java matches:");
		int match = 1;
		boolean found = dm.matchFirst();
		while (found)
		{
			String [] matchedStrs = dm.matchedStrs();
			printStrs (matchedStrs, match);
			match ++;
			found = dm.matchNext();
		}
	}

	public static void printStrs (String [] strs, int match) {
		for (int i = 0; i < strs.length; i++)
		{
			String d = strs[i];
			System.out.println ("Match # " + match + 
					" for regexp <" + i + "> " + 
					((d == null) ? "<null>" : "<" + d + ">"));
		}
	}

}
