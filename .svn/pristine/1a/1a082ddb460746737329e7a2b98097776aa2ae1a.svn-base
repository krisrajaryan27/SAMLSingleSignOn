package com.talentPool.socialNetwork.ITransferObject;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.talentPool.socialNetwork.dataobject.SocialPostObject;

/**
 * @author SumeetS
 *
 */
@XmlRootElement
public interface ISocialNetwork {
	
	/**
	 * @return oauth token
	 */
	public String getOauthToken();
	
	/**
	 * @param flag
	 * @return oauth token expired flag
	 */
	public void setOauthTokenExpired();
	
	/**
	 * @return oauth token expired flag
	 */
	public boolean getOauthTokenExpiredFlag();
	
	/**
	 * @return post object which contains title,description,companyUrl,imageurl
	 */
	public SocialPostObject getPostObject();
	
	/**
	 * @return list of grps and pages to be published
	 */
	public List<IPostOn> getListOfGrpAndPagesToPostOn();
	
	/**
	 * @param add grp and page to post to
	 */
	public void addGrpAndPages(IPostOn obj);
	
	/**
	 * @return map of id vs response
	 */
	public Map<String,String> getResponsesForJobPostings();
	
	/**
	 * @param id
	 * @param response
	 * add response for a particular grp or page
	 * 
	 */
	public void addResponseForJobPosting(String id, String response);
}
