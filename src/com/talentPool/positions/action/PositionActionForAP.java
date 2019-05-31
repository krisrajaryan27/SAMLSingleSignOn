/**
 * 
 */
package com.talentPool.positions.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
import com.talentPool.positions.constants.PositionConstantsForAP;
import com.talentPool.positions.dataobject.PositionDataForAP;
import com.talentPool.positions.manager.PositionManagerForAP;

import sun.misc.BASE64Encoder;

/**
 * @author ArvindKhatik
 *
 */
public class PositionActionForAP {

	/**
	 * 
	 */
	public PositionActionForAP() {
		// TODO Auto-generated constructor stub
	}

	public static void syncPositionDataForAP() {
		// TODO Auto-generated method stub
		try {
			TPLogger.getLogger().info("Fetching Position master data");
			PositionActionForAP.savePositionDataToDB(TPLabels.getLabel("URL_TO_FETCH_POSITION_DATA"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}// end of method syncPositionDataForAP

	/**
	 * savePositionDataToDB Method to save Position data into DB
	 * 
	 * @param url
	 */
	public static void savePositionDataToDB(String url) {

		String effectiveStartDate = StringUtils.EMPTY;
		String formattedEffectiveStartDate = StringUtils.EMPTY;
		String positionTitle = StringUtils.EMPTY;
		String positionCode = StringUtils.EMPTY;

		String apiResponseData = getApiData(url);

		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);

		// For List of Positions
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject jsonObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(jsonObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(jsonObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();

				if (null != jsonObjectD.getAsJsonArray(PositionConstantsForAP.GET_JSON_ARRAY_RESULTS)) {
					JsonArray array = jsonObjectD.getAsJsonArray(PositionConstantsForAP.GET_JSON_ARRAY_RESULTS);

					// Iterate through the elements of the array i.
					for (int i = 0; i < array.size(); i++) {
						effectiveStartDate = getJsonPrimitiveAsString(array, i,
								PositionConstantsForAP.GET_JSON_PRIMITIVE_EFFECTIVE_START_DATE);
						if (!Utils.isBlankOrNull(effectiveStartDate)) {
							effectiveStartDate = effectiveStartDate.replaceAll("[^0-9]", "");
							Date date = new Date(Long.parseLong(effectiveStartDate));

							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
							simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
							formattedEffectiveStartDate = simpleDateFormat.format(date);
						}

						positionTitle = getJsonPrimitiveAsString(array, i,
								PositionConstantsForAP.GET_JSON_PRIMITIVE_TITLE);
						positionCode = getJsonPrimitiveAsString(array, i,
								PositionConstantsForAP.GET_JSON_PRIMITIVE_CODE);

						try {
							// save position data into DB
							PositionManagerForAP positionManagerForAP = new PositionManagerForAP();
							positionManagerForAP.addPositionToDB(positionCode, positionTitle,
									formattedEffectiveStartDate);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							TPLogger.getLogger().error(GlobalConstants.ERROR, e);
						}
					} // end of loop

				}
				if (null != jsonObjectD.get(PositionConstantsForAP.GET_JSON_OBJECT_NEXT)) {
					String uri_next = getUriToFetchNextResults(jsonObjectD);
					savePositionDataToDB(uri_next);

				}
			}

		}

	}// end of method savePositionDataToDB

	/**
	 * @param jsonObjectD
	 * @return
	 */
	private static String getUriToFetchNextResults(JsonObject jsonObjectD) {
		String uri_next = StringUtils.EMPTY;

		uri_next = jsonObjectD.get(PositionConstantsForAP.GET_JSON_OBJECT_NEXT).toString().replaceAll("^\"|\"$", "");
		return uri_next;
	}

	/**
	 * @param jsonObject
	 * @return
	 */
	private static boolean isObjectD_As_JsonNull_Instance(JsonObject jsonObject) {
		return JsonNull.INSTANCE == jsonObject.get(PositionConstantsForAP.GET_JSON_OBJECT_D);
	}

	/**
	 * savePositionDataToDB Method to save Position data into DB
	 * 
	 * @param url
	 */
	public static List<PositionDataForAP> getPositionDataForAP(String positionCode) {

		String companyCode = StringUtils.EMPTY;
		String businessUnitCode = StringUtils.EMPTY;
		String functionCode = StringUtils.EMPTY;
		String deptCode = StringUtils.EMPTY;
		String jobCode = StringUtils.EMPTY;
		String payGradeCode = StringUtils.EMPTY;
		String divisionCode = StringUtils.EMPTY;
		String regionCode = StringUtils.EMPTY;
		String locationCode = StringUtils.EMPTY;
		String effectiveStartDate = StringUtils.EMPTY;
		String url = StringUtils.EMPTY;

		PositionManagerForAP positionManagerForAP = new PositionManagerForAP();
		effectiveStartDate = positionManagerForAP.getPositionEffectiveStartDateForAP(positionCode);

		if (!Utils.isBlankOrNull(effectiveStartDate)) {
			url = TPLabels.getLabel("URL_TO_FETCH_POSITION_BY_POSITION_CODE") + "(code='" + positionCode
					+ "',effectiveStartDate=datetime'" + effectiveStartDate + "')";
		}

		String apiResponseData = getApiData(url);
		TPLogger.getLogger().info("json response from url : " + url + "is :\n " + apiResponseData);

		List<PositionDataForAP> results = new ArrayList<PositionDataForAP>();

		// For List of Positions
		if (!Utils.isBlankOrNull(apiResponseData)) {
			JsonObject apiResponseDataAsObject = getApiResponseDataAsObject(apiResponseData);
			if (!isObjectD_As_JsonNull_Instance(apiResponseDataAsObject)) {
				JsonElement jsonElementD = getDataFromJsonElementD(apiResponseDataAsObject);
				JsonObject jsonObjectD = jsonElementD.getAsJsonObject();

				companyCode = getJsonPrimitiveDataAsString(jsonObjectD,
						PositionConstantsForAP.GET_JSON_PRIMITIVE_COMPANY_CODE);
				businessUnitCode = getJsonPrimitiveDataAsString(jsonObjectD,
						PositionConstantsForAP.GET_JSON_PRIMITIVE_BUSINESS_UNIT_CODE);
				functionCode = getJsonPrimitiveDataAsString(jsonObjectD,
						PositionConstantsForAP.GET_JSON_PRIMITIVE_FUNCTION_CODE);
				deptCode = getJsonPrimitiveDataAsString(jsonObjectD,
						PositionConstantsForAP.GET_JSON_PRIMITIVE_DEPARTMENT_CODE);
				jobCode = getJsonPrimitiveDataAsString(jsonObjectD, PositionConstantsForAP.GET_JSON_PRIMITIVE_JOB_CODE);
				payGradeCode = getJsonPrimitiveDataAsString(jsonObjectD,
						PositionConstantsForAP.GET_JSON_PRIMITIVE_PAYGRADE_CODE);
				divisionCode = getJsonPrimitiveDataAsString(jsonObjectD,
						PositionConstantsForAP.GET_JSON_PRIMITIVE_DIVISION_CODE);
				regionCode = getJsonPrimitiveDataAsString(jsonObjectD,
						PositionConstantsForAP.GET_JSON_PRIMITIVE_REGION_CODE);
				locationCode = getJsonPrimitiveDataAsString(jsonObjectD,
						PositionConstantsForAP.GET_JSON_PRIMITIVE_LOCATION_CODE);
				try {
					// get position data from DB
					positionManagerForAP = new PositionManagerForAP();

					results = positionManagerForAP.getPositionDataListFromDBForAP(companyCode, businessUnitCode,
							deptCode, functionCode, jobCode, payGradeCode, divisionCode, regionCode, locationCode,
							positionCode);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				}

			}

		}
		return results;

	}// end of method savePositionDataToDB

	/**
	 * @param apiResponseDataAsObject
	 * @return
	 */
	private static JsonElement getDataFromJsonElementD(JsonObject apiResponseDataAsObject) {
		JsonElement jsonElementD = apiResponseDataAsObject.get(PositionConstantsForAP.GET_JSON_OBJECT_D);
		return jsonElementD;
	}

	/**
	 * @param apiResponseData
	 * @return
	 */
	private static JsonObject getApiResponseDataAsObject(String apiResponseData) {
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(apiResponseData).getAsJsonObject();
		return jsonObject;
	}

	/**
	 * @param obj
	 * @param jsonInputPrimitive
	 * @return
	 */
	private static String getJsonPrimitiveDataAsString(JsonObject obj, String jsonInputPrimitive) {
		String inputPrimitive = StringUtils.EMPTY;
		if (obj.get(jsonInputPrimitive) != JsonNull.INSTANCE) {
			inputPrimitive = obj.get(jsonInputPrimitive).toString().replaceAll("^\"|\"$", "");
		}

		return inputPrimitive;
	}// end of method getJsonPrimitiveAsString

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
	private static String getJsonPrimitiveAsString(JsonArray array, int i, String jsonInputPrimitive) {
		String inputPrimitive = StringUtils.EMPTY;
		if (null != jsonInputPrimitive) {
			if (array.get(i).getAsJsonObject().get(jsonInputPrimitive) != JsonNull.INSTANCE)
				inputPrimitive = array.get(i).getAsJsonObject().getAsJsonPrimitive(jsonInputPrimitive).toString()
						.replaceAll("^\"|\"$", "");
			return inputPrimitive;
		}

		return inputPrimitive;
	}// end of method getJsonPrimitiveAsString

}
