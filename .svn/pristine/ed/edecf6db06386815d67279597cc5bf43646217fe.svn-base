/**
 * This class is intended to support matching of a bunch of
 * regular expressions for one entity of a kind (such as degree, 
 * institution, skills etc.), each of which is represented by a 'unique'
 * integer id, against a given CharSequence. 
 * It provides facilities to add all the desired regular
 * expressions for the entity, match it against a given CharSequence, and ask
 * for all the matches. The returned matches are in the form of
 * an array of Entity objects where each Entity consists of the
 * id of the matched object and the corresponding text from the
 * given CharSequence.
 */

package com.talentPool.parser.regex;

import java.util.Vector;

public class REMatch
{
	TPRegExp entityMatcher;	// Matcher for given entity

	// constructor
	public REMatch ()
	{
		//entityRegExps = new ArrayList ();
		entityMatcher = new TPRegExp ();
	}

	/* Method to add regular expressions for degrees */
	void addRE (String entityRegExp)
	{
		//entityRegExps.add (entityRegExp);
		entityMatcher.addNewRegExp (entityRegExp);
	}

	/* Return an arraylist of Strings that matched in this
	 * strBuff */

	Vector matchInString (CharSequence strBuff)
	{
		entityMatcher.stringToBeMatched (strBuff);
		entityMatcher.match();
		boolean found = entityMatcher.matchFirst ();
		Vector foundEntitys = new Vector ();
		while (found)
		{
			String [] matchedEnts = entityMatcher.matchedStrs ();
			int len = matchedEnts.length;
			for (int i = 0; i < len; i++)
			{
				if (matchedEnts[i] != null)		// Matched this regexp
				{
					foundEntitys.add (matchedEnts[i]);
				}
			}
			found = entityMatcher.matchNext ();
		}
		return foundEntitys;
	}
	
	public Vector matchREAgainstString (String regExp, CharSequence strBuff)
	{
		addRE (regExp);
		return matchInString (strBuff);
	}
}
