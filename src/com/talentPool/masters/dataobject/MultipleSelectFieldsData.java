/**
 * 
 */
package com.talentPool.masters.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class MultipleSelectFieldsData extends SimpleDataObject {
	/**
	 * @return the selectId
	 */
	public String getSelectId() {
		return getString("selectId");
	}

	/**
	 * @param selectId
	 *            the selectId to set
	 */
	public void setSelectId(String selectId) {
		setAttribute("selectId", selectId);
	}

	/**
	 * @return the selectFieldDesc
	 */
	public String getSelectFieldDesc() {
		return getString("selectFieldDesc");
	}

	/**
	 * @param selectFieldDesc
	 *            the selectFieldDesc to set
	 */
	public void setSelectFieldDesc(String selectFieldDesc) {
		setAttribute("selectFieldDesc", selectFieldDesc);
	}

	/**
	 * @return the selectFieldId
	 */
	public String getSelectFieldId() {
		return getString("selectFieldId");
	}

	/**
	 * @param selectFieldId
	 *            the selectFieldId to set
	 */
	public void setSelectFieldId(String selectFieldId) {
		setAttribute("selectFieldId", selectFieldId);
	}

	/**
	 * @return the selectFieldRank
	 */
	public int getSelectFieldRank() {
		return getInt("selectFieldRank");
	}

	/**
	 * @param selectFieldRank
	 *            the selectFieldRank to set
	 */
	public void setSelectFieldRank(int selectFieldRank) {
		setAttribute("selectFieldRank", selectFieldRank);
	}

	public String getSelectFieldStatus() {
		return getString("selectFieldStatus");
	}

	public void setSelectFieldStatus(String selectFieldStatus) {
		setAttribute("selectFieldStatus", selectFieldStatus);
	}

}
