package com.talentPool.salaryStructure.entity;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tp_salary_components")
public class SalaryComponent implements java.io.Serializable {

	private static final long serialVersionUID = -4029959784285516722L;
	private Integer salaryComponentId;
	private SalaryComponentCategory salaryComponentCategory;
	private String salaryComponentName;
	private String salaryComponentDescription;
	private char salaryComponentType;

	public SalaryComponent() {
	}

	public SalaryComponent(
			SalaryComponentCategory salaryComponentCategory,
			String salaryComponentName, String salaryComponentDescription,
			char salaryComponentType) {
		this.salaryComponentCategory = salaryComponentCategory;
		this.salaryComponentName = salaryComponentName;
		this.salaryComponentDescription = salaryComponentDescription;
		this.salaryComponentType = salaryComponentType;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "salary_component_id", unique = true, nullable = false)
	public Integer getSalaryComponentId() {
		return this.salaryComponentId;
	}

	public void setSalaryComponentId(Integer salaryComponentId) {
		this.salaryComponentId = salaryComponentId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "salary_component_category_id", nullable = false)
	public SalaryComponentCategory getSalaryComponentCategory() {
		return this.salaryComponentCategory;
	}

	public void setSalaryComponentCategory(
			SalaryComponentCategory salaryComponentCategory) {
		this.salaryComponentCategory = salaryComponentCategory;
	}

	@Column(name = "salary_component_name", nullable = false, length = 50)
	public String getSalaryComponentName() {
		return this.salaryComponentName;
	}

	public void setSalaryComponentName(String salaryComponentName) {
		this.salaryComponentName = salaryComponentName;
	}

	@Column(name = "salary_component_description", nullable = false, length = 250)
	public String getSalaryComponentDescription() {
		return this.salaryComponentDescription;
	}

	public void setSalaryComponentDescription(String salaryComponentDescription) {
		this.salaryComponentDescription = salaryComponentDescription;
	}

	@Column(name = "salary_component_type", nullable = false, length = 1)
	public char getSalaryComponentType() {
		return this.salaryComponentType;
	}

	public void setSalaryComponentType(char salaryComponentType) {
		this.salaryComponentType = salaryComponentType;
	}
}
