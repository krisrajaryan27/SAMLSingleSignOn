package com.talentPool.socialNetwork.ITransferObjectImpl;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.talentPool.socialNetwork.ITransferObject.IPostOn;

/**
 * @author SumeetS
 *
 */
@XmlRootElement
public final class PostOnGroup implements IPostOn,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3977501219784741877L;
	/**
	 * 
	 */
	private final String id;
	
	/**
	 * @param id
	 */
	public PostOnGroup(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}

}
