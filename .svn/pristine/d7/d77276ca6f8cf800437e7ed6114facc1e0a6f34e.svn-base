package com.talentPool.socialNetwork.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.talentPool.socialNetwork.ITransferObject.ISocialNetwork;

/**
 * @author SumeetS
 *
 */
public final class SocialPostTransferObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1106230586653583175L;
	private final List<ISocialNetwork> listObject = new ArrayList<ISocialNetwork>();
	
	public void addPostObjForSocialJobPosting(ISocialNetwork obj){
		listObject.add(obj);
	}
	
	public List<ISocialNetwork> getPostObjectsForSocialJobPosting(){
		return Collections.unmodifiableList(listObject);
	}
	
}
