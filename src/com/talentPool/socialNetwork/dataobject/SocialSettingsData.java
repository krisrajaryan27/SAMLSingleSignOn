/**
 * Created : Oct 24, 2013 4:08:46 PM
 * @author : Sachinm
 */
package com.talentPool.socialNetwork.dataobject;


import com.talentPool.common.db.SimpleDataObject;

/**
 * @author Sachinm
 *
 */
public class SocialSettingsData extends SimpleDataObject {

	/**
	 * default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @return socialId
	 */
	public String getSocialId() {
		return getString("socialId");
	}
	
	/**
	 * @param socialId
	 */
	public void setSocialId(String socialId) {
		setAttribute("socialId", socialId);
	}
	
	/**
	 * @return socialName
	 */
	public String getSocialName() {
		return getString("socialName");
	}
	
	/**
	 * @param socialName
	 */
	public void setSocialName(String socialName) {
		setAttribute("socialName", socialName);
	}
	
	/**
	 * @return url
	 */
	public String getUrl() {
		return getString("url");
	}
	
	/**
	 * @param url
	 */
	public void setUrl(String url) {
		setAttribute("url", url);
	}
	
	/**
	 * @return socialType
	 */
	public String getSocialType() {
		return getString("socialType");
	}
	
	/**
	 * @param socialType
	 */
	public void setSocialType(String socialType) {
		setAttribute("socialType", socialType);
	}
	
	/**
	 * @return userId
	 */
	public String getUserId(){
		return getString("userId");
	}
	
	/**
	 * @param userId
	 */
	public void setUserId(String userId){
		setAttribute("userId", userId);
	}
	
	/**
	 * @return token
	 */
	public String getToken(){
		return getString("token");
	} 
	
	/**
	 * @param token
	 */
	public void setToken(String token){
		setAttribute("token", token);
	}
	
	/**
	 * @return implementationId
	 */
	public String getImplementationId(){
		return getString("implementationId");
	}
	
	/**
	 * @param implementationId
	 */
	public void setImplementationId(String implementationId){
		setAttribute("implementationId", implementationId);
	}
	
	/**
	 * @return apiType
	 */
	public String getApiType(){
		return getString("apiType");
	}
	
	/**
	 * @param apiType
	 */
	public void setApiType(String apiType){
		setAttribute("apiType", apiType);
	}
	
	/**
	 * @return sourceId
	 */
	public String getSourceId(){
		return getString("sourceId");
	}
	
	/**
	 * @param sourceId
	 */
	public void setSourceId(String sourceId){
		setAttribute("sourceId", sourceId);
	}
	
	/**
	 * @return clientId
	 */
	public String getClientId(){
		return getString("clientId");
	}
	
	/**
	 * @param clientId
	 */
	public void setClientId(String clientId){
		setAttribute("clientId", clientId);
	}
	
	/**
	 * @return clientSecret
	 */
	public String getClientSecret(){
		return getString("clientSecret");
	}
	
	/**
	 * @param clientSecret
	 */
	public void setClientSecret(String clientSecret){
		setAttribute("clientSecret", clientSecret);
	}
	
	/**
	 * @return companyId
	 */
	public String getCompanyId(){
		return getString("companyId");
	}
	
	/**
	 * @param companyId
	 */
	public void setCompanyId(String companyId){
		setAttribute("companyId", companyId);
	}
	
	
}
