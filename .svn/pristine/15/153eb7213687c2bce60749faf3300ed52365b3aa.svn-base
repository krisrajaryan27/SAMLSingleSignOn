package com.talentPool.parser.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class ParserDegreesRegexTest extends TestCase {
	public void testExpr_Aviation() {
		String regex = "(?mid)\\bAviation\\b";		
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher("Aviation");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123Aviationabcd ");		
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("aviation");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("AVIATION");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123 Aviation abc");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("\tAviation ");
		assertTrue("Wrong Result", matcher.find());
	}
	
	public void testExpr_BA() {
		String regex = "(?md)\\bB\\.\\ ?A\\.?\\b";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher("B.A.");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("abc B.A. pqr");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("abcB.A.pqr");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("\t B.A.  ");
		assertTrue("Wrong Result", matcher.find());
		
		String regex2 = "(?mid).*\\bBachelor[\\ \\t]+Of[\\ \\t]+Art(s)?\\b.*";
		pattern = Pattern.compile(regex2);
		
		matcher = pattern.matcher("Bachelor Of Arts");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("bachelor of arts");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("Bachelor Of Art");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("bachelor of art");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("abc Bachelor Of Arts pqr");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123 bachelor of arts 4");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("BachelorOfArts");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("bachelorofarts");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123Bachelor Of Artsaaa");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("aaaabachelor of artsnnnnnnn");
		assertFalse("Wrong Result", matcher.find());
	}
	
	public void testExpr_BArch() {
		String regex = "(?md).*\\bB\\.?\\ ?(Arch|ARCH)\\.?\\b.*";	
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher("B Arch");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("B ARCH");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("B. Arch.");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("B. ARCH.");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123 B Arch abc");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("1 B ARCH pqr");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("ab B. Arch. pq");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123 B. ARCH. 123");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123B Archabc");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("1B ARCHpqr");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("abB. Arch.pq");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123B. ARCH.123");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("BArch");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("BARCH");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("B.Arch.");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("B.ARCH.");
		assertTrue("Wrong Result", matcher.find());
	}
	
	public void testExpr_BBA() {
		String regex = "(?md)\\bB\\.B\\.A\\.?\\b";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher("B.B.A.");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123 B.B.A. pqr");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123B.B.A.pqr");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("\t B.B.A. ");
		assertTrue("Wrong Result", matcher.find());
	}
	
	public void testExpr_BCom() {
		String regex = "(?md)\\bB\\.?(Com|COM)\\.?\\b";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher("BCom");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("BCOM");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("B.Com");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("B.COM");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("abc BCom pqr");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123 BCOM 234");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("1b B.Com 11");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("a B.COM 123");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("abcBCompqr");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123BCOM234");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("1bB.Com11");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("aB.COM123");
		assertFalse("Wrong Result", matcher.find());
		
		regex = "(?mid).*\\bB\\.\\ ?com\\.?\\b.*";
		pattern = Pattern.compile(regex);
		
		matcher = pattern.matcher("B. Com.");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("B. COM.");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("B.Com.");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("B.COM.");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("abc B. Com. pqr");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123 B. COM. 234");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("1b B.Com. 11");
		assertTrue("Wrong Result", matcher.find());
				
		matcher = pattern.matcher("a B.COM. 123");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("abcB. Com.pqr");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123B. COM.234");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("1bB.Com.11");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("aB.COM.123");
		assertFalse("Wrong Result", matcher.find());
		
		regex = "(?mid).*\\bBachelor[\\ \\t]+Of[\\ \\t]+commerce\\b.*";
		pattern = Pattern.compile(regex);
		
		matcher = pattern.matcher("Bachelor Of Commerce");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("bachelor of commerce");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("Bachelor		Of	 Commerce");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("abc Bachelor Of Commerce 1233");
		assertTrue("Wrong Result", matcher.find());		
		
		matcher = pattern.matcher("abc bachelor of commerce fsd");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123 Bachelor		Of	 Commerce wefe2");
		assertTrue("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("abcBachelor Of Commerce1233");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("abcbachelor of commercefsd");
		assertFalse("Wrong Result", matcher.find());
		
		matcher = pattern.matcher("123Bachelor		Of	 Commercewefe2");
		assertFalse("Wrong Result", matcher.find());
	}
	
	public void testExpr_BE() {
		String regex = "(?mid).*\\bB\\.E\\.?\\b.*";
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher("B.E");
		assertTrue("Wrong Result", matcher.find());	
		
		matcher = pattern.matcher("B.E.");
		assertTrue("Wrong Result", matcher.find());	
		
		matcher = pattern.matcher("B.E. asd");
		assertTrue("Wrong Result", matcher.find());	
	}
}
