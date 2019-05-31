/**
 * 
 */
package com.talentPool.masters.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.constants.MastersConstantsForAP;
import com.talentPool.masters.dataobject.MasterDataForAP;
import com.talentPool.masters.manager.MasterManagerForAP;

import sun.misc.BASE64Encoder;

/**
 * @author ArvindKhatik
 *
 */
public class MasterActionForAP {

	/**
	 * 
	 */
	public MasterActionForAP() {
		// TODO Auto-generated constructor stub
	}

	public static void syncMasterDataForAP() {
		// TODO Auto-generated method stub
		try {
			TPLogger.getLogger().info("Fetching company master data");
			MasterActionForAP.saveCompnayMasterDataToDB(TPLabels.getLabel("URL_TO_FETCH_COMPANY_DATA"));
			TPLogger.getLogger().info("Fetching businessUnit master data");
			MasterActionForAP.saveBusinessUnitMasterDataToDB(TPLabels.getLabel("URL_TO_FETCH_BUSINESS_UNIT_DATA"));
			TPLogger.getLogger().info("Fetching jobFunction master data");
			MasterActionForAP.saveJobFunctionMasterDataToDB(TPLabels.getLabel("URL_TO_FETCH_JOB_FUNCTION_DATA"));
			TPLogger.getLogger().info("Fetching department master data");
			MasterActionForAP.saveDepartmentMasterDataToDB(TPLabels.getLabel("URL_TO_FETCH_DEPARTMENT_DATA"));
			TPLogger.getLogger().info("Fetching jobCode master data");
			MasterActionForAP.saveJobCodeMasterDataToDB(TPLabels.getLabel("URL_TO_FETCH_JOB_CODE_DATA"));
			TPLogger.getLogger().info("Fetching paygrade master data");
			MasterActionForAP.savePayGradeMasterDataToDB(TPLabels.getLabel("URL_TO_FETCH_PAY_GRADE_DATA"));
			TPLogger.getLogger().info("Fetching division-region master data");
			MasterActionForAP.saveDivisionRegionMasterDataToDB(TPLabels.getLabel("URL_TO_FETCH_DIVISION_REGION_DATA"));
			TPLogger.getLogger().info("Fetching location master data");
			MasterActionForAP.saveLocationMasterDataToDB(TPLabels.getLabel("URL_TO_FETCH_LOCATION_DATA"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

	}// end of method syncMasterDataForAP

	/**
	 * saveCompnayMasterDataToDB Method to save company data into DB
	 * 
	 * @param url
	 */
	public static void saveCompnayMasterDataToDB(String url) {

		String companyName = StringUtils.EMPTY;
		String companyExternalCode = StringUtils.EMPTY;

		String apiResponseData = getApiData(url);

		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);

		// For List of Company
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject apiResponseDataAsObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(apiResponseDataAsObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(apiResponseDataAsObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();

				if (null != jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray results = jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS);

					// Iterate through the elements of the array i.
					for (int i = 0; i < results.size(); i++) {
						companyName = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_NAME);
						companyExternalCode = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_CODE);

						try {
							// save company master data into DB
							// @param "0" for Null Parent
							// @param "1" for department level 1
							MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
							masterManagerForAP.addDeptToDB(companyName, "0", companyExternalCode, "1");

						} catch (Exception e) {
							// TODO Auto-generated catch block
							TPLogger.getLogger().error(GlobalConstants.ERROR, e);
						}
					} // end of loop

				}
				if (null != jsonObjectD.get(MastersConstantsForAP.GET_JSON_OBJECT_NEXT)) {
					String uri_next = getUriToFetchNextResults(jsonObjectD);
					saveCompnayMasterDataToDB(uri_next);

				}
			}

		}

	}// end of method saveCompnayMasterDataToDB

	public static void saveBusinessUnitMasterDataToDB(String url) {
		String apiResponseData = getApiData(url);

		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);

		// For List of Business Unit
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject apiResponseDataAsObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(apiResponseDataAsObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(apiResponseDataAsObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();

				if (null != jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray results = jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS);

					// Iterate through the elements of the array i.
					for (int i = 0; i < results.size(); i++) {
						String businessUnitName = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_NAME);
						String businessUnitExternalCode = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_CODE);
						String uri = results.get(i).getAsJsonObject()
								.getAsJsonObject(MastersConstantsForAP.GET_JSON_OBJECT_CUST_TO_LEGAL_ENTITY)
								.getAsJsonObject(MastersConstantsForAP.GET_JSON_OBJECT_DEFERRED)
								.getAsJsonPrimitive(MastersConstantsForAP.GET_JSON_PRIMITIVE_URI).toString();

						uri = stripDoubleQuotes(uri);

						try {
							// To get Business Unit Parent Company ID
							String businessUnitParent_id = getCompanyParentIdList(uri);

							// save Business Unit master data into DB
							// @param "2" for department level 2
							if (!Utils.isBlankOrNull(businessUnitParent_id)) {
								MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
								masterManagerForAP.addDeptToDB(businessUnitName, businessUnitParent_id,
										businessUnitExternalCode, "2");
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							TPLogger.getLogger().error(GlobalConstants.ERROR, e);
						}
					} // end of loop
				}
				if (null != jsonObjectD.get(MastersConstantsForAP.GET_JSON_OBJECT_NEXT)) {
					String uri_next = getUriToFetchNextResults(jsonObjectD);
					saveBusinessUnitMasterDataToDB(uri_next);

				}
			}
		} // end of if

	}// end of method saveBusinessUnitMasterDataToDB

	public static void saveJobFunctionMasterDataToDB(String url) {
		String apiResponseData = getApiData(url);

		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);

		// For List of Job Function
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject apiResponseDataAsObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(apiResponseDataAsObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(apiResponseDataAsObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();
				if (null != jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray results = jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS);

					// Iterate through the elements of the array i.
					for (int i = 0; i < results.size(); i++) {
						String jobFunctionName = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_NAME);
						String jobFunctionExternalCode = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_CODE);

						String uri = results.get(i).getAsJsonObject()
								.getAsJsonObject(MastersConstantsForAP.GET_JSON_OBJECT_CUST_TO_BUSINESS_UNIT)
								.getAsJsonObject(MastersConstantsForAP.GET_JSON_OBJECT_DEFERRED)
								.getAsJsonPrimitive(MastersConstantsForAP.GET_JSON_PRIMITIVE_URI).toString();
						uri = stripDoubleQuotes(uri);

						try {
							// To get JobFunction Parent Business ID
							String parent_id = getJobFunctionParentId(uri);

							// save Job Function master data into DB
							if (Utils.isBlankOrNull(parent_id)) {
								parent_id = "0";
							}
							MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
							masterManagerForAP.addJobFunctionToDB(jobFunctionName, parent_id, jobFunctionExternalCode);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							TPLogger.getLogger().error(GlobalConstants.ERROR, e);
						}
					} // end of loop
				}

				if (null != jsonObjectD.get(MastersConstantsForAP.GET_JSON_OBJECT_NEXT)) {
					String uri_next = getUriToFetchNextResults(jsonObjectD);
					saveJobFunctionMasterDataToDB(uri_next);

				}
			}
		}

	}// end of method saveJobFunctionMasterDataToDB

	public static void saveDepartmentMasterDataToDB(String url) {
		String apiResponseData = getApiData(url);

		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);

		// For List of Department
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject apiResponseDataAsObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(apiResponseDataAsObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(apiResponseDataAsObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();
				if (null != jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray results = jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS);

					// Iterate through the elements of the array i.
					for (int i = 0; i < results.size(); i++) {
						String departmentName = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_NAME);

						String departmentExternalCode = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_CODE);

						String uri = results.get(i).getAsJsonObject()
								.getAsJsonObject(MastersConstantsForAP.GET_JSON_OBJECT_CUST_TO_BUSINESS_UNIT)
								.getAsJsonObject(MastersConstantsForAP.GET_JSON_OBJECT_DEFERRED)
								.getAsJsonPrimitive(MastersConstantsForAP.GET_JSON_PRIMITIVE_URI).toString();

						uri = stripDoubleQuotes(uri);

						try {
							// To get Department Parent Business ID
							String parent_id = getDepartmentParentId(uri);

							// save Department master data into DB
							// @param "3" for department level 3
							if (!Utils.isBlankOrNull(parent_id)) {
								MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
								masterManagerForAP.addDeptToDB(departmentName, parent_id, departmentExternalCode, "3");
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							TPLogger.getLogger().error(GlobalConstants.ERROR, e);
						}
					} // end of loop
				}
				if (null != jsonObjectD.get(MastersConstantsForAP.GET_JSON_OBJECT_NEXT)) {
					String uri_next = getUriToFetchNextResults(jsonObjectD);
					saveDepartmentMasterDataToDB(uri_next);

				}
			}
		}

	}// end of method saveDepartmentMasterDataToDB

	public static void saveJobCodeMasterDataToDB(String url) {
		String apiResponseData = getApiData(url);

		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);

		// For List of Job Code
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject apiResponseDataAsObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(apiResponseDataAsObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(apiResponseDataAsObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();
				if (null != jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray results = jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS);

					// Iterate through the elements of the array i.
					for (int i = 0; i < results.size(); i++) {
						String jobName = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_NAME);

						String JobExternalCode = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_CODE);

						try {
							MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
							masterManagerForAP.addJobCodeToDB(jobName, "0", JobExternalCode);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							TPLogger.getLogger().error(GlobalConstants.ERROR, e);
						}
					} // end of loop
				}
				if (null != jsonObjectD.get(MastersConstantsForAP.GET_JSON_OBJECT_NEXT)) {
					String uri_next = getUriToFetchNextResults(jsonObjectD);
					saveJobCodeMasterDataToDB(uri_next);

				}
			}
		}

	}// end of method saveJobCodeMasterDataToDB

	public static void savePayGradeMasterDataToDB(String url) {
		String apiResponseData = getApiData(url);
		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);

		// For List of PayGrade
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject apiResponseDataAsObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(apiResponseDataAsObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(apiResponseDataAsObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();
				if (null != jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray results = jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS);

					// Iterate through the elements of the array i.
					for (int i = 0; i < results.size(); i++) {
						String payGradeName = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_NAME);
						String payGradeExternalCode = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_CODE);

						try {
							MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
							masterManagerForAP.addPayGradeToDB(payGradeName, "0", payGradeExternalCode);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							TPLogger.getLogger().error(GlobalConstants.ERROR, e);
						}
					} // end of loop
				}
				if (null != jsonObjectD.get(MastersConstantsForAP.GET_JSON_OBJECT_NEXT)) {
					String uri_next = getUriToFetchNextResults(jsonObjectD);
					savePayGradeMasterDataToDB(uri_next);

				}
			}
		}

	}// end of method savePayGradeMasterDataToDB

	public static void saveDivisionRegionMasterDataToDB(String url) {
		String apiResponseData = getApiData(url);
		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);
		// For List of Division an Region
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject apiResponseDataAsObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(apiResponseDataAsObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(apiResponseDataAsObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();
				if (null != jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray results = jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS);

					// Iterate through the elements of the array i.
					for (int i = 0; i < results.size(); i++) {
						String divisionName = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_NAME);

						String divisionExternalCode = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_CODE);

						String uriForRegion = results.get(i).getAsJsonObject()
								.getAsJsonObject(MastersConstantsForAP.GET_JSON_OBJECT_CUST_TO_CUST_GO_REGION)
								.getAsJsonObject(MastersConstantsForAP.GET_JSON_OBJECT_DEFERRED)
								.getAsJsonPrimitive(MastersConstantsForAP.GET_JSON_PRIMITIVE_URI).toString();
						uriForRegion = stripDoubleQuotes(uriForRegion);

						try {
							// save Division master data into DB
							// @param "0" for Null parent to maintain location
							// hierarchy with division as top parent
							MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
							masterManagerForAP.addLocToDB(divisionName, "0", divisionExternalCode, "", "1");

							// save Region Data into DB
							saveRegionMasterDataToDB(uriForRegion, divisionExternalCode);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							TPLogger.getLogger().error(GlobalConstants.ERROR, e);
						}
					} // end of loop
				}

				if (null != jsonObjectD.get(MastersConstantsForAP.GET_JSON_OBJECT_NEXT)) {
					String uri_next = getUriToFetchNextResults(jsonObjectD);
					saveDivisionRegionMasterDataToDB(uri_next);

				}
			}
		} // end of if

	}// end of method saveDivisionRegionMasterDataToDB

	public static void saveRegionMasterDataToDB(String url, String divisionExternalCode) {
		String apiResponseDataForRegion = getApiData(url);
		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseDataForRegion);
		// For List of Regions
		if (!Utils.isBlankOrNull(apiResponseDataForRegion)) {
			JsonObject jsonObjectOfRegion = getApiResponseDataAsObject(apiResponseDataForRegion);
			if (!isObjectD_As_JsonNull_Instance(jsonObjectOfRegion)) {
				JsonElement jsonElementOfRegion = getDataFromJsonElementD(jsonObjectOfRegion);
				JsonObject jsonObjectDOfRegion = jsonElementOfRegion.getAsJsonObject();
				if (null != jsonObjectDOfRegion.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray arrayOfRegion = jsonObjectDOfRegion
							.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS);
					for (int j = 0; j < arrayOfRegion.size(); j++) {
						String parent_id_for_region = StringUtils.EMPTY;
						String regionName = getJsonPrimitiveAsString(arrayOfRegion, j,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_NAME);

						String regionExternalCode = getJsonPrimitiveAsString(arrayOfRegion, j,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_CODE);

						MasterManagerForAP masterManagerForAP = new MasterManagerForAP();

						// To get Region Parent Division ID
						if (!Utils.isBlankOrNull(regionExternalCode)) {
							MasterDataForAP masterDataForAP = masterManagerForAP
									.getLocParentIdFromDBByExternalCode(divisionExternalCode);

							if (null != masterDataForAP) {
								parent_id_for_region = masterDataForAP.getItemId() + "";

								// save Region master data
								// into DB
								// @param "1" if it is a
								// region
								// @param "2" for location
								// level 2
								if (!Utils.isBlankOrNull(parent_id_for_region)) {
									try {
										masterManagerForAP.addLocToDB(regionName, parent_id_for_region,
												regionExternalCode, "1", "2");
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							}
						}

					}
				}

				if (null != jsonObjectDOfRegion.get(MastersConstantsForAP.GET_JSON_OBJECT_NEXT)) {
					String uri_next = getUriToFetchNextResults(jsonObjectDOfRegion);
					saveRegionMasterDataToDB(uri_next, divisionExternalCode);

				}
			}
		}

	}

	public static void saveLocationMasterDataToDB(String url) {
		String apiResponseData = getApiData(url);

		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);
		// For List of Location
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject apiResponseDataAsObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(apiResponseDataAsObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(apiResponseDataAsObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();
				if (null != jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray results = jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS);

					// Iterate through the elements of the array i.
					for (int i = 0; i < results.size(); i++) {
						String locationName = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_NAME);

						String locationExternalCode = getJsonPrimitiveAsString(results, i,
								MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_CODE);

						String uri = results.get(i).getAsJsonObject()
								.getAsJsonObject(MastersConstantsForAP.GET_JSON_OBJECT_DIVISION_FLX_NAV)
								.getAsJsonObject(MastersConstantsForAP.GET_JSON_OBJECT_DEFERRED)
								.getAsJsonPrimitive(MastersConstantsForAP.GET_JSON_PRIMITIVE_URI).toString();
						uri = stripDoubleQuotes(uri);

						try {
							// To get Location Parent Division ID
							String parent_id = getLocParentId(uri);

							// save Location master data into DB
							// @param "0" if it is a location
							// @param "3" for location level 3
							if (!Utils.isBlankOrNull(parent_id)) {
								MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
								masterManagerForAP.addLocToDB(locationName, parent_id, locationExternalCode, "0", "3");
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							TPLogger.getLogger().error(GlobalConstants.ERROR, e);
						}
					} // end of loop
				}
				if (null != jsonObjectD.get(MastersConstantsForAP.GET_JSON_OBJECT_NEXT)) {
					String uri_next = getUriToFetchNextResults(jsonObjectD);
					saveLocationMasterDataToDB(uri_next);
				}
			}
		}

	}// end of method saveDepartmentMasterDataToDB

	/**
	 * @param jsonObjectD
	 * @return
	 */
	private static String getUriToFetchNextResults(JsonObject jsonObjectD) {
		String uri_next = StringUtils.EMPTY;

		uri_next = jsonObjectD.get(MastersConstantsForAP.GET_JSON_OBJECT_NEXT).toString().replaceAll("^\"|\"$", "");
		return uri_next;
	}

	/**
	 * @param apiResponseDataAsObject
	 * @return
	 */
	private static boolean isObjectD_As_JsonNull_Instance(JsonObject apiResponseDataAsObject) {
		return JsonNull.INSTANCE == apiResponseDataAsObject.get(MastersConstantsForAP.GET_JSON_OBJECT_D);
	}

	/**
	 * @param apiResponseDataAsObject
	 * @return
	 */
	private static JsonElement getDataFromJsonElementD(JsonObject apiResponseDataAsObject) {
		JsonElement jsonElementD = apiResponseDataAsObject.get(MastersConstantsForAP.GET_JSON_OBJECT_D);
		return jsonElementD;
	}

	/**
	 * @param apiResponseData
	 * @return
	 */
	private static JsonObject getApiResponseDataAsObject(String apiResponseData) {
		JsonParser jsonParser = new JsonParser();
		JsonObject apiResponseDataAsObject = jsonParser.parse(apiResponseData).getAsJsonObject();
		return apiResponseDataAsObject;
	}

	/**
	 * getApiData Method returns the API output
	 * 
	 * @param url
	 * @return
	 */
	private static String getApiData(String url) {
		ClientResponse resp;
		String apiResponse = "";
		try {
			String username = TPLabels.getLabel("SUCCESS_FACTOR_USERNAME");
			String password = TPLabels.getLabel("SUCCESS_FACTOR_PASSWORD");
			String authString = username + ":" + password;
			String authStringEnc = new BASE64Encoder().encode(authString.getBytes());

			Client restClient = Client.create();
			restClient.addFilter(new HTTPBasicAuthFilter(username, password));

			TPLogger.getLogger().info("sending request to url : " + url);

			WebResource webResource = restClient.resource(url);
			resp = webResource.accept("application/json").header("Authorization", "Basic " + authStringEnc)
					.get(ClientResponse.class);

			if (resp.getStatus() != 200) {
				TPLogger.getLogger().error(MastersConstantsForAP.SERVER_ERROR);
			}
			apiResponse = resp.getEntity(String.class);
		} catch (UniformInterfaceException e) {
			// TODO Auto-generated catch block
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} catch (ClientHandlerException e) {
			// TODO Auto-generated catch block
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

		return apiResponse;
	}// end of method getApiData

	/**
	 * @param array
	 * @param i
	 * @return
	 */
	private static String getJsonPrimitiveAsString(JsonArray results, int i, String jsonInputPrimitive) {
		String inputPrimitive = StringUtils.EMPTY;
		if (null != jsonInputPrimitive) {
			if (results.get(i).getAsJsonObject().get(jsonInputPrimitive) != JsonNull.INSTANCE)
				inputPrimitive = results.get(i).getAsJsonObject().getAsJsonPrimitive(jsonInputPrimitive).toString()
						.replaceAll("^\"|\"$", "");
			return inputPrimitive;
		}

		return inputPrimitive;
	}// end of method getJsonPrimitiveAsString

	/**
	 * @param uri
	 * @return
	 */
	private static String stripDoubleQuotes(String uri) {
		if (!Utils.isBlankOrNull(uri)) {
			uri = uri.replaceAll("^\"|\"$", "");
		}
		return uri;
	}

	/**
	 * getExternalCode Method returns external Code from JSON Array
	 * 
	 * @param url
	 * @return
	 */
	public static String getExternalCode(String url) {
		String externalCode = StringUtils.EMPTY;

		String apiResponseData = getApiData(url);

		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject apiResponseDataAsObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(apiResponseDataAsObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(apiResponseDataAsObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();
				if (null != jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray results = jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS);

					// Iterate through the elements of the array i.
					for (int i = 0; i < results.size(); i++) {
						try {
							// For company external code
							externalCode = getJsonPrimitiveAsString(results, i,
									MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_CODE);

							return externalCode;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							TPLogger.getLogger().error(GlobalConstants.ERROR, e);
						}
					} // end of loop

				}
				if (null != jsonObjectD.get(MastersConstantsForAP.GET_JSON_OBJECT_NEXT)) {
					String uri_next = getUriToFetchNextResults(jsonObjectD);
					getExternalCode(uri_next);
				}
			}
		} // end of if
		return externalCode;

	}// end of method getExternalCode

	/**
	 * getExternalCodeList Method returns external Code list from JSON Array
	 * 
	 * @param url
	 * @return
	 */
	public static List<String> getExternalCodeList(String url) {
		String externalCode = StringUtils.EMPTY;
		List<String> externalCodeList = new ArrayList<String>();

		String apiResponseData = getApiData(url);

		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject apiResponseDataAsObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(apiResponseDataAsObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(apiResponseDataAsObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();
				if (null != jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray results = jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS);

					// Iterate through the elements of the array i.
					for (int i = 0; i < results.size(); i++) {
						try {
							// For company external code
							externalCode = getJsonPrimitiveAsString(results, i,
									MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_CODE);

							externalCodeList.add(externalCode);
							return externalCodeList;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							TPLogger.getLogger().error(GlobalConstants.ERROR, e);
						}
					} // end of loop

				}
				if (null != jsonObjectD.get(MastersConstantsForAP.GET_JSON_OBJECT_NEXT)) {
					String uri_next = getUriToFetchNextResults(jsonObjectD);
					getExternalCodeList(uri_next);
				}
			}
		} // end of if
		return externalCodeList;

	}// end of method getExternalCodeList

	public static String getRegionName(String url) {

		String regionName = StringUtils.EMPTY;
		String apiResponseData = getApiData(url);
		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);
		// For List of Location
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject apiResponseDataAsObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(apiResponseDataAsObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(apiResponseDataAsObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();
				if (null != jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray results = jsonObjectD.getAsJsonArray(MastersConstantsForAP.GET_JSON_ARRAY_RESULTS);

					// Iterate through the elements of the array i.
					for (int i = 0; i < results.size(); i++) {
						try {
							// For Region Name
							regionName = getJsonPrimitiveAsString(results, i,
									MastersConstantsForAP.GET_JSON_PRIMITIVE_EXTERNAL_NAME);

							return regionName;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							TPLogger.getLogger().error(GlobalConstants.ERROR, e);
						}
					} // end of loop

				}
			}
		} // end of if
		return regionName;

	}// end of method getRegionName

	@SuppressWarnings("unused")
	public static String getCompanyParentIdList(String uri) {
		String parentId = StringUtils.EMPTY;
		List<String> companyExternalCodeList = new ArrayList<String>();

		companyExternalCodeList = getExternalCodeList(uri);

		for (String companyExternalCode : companyExternalCodeList) {
			if (!Utils.isBlankOrNull(companyExternalCode)) {
				companyExternalCode = companyExternalCode.replaceAll("^\"|\"$", "");

				MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
				MasterDataForAP masterDataForAP = masterManagerForAP
						.getDepParentIdFromDBByExternalCode(companyExternalCode);
				if (null != masterDataForAP) {
					parentId = masterDataForAP.getItemId() + "";
					return parentId;
				}
			}

		}

		return parentId;

	}// end of method getCompanyParentIdList

	public static String getJobFunctionParentId(String uri) {
		String parentId = StringUtils.EMPTY;
		String companyExternalCode = getExternalCode(uri);

		MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
		MasterDataForAP masterDataForAP = masterManagerForAP.getDepParentIdFromDBByExternalCode(companyExternalCode);
		if (null != masterDataForAP) {
			parentId = masterDataForAP.getItemId() + "";
			return parentId;
		}

		return parentId;

	}// end of method getJobFunctionParentId

	public static String getDepartmentParentId(String uri) {
		String parentId = StringUtils.EMPTY;
		String companyExternalCode = getExternalCode(uri);

		MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
		MasterDataForAP masterDataForAP = masterManagerForAP.getDepParentIdFromDBByExternalCode(companyExternalCode);
		if (null != masterDataForAP) {
			parentId = masterDataForAP.getItemId() + "";
			return parentId;
		}

		return parentId;

	}// end of method getDepartmentParentId

	public static String getJobCodeParentId(String uri) {
		String parentId = StringUtils.EMPTY;
		String companyExternalCode = getExternalCode(uri);

		MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
		MasterDataForAP masterDataForAP = masterManagerForAP
				.getJobCodeParentIdFromDBByExternalCode(companyExternalCode);
		if (null != masterDataForAP) {
			parentId = masterDataForAP.getItemId() + "";
			return parentId;
		}

		return parentId;

	}// end of method getJobCodeParentId

	public static String getPayGradeParentId(String uri) {
		String parentId = StringUtils.EMPTY;
		String jobCodeExternalCode = getExternalCode(uri);

		MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
		MasterDataForAP masterDataForAP = masterManagerForAP
				.getPayGradeParentIdFromDBByExternalCode(jobCodeExternalCode);
		if (null != masterDataForAP) {
			parentId = masterDataForAP.getItemId() + "";
			return parentId;
		}

		return parentId;

	}// end of method getPayGradeParentId

	public static String getLocParentId(String uri) {
		String parentId = StringUtils.EMPTY;
		String divisionExternalCode = getExternalCode(uri);

		MasterManagerForAP masterManagerForAP = new MasterManagerForAP();
		MasterDataForAP masterDataForAP = masterManagerForAP.getLocParentIdFromDBByExternalCode(divisionExternalCode);
		if (null != masterDataForAP) {
			parentId = masterDataForAP.getItemId() + "";
			return parentId;
		}

		return parentId;

	}// end of method getLocParentId

}
