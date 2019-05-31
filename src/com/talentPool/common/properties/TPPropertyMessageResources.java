/**
 * 
 */
package com.talentPool.common.properties;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
 
import org.apache.struts.util.PropertyMessageResources;

import com.talentPool.application.manager.ApplicationManager;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
 
public class TPPropertyMessageResources extends PropertyMessageResources { 
	private static final long serialVersionUID = 3375389356590107075L;

	public TPPropertyMessageResources(final TPPropertyMessageResourcesFactory factory, final String config) {
		super(factory, config);
	}
 
	public TPPropertyMessageResources(final TPPropertyMessageResourcesFactory factory, final String config, final boolean returnNull) {
		super(factory, config, returnNull);
		reload(null);
	}
	
	public String getMessage(final Locale locale, final String key) {
		return super.getMessage(locale, key);
	}
 
	public String getMessage(final Locale locale, final String key, final Object[] args) {
		return super.getMessage(locale, key, args);
	}
 
	public synchronized void reload(final Locale locale) { 
		updateLabelsFromDB();
		locales.clear();
		messages.clear();
		formats.clear();
	}

	private void updateLabelsFromDB() {
		Properties properties = new Properties();	
		Properties customizationProperties = new Properties();
		ApplicationManager manager = new ApplicationManager();
		List<SimpleDataObject> customLabels = manager.getCustomLabels();

		properties = Utils.loadAppliocationLabels(null);
		customizationProperties = Utils.loadAppliocationLabels("customTalentpoollabels.properties");
		if (customizationProperties!=null && !customizationProperties.isEmpty()){
			Set<Entry<Object, Object>> entrySet =  customizationProperties.entrySet();
			Iterator<Entry<Object, Object>> iterator = entrySet.iterator();
			while (iterator.hasNext()){
				Entry<Object, Object> entry = iterator.next();
				properties.put(entry.getKey(), entry.getValue());
			}
			Utils.saveAppliocationLabels(properties);
		}
		if(customLabels != null && customLabels.size() > 0) {
			Iterator<SimpleDataObject> itr = customLabels.iterator();
			while(itr.hasNext()) {
				SimpleDataObject sdo = itr.next();
				if(sdo != null) {
					properties.put(sdo.getString("key"), sdo.getString("value"));
				}
			}
			Utils.saveAppliocationLabels(properties);
		}
		
	}
}