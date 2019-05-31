package com.talentPool.porting.utils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.export.bo.Field;
import com.talentPool.export.bo.Fields;

public class ImportUtils {

	public static String getListJavaScriptArrayForMasterFields(Fields fields) {
		StringBuffer sb = new StringBuffer();
		try {
			
			if (fields != null && fields.getHeaders() != null && fields.getHeaders().size() > 0) {
				sb.append("[");
				sb.append("new SelectOption('-1','" +  TPLabels.getLabel("common.selectlist.default") + "')");
				for (int i = 0; i < fields.getHeaders().size(); i++) {
					Field field = (Field) fields.getHeaders().get(i);					
					sb.append(", new SelectOption('" + Utils.escapeJavaScript(field.getId()) + "','" + Utils.escapeJavaScript(field.getName()) + "')");
					
				}
				sb.append("]");
			}else{
				sb.append("new Array()");
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("error while generating javascript array", e);
			sb = new StringBuffer("new Array()");
		}
		return sb.toString();
	}
	
}
