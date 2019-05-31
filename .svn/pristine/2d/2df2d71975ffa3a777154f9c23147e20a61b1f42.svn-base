/**
 * 
 */
package com.talentPool.vendorservice.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.exolab.castor.tools.MappingTool;
import org.exolab.castor.util.LocalConfiguration;

import com.talentPool.common.utils.Utils;
import com.talentPool.employeeservice.EmployeeServiceConstants;
import com.talentPool.employeeservice.dataobject.EPortalSettings;
import com.talentPool.employeeservice.dataobject.EapplicantData;
import com.talentPool.employeeservice.dataobject.EapplicantList;
import com.talentPool.employeeservice.dataobject.EcommunicationData;
import com.talentPool.employeeservice.dataobject.EcustomFieldData;
import com.talentPool.employeeservice.dataobject.EcustomFieldDataList;
import com.talentPool.employeeservice.dataobject.EeducationalData;
import com.talentPool.employeeservice.dataobject.EerrorData;
import com.talentPool.employeeservice.dataobject.EidsNames;
import com.talentPool.employeeservice.dataobject.EimportFieldData;
import com.talentPool.employeeservice.dataobject.EimportFieldList;
import com.talentPool.employeeservice.dataobject.EinboxData;
import com.talentPool.employeeservice.dataobject.EitemData;
import com.talentPool.employeeservice.dataobject.EloginData;
import com.talentPool.employeeservice.dataobject.Epagination;
import com.talentPool.employeeservice.dataobject.EpositionData;
import com.talentPool.employeeservice.dataobject.EpositionFilters;
import com.talentPool.employeeservice.dataobject.EpositionList;
import com.talentPool.employeeservice.dataobject.EpositionSkills;
import com.talentPool.vendorservice.VendorServiceConstants;
import com.talentPool.vendorservice.dataobject.VactivityData;
import com.talentPool.vendorservice.dataobject.VactivityList;
import com.talentPool.vendorservice.dataobject.VapplicantData;
import com.talentPool.vendorservice.dataobject.VapplicantList;
import com.talentPool.vendorservice.dataobject.VcommunicationData;
import com.talentPool.vendorservice.dataobject.VcustomFieldData;
import com.talentPool.vendorservice.dataobject.VcustomFieldDataList;
import com.talentPool.vendorservice.dataobject.VeducationalData;
import com.talentPool.vendorservice.dataobject.VerrorData;
import com.talentPool.vendorservice.dataobject.VidsNames;
import com.talentPool.vendorservice.dataobject.VimportFieldData;
import com.talentPool.vendorservice.dataobject.VimportFieldList;
import com.talentPool.vendorservice.dataobject.VinboxData;
import com.talentPool.vendorservice.dataobject.VitemData;
import com.talentPool.vendorservice.dataobject.VloginData;
import com.talentPool.vendorservice.dataobject.Vpagination;
import com.talentPool.vendorservice.dataobject.VpositionData;
import com.talentPool.vendorservice.dataobject.VpositionList;
import com.talentPool.websiteservice.WebsiteServiceConstants;
import com.talentPool.websiteservice.dataobject.WapplicantData;
import com.talentPool.websiteservice.dataobject.WcustomFieldData;
import com.talentPool.websiteservice.dataobject.WcustomFieldDataList;
import com.talentPool.websiteservice.dataobject.WeducationalData;
import com.talentPool.websiteservice.dataobject.WerrorData;
import com.talentPool.websiteservice.dataobject.WidsNames;
import com.talentPool.websiteservice.dataobject.WimportFieldData;
import com.talentPool.websiteservice.dataobject.WimportFieldList;
import com.talentPool.websiteservice.dataobject.WinboxData;
import com.talentPool.websiteservice.dataobject.WitemData;
import com.talentPool.websiteservice.dataobject.WpositionData;
import com.talentPool.websiteservice.dataobject.WpositionFieldData;
import com.talentPool.websiteservice.dataobject.WpositionFieldList;
import com.talentPool.websiteservice.dataobject.WpositionList;


