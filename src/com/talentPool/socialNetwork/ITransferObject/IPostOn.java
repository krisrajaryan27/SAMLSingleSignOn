package com.talentPool.socialNetwork.ITransferObject;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author SumeetS
 *
 */
@XmlRootElement
public interface IPostOn{
	
	/**
	 * @return group or company Id
	 */
	public String getId();
}
