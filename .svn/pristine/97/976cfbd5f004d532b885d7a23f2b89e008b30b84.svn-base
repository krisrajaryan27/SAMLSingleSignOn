package com.talentPool.positions.utils;

import java.io.StringWriter;
import java.net.URLEncoder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.positions.dataobject.JobPositionPostings;

public class PositionJobPortalUtils {
	
	public static String postOnNaukri( JobPositionPostings jobPositionPostings){
		String url = TPApplicationProperties.getProperty("naukri.url");
		String accessKey = TPApplicationProperties.getProperty("naukri.access_key");
		String secretKey = TPApplicationProperties.getProperty("naukri.secret_key");
		String result= "";
		try{
			JAXBContext context = JAXBContext.newInstance(JobPositionPostings.class);
			StringWriter wr = new StringWriter();
		    Marshaller m = context.createMarshaller();
		    m.marshal(jobPositionPostings,wr);
		    
		    MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		    TPLogger.getLogger().info("XML sent to Naukri::"+wr.toString());
			formData.add("content", URLEncoder.encode(wr.toString(), "UTF-8"));
			URLConnectionClientHandler urlConnectionClientHandler = new URLConnectionClientHandler(new MyHttpURLConnectionFactory());
			Client client = new Client(urlConnectionClientHandler);
			WebResource webResource = client.resource(url);
			ClientResponse response = webResource.header("accessKey", accessKey).header("secretKey", secretKey).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);
			result = response.getEntity(String.class);
			response.close();
			TPLogger.getLogger().info("Naukri Response returned::"+result);

			
		} catch (Exception e){
			TPLogger.getLogger().error("Error publishing position:", e);
		}
		return result;
	}
	

}
