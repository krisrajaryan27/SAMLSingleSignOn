package com.talentPool.rest.index;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.talentPool.repository.TPIndexEvent;
import com.talentPool.repository.TPIndexEventQueue;

/**
 * @author sumeets
 *
 */
@Path("/index")
public class TPIndexRestService {
	
	/**
	 * @param eventType
	 * @param eventTypeId
	 * @param priority
	 * @return
	 */
	@GET
	@Path("/push")
	public Response insertIntoEventQueue(@QueryParam("eventType") String eventType, @QueryParam("eventTypeId") String eventTypeId,@QueryParam("priority") String priority){
		TPIndexEventQueue.push(new TPIndexEvent(eventType, eventTypeId, Integer.parseInt(priority)));
		return Response.status(200).build();
		
	} 
}
