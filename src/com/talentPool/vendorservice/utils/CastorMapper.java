/**
 * 
 */
package com.talentPool.vendorservice.utils;

import java.io.IOException;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.util.LocalConfiguration;
import org.exolab.castor.xml.XMLContext;

import com.talentPool.common.utils.Utils;
import com.talentPool.vendorservice.VendorServiceConstants;

/**
 * @author shivprasad
 *
 */
public class CastorMapper {
	static{
		LocalConfiguration.getInstance().getProperties().setProperty("org.exolab.castor.indent", "true");
	}
	protected Mapping getMapping(String xmlMapping) throws MappingException, IOException {
		Mapping mapping = XMLContext.createMapping();
		String mappingAbsolutePath = Utils.concatFilePath(VendorServiceConstants.MAPPING_XML_FOLDER_ABSOLUTE_PATH, xmlMapping);
		mapping.loadMapping(mappingAbsolutePath);
		return mapping;
	}
}
