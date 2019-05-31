/**
 * 
 */
package com.talentPool.vendorservice.utils;

import java.io.StringReader;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.XMLContext;

import com.talentPool.common.Logger.TPLogger;

/**
 * @author shivprasad
 *
 */
public class CastorUnMarshaller extends CastorMapper{
	public Object unmarshall(String xmlMapping, String xml, Class cls) {
		Object obj = null;
		try {
			Mapping mapping = getMapping(xmlMapping);
			XMLContext context = new XMLContext();
			context.addMapping(mapping);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setClass(cls);
			obj = unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			TPLogger.getLogger().debug("Error in unmarshall", e);
		}
		return obj;
	}
}
