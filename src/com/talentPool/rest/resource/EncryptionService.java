package com.talentPool.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.EncryptionUtils;

/**
 * @author SumeetS
 *
 */
@Path("/encryptDecryptService")
public class EncryptionService {
	
	@Path("/encrypt")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response encryptService(@FormParam("value") String value,@FormParam("key") String key){
		String result = null;
		try {
			result = EncryptionUtils.encryptLogic(value,key);
		} catch (Exception e) {
			TPLogger.getLogger().error("error encrypting",e);
			return Response.status(Status.BAD_REQUEST).entity(result).build();
		}
		return Response.status(Status.OK).entity(result).build();
		
	} 
	
	@Path("/decrypt")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response decryptService(@FormParam("value") String value,@FormParam("key") String key){
		String result = null;
		try {
			result = EncryptionUtils.decryptLogic(value,key);
		} catch (Exception e) {
			TPLogger.getLogger().error("error decrypting",e);
			return Response.status(Status.BAD_REQUEST).entity(result).build();
		}
		return Response.status(Status.OK).entity(result).build();
	} 
}
