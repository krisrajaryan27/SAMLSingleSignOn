package com.talentPool.custom.dataobject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.search.SearchConstants;

public class CustomFieldData extends SimpleDataObject implements Cloneable {
	private static final long serialVersionUID = 1L;
	private static final String PATTERN_OPTION_VAL = "(?mids)\\{.*?\\}";
	String[] fieldValues;
	String[] toValues;
	
	public String getTableId() {
		return getString("tableId");
	}

	public void setTableId(String tableId) {
		setAttribute("tableId", tableId);
	}
	
	public String getTableName() {
		return getString("tableName");
	}

	public void setTableName(String tableName) {
		setAttribute("tableName", tableName);
	}

	/**
	 * @return the fieldAttributes
	 */
	public String getFieldAttributes() {
		return getString("fieldAttributes");
	}

	/**
	 * @param fieldAttributes
	 *            the fieldAttributes to set
	 */
	public void setFieldAttributes(String fieldAttributes) {
		setAttribute("fieldAttributes", fieldAttributes);
	}

	/**
	 * @return the fieldDisplayName
	 */
	public String getFieldDisplayName() {
		return getString("fieldDisplayName");
	}

	/**
	 * @param fieldDisplayName
	 *            the fieldDisplayName to set
	 */
	public void setFieldDisplayName(String fieldDisplayName) {
		setAttribute("fieldDisplayName", fieldDisplayName);
	}

	/**
	 * @return the fieldEntityType
	 */
	public int getFieldEntityType() {
		return getInt("fieldEntityType");
	}

	/**
	 * @param fieldEntityType
	 *            the fieldEntityType to set
	 */
	public void setFieldEntityType(int fieldEntityType) {
		setAttribute("fieldEntityType", new Integer(fieldEntityType));
	}

	/**
	 * @return the fieldFormat
	 */
	public String getFieldOtherAttributes() {
		return getString("fieldOtherAttributes");
	}

	/**
	 * @param fieldFormat
	 *            the fieldFormat to set
	 */
	public void setFieldOtherAttributes(String fieldOtherAttributes) {
		setAttribute("fieldOtherAttributes", fieldOtherAttributes);
	}

	/**
	 * @return the fieldId
	 */
	public String getFieldId() {
		return getString("fieldId");
	}

	/**
	 * @param fieldId
	 *            the fieldId to set
	 */
	public void setFieldId(String fieldId) {
		setAttribute("fieldId", fieldId);
	}

	/**
	 * @return the fieldInputAllowed
	 */
	public int getFieldInputAllowed() {
		return getInt("fieldInputAllowed");
	}

	/**
	 * @param fieldInputAllowed
	 *            the fieldInputAllowed to set
	 */
	public void setFieldInputAllowed(int fieldInputAllowed) {
		setAttribute("fieldInputAllowed", new Integer(fieldInputAllowed));
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return getString("fieldName");
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		setAttribute("fieldName", fieldName);
	}

	/**
	 * @return the fieldOptions
	 */
	public String getFieldOptions() {
		return getString("fieldOptions");
	}

	/**
	 * @param fieldOptions
	 *            the fieldOptions to set
	 */
	public void setFieldOptions(String fieldOptions) {
		setAttribute("fieldOptions", fieldOptions);
	}

	/**
	 * @return the fieldRank
	 */
	public int getFieldRank() {
		return getInt("fieldRank");
	}

	/**
	 * @param fieldRank
	 *            the fieldRank to set
	 */
	public void setFieldRank(int fieldRank) {
		setAttribute("fieldRank", new Integer(fieldRank));
	}

	/**
	 * @return the fieldRequired
	 */
	public int getFieldRequired() {
		return getInt("fieldRequired");
	}

	/**
	 * @param fieldRequired
	 *            the fieldRequired to set
	 */
	public void setFieldRequired(int fieldRequired) {
		setAttribute("fieldRequired", new Integer(fieldRequired));
	}

	/**
	 * @return the fieldSearchable
	 */
	public int getFieldSearchable() {
		return getInt("fieldSearchable");
	}

	/**
	 * @param fieldSearchable
	 *            the fieldSearchable to set
	 */
	public void setFieldSearchable(int fieldSearchable) {
		setAttribute("fieldSearchable", new Integer(fieldSearchable));
	}

	/**
	 * @return the fieldType
	 */
	public String getFieldType() {
		return getString("fieldType");
	}

	/**
	 * @param fieldType
	 *            the fieldType to set
	 */
	public void setFieldType(String fieldType) {
		setAttribute("fieldType", fieldType);
	}

