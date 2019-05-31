/**
 * 
 */
package com.talentPool.parser.dataobject;

/**
 * @author shivprasad
 * 
 * Used to store the result of regular expressions
 */
public class ParsedResultData {
	String searchTerm;
	String matchedString;
	int start;
	int end;
	int probability;
	/**
	 * @param matchedString
	 * @param start
	 * @param end
	 */
	public ParsedResultData(String matchedString, int start, int end) {
		this.matchedString = matchedString;
		this.start = start;
		this.end = end;
		this.probability=0;
	}
	
	public ParsedResultData(String searchTerm, String matchedString, int start, int end) {
		this.searchTerm=searchTerm;
		this.matchedString = matchedString;
		this.start = start;
		this.end = end;
		this.probability=0;
	}

	/**
	 * 
	 */
	public ParsedResultData() {
		this.start = 0;
		this.end = 0;
		this.probability=0;
	}
	
	public String toString(){
		return "\n[ searchTerm= " + this.searchTerm + " matchedString= " + this.matchedString + " {" + this.start +", " + this.end +"} probability="+ this.probability+" ]";
	}

	/**
	 * @return Returns the matchedString.
	 */
	public String getMatchedString() {
		return matchedString;
	}

	/**
	 * @param matchedString The matchedString to set.
	 */
	public void setMatchedString(String matchedString) {
		this.matchedString = matchedString;
	}

	/**
	 * @return Returns the probability.
	 */
	public int getProbability() {
		return probability;
	}

	/**
	 * @param probability The probability to set.
	 */
	public void setProbability(int probability) {
		this.probability = probability;
	}

	/**
	 * @return Returns the end.
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * @param end The end to set.
	 */
	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * @return Returns the start.
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start The start to set.
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return Returns the searchTerm.
	 */
	public String getSearchTerm() {
		return searchTerm;
	}

	/**
	 * @param searchTerm The searchTerm to set.
	 */
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	
}