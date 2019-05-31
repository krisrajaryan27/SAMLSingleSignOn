package com.talentPool.socialNetwork.dataobject;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author SumeetS
 *
 */

@XmlRootElement
public final class SocialPostObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4168097987341461322L;
	/**
	 * 
	 */
	private final String title;
	private final String description;
	private final String companyUrl;
	private final String imageUrl;
	
	private SocialPostObject(Builder builder){
		title = builder.title;
		description = builder.description;
		companyUrl = builder.companyUrl;
		imageUrl = builder.imageUrl;
	}
	
	public static class Builder{
		private final String title;
		private final String description;
		
		private String companyUrl = "";
		private String imageUrl = "";
		
		public Builder(String title,String description){
			this.title= title;
			this.description = description;
		}
		
		public Builder companyUrl(String companyUrl){
			this.companyUrl = companyUrl;
			return this;
		}
		
		public Builder imageUrl(String imageUrl){
			this.imageUrl = imageUrl;
			return this;
		}
		
		public SocialPostObject build(){
			return new SocialPostObject(this);
		}
	}
	
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getCompanyUrl() {
		return companyUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	
	/**
	 * @param obj
	 * @return copy of SocialPostObject
	 */
	public static SocialPostObject getCopy(SocialPostObject obj){
		return new Builder(obj.getTitle(),obj.getDescription()).companyUrl(obj.getCompanyUrl()).imageUrl(obj.getImageUrl()).build();
	}
	
}
