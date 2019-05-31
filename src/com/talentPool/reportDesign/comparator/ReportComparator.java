/**
 * 
 */
package com.talentPool.reportDesign.comparator;

import java.util.Comparator;
import java.util.LinkedHashMap;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.reportDesign.constants.ReportDesignConstants;

/**
 * @author Ajeet
 * 
 */
public class ReportComparator implements Comparator {

	private String sortField;
	private String sortWith;
	private String sortType;

	public ReportComparator(String field, String with, String type) {
		this.sortField = field;
		this.sortWith = with;
		this.sortType = type;
	}

	public int compare(Object o1, Object o2) {
		try {
			if (!(o1 instanceof LinkedHashMap)
					|| !(o2 instanceof LinkedHashMap))
				throw new ClassCastException();

			LinkedHashMap<String, String> e1 = (LinkedHashMap<String, String>) o1;
			LinkedHashMap<String, String> e2 = (LinkedHashMap<String, String>) o2;
			if (!Utils.isBlankOrNull(sortType)) {
				if (sortType.equals(ReportDesignConstants.SORT_TYPE_TEXT) ||
						sortType.equals(ReportDesignConstants.SORT_TYPE_CHAR) ||
						sortType.equals(ReportDesignConstants.SORT_TYPE_VARCHAR)) {
					return compareText(e1, e2);
				} else if (sortType.equals(ReportDesignConstants.SORT_TYPE_NUMERIC)||
						sortType.equals(ReportDesignConstants.SORT_TYPE_DECIMAL)||
						sortType.equals(ReportDesignConstants.SORT_TYPE_DOUBLE)||
						sortType.equals(ReportDesignConstants.SORT_TYPE_FLOAT)||
						sortType.equals(ReportDesignConstants.SORT_TYPE_BIGINT)||
						sortType.equals(ReportDesignConstants.SORT_TYPE_INTEGER)||
						sortType.equals(ReportDesignConstants.SORT_TYPE_INT)||
						sortType.equals(ReportDesignConstants.SORT_TYPE_TINYINT)||
						sortType.equals(ReportDesignConstants.SORT_TYPE_SMALLINT)||
						sortType.equals(ReportDesignConstants.SORT_TYPE_MEDIUMINT)) {
					return compareNumber(e1, e2);
				} else if (sortType.equals(ReportDesignConstants.SORT_TYPE_DATETIME)||
						sortType.equals(ReportDesignConstants.SORT_TYPE_TIMESTAMP)||
						sortType.equals(ReportDesignConstants.SORT_TYPE_DATE)) {
					return compareDate(e1, e2);
				}
			} else {
				return compareText(e1, e2);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	private int compareText(LinkedHashMap<String, String> e1,
			LinkedHashMap<String, String> e2) {
		try {
			if (!Utils.isBlankOrNull(sortWith)
					&& sortWith.equals(ReportDesignConstants.SORT_ORDER_ASC)) {
				return (e1.get(sortField))
						.compareTo((String) e2.get(sortField));
			} else {
				return (e2.get(sortField))
						.compareTo((String) e1.get(sortField));
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return -1;
	}

	private int compareNumber(LinkedHashMap<String, String> e1, LinkedHashMap<String, String> e2) {
		try {
			if(!Utils.isBlankOrNull(sortWith) && sortWith.equals(ReportDesignConstants.SORT_ORDER_ASC)){
			return (e1.get(sortField)).compareTo(e2.get(sortField));
		}else{
			return (e2.get(sortField)).compareTo(e1.get(sortField));
		}
	}catch (Exception e) {
		TPLogger.getLogger().error(e);
	}
	return -1;
	}

	private int compareDate(LinkedHashMap<String, String> e1,
			LinkedHashMap<String, String> e2) {
		try {
			if (!Utils.isBlankOrNull(sortWith)
					&& sortWith.equals(ReportDesignConstants.SORT_ORDER_ASC)) {
				return (e1.get(sortField)).compareTo((String) e2.get(sortField));
			} else {
				return (e2.get(sortField)).compareTo((String) e1.get(sortField));
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return -1;
	}

}
