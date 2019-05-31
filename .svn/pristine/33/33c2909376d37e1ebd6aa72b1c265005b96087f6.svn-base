/**
 * 
 */
package com.talentPool.otherApplications.rest.client;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBElement;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.otherApplications.constant.OtherApplicationConstants;
import com.talentPool.otherApplications.rest.model.ListApplicantDetailModel;

/**
 * @author Shantanu
 *
 */
public class RestDataProcessorClient {
	public static final URI BASE_URI = getBaseURI();
	
	private static URI getBaseURI(){
		String talentpoolUrl= OtherApplicationConstants.TALENTPOOL_URL;
		return UriBuilder.fromUri(talentpoolUrl).build();
	}
	
	public ListApplicantDetailModel getFromRestApplicantDetailData(){
		ListApplicantDetailModel listApplicantDetailModel = null;
		try{
			ClientConfig clientConfig = new DefaultClientConfig();
			Client client =  Client.create(clientConfig);		
			String restPath = OtherApplicationConstants.TALENTPOOL_REST_URL;
			WebResource webResource = client.resource(BASE_URI);
			GenericType<JAXBElement<ListApplicantDetailModel>> listApplicantsDetail= new GenericType<JAXBElement<ListApplicantDetailModel>>(){};		
			listApplicantDetailModel = (ListApplicantDetailModel) webResource.path(restPath).accept(MediaType.APPLICATION_XML).get(listApplicantsDetail).getValue();			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
			return null;
		}				
		return listApplicantDetailModel;
	}
	
	public void postToRestApplicantDetailData(ListApplicantDetailModel listApplicantDetails){
		try{
			ClientConfig clientConfig = new DefaultClientConfig();
			Client client =  Client.create(clientConfig);
			String restPath = OtherApplicationConstants.TALENTPOOL_REST_URL;
			WebResource webResource = client.resource(BASE_URI);
			ClientResponse clientResponse = webResource.path(restPath).accept(MediaType.APPLICATION_XML).post(ClientResponse.class,listApplicantDetails);			
		}catch (Exception e) {
			TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
		}
		
	}	
}