	/**
	 * @return the fieldDefaultValue
	 */
	public String getFieldDefaultValue() {
		return getString("fieldDefaultValue");
	}

	/**
	 * @param fieldDefaultValue
	 *            the fieldDefaultValue to set
	 */
	public void setFieldDefaultValue(String fieldDefaultValue) {
		setAttribute("fieldDefaultValue", fieldDefaultValue);
	}

	public String getFieldStringValue() {
		return getString("fieldStringValue");
	}

	public void setFieldStringValue(String fieldStringValue) {
		setAttribute("fieldStringValue", fieldStringValue);
	}

	public double getFieldNumberValueTo() {
		return getDouble("fieldNumberValueTo");
	}

	public void setFieldNumberValueTo(double fieldNumberValueTo) {
		setAttribute("fieldNumberValueTo", fieldNumberValueTo);
	}

	public String getFieldNumberValueToInString() {
		return getString("fieldNumberValueTo");
	}

	public Date getFieldDateValueTo() {
		return getDate("fieldDateValueTo");
	}

	public void setFieldDateValueTo(Date fieldDateValueTo) {
		setAttribute("fieldDateValueTo", fieldDateValueTo);
	}

	public double getFieldNumberValue() {
		return getDouble("fieldNumberValue");
	}

	public void setFieldNumberValue(double fieldNumberValue) {
		setAttribute("fieldNumberValue", fieldNumberValue);
	}

	public String getFieldNumberValueInString() {
		return getString("fieldNumberValue");
	}

	public Date getFieldDateValue() {
		return getDate("fieldDateValue");
	}

	public void setFieldDateValue(Date fieldDateValue) {
		setAttribute("fieldDateValue", fieldDateValue);
	}
	
	public String getFieldDateValues() {
		return getString("fieldDateValues");
	}

	public void setFieldDateValues(String fieldDateValues) {
		setAttribute("fieldDateValue", fieldDateValues);
	}

	public void setCustomFieldTableColumnIds(String customFieldTableColumnIds) {
		setAttribute("customFieldTableColumnIds", customFieldTableColumnIds);
	}
	
	public String getCustomFieldTableColumnIds() {
		return getString("customFieldTableColumnIds");
	}
	
	/**
	 * @return the fieldValues
	 */
	public String[] getFieldValues() {
		return fieldValues;
	}

	/**
	 * @param fieldValues
	 *            the fieldValues to set
	 */
	public void setFieldValues(String[] fieldValues) {
		this.fieldValues = Utils.getArrayCopy(fieldValues);
	}

