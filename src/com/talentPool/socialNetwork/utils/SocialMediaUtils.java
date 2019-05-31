package com.talentPool.socialNetwork.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.Whitelist;
import org.xml.sax.helpers.AttributesImpl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.socialNetwork.dataobject.DataObject;
import com.talentPool.socialNetwork.dataobject.EntryObject;
import com.talentPool.socialNetwork.dataobject.Person;
import com.talentPool.socialNetwork.dataobject.SocialPostTransferObject;
import com.talentPool.socialNetwork.dataobject.SocialSettingsData;
import com.talentPool.socialNetwork.dataobject.UploadPersonList;

/**
 * @author SumeetS
 *
 */
public class SocialMediaUtils {
	
	/**
	 * @param dao
	 * @param url
	 * @return posts on social media 
	 */
	public static SocialPostTransferObject postOnSocialMedia(SocialPostTransferObject to){
		try{
			Client client = Client.create();
			WebResource webResource = client.resource(Utils.buildSocialURL(TPApplicationProperties.getProperty("social.post.URL")));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(to);
			oos.flush();
			oos.close();
			ClientResponse response = webResource.type(MediaType.APPLICATION_OCTET_STREAM).post(ClientResponse.class,new ByteArrayInputStream(baos.toByteArray()));
			InputStream is = response.getEntity(InputStream.class);
			ObjectInput oi = new ObjectInputStream(is);
			to = (SocialPostTransferObject) oi.readObject();
			response.close();
		} catch (Exception e){
			TPLogger.getLogger().error("Error publishing position:", e);
		}
		return to;
	}

	
	public static UploadPersonList uploadPersonNodesIntoGraph(UploadPersonList uploadPersonList){
		 UploadPersonList returnPersonList = null;
		try{
			Client client = Client.create();
			WebResource webResource = client.resource(Utils.buildSocialURL(TPApplicationProperties.getProperty("social.graph.personNode.upload")));
			ClientResponse response = webResource.type("application/xml").post(ClientResponse.class,uploadPersonList);
			returnPersonList = response.getEntity(UploadPersonList.class);
			response.close();
		}catch(Exception e){
			TPLogger.getLogger().error("Error uploading nodes into graph:", e);
		}
		return returnPersonList;
	}
	
	public static Person fetchCandidateProfile(String url){
		Person person = null;
		try{
			Client client = Client.create();
			WebResource webResource = client.resource(url);
			ClientResponse response = webResource.type("text/plain").get(ClientResponse.class);
			person = response.getEntity(Person.class);
			if(person==null){
				person=new Person();
			}
			//Applicant p = response.getEntity(Person.class);
		} catch(ClientHandlerException e){
			TPLogger.getLogger().debug("No information found in graph Person object is null");
		}
		catch (Exception e){
			TPLogger.getLogger().error("Error fetching candidate Profile:", e);
		}
		return person;
	}
	
	/**
	 * @param postContent
	 * @return converts html to string
	 */
	public static String getTextContentFromHTML(String postContent) {
		String result=null;
		postContent= postContent.replaceAll("&nbsp;"," ");
		String htmlString = Jsoup.clean(postContent, "", Whitelist.none().addTags("br", "p"), new OutputSettings().prettyPrint(true));
		result = Jsoup.clean(htmlString, "", Whitelist.none(), new OutputSettings().prettyPrint(false));
		return result;
	}

	/**
	 * @param groups
	 * @return xml for social media group list
	 */
	public static String getXMLForGroupList(ArrayList<SocialSettingsData> groups) {
		StringWriter swr = new StringWriter();
		XMLWriter wr = new XMLWriter(swr);
		int cnt=0;
		try{
			wr.startDocument();
			if(groups != null && groups.size() > 0){
				wr.startElement("rows");
				for(SocialSettingsData group: groups){
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "",""+cnt);
					wr.startElement("","row","",at);
					
					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" style=\"cursor:pointer;\" onclick=\"javascript: deleteField("+ cnt +");\"/>");
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(group.getSocialName());
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(group.getSocialType());
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(group.getUrl());
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(group.getImplementationId());
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(group.getSocialId());
					wr.endElement("cell");
					
					wr.endElement("row");
					cnt++;
				}
			}else{
				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "total_rows", "", "", "0");
				wr.startElement("", "rows", "", at);
			}
			wr.endElement("rows");
			wr.endDocument();
		}catch(Exception e){
			TPLogger.getLogger().error("Error generating XML: ", e);
		}
		return swr.getBuffer().toString();
	}
	
	/**
	 * @param groups
	 * @return
	 */
	public static String getXMLForGroupCompanyListPublish(ArrayList<SocialSettingsData> groups) {
		StringWriter swr = new StringWriter();
		XMLWriter wr = new XMLWriter(swr);
		int cnt=0;
		try{
			wr.startDocument();
			if(groups != null && groups.size() > 0){
				wr.startElement("rows");
				for(SocialSettingsData group: groups){
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "",""+group.getSocialId()+"$$"+group.getImplementationId()+"$$"+group.getSourceId());
					wr.startElement("","row","",at);
					
					wr.startElement("cell");
					wr.characters("<img src=\"images/ico_delete.gif\" style=\"cursor:pointer;\" onclick=\"javascript: deleteField("+ cnt +");\"/>");
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(group.getSocialName());
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(group.getSocialType());
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(group.getUrl());
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(group.getImplementationId());
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(group.getSocialId());
					wr.endElement("cell");
					
					wr.endElement("row");
					cnt++;
				}
			}else{
				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "total_rows", "", "", "0");
				wr.startElement("", "rows", "", at);
			}
			wr.endElement("rows");
			wr.endDocument();
		}catch(Exception e){
			TPLogger.getLogger().error("Error generating XML: ", e);
		}
		return swr.getBuffer().toString();
	}
	
	/**
	 * @return returns application action return path that needs to be passed to the social media service
	 */
	public static String getReturnPath(){
		String host = TPApplicationProperties.getProperty("application.external_IP");
		if (Utils.isBlankOrNull(host)){
			host = TPApplicationProperties.getProperty("system.name");
		}
		String path = TPApplicationProperties.getProperty("application.protocol")+ "://"
				+ host+ ":"
				+ TPApplicationProperties.getProperty("application.port")+ "/"
				+ TPApplicationProperties.getProperty("application.alias")
				+ "/extractEvent.action";
		return path;
	}

	
	public static DataObject fetchLinkedInProfile(DataObject obj,String personId) {
		Client client = Client.create();
		String url = Utils.buildSocialURL(TPApplicationProperties.getProperty("social.connectionSearch"));
		url=url.replace("{person-id}",personId);
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type("application/xml").post(ClientResponse.class,obj);
		DataObject result = response.getEntity(DataObject.class);
		response.close();
		return result;
	}

	public static DataObject getCommonFactorsFromGraph(String personId) {
		Client client = Client.create();
		String url = Utils.buildSocialURL(TPApplicationProperties.getProperty("social.common.graphSearch"));
		url=url.replace("{person-id}",personId);
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type("application/xml").get(ClientResponse.class);
		DataObject result = response.getEntity(DataObject.class);
		response.close();
		return result;
	}

	public static String setCredentials(String sourceName, String clientId,String clientSecret,String companyIds) {
		DataObject obj = new DataObject();
		String props = "sourceName="+sourceName+"|clientId="+clientId+"|clientSecret="+clientSecret+"|companyIds="+companyIds;
		EntryObject obj1 = new EntryObject();
		obj1.setKey("props");
		obj1.setValue(props);
		obj.getEntries().add(obj1);
		String result = putConfiguration(obj);
		return result;
	}
	
	private static String putConfiguration(DataObject obj){
		Client client = Client.create();
		String url = Utils.buildSocialURL(TPApplicationProperties.getProperty("social.credentials.update"));
		WebResource webResource = client.resource(url);
		ClientResponse response = webResource.type("application/xml").put(ClientResponse.class,obj);
		String result = response.getEntity(String.class);
		response.close();
		return result;
	}

	/**
	 * removes relationship of a person from graph
	 * @param person
	 * @return 
	 */
	public static String removeUserInfoFromGraph(String personId) {
		String result = null;
		ClientResponse response =null;
		try{
			Client client = Client.create();
			String url = Utils.buildSocialURL(TPApplicationProperties.getProperty("social.graph.personNode.delete"));
			url=url.replace("{person-id}",personId);
			WebResource webResource = client.resource(url);
			response= webResource.type("application/xml").get(ClientResponse.class);
			result = response.getEntity(String.class);
		}catch(Exception e){
			TPLogger.getLogger().error("Error deleting nodes into graph:", e);
		}finally{
			response.close();
		}
		return result;
	}

}
