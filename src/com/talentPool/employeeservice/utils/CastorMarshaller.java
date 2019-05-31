/**
 * 
 */
package com.talentPool.employeeservice.utils;

import java.io.StringWriter;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.XMLContext;

import com.talentPool.common.Logger.TPLogger;


/**
 * @author shivprasad
 *
 */
public class CastorMarshaller extends CastorMapper{
	public String marshall(String xmlMapping, Object obj) {
		String xmlFile = "";
		try {
			Mapping mapping = getMapping(xmlMapping);

			StringWriter swriter = new StringWriter();
			XMLContext context = new XMLContext();
			context.addMapping(mapping);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setWriter(swriter);
			marshaller.marshal(obj);
			xmlFile = swriter.toString();
		} catch (Exception e) {
			TPLogger.getLogger().debug("Error in unmarshall", e);
		}
		return xmlFile;
	}

}
