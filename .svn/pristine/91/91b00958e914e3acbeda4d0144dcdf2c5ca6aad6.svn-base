/**
 * 
 */
package com.talentPool.masters.utils;

import java.util.ArrayList;

import com.talentPool.common.utils.Utils;
import com.talentPool.masters.dataobject.RatingFieldsData;

/**
 * @author shivprasad
 * 
 */
public class RatingUtils {
	public ArrayList<RatingFieldsData> getRatingFieldsListFromString(String ratingFieldsString) {
		ArrayList<RatingFieldsData> ratingFields = new ArrayList<RatingFieldsData>();
		if (!Utils.isBlankOrNull(ratingFieldsString)) {
			String[] singleRatingFields = ratingFieldsString.split("\\|\\|");
			for (int i = 0; i < singleRatingFields.length; i++) {
				String[] flds = singleRatingFields[i].split("\\|");
				RatingFieldsData ratingFieldDFieldsData = new RatingFieldsData();
				ratingFieldDFieldsData.setRatingFieldId(flds[0]);
				ratingFieldDFieldsData.setRatingFieldDesc(flds[1].replaceAll("&#124;", "|"));
				ratingFields.add(ratingFieldDFieldsData);
			}
		}
		return ratingFields;
	}

}
