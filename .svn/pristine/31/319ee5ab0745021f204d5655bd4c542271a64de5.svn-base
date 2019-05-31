package com.talentPool.salaryStructure.entity;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tp_salary_component_categories")
public class SalaryComponentCategory implements java.io.Serializable {

	private static final long serialVersionUID = -3610239117058201006L;
	private Integer categoryId;
	private String categoryName;
	private Set<SalaryComponent> salaryComponents = new HashSet<SalaryComponent>(0);

	public SalaryComponentCategory() {
	}

	public SalaryComponentCategory(Integer categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public SalaryComponentCategory(Integer categoryId, String categiryName,
			Set<SalaryComponent> salaryComponents) {
		this.categoryId = categoryId;
		this.categoryName = categiryName;
		this.salaryComponents = salaryComponents;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "category_id", unique = true, nullable = false)
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "category_name", nullable = false, length = 40)
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categiryName) {
		this.categoryName = categiryName;
	}

/*	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "salaryComponentCategory")
	public Set<SalaryComponent> getSalaryComponents() {
		return this.salaryComponents;
	}*/

	public void setSalaryComponents(Set<SalaryComponent> salaryComponents) {
		this.salaryComponents = salaryComponents;
	}

}