	public String getValuesWithSeparator(String separator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; this.fieldValues != null && i < this.fieldValues.length; i++) {
			if (!Utils.isBlankOrNull(this.fieldValues[i])) {
				if (i > 0) {
					sb.append(separator);
				}
				sb.append(this.fieldValues[i]);
			}
		}
		return sb.toString();
	}

	public String getToValuesWithSeparator(String separator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; this.toValues != null && i < this.toValues.length; i++) {
			if (!Utils.isBlankOrNull(this.toValues[i])) {
				if (i > 0) {
					sb.append(separator);
				}
				sb.append(this.toValues[i]);
			}
		}
		return sb.toString();
	}

	public boolean isMultipleValuesAllowed() {
		String typ = getFieldType();
		if (typ.equalsIgnoreCase(CustomFieldConstants.TYPE_CHECKBOX) || typ.equalsIgnoreCase(CustomFieldConstants.TYPE_LISTBOX)) {
			return true;
		}
		return false;
	}

	public String getDisplayValue() {
		return getValuesWithSeparator(", ");
	}

	public String getDisplayValueForSearch() {
		StringBuffer sb = new StringBuffer();
		if (!Utils.isBlankOrNull(getRangeCriteria())) {
			sb.append(SearchConstants.rangeCriteriaMapValue.get(getRangeCriteria()));
			sb.append("&nbsp;");
		}
		sb.append(getValuesWithSeparator(", "));
		String toVals = getToValuesWithSeparator(", ");
		if (!Utils.isBlankOrNull(toVals) && !"null".equalsIgnoreCase(toVals)) {
			sb.append("&nbsp;");
			sb.append(TPLabels.getLabel("common.to"));
			sb.append("&nbsp;");
			sb.append(toVals);
		}
		return sb.toString();
	}

	public String getOtherAttribute(String attributeName) {
		String val = "";
		ArrayList<String> otherAttribs = getAttributesList(getFieldOtherAttributes());
		for (int i = 0; otherAttribs != null && i < otherAttribs.size(); i++) {
			String[] att = otherAttribs.get(i).split("=");
			if (att[0].equalsIgnoreCase(attributeName)) {
				val = att[1];
				val = val.replace("\"", "");
				break;
			}
		}
		return val;
	}

	public ArrayList<String> getAttributesList(String content) {
		ArrayList<String> groups = new ArrayList<String>();
		try {
			if (!Utils.isBlankOrNull(content)) {
				Pattern p = Pattern.compile("(?mids)\\[.*?\\]");
				Matcher m = p.matcher(content);
				boolean theEnd = false;
				while (!theEnd) {
					theEnd = !m.find();
					if (!theEnd) {
						String extract = content.substring(m.start(), m.end());
						groups.add(extract.substring(1, extract.length() - 1));
					}
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return groups;
	}

	public ArrayList<CustomFieldOption> getOptionsList(String content) {
		ArrayList<CustomFieldOption> groups = new ArrayList<CustomFieldOption>();

		try {
			if (!Utils.isBlankOrNull(content)) {
				Pattern p = Pattern.compile(PATTERN_OPTION_VAL);
				Matcher m = p.matcher(content);
				boolean theEnd = false;
				while (!theEnd) {
					theEnd = !m.find();
					if (!theEnd) {
						String extract = content.substring(m.start(), m.end());
						extract = extract.substring(1, extract.length() - 1);
						String key = extract.substring(0, extract.indexOf("|"));
						String value = extract.substring(extract.indexOf("|") + 1);
						CustomFieldOption option = new CustomFieldOption(key, value);
						groups.add(option);
					}
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return groups;
	}
	
	public String getUI(){
		return getUI("");
	}

	public String getUI(String postFix) {
		String fType = getFieldType();
		if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_TEXT) || fType.equalsIgnoreCase(CustomFieldConstants.TYPE_NUMBER)) {
			return getTextBox(postFix);
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_TEXTAREA)) {
			return getTextArea();
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_DATE)) {
			return getDateBox(postFix);
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_RADIO)) {
			return getRadioBox(false);
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_CHECKBOX)) {
			return getCheckBox(false);
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_DROPDOWN)) {
			return getDropDownBox(false);
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_LISTBOX)) {
			return getListBox(false);
		}
		return "";
	}

	public String getSearchUI(String postFix) {
		String fType = getFieldType();
		if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_TEXT)) {
			return getTextBox("");
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_NUMBER)) {
			return getTextBox(postFix);
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_TEXTAREA)) {
			return getTextArea();
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_DATE)) {
			return getDateBox(postFix);
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_RADIO)) {
			return getRadioBox(true);
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_CHECKBOX)) {
			return getCheckBox(true);
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_DROPDOWN)) {
			return getDropDownBox(true);
		} else if (fType.equalsIgnoreCase(CustomFieldConstants.TYPE_LISTBOX)) {
			return getListBox(true);
		}
		return "";
	}
	
	public String getAttributesForJS(String content) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		try {
			if (!Utils.isBlankOrNull(content)) {
				Pattern p = Pattern.compile("(?mids)\\[.*?\\]");
				Matcher m = p.matcher(content);
				boolean theEnd = false;
				int cnt = 0;
				while (!theEnd) {
					theEnd = !m.find();
					if (!theEnd) {
						String extract = content.substring(m.start(), m.end());
						if (cnt > 0) {
							sb.append(", ");
						}
						sb.append(extract.substring(1, extract.length() - 1).replace("=", ":").replace("\"", "'"));
						cnt++;
					}
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		sb.append("}");
		return sb.toString();
	}

	public String getListBox(boolean forSearch) {
		StringBuffer sb = new StringBuffer();
		ArrayList<CustomFieldOption> options = getOptionsList(getFieldOptions());
//		if (forSearch) {
//			CustomFieldOption option = new CustomFieldOption(SearchConstants.CRITERIA_NOT_SPECIFIED, TPLabels.getLabel("search_applicant.home.criteria.not_specified"));
//			options.add(option);
//		}
		sb.append("<input type=\"hidden\" name=\"" + getFieldName() + "\" id=\"" + getFieldName() + "\" />");
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("var opts = " + CommonUtils.getListJavaScriptArrayForCustomFieldOptions(options) + ";\n");
		sb.append(getFieldName() + " = new CheckBoxList(opts,'" + getSelectedKeys().replaceAll("'", "\\\\'") + "', " + getAttributesForJS(getFieldAttributes()) + ");\n");
		sb.append("document.write(" + getFieldName() + ".getHtml());\n");
		sb.append(getFieldName() + ".init();\n");
		sb.append("</script>\n");
		return sb.toString();
	}

	public String getDropDownBox(boolean forSearch) {
		StringBuffer sb = new StringBuffer();
		ArrayList<CustomFieldOption> options = getOptionsList(getFieldOptions());
//		if (forSearch) {
//			CustomFieldOption option = new CustomFieldOption(SearchConstants.CRITERIA_NOT_SPECIFIED, TPLabels.getLabel("search_applicant.home.criteria.not_specified"));
//			options.add(option);
//		}
		sb.append("<input type=\"hidden\" name=\"" + getFieldName() + "\" id=\"" + getFieldName() + "\" />");
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("var opts = " + CommonUtils.getListJavaScriptArrayForCustomFieldOptions(options) + ";\n");
		if (forSearch) {
			sb.append("var m = [new SelectOption('-1','" + TPLabels.getLabel("common.option.all") + "')];\n");
		} else {
			sb.append("var m = [new SelectOption('" + CustomFieldConstants.DEFAULT_SELECT_OPTION + "','" + CustomFieldConstants.DEFAULT_SELECT_OPTION + "')];\n");
		}
		sb.append("opts = m.concat(opts);\n");
		sb.append(getFieldName() + " = new SelectBox(opts,'" + getSelectedKeys().replaceAll("'", "\\\\'") + "','images/btn_dropdown.gif', " + getAttributesForJS(getFieldAttributes()) + ");\n");
		sb.append("document.write(" + getFieldName() + ".getHtml());\n");
		sb.append(getFieldName() + ".init();\n");
		sb.append("</script>\n");
		return sb.toString();
	}

	public String getCheckBox(boolean forSearch) {
		StringBuffer sb = new StringBuffer();
		ArrayList<CustomFieldOption> options = getOptionsList(getFieldOptions());
//		if (forSearch) {
//			CustomFieldOption option = new CustomFieldOption(SearchConstants.CRITERIA_NOT_SPECIFIED, TPLabels.getLabel("search_applicant.home.criteria.not_specified"));
//			options.add(option);
//		}
		sb.append("<input type=\"hidden\" name=\"" + getFieldName() + "\" id=\"" + getFieldName() + "\" />");
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("var opts = " + CommonUtils.getListJavaScriptArrayForCustomFieldOptions(options) + ";\n");
		sb.append(getFieldName() + " = new CheckBoxRadioGroup(opts,'" + getSelectedKeys().replaceAll("'", "\\\\'") + "'," + getAttributesForJS(getFieldAttributes()) + ");\n");
		sb.append("document.write(" + getFieldName() + ".getHtml());\n");
		sb.append(getFieldName() + ".init();\n");
		sb.append("</script>\n");
		return sb.toString();
	}

	public String getRadioBox(boolean forSearch) {
		return getCheckBox(forSearch);

	}

	public String getTextArea() {
		StringBuffer sb = new StringBuffer();
		sb.append("<textarea name=\"" + getFieldName() + "\" id=\"" + getFieldName() + "\" ");

		ArrayList<String> fieldAtt = getAttributesList(getFieldAttributes());
		for (int i = 0; i < fieldAtt.size(); i++) {
			sb.append(fieldAtt.get(i) + " ");
		}
		sb.append(">");
		sb.append(getCheckedNullValue(getFieldValues()));
		sb.append("</textarea>");
		return sb.toString();
	}

	public String getTextBox(String postFix) {
		StringBuffer sb = new StringBuffer();
		String[] v = getFieldValues();
		if (CustomFieldConstants.POSTFIX_TO.equals(postFix)) {
			v = getToValues();
		}

		sb.append("<input type=\"text\" name=\"" + getFieldName() + postFix + "\" value=\"" + getCheckedNullValue(v) + "\" id=\"" + getFieldName() + postFix + "\" ");

		ArrayList<String> fieldAtt = getAttributesList(getFieldAttributes());
		for (int i = 0; i < fieldAtt.size(); i++) {
			sb.append(fieldAtt.get(i) + " ");
		}
		sb.append("/>");
		return sb.toString();
	}

	public String getDateBox(String postFix) {
		StringBuffer sb = new StringBuffer();
		String[] v = getFieldValues();
		if (CustomFieldConstants.POSTFIX_TO.equals(postFix)) {
			v = getToValues();
		}
		sb.append("<input type=\"text\" name=\"" + getFieldName() + postFix + "\" value=\"" + getCheckedNullValue(v) + "\" id=\"" + getFieldName() + postFix + "\" ");
		String format = getOtherAttribute(CustomFieldConstants.ATTRIBUTE_DATE_FORMAT);
		ArrayList<String> fieldAtt = getAttributesList(getFieldAttributes());
		for (int i = 0; i < fieldAtt.size(); i++) {
			sb.append(fieldAtt.get(i) + " ");
		}
		if (!Utils.isBlankOrNull(format)) {
			sb.append(" onblur=\"getFDate(this,'" + format + "');\" ");
		}
		sb.append("/>");

		sb.append("&nbsp;<img src=\"images/ico_cal.gif\" style=\"height:16px;margin-bottom:-3px;cursor:hand;\" onclick=\"new CalendarPopup('customCalDiv"+getFieldName()+"').select($('" + getFieldName() + postFix + "'),'" + getFieldName()
				 + postFix + "','" + format + "'); return false;\" //>");
		sb.append("<DIV id='customCalDiv"+getFieldName()+"' class=\"calDiv\" style=\"position:absolute;background:#FFFFFF;z-index:1000;\" ></DIV>");
		return sb.toString();
	}

	private String getCheckedNullValue(String[] v) {
		if (v != null && v.length > 0) {
			return (v[0] == null) ? "" : v[0];
		}
		return "";
	}

	public boolean isSelected(String key) {
		String[] values = getFieldValues();
		for (int i = 0; values != null && i < values.length; i++) {
			if (values[i] != null && values[i].equalsIgnoreCase(key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return comma separated list of select ids
	 */
	public String getSelectedKeys() {
		String sb = "";

		String[] values = getFieldValues();
		for (int i = 0; values != null && i < values.length; i++) {
			if (i > 0) {
				sb += ",";
			}
			if (values[i] != null) {
				sb += values[i];
			}
		}
		return sb;
	}

	public Object clone() {
		try {
			CustomFieldData v = (CustomFieldData) super.clone();
			return v;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}

	public CustomFieldData deepCopy() {
		CustomFieldData v = null;
		try {
			v = new CustomFieldData();
			v.setFieldEntityType(getFieldEntityType());
			v.setFieldType(getFieldType());
			v.setFieldId(getFieldId());
			v.setFieldName(getFieldName());
			v.setFieldDisplayName(getFieldDisplayName());
			v.setFieldAttributes(getFieldAttributes());
			v.setFieldOtherAttributes(getFieldOtherAttributes());
			v.setFieldInputAllowed(getFieldInputAllowed());
			v.setFieldOptions(getFieldOptions());
			v.setFieldRank(getFieldRank());
			v.setFieldRequired(getFieldRequired());
			v.setFieldSearchable(getFieldSearchable());
			v.setFieldDefaultValue(getFieldDefaultValue());
			v.setFieldStringValue(getFieldStringValue());
			v.setFieldNumberValue(getFieldNumberValue());
			v.setFieldDateValue(getFieldDateValue());
			v.setFieldDateValues(getFieldDateValues());
			v.setCustomFieldTableColumnIds(getCustomFieldTableColumnIds());
			v.setTableId(getTableId());
			v.setTableName(getTableName());
			if (getFieldValues() != null) {
				v.setFieldValues(getFieldValues().clone());
			}
			if (getToValues() != null) {
				v.setToValues(getToValues().clone());
			}
			v.setRangeCriteria(getRangeCriteria());
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);

			// this shouldn't happen, since we are Cloneable
			// throw new InternalError();
		}
		return v;
	}

	/**
	 * @return the toValues
	 */
	public String[] getToValues() {
		return toValues;
	}

	/**
	 * @param toValues
	 *            the toValues to set
	 */
	public void setToValues(String[] toValues) {
		this.toValues = Utils.getArrayCopy(toValues);
	}

	public String getRangeCriteria() {
		return getString("rangeCriteria");
	}

	public void setRangeCriteria(String rangeCriteria) {
		setAttribute("rangeCriteria", rangeCriteria);
	}
	
	public ArrayList<String> getValues() {
		String content = getFieldOptions();
		ArrayList<String> groups = new ArrayList<String>();

		try {
			if (!Utils.isBlankOrNull(content)) {
				Pattern p = Pattern.compile(PATTERN_OPTION_VAL);
				Matcher m = p.matcher(content);
				boolean theEnd = false;
				while (!theEnd) {
					theEnd = !m.find();
					if (!theEnd) {
						String extract = content.substring(m.start(), m.end());
						extract = extract.substring(1, extract.length() - 1);
						String key = extract.substring(0, extract.indexOf("|"));						
						groups.add(key);
					}
				}

			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return groups;
	}
}
