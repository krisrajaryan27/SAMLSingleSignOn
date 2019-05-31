/**
 * 
 */
package com.talentPool.otherApplications.constant;

import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.otherApplications.properties.OtherApplicationProperties;

/**
 * @author Shantanu
 *
 */
public class OtherApplicationConstants {
	public static final String IS_CUSTOMER_HRMS_INTEGRATE = TPApplicationProperties.getProperty("is_customer.hrms_integrate").trim();
	public static final String CUSTOMER_HRMS_URL = TPApplicationProperties.getProperty("customer.hrms_url").trim();
	
	public static String relativeXMLsPath;	
	public static String customerXMLPath;	
	
	static{
		relativeXMLsPath = TPApplicationProperties.getProperty("customer.request_xml.dir").trim();		
		customerXMLPath = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), relativeXMLsPath).trim();		
	}	
	
	public static final String KOTAK_HRMS_CODE_REQ_XML = "CUSTOMER_HRMS_CODE_REQUEST.XML"; //filename of the kotak hrms code request file.	
	public static final String KOTAK_EMPLOYEE_CODE_REQ_XML = "CUSTOMER_EMP_CODE_REQUEST.xml"; //filename of the kotak emp code request file.
	
	public static final String KOTAK_XML_TYPE_HRMS_CODE = "1";
	public static final String KOTAK_XML_TYPE_EMPLOYEE_CODE = "2";
	
	public static final String GREYTIP_QUERY_XML = "GREYTIP_QUERY_XML.XML";
	
	//REQUEST FOR HRMS CODE: Node Element Tag Name
	public static final String KOTAK_HRMS_CODE_REQ_XML_HEADER = "Header";
	public static final String KOTAK_HRMS_CODE_REQ_XML_IDREQUEST = "idrequest";
	public static final String KOTAK_HRMS_CODE_REQ_XML_DATPOST = "datpost";
	public static final String KOTAK_HRMS_CODE_REQ_XML_REFERENCENO = "referenceno";
	public static final String KOTAK_HRMS_CODE_REQ_XML_EXTSYSTEM = "extsystem";
	public static final String KOTAK_HRMS_CODE_REQ_XML_EXTSYSTEM_CONSTANT = "HRMS";
	
	public static final String KOTAK_HRMS_CODE_REQ_XML_BODY = "Body";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PVALIDATE = "p_validate";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PDATERECEIVED = "p_date_received";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PBUSINESSGROUPID = "p_business_group_id";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PLASTNAME = "p_last_name";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PPERSONTYPEID = "p_person_type_id";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PPERSONTYPEID_CONSTANT = "62";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PPERSONTYPEID_KMPL = "96";
	
	public static final String KOTAK_HRMS_CODE_REQ_XML_PEMAILADDRESS = "p_email_address";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PPHONENUMBER = "p_phone_number";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PCURRENTEMPLOYER = "p_current_employer";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PDEPARTMENT = "department";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PPROJECTEDHIREDATE = "p_projected_hire_date";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PTARGETCTC = "target_ctc";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PPOSITIONBUSINESSUNIT = "position_business_unit";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PSOURCETYPE = "p_source_type";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PVENDORNAME = "p_vendor_name";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PCUSTOMFIELD = "custom_field_";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PEFFECTIVEDATE = "p_effective_date";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PPHONETYPE = "p_phone_type";
	
	//XML tag value constants
	public static final String KOTAK_HRMS_CODE_REQ_XML_PPHONETYPEMOBILE = "M";
	public static final String KOTAK_HRMS_CODE_REQ_XML_PPHONETYPERESIDENTIAL = "H1";
	
	//RESPONSE FOR HRMS CODE: Node Element Tag Name
	public static final String KOTAK_HRMS_CODE_RES_XML_HEADER = "ns0:Header";
	public static final String KOTAK_HRMS_CODE_RES_XML_BODY = "ns0:Body";
	public static final String KOTAK_HRMS_CODE_RES_XML_PAPPLICATIONNUMBER = "ns0:p_application_number";
	
	////REQUEST FOR EMPLOYEE CODE: Node Element Tag Name
	public static final String KOTAK_EMPLOYEE_CODE_REQ_XML_HEADER = "Header";
	public static final String KOTAK_EMPLOYEE_CODE_REQ_XML_DATPOST = "datpost";
	public static final String KOTAK_EMPLOYEE_CODE_REQ_XML_EXTSYSTEM = "extsystem";	
	public static final String KOTAK_EMPLOYEE_CODE_REQ_XML_BODY = "Body";
	public static final String KOTAK_EMPLOYEE_CODE_REQ_XML_HRMSCODE = "application_number";
	public static final String KOTAK_EMPLOYEE_CODE_REQ_XML_BUSINESSGROUPID = "business_group_id";
	
	//RESPONSE FOR EMPLOYEE CODE: Node Element Tag Name
	public static final String KOTAK_EMPLOYEE_CODE_RES_XML_HEADER = "ns0:Header";
	public static final String KOTAK_EMPLOYEE_CODE_RES_XML_BODY = "ns0:Body";
	public static final String KOTAK_EMPLOYEE_CODE_RES_XML_APPLICATIONNUMBER = "ns0:application_number";
	public static final String KOTAK_EMPLOYEE_CODE_RES_XML_EMPLOYEENUMBER = "ns0:employee_number";
	
	
	//Trigger constants
	public static String TRIGGER_STEP_LEVEL_CHANGE = "STEP_LEVEL_CHANGE_CSV";
	public static String JOB_STEP_LEVEL_CHANGE_CSV = "JOB_STEP_LEVEL_CHANGE_CSV";
	
	//
	public static final String STEP_LEVEL_CHANGE_CSV = TPApplicationProperties.getProperty("is_step_level_csv_schedule").trim();
	
	//Trigger constants fo
	public static final String  TALENTPOOL_URL = OtherApplicationProperties.getProperty("talentpool.url").trim();
	public static final String  TALENTPOOL_REST_URL = "rest/appData";
	
	public static String TRIGGER_GET_DATA_FOR_GREYTIP = "GET_DATA_FOR_GREYTIP";
	public static String JOB_GET_DATA_FOR_GREYTIP = "JOB_GET_DATA_FOR_GREYTIP";

	public static String TRIGGER_POST_DATA_FROM_GREYTIP = "POST_DATA_FROM_GREYTIP";
	public static String JOB_POST_DATA_FROM_GREYTIP = "JOB_POST_DATA_FROM_GREYTIP";
	
	public static final String IS_GREYTIP_INTEGRATE=TPApplicationProperties.getProperty("is_greytip_integrate").trim();
	
	//filename of the Greytip column maps.
	public static final String GREYTIP_QUERY_XML_FILE = OtherApplicationProperties.getProperty("xml.greytip.column_map.file").trim();
	public static final String GREYTIP_INSERT_DB_TABLE_NAME = OtherApplicationProperties.getProperty("greytip.db.insert_table_name").trim();
	public static final String GREYTIP_FETCH_DB_TABLE_NAME = OtherApplicationProperties.getProperty("greytip.db.fetch_table_name").trim();
	
	//
	public static final String THYSSENKRUPP_CUSTOM_FIELD_REGISTRATION_NO=OtherApplicationProperties.getProperty("thyssenkrupp.custom_field.registration_no").trim();
	
}