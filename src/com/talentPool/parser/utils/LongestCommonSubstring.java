/**
 * 
 */
package com.talentPool.parser.utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author shivprasad
 * 
 */
public class LongestCommonSubstring {

	/**
	 * Construct the longest common substring between two strings if such a
	 * substring exists. Note that this is different from the longest common
	 * subsequence in that it assumes you want the longest continuous sequence.
	 * The cost of this routine can be made less by keeping a master copy of
	 * data around that you want to check input against. That is, imagine that
	 * you keep the sorted suffix arrays around for some collection of data
	 * items. Then finding the LCS against that set is just a matter of
	 * computing the suffix matrix for the input (e.g., line) and comparing
	 * against the pre-computed suffix arrays for each data item.
	 * 
	 * In any event, this routine always computes and sorts the suffix arrays
	 * for both input string parameters.
	 * 
	 * @param data
	 *            the first string instance
	 * @param line
	 *            the second string instance
	 * @return the longest common substring, or the empty string if at least one
	 *         of the arguments are <code>null</code>, empty, or there is no
	 *         match.
	 */
	public String lcs(String data, String line) {
		/* BEFORE WE ALLOCATE ANY DATA STORAGE, VALIDATE ARGS */
		if (null == data || "".equals(data))
			return "";
		if (null == line || "".equals(line))
			return "";
		if (data.equals(line))
			return data;

		/* ALLOCATE VARIABLES WE'LL NEED FOR THE ROUTINE */
		StringBuffer bestMatch = new StringBuffer(0);
		StringBuffer currentMatch = new StringBuffer(1024);
		ArrayList dataSuffixList = new ArrayList();
		ArrayList lineSuffixList = new ArrayList();
		String shorter = null;
		String longer = null;

		if (data.length() < line.length()) {
			shorter = data;
			longer = line;
		} else {
			shorter = line;
			longer = data;
		}

		/* Using some builtin String methods, take a couple of shortcuts */
		if (longer.startsWith(shorter)) {
			return shorter;
		} else if (longer.endsWith(shorter)) {
			return shorter;
		}

		/* FIRST, COMPUTE SUFFIX ARRAYS */
		for (int i = 0; i < data.length(); i++) {
			dataSuffixList.add(data.substring(i, data.length()));
		}
		for (int i = 0; i < line.length(); i++) {
			lineSuffixList.add(line.substring(i, line.length()));
		}

		/* LEXOGRAPHICALLY SORT SUFFIX ARRAYS (not strictly necessary) */
		Collections.sort(dataSuffixList);
		Collections.sort(lineSuffixList);

		/* NOW COMPARE ARRAYS MEMBER BY MEMBER */
		String d = null;
		String l = null;
		String shorterTemp = null;
		int stopLength = 0;
		int k = 0;
		boolean match = false;

		bestMatch = new StringBuffer(currentMatch.toString());
		for (int i = 0; i < dataSuffixList.size(); i++) {
			d = (String) dataSuffixList.get(i);
			for (int j = 0; j < lineSuffixList.size(); j++) {
				l = (String) lineSuffixList.get(j);
				if (d.length() < l.length()) {
					shorterTemp = d;
				} else {
					shorterTemp = l;
				}

				// potentially expensive, but safe
				currentMatch.delete(0, currentMatch.length());
				k = 0;
				stopLength = shorterTemp.length();
				/**
				 * You can add the assert back in if you compile and run the
				 * program with the appropriate flags to enable asserts (jdk
				 * >=1.4)
				 */
				// assert(k<stopLength);
				match = (l.charAt(k) == d.charAt(k));
				while (k < stopLength && match) {
					if (l.charAt(k) == d.charAt(k)) {
						currentMatch.append(shorterTemp.charAt(k));
						k++;
					} else {
						match = false;
					}
				}
				// got a longer match, so erase bestMatch and replace it.
				if (currentMatch.length() > bestMatch.length()) {
					// potentially expensive, but safe
					bestMatch.delete(0, bestMatch.length());
					/* replace bestMatch with our current match, which is longer */
					bestMatch = new StringBuffer(currentMatch.toString());
				}
			}
		}
		return bestMatch.toString();
	}

	/**
	 * Return all equal-length longest common substrings.
	 * 
	 * @param data
	 * @param line
	 */
	public ArrayList lcss(String data, String line) {
		return null;
	}

	public String lcsFromStart(String data, String line) {
		if (null == data || "".equals(data))
			return "";
		if (null == line || "".equals(line))
			return "";
		if (data.equals(line))
			return data;

		StringBuffer bestMatch = new StringBuffer(0);
		String shorter = null;
		String longer = null;

		if (data.length() < line.length()) {
			shorter = data;
			longer = line;
		} else {
			shorter = line;
			longer = data;
		}

		/* Using some builtin String methods, take a couple of shortcuts */
		if (longer.startsWith(shorter)) {
			return shorter;
		} else if (longer.endsWith(shorter)) {
			return shorter;
		}
		for (int i = 0; i < shorter.length(); i++) {
			if (shorter.charAt(i) == longer.charAt(i)) {
				bestMatch.append(shorter.charAt(i));
			} else {
				break;
			}
		}
		return bestMatch.toString();
	}

	/**
	 * Find the longest common substring between two strings. Invoke by:
	 * 
	 * java LongestCommonSubstring [string1] [string2]
	 * 
	 */
	public static void main(String[] args) {
//		 LongestCommonSubstring application = new LongestCommonSubstring();
	}

}