/**
 * @author shivprasad
 * 
 */
public class JavaToXMLMapping {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, ArrayList<Class>> mapper = new HashMap<String, ArrayList<Class>>();
		try {
			// where xmls will be generated
			if (!new File(VendorServiceConstants.MAPPING_XML_FOLDER_ABSOLUTE_PATH).exists()) {
				new File(VendorServiceConstants.MAPPING_XML_FOLDER_ABSOLUTE_PATH).mkdir();
			}
			LocalConfiguration.getInstance().getProperties().setProperty("org.exolab.castor.indent", "true");

			// put all classes and xmlfilemapping name here
			ArrayList<Class> classes = new ArrayList<Class>();
			classes.add(VloginData.class);
			mapper.put(VendorServiceConstants.VLOGINDATA_MAPPING, classes);

			classes = new ArrayList<Class>();
			classes.add(VerrorData.class);
			mapper.put(VendorServiceConstants.VERRORDATA_MAPPING, classes);

			classes = new ArrayList<Class>();
			classes.add(VidsNames.class);
			mapper.put(VendorServiceConstants.VIDSNAMES_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(VpositionList.class);
			classes.add(VpositionData.class);
			classes.add(VitemData.class);
			mapper.put(VendorServiceConstants.VPOSITIONLIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(VpositionData.class);
			mapper.put(VendorServiceConstants.VPOSITIONDATA_MAPPING, classes);

			classes = new ArrayList<Class>();
			classes.add(VitemData.class);
			mapper.put(VendorServiceConstants.VITEMDATA_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(VapplicantList.class);
			classes.add(VapplicantData.class);
			classes.add(Vpagination.class);
			mapper.put(VendorServiceConstants.VAPPLICANTLIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(VapplicantData.class);
			classes.add(VeducationalData.class);
			classes.add(VcustomFieldData.class);
			mapper.put(VendorServiceConstants.VAPPLICANTDATA_MAPPING, classes);

			classes = new ArrayList<Class>();
			classes.add(VcommunicationData.class);
			mapper.put(VendorServiceConstants.VCOMMUNICATIONDATA_MAPPING, classes);

			classes = new ArrayList<Class>();
			classes.add(VactivityList.class);
			classes.add(VactivityData.class);
			classes.add(Vpagination.class);
			classes.add(VpositionData.class);
			mapper.put(VendorServiceConstants.VACTIVITYLIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(VcustomFieldDataList.class);
			classes.add(VcustomFieldData.class);
			mapper.put(VendorServiceConstants.VCUSTOMFIELDDATALIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(VinboxData.class);
			mapper.put(VendorServiceConstants.VINBOXDATA_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(VimportFieldList.class);
			classes.add(VimportFieldData.class);
			mapper.put(VendorServiceConstants.VIMPORTFIELDLIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(VimportFieldData.class);
			mapper.put(VendorServiceConstants.VIMPORTFIELDDATA_MAPPING, classes);
			
			//put all employee xml mapping code here
			classes = new ArrayList<Class>();
			classes.add(EloginData.class);
			mapper.put(EmployeeServiceConstants.ELOGINDATA_MAPPING, classes);

			classes = new ArrayList<Class>();
			classes.add(EerrorData.class);
			mapper.put(EmployeeServiceConstants.EERRORDATA_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(EitemData.class);
			mapper.put(EmployeeServiceConstants.EITEMDATA_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(EinboxData.class);
			mapper.put(EmployeeServiceConstants.EINBOXDATA_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(EcustomFieldDataList.class);
			classes.add(EcustomFieldData.class);
			mapper.put(EmployeeServiceConstants.ECUSTOMFIELDDATALIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(EapplicantList.class);
			classes.add(EapplicantData.class);
			classes.add(Epagination.class);
			mapper.put(EmployeeServiceConstants.EAPPLICANTLIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(EapplicantData.class);
			classes.add(EeducationalData.class);
			classes.add(EcustomFieldData.class);
			mapper.put(EmployeeServiceConstants.EAPPLICANTDATA_MAPPING, classes);

			classes = new ArrayList<Class>();
			classes.add(EcommunicationData.class);
			mapper.put(EmployeeServiceConstants.ECOMMUNICATIONDATA_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(EidsNames.class);
			mapper.put(EmployeeServiceConstants.EIDSNAMES_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(EpositionList.class);
			classes.add(EpositionData.class);
			classes.add(Epagination.class);
			classes.add(EitemData.class);			
			mapper.put(EmployeeServiceConstants.EPOSITIONLIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(EpositionData.class);
			mapper.put(EmployeeServiceConstants.EPOSITIONDATA_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(EimportFieldList.class);
			classes.add(EimportFieldData.class);
			mapper.put(EmployeeServiceConstants.EIMPORTFIELDLIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(EimportFieldData.class);
			mapper.put(EmployeeServiceConstants.EIMPORTFIELDDATA_MAPPING, classes);
			
			classes = new ArrayList<Class>();			
			classes.add(WerrorData.class);
			mapper.put(WebsiteServiceConstants.WERRORDATA_MAPPING, classes);

			classes = new ArrayList<Class>();
			classes.add(WidsNames.class);
			mapper.put(WebsiteServiceConstants.WIDSNAMES_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(WpositionList.class);
			classes.add(WpositionData.class);
			classes.add(WcustomFieldData.class);
			mapper.put(WebsiteServiceConstants.WPOSITIONLIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(WpositionData.class);
			classes.add(WcustomFieldData.class);
			mapper.put(WebsiteServiceConstants.WPOSITIONDATA_MAPPING, classes);

			classes = new ArrayList<Class>();
			classes.add(WitemData.class);
			mapper.put(WebsiteServiceConstants.WITEMDATA_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(WapplicantData.class);
			classes.add(WeducationalData.class);
			classes.add(WcustomFieldData.class);
			mapper.put(WebsiteServiceConstants.WAPPLICANTDATA_MAPPING, classes);

			classes = new ArrayList<Class>();
			classes.add(WcustomFieldDataList.class);
			classes.add(WcustomFieldData.class);
			mapper.put(WebsiteServiceConstants.WCUSTOMFIELDDATALIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(WimportFieldList.class);
			classes.add(WimportFieldData.class);
			mapper.put(WebsiteServiceConstants.WIMPORTFIELDLIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(WimportFieldData.class);
			mapper.put(WebsiteServiceConstants.WIMPORTFIELDDATA_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(WinboxData.class);
			mapper.put(WebsiteServiceConstants.WINBOXDATA_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(WpositionFieldList.class);
			classes.add(WpositionFieldData.class);
			mapper.put(WebsiteServiceConstants.WPOSITIONFIELDLIST_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(WpositionFieldData.class);
			classes.add(WcustomFieldData.class);
			mapper.put(WebsiteServiceConstants.WPOSITIONFIELDDATA_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(EpositionFilters.class);
			classes.add(EpositionSkills.class);
			mapper.put(EmployeeServiceConstants.EPOSITIONS_SCREEN_FILTERS_MAPPING, classes);
			
			classes = new ArrayList<Class>();
			classes.add(EPortalSettings.class);
			mapper.put(EmployeeServiceConstants.EPORTAL_SETTINGS, classes);
			
			// iterate and generate mapping xmls
			Iterator<String> itr = mapper.keySet().iterator();
			while (itr.hasNext()) {
				String key = itr.next();
				ArrayList<Class> values = mapper.get(key);
				MappingTool mappingTool = new MappingTool();
				for (int i = 0; i < values.size(); i++) {
					mappingTool.addClass(values.get(i));
				}
				File file = new File(Utils.concatFilePath(VendorServiceConstants.MAPPING_XML_FOLDER_ABSOLUTE_PATH, key));
				if (file.exists()) {
					file.delete();
				}
				java.io.OutputStream stream = new FileOutputStream(file);
				Writer writer = new OutputStreamWriter(stream);
				mappingTool.write(writer);
				writer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
