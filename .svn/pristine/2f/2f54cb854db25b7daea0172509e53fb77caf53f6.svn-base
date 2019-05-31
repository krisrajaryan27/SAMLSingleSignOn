/**
 * 
 */
package com.talentPool.parser.dataobject;

/**
 * @author Shivprasad
 * 
 */
public class PhoneParsedData extends ParsedResultData {
	private int phoneType;

	public PhoneParsedData(ParsedResultData pData) {
		this.matchedString = pData.getMatchedString();
		this.start = pData.getStart();
		this.end = pData.getEnd();
		this.probability = pData.getProbability();
		this.phoneType = 0;
	}

	public PhoneParsedData(ParsedResultData pData, int phoneType) {
		this.matchedString = pData.getMatchedString();
		this.start = pData.getStart();
		this.end = pData.getEnd();
		this.probability = pData.getProbability();
		this.phoneType = phoneType;
	}

	public int getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(int phoneType) {
		this.phoneType = phoneType;
	}

	public String toString(){
		return super.toString() + "\n phoneType=" + phoneType;
	}

}