/**
 * 
 */
package com.talentPool.vendorservice.dataobject;

/**
 * @author pallavi
 *
 */
public class VitemData {
	private String itemId;
	private String itemName;
	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * @param itemId 
	 *			the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * @param itemName 
	 *			the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getItemId() + " " + getItemName();
	}
	
}
