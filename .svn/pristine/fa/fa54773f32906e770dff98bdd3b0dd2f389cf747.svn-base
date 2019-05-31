package com.talentPool.socialNetwork.ITransferObjectImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.talentPool.socialNetwork.ITransferObject.IPostOn;
import com.talentPool.socialNetwork.ITransferObject.ISocialNetwork;
import com.talentPool.socialNetwork.dataobject.SocialPostObject;

/**
 * @author SumeetS
 *
 */
@XmlRootElement
public final class LinkedInTO implements ISocialNetwork,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5562857999542808385L;
	private String oauthToken;
	private SocialPostObject postObject;
	private List<IPostOn> listOfGrpAndPagesToPostOn = new ArrayList<IPostOn>();
	private Map<String,String> responseMap = new HashMap<String,String>();
	private boolean oauthExpiredFlag = false;
	
	public LinkedInTO(SocialPostObject postObject,String oauthToken){
		this.oauthToken = oauthToken;
		this.postObject = SocialPostObject.getCopy(postObject);
	}
	
	public LinkedInTO(){
		
	}
	
	@Override
	public String getOauthToken() {
		return oauthToken;
	}

	@Override
	public SocialPostObject getPostObject() {
		return  SocialPostObject.getCopy(postObject);
	}

	@Override
	public List<IPostOn> getListOfGrpAndPagesToPostOn() {
		return Collections.unmodifiableList(listOfGrpAndPagesToPostOn);
	}

	@Override
	public void addGrpAndPages(IPostOn obj) {
		listOfGrpAndPagesToPostOn.add(obj);
	}

	@Override
	public Map<String, String> getResponsesForJobPostings() {
		return Collections.unmodifiableMap(responseMap);
	}

	@Override
	public void addResponseForJobPosting(String id, String response) {
		responseMap.put(id,response);
	}


	@Override
	public void setOauthTokenExpired() {
		oauthExpiredFlag = true;
	}

	@Override
	public boolean getOauthTokenExpiredFlag() {
		return oauthExpiredFlag;
	}
	
}
