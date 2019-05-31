/**
 * 
 */
package com.talentPool.customReports.dataobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author PraveenK
 * @since  Dec 9, 2011
 */
@Entity
@Table(name="tp_cr_table_type")
public class CRTableType {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "table_type_id")
	private Long tableTypId;
	@Column(updatable = false, name = "table_short_name", nullable = false)
	private String tableShortName;
	@Column(updatable = false, name = "table_db_name", nullable = false)
	private String tableDbName;
	@Column(updatable = false, name = "table_column_join", nullable = false)
	private String tableColumnJoin;
	
	/**
	 * @return the tableTypId
	 */
	public Long getTableTypId() {
		return tableTypId;
	}
	/**
	 * @param tableTypId the tableTypId to set
	 */
	public void setTableTypId(Long tableTypId) {
		this.tableTypId = tableTypId;
	}
	/**
	 * @return the tableShortName
	 */
	public String getTableShortName() {
		return tableShortName;
	}
	/**
	 * @param tableShortName the tableShortName to set
	 */
	public void setTableShortName(String tableShortName) {
		this.tableShortName = tableShortName;
	}
	/**
	 * @return the tableDbName
	 */
	public String getTableDbName() {
		return tableDbName;
	}
	/**
	 * @param tableDbName the tableDbName to set
	 */
	public void setTableDbName(String tableDbName) {
		this.tableDbName = tableDbName;
	}
	
	public String getTableColumnJoin() {
		return tableColumnJoin;
	}
	public void setTableColumnJoin(String tableColumnJoin) {
		this.tableColumnJoin = tableColumnJoin;
	}
}
