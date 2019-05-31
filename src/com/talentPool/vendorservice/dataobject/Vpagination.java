/**
 * 
 */
package com.talentPool.vendorservice.dataobject;

import java.io.Serializable;

/**
 * @author shivprasad
 * 
 */
public class Vpagination implements Serializable{
	private long recordCount;
	private int pageNo;
	private int pageSize;
	private int showPages = 10;

	/** Creates a new instance of Vpagination */
	public Vpagination() {
	}

	public Vpagination(long recordCount, int pageSize, int pageNo) {
		this.recordCount = recordCount;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRecordNo() {
		int rec = pageSize * (pageNo - 1);
		if (rec < 0)
			rec = 0;
		return rec;
	}

	public String getRecordRange() {
		long start = pageSize * (pageNo - 1) + 1;
		long end = pageSize * pageNo;
		if (end > recordCount)
			end = recordCount;
		return ("Results <b>" + start + "</b> - <b>" + end + "</b> of about <b>" + recordCount + "</b> Records");
	}

	public int getNoOfPages() {
		try {
			int fullPages = (int)recordCount / pageSize;
			if (recordCount % pageSize > 0)
				fullPages++;
			return fullPages;
		} catch (Exception e) {
			return 0;
		}
	}

	public String getShowPaging() {
		String paging = "";
		int totPages = getNoOfPages();
		if (totPages > 1) {
			if (pageNo > 1) {
				paging = "<a href=\"javascript:submitForm(" + (pageNo - 1) + ")\" ><img src=\"images/ico_prev.gif\" border=\"0\" class=\"pageimage\"></a>";
				paging += "<span class=\"spacer\"/><SPAN class=\"divider\">|</SPAN>";
			}
			int lBound = 1;
			int uBound = lBound + showPages - 1;
			while (pageNo - lBound >= showPages) {
				// paging += "(";
				paging += "<span class=\"spacer\"/>(<a href=\"javascript:submitForm(" + lBound + ")\" >" + lBound + "</a>-";
				// paging += "-";
				paging += "<a href=\"javascript:submitForm(" + uBound + ")\" >" + uBound + "</a>)";
				// paging += ") ";
				lBound = uBound + 1;
				uBound = lBound + showPages - 1;
			}

			if (uBound >= totPages)
				uBound = totPages;
			while (lBound <= uBound) {
				if (lBound == pageNo) {
					paging += "<span class=\"spacer\"/><span class=\"selected\">"+lBound+"</span>"  ;
				} else {
					paging += "<span class=\"spacer\"/><a href=\"javascript:submitForm(" + lBound + ")\" >" + lBound + "</a>";
				}
				lBound++;
			}
			lBound = uBound + 1;
			uBound = lBound + showPages - 1;
			if (uBound >= totPages)
				uBound = totPages;
			while (lBound <= totPages) {
				// paging += "(";
				paging += "<span class=\"spacer\"/>(<a href=\"javascript:submitForm(" + lBound + ")\" >" + lBound + "</a>-";
				// paging += "-";
				paging += "<a href=\"javascript:submitForm(" + uBound + ")\" >" + uBound + "</a>)";
				// paging += ") ";
				lBound = uBound + 1;
				uBound = lBound + showPages - 1;
				if (uBound >= totPages)
					uBound = totPages;
			}

			if (pageNo < totPages) {
				paging += "<span class=\"spacer\"/><SPAN class=\"divider\">|</SPAN><span class=\"spacer\"/>";
				paging += "<a href=\"javascript:submitForm(" + (pageNo + 1) + ");\" ><img src=\"images/ico_next.gif\" border=\"0\" class=\"pageimage\"></a>";

			}
		}

		return paging;

	}

}
