package com.talentPool.parser.regex.test;

import junit.framework.TestCase;
import com.talentPool.parser.regex.REMatch;
import java.util.Vector;

public class REMatchTest extends TestCase {
	private static String testBuff [] = 
	{ "Studied B. Tech",
	"Cleared B.Tech in 2000",
	"Got BTECH blah blah",
	"Am a Bachelor of Technology from",
	"Bacher Tec",		/* Shouldn't match */
	"BE",				/* shouldn't match?? */
	"Bachelor of Engg",	
	"B.E.",
	"To be or not to be a B.E.",	/* should match once!! */
	"Have a B.Techn degree", /* no match?? */
	"Wanted to do a B.Tech or B.E. but did a Bachelor of Technology" /* Should match thrice! */
	};
	
	private static String EnggRegExp = 
		"\\bB\\.?\\s*Tech\\b|" +
		"\\bB\\.\\s*E\\b|" + 
		"\\bBachelor\\s+of\\s+(engineering|engg|technology)\\b";
	
	public static void test_0 () {
		baseTest (0);
	}
	public static void test_1 () {
		baseTest (1);
	}
	public static void test_2 () {
		baseTest (2);
	}
	public static void test_3 () {
		baseTest (3);
	}
	public static void test_4 () {
		baseTest (4);
	}
	public static void test_5 () {
		baseTest (5);
	}
	public static void test_6 () {
		baseTest (6);
	}
	public static void test_7 () {
		baseTest (7);
	}
	public static void test_8 () {
		baseTest (8);
	}
	public static void test_9 () {
		baseTest (9);
	}
	
	public static void test_10 () {
		baseTest (10);
	}

	private static void baseTest (int i) {
		System.out.println("Test # " + i);
		REMatch dm = new REMatch ();
		Vector al = dm.matchREAgainstString(EnggRegExp, testBuff[i]);
		printResult (al);
	}
	
	public static void printResult(Vector al) {
		System.out.println("No of matches: " + al.size ());
		for (int i = 0; i < al.size (); i++)
		{
			String d = (String) al.elementAt (i);
			System.out.println ("Matched <" + d + ">");
		}
	}
}
