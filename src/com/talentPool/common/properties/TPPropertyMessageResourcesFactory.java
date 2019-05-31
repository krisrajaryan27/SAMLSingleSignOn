package com.talentPool.common.properties;

/**
 * 
 */
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;

public class TPPropertyMessageResourcesFactory extends PropertyMessageResourcesFactory { 
	private static final long serialVersionUID = -3935837655549555879L;

	public TPPropertyMessageResourcesFactory() {
		super();
		setFactoryClass(this.getClass().getCanonicalName());
	}
 
	public MessageResources createResources(final String config) {
		return new TPPropertyMessageResources(this, config, this.getReturnNull());
	} 
}