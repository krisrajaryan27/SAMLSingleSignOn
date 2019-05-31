package com.talentPool.socialNetwork.dataobject;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
"people"
})
public class LinkedInConnectionSearchDataObject {

	@JsonProperty("people")
	private LinkedInConnectionSearchPeopleObject people;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("people")
	public LinkedInConnectionSearchPeopleObject getPeople() {
		return people;
	}

	@JsonProperty("people")
	public void setPeople(LinkedInConnectionSearchPeopleObject people) {
		this.people = people;
	}

	public LinkedInConnectionSearchDataObject withPeople(LinkedInConnectionSearchPeopleObject people) {
		this.people = people;
		return this;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperties(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
