/**
 * 
 */
package com.talentPool.masters.utils;

import java.util.ArrayList;

import com.talentPool.common.utils.Utils;
import com.talentPool.masters.dataobject.MultipleSelectFieldsData;

/**
 * @author shivprasad
 * 
 */
public class MultipleSelectUtils {
	public ArrayList<MultipleSelectFieldsData> getMultipleSelectFieldsListFromString(String multipleSelectFieldsString) {
		ArrayList<MultipleSelectFieldsData> multipleSelectFields = new ArrayList<MultipleSelectFieldsData>();
		if (!Utils.isBlankOrNull(multipleSelectFieldsString)) {
			String[] singleMultipleSelectFields = multipleSelectFieldsString.split("\\|\\|");
			for (int i = 0; i < singleMultipleSelectFields.length; i++) {
				String[] flds = singleMultipleSelectFields[i].split("\\|");
				MultipleSelectFieldsData multipleSelectFieldsData = new MultipleSelectFieldsData();
				multipleSelectFieldsData.setSelectFieldId(flds[0]);
				multipleSelectFieldsData.setSelectFieldDesc(flds[1].replaceAll("&#124;", "|"));				
				multipleSelectFields.add(multipleSelectFieldsData);
			}
		}
		return multipleSelectFields;
	}

}
