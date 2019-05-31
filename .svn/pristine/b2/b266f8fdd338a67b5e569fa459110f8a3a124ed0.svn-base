package com.talentPool.common.utils.HTMLUtils;

import java.util.Map;

import com.talentPool.common.utils.Utils;

public class HTMLUtils implements HTMLConstants {
	
	public static String writeInputTag(String type, String name, String id, String defaultValue, String className){
		StringBuffer sb = new StringBuffer();
		sb.append(START_TAG).append("input ");
		writeType(type, sb);
		writeName(name, sb);
		writeId(id,sb);
		writeClass(className, sb);
		writeValue(defaultValue, sb);
		sb.append(END_TAG);
		return sb.toString();
	}
	
	public static String writeInputTag(String type, Map<String,String> attributes){
		StringBuffer sb = new StringBuffer();
		sb.append(START_TAG).append("input ");
		writeType(type, sb);
		writeAttributes(attributes,sb);
		sb.append(END_TAG);
		return sb.toString();
	}
	
	public static String writeImageTag(String src, Map<String,String> attributes){
		StringBuffer sb = new StringBuffer();
		sb.append(START_TAG).append("img ");
		writeImgSoucre(src, sb);
		writeAttributes(attributes,sb);
		sb.append(END_TAG);
		return sb.toString();
	}
	
	public static String writeAnchorTag(String href, String linkName, Map<String,String> attributes){
		StringBuffer sb = new StringBuffer();
		sb.append(ANCHOR_START_TAG);
		writeHref(href, sb);
		writeAttributes(attributes,sb);
		sb.append(PARTIAL_END_TAG);
		sb.append(linkName);
		sb.append(ANCHOR_END_TAG);
		return sb.toString();
	}
	
	private static void writeAttributes(Map<String,String> attributes,StringBuffer sb){
		if(attributes!=null){
			for (Map.Entry<String, String> entry : attributes.entrySet()) {
				writeAttribute(entry.getKey(), entry.getValue(), sb);
			}
		}
	}
	
	private static void writeAttribute(String name,String value,StringBuffer sb){
		if(!Utils.isBlankOrNull(name) && !Utils.isBlankOrNull(value)){
			sb.append(name).append(EQUAL_TO).append(DOUBLE_QUOTE).append(value).append(DOUBLE_QUOTE).append(BLANK_SPACE);
		}
	}
	
	private static void writeName(String name,StringBuffer sb){
		if(!Utils.isBlankOrNull(name))
			writeAttribute("name", name, sb);
	}
	
	private static void writeId(String id,StringBuffer sb){
		if(!Utils.isBlankOrNull(id))
			writeAttribute("id", id, sb);
	}
	
	private static void writeClass(String className,StringBuffer sb){
		if(!Utils.isBlankOrNull(className))
			writeAttribute("class", className, sb);
	}
	
	private static void writeValue(String defaultVal,StringBuffer sb){
		if(!Utils.isBlankOrNull(defaultVal))
			writeAttribute("value", defaultVal, sb);
	}
	
	private static void writeType(String type,StringBuffer sb){
		if(!Utils.isBlankOrNull(type))
			writeAttribute("type", type, sb);
	}
	
	private static void writeImgSoucre(String src,StringBuffer sb){
		if(!Utils.isBlankOrNull(src))
			writeAttribute("src", src, sb);
	}
	
	private static void writeHref(String src,StringBuffer sb){
		if(!Utils.isBlankOrNull(src))
			writeAttribute("href", src, sb);
	}
	
}
