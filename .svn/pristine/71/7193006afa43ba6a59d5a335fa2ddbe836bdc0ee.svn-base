package com.talentPool.customReports.dataobject;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.customReports.constants.CRColumnValueConstants;

/**
 * @author PraveenK
 * @since  Dec 21, 2011
 */
@Entity
@Table(name = "tp_custom_report_columns", uniqueConstraints = @UniqueConstraint(columnNames = "column_property"))
public class CRColumn implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Short columnId;
	private CRTableType crTableType;
	private String columnProperty;
	private String columnDisplayName;
	private String columnCategory;
	private String columnDataType;
	private String columnDbname;
	private int columnWidth;
	private CRColumn groupByColumn;
	private CRColumn orderByColumn;
	private Boolean isCustomField;
	private Boolean isActive;
	private String valueType;

	public CRColumn() {
	}

	public CRColumn(String columnProperty,
			String columnDisplayName, String columnCategory, String columnDataType,
			String columnDbname, int columnWidth,
			Boolean isCustomField, Boolean isActive, String valueType) {
		this.columnProperty = columnProperty;
		this.columnDisplayName = columnDisplayName;
		this.columnCategory = columnCategory;
		this.columnDataType = columnDataType;
		this.columnDbname = columnDbname;
		this.columnWidth = columnWidth;
		this.isCustomField = isCustomField;
		this.isActive = isActive;
		this.valueType=valueType;
	}

	public CRColumn(CRTableType crTableType,
			String columnProperty, String columnDisplayName, String columnCategory,
			String columnDataType, String columnDbname, byte columnWidth,
			Boolean isCustomField, Boolean isActive, String valueType) {
		this.crTableType = crTableType;
		this.columnProperty = columnProperty;
		this.columnDisplayName = columnDisplayName;
		this.columnCategory = columnCategory;
		this.columnDataType = columnDataType;
		this.columnDbname = columnDbname;
		this.columnWidth = columnWidth;
		this.isCustomField = isCustomField;
		this.isActive = isActive;
		this.valueType=valueType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "column_id", unique = true, nullable = false)
	public Short getColumnId() {
		return this.columnId;
	}

	public void setColumnId(Short columnId) {
		this.columnId = columnId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "table_type")
	public CRTableType getCRTableType() {
		return this.crTableType;
	}

	public void setCRTableType(CRTableType crTableType) {
		this.crTableType = crTableType;
	}

	@Column(name = "column_property", unique = true, nullable = false, length = 100)
	public String getColumnProperty() {
		return this.columnProperty;
	}

	public void setColumnProperty(String columnProperty) {
		this.columnProperty = columnProperty;
	}
	
	/**
	 * Display name is fetched from application settings or resource bundle or returned directly 
	 * @return
	 */
	@Access(AccessType.FIELD)
	@Column(name = "column_display_name", nullable = false, length = 100)
	public String getColumnDisplayName() {
		if(this.columnDisplayName.indexOf(CRColumnValueConstants.COLUMN_DISPLAY_NAME_APPLICATION_LABEL_PREFIX)!=-1){
			String appliction_setting_property = this.columnDisplayName.substring(CRColumnValueConstants.COLUMN_DISPLAY_NAME_APPLICATION_LABEL_PREFIX.length());
			return GlobalApplicationProperties.getProperty(appliction_setting_property)==null?appliction_setting_property:GlobalApplicationProperties.getProperty(appliction_setting_property);
		}else if(this.columnDisplayName.indexOf(CRColumnValueConstants.COLUMN_DISPLAY_NAME_RESOURCE_BUNDLE_PREFIX)!=-1){
			String rb_key = this.columnDisplayName.substring(CRColumnValueConstants.COLUMN_DISPLAY_NAME_RESOURCE_BUNDLE_PREFIX.length());
			return TPLabels.getLabel(rb_key);
		}else {
			return this.columnDisplayName;
		}
	}

	public void setColumnDisplayName(String columnDisplayName) {
		this.columnDisplayName = columnDisplayName;
	}

	@Column(name = "column_category", nullable = false, length = 1)
	public String getColumnCategory() {
		return this.columnCategory;
	}

	public void setColumnCategory(String columnCategory) {
		this.columnCategory = columnCategory;
	}
	
	@Column(name = "value_type", nullable = false, length = 1)
	public String getValueType() {
		return this.valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	@Column(name = "column_data_type", nullable = false, length = 100)
	public String getColumnDataType() {
		return this.columnDataType;
	}

	public void setColumnDataType(String columnDataType) {
		this.columnDataType = columnDataType;
	}

	@Column(name = "column_dbname", nullable = false, length = 150)
	public String getColumnDbname() {
		return this.columnDbname;
	}

	public void setColumnDbname(String columnDbname) {
		this.columnDbname = columnDbname;
	}

	@Column(name = "column_width", nullable = false)
	public int getColumnWidth() {
		return this.columnWidth;
	}

	public void setColumnWidth(int columnWidth) {
		this.columnWidth = columnWidth;
	}

	@Column(name = "is_custom_field", nullable = false, length = 1)
	public Boolean getIsCustomField() {
		return this.isCustomField;
	}

	public void setIsCustomField(Boolean isCustomField) {
		this.isCustomField = isCustomField;
	}

	@Column(name = "is_active", nullable = false, length = 1)
	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the groupByColumn
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_by_column",referencedColumnName="column_property")
	public CRColumn getGroupByColumn() {
		return groupByColumn;
	}

	/**
	 * @param groupByColumn the groupByColumn to set
	 */
	public void setGroupByColumn(CRColumn groupByColumn) {
		this.groupByColumn = groupByColumn;
	}
	
	/**
	 * @return the orderByColumn
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_by_column",referencedColumnName="column_property")
	public CRColumn getOrderByColumn() {
		return orderByColumn;
	}

	/**
	 * @param orderByColumn the orderByColumn to set
	 */
	public void setOrderByColumn(CRColumn orderByColumn) {
		this.orderByColumn = orderByColumn;
	}
}
