
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;


import antlr.NameSpace;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.FeatureMap;
import gate.creole.annic.apache.lucene.search.ScoreDoc;
import gate.util.InvalidOffsetException;
import gate.util.OffsetComparator;

def annotationComparator;
processDocument();
extractLocation();
extractSalary();
public void extractLocation(){
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet sentenceSet = annAll.get("LocationToken");
	if(sentenceSet != null && !sentenceSet.isEmpty()){
		List sentenceList = new ArrayList(sentenceSet);
		Collections.sort(sentenceList, annotationComparator);
		Annotation locAnn = (Annotation)sentenceList.get(0);
		String location = doc.getContent().getContent(locAnn.getStartNode().getOffset(), locAnn.getEndNode().getOffset());
		gate.FeatureMap fMap = gate.Factory.newFeatureMap();
		fMap.put("category", "basicInfo");
		fMap.put("kind", "location");
		fMap.put("rule", locAnn.getFeatures().get("rule").toString());		
		doc.annotations.add(locAnn.getStartNode(), locAnn.getEndNode(), "ResumeToken", fMap);
		//doc.annotations.add
	}
}
public void extractSalary(){
	AnnotationSet annAll = doc.getAnnotations();
	gate.FeatureMap fMap = gate.Factory.newFeatureMap();
	fMap.put("kind", "current");
	AnnotationSet sentenceSet = annAll.get("TempSalaryToken", fMap);
	if(sentenceSet != null && !sentenceSet.isEmpty()){
		List sentenceList = new ArrayList(sentenceSet);
		Collections.sort(sentenceList, annotationComparator);
		Annotation locAnn = (Annotation)sentenceList.get(0);
		String location = doc.getContent().getContent(locAnn.getStartNode().getOffset(), locAnn.getEndNode().getOffset());
		gate.FeatureMap fMap1 = gate.Factory.newFeatureMap();
		fMap1.put("category", "basicInfo");
		fMap1.put("kind", "salary_current");
		fMap1.put("rule", locAnn.getFeatures().get("rule").toString());		
		doc.annotations.add(locAnn.getStartNode(), locAnn.getEndNode(), "ResumeToken", fMap1);

	}
	fMap.put("kind", "expected");
	sentenceSet = annAll.get("TempSalaryToken", fMap);
	if(sentenceSet != null && !sentenceSet.isEmpty()){
		List sentenceList = new ArrayList(sentenceSet);
		Collections.sort(sentenceList,annotationComparator);
		Annotation locAnn = (Annotation)sentenceList.get(0);
		String location = doc.getContent().getContent(locAnn.getStartNode().getOffset(), locAnn.getEndNode().getOffset());
		gate.FeatureMap fMap1 = gate.Factory.newFeatureMap();
		fMap1.put("category", "basicInfo");
		fMap1.put("kind", "salary_expected");
		fMap1.put("rule", locAnn.getFeatures().get("rule").toString());		
		doc.annotations.add(locAnn.getStartNode(), locAnn.getEndNode(), "ResumeToken", fMap1);

	}
}
public void processDocument() {
	//Groovy Closure
	annotationComparator = [
        compare:{ann1, ann2 ->
			Integer priority1 = 0;
			Integer priority2 = 0;
			FeatureMap fm1 = ann1.getFeatures();
			if (fm1 != null && fm1.containsKey("priority")) {
				try {
					priority1 = Integer.parseInt(fm1.get("priority").toString());
				} catch (NumberFormatException ne) {
					ne.printStackTrace();
				}
			}
			FeatureMap fm2 = ann2.getFeatures();
			if (fm2 != null && fm2.containsKey("priority")) {
				try {
					priority2 = Integer.parseInt(fm2.get("priority").toString());
				} catch (NumberFormatException ne) {
					ne.printStackTrace();
				}
			}
			return priority2.compareTo(priority1);
			}] as Comparator<Annotation>;
	 
	sentenecifyTokens(doc, "TempEmailToken", null);
	extractEmail(doc);
	
	FeatureMap tempResMap = gate.Factory.newFeatureMap();
	tempResMap.put("majorType", "name");
	sentenecifyTokens(doc, "TempNameToken", tempResMap);
	//sentenecifyTokens(DOCUMENT);
	extractName(doc);
	sentenecifyTokens(doc, "TempPhoneToken", null);
	extractPhoneNo(doc);
	//sentenecifyPhoneTokens(DOCUMENT);
	//getNameUsingFirstOrLastName(DOCUMENT);
	//getNameUsingLCS(DOCUMENT);
}

public boolean sentenecifyTokens(Document doc, String tokenName, FeatureMap featuresFilter){
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet sentenceSet = annAll.get("CustomSentence");
	if(sentenceSet != null || sentenceSet.size() > 0){
		List sentenceList = new ArrayList(sentenceSet);
		Collections.sort(sentenceList, new OffsetComparator());
		List startingSentence;
		List endingSentence;
		if(sentenceList.size()>= 10) {
			startingSentence = sentenceList.subList(0,10);
			endingSentence = sentenceList.subList(sentenceSet.size() - 10,sentenceSet.size());
		}
		else {
			startingSentence = sentenceList.subList(0,sentenceSet.size());
			endingSentence = sentenceList.subList(0,sentenceSet.size());		
		}
		AnnotationSet nameAnns = null;
		if(featuresFilter != null){
			nameAnns = annAll.get(tokenName, featuresFilter);
		}
		else {
			nameAnns = annAll.get(tokenName);
		}
		List nameAnnsList = new ArrayList(nameAnns);
		Collections.sort(nameAnnsList, new OffsetComparator());
		
		for(Annotation ann : nameAnnsList){
			long start = ann.getStartNode().getOffset();
			long end = ann.getEndNode().getOffset();
			String annotation = doc.getContent().getContent(start, end);
			
			FeatureMap fMap = ann.getFeatures();
			if(withinSpanOf(startingSentence, ann)){
				fMap.put("position","start");
			}
			else if(withinSpanOf(endingSentence, ann)){
				if(!fMap.containsKey("position")) {
					fMap.put("position", "end");
				}
			}
			else{
				if(!fMap.containsKey("position")) {
					fMap.put("position", "middle");
				}
			}
			doc.annotations.remove(ann);
			doc.annotations.add(ann.getStartNode(), ann.getEndNode(), tokenName,fMap);
		}
	}
}
public boolean withinSpanOf(List parentAnns, Annotation childAnn){
	if(parentAnns != null){
		for (Annotation ann : parentAnns) {
			if(childAnn.withinSpanOf(ann)) {
				return true;
			}
		}
	}
	return false;
}
public void extractName(Document doc){
	AnnotationSet annAll = doc.getAnnotations();
	gate.FeatureMap features = gate.Factory.newFeatureMap();
	features.put("category","basicInfo");
	features.put("kind","name");
	AnnotationSet nameAnns = annAll.get("ResumeToken", features);
	if(nameAnns != null && nameAnns.size() > 0){
		return;
	}
	LCSToken[] emailTokens = getEmailTokens(doc);
	
	LCSToken[] nameTokens = null; 
	boolean success = false;
	
	if(!success){	
		nameTokens = getTempResumeNameTokens(doc, "name_colon", "all", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_colon", true, false);
	}
	if(!success){	
		nameTokens = getTempResumeNameTokens(doc, "name_colon", "start", false,1);
		success = extractNameUsingGazetteer(doc, nameTokens, "name_colon");
		//success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_colon_gazatter", true);
	}
	
	
	if(!success){	
		nameTokens = getTempResumeNameTokens(doc, "smart_name_newline_known", "start", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "smart_name_newline_known_start", true, false);
	}
	
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_newline_known", "end", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "smart_name_newline_known_end", true, false);		
	}
	
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_newline_unknown", "start", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "smart_name_newline_unknown_start", true, false);		
	}
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_newline_unknown", "end", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "smart_name_newline_unknown_end", true, false);		
	}
	
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_newline_known", "middle", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "smart_name_newline_known_middle", true, false);		
	}
	
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_newline_unknown", "middle", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "smart_name_newline_unknown_middle", true, false);		
	}
	
	
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_known", "start", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "smart_name_known_start", true, false);		
	}
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_known", "end", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "smart_name_known_end", true, false);		
	}
	
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "name_newline_known", "start", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_newline_known_start", true, false);		
	}
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "name_newline_known", "end", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_newline_known_end", true, false);		
	}
	
	
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_known", "middle", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "smart_name_known_middle", true, false);		
	}
	
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "name_newline_token", "start", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_newline_token_start", true, false);		
	}
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "name_newline_token", "end", true,1);
		success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_newline_token_end", true, false);		
	}
	
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "name_colon", "all", false,1);
		success = extractNameUsingGazetteer(doc, nameTokens, "name_colon_gazatteer");
	}
	
	
	//Directly from gazetter
	if(!success){
		success = extractNameUsingGazetteers(success, doc, 2);
	}
	if(!success){
		success = extractNameUsingGazetteers(success, doc, 1);
	}
	/*
	 if(!success){
	 nameTokens = getTempResumeNameTokens(doc, "name_newline_token", "middle");
	 success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_newline_token_middle", true);		
	 }
	 */
	
	if(!success){
		success = extractNameUsingEmail(doc, emailTokens);
	}
	/*	
	 if(!success){
	 nameTokens = getTempResumeNameTokens(doc, "known", "start");
	 success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_lcs_known_start", true);		
	 }
	 if(!success){
	 nameTokens = getTempResumeNameTokens(doc, "known", "end");
	 success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_lcs_known_end", true);
	 }
	 if(!success){
	 nameTokens = getTempResumeNameTokens(doc, "unknown", "start");
	 success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_lcs_unknown_start", true);
	 }
	 if(!success){
	 nameTokens = getTempResumeNameTokens(doc, "unknown", "end");
	 success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_lcs_unknown_end", true);
	 }
	 if(!success){
	 nameTokens = getTempResumeNameTokens(doc, "known", "middle");
	 success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_lcs_known_middle", true);
	 }
	 if(!success){
	 nameTokens = getTempResumeNameTokens(doc, "unknown", "middle");
	 success = extractNameUsingLCS(doc, emailTokens, nameTokens, "name_lcs_unknown_middle", true);
	 }
	 if(!success){
	 success = extractNameUsingEmail(doc, emailTokens);
	 }
	 if(!success){
	 nameTokens = getTempResumeNameTokens(doc, "known_newline", "start");
	 success = extractNameUsingGazetteer(doc, nameTokens, "name_gazetteer_start");
	 }
	 if(!success){
	 nameTokens = getTempResumeNameTokens(doc, "known_newline", "end");
	 success = extractNameUsingGazetteer(doc, nameTokens, "name_gazetteer_end");
	 }
	 if(!success){
	 nameTokens = getTempResumeNameTokens(doc, "known_newline", "middle");
	 success = extractNameUsingGazetteer(doc, nameTokens, "name_gazetteer_middle");
	 }
	 */
}
public boolean extractNameUsingGazetteers(boolean success1, Document doc, int minTokenLength){
	LCSToken[] nameTokens = null; 
	boolean success = success1;
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_newline_known", "start", false,minTokenLength);
		success = extractNameUsingGazetteer(doc, nameTokens, "smart_name_newline_known_gazatteer_start");
		
	}
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_newline_known", "end", false,minTokenLength);
		success = extractNameUsingGazetteer(doc, nameTokens, "smart_name_newline_known_gazatteer_end");
	}
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_known", "start", false,minTokenLength);
		success = extractNameUsingGazetteer(doc, nameTokens, "smart_name_known_gazatteer_start");
		
	}
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_known", "end", false,minTokenLength);
		success = extractNameUsingGazetteer(doc, nameTokens, "smart_name_known_gazatteer_end");
	}
	
	
	
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_newline_unknown", "start", false,minTokenLength);
		success = extractNameUsingGazetteer(doc, nameTokens, "smart_name_newline_unknown_gazatteer_start");
	}
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_newline_unknown", "end", false, minTokenLength);
		success = extractNameUsingGazetteer(doc, nameTokens, "smart_name_newline_unknown_gazatteer_end");
	}
	
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "name_newline_known", "start", false, minTokenLength);
		success = extractNameUsingGazetteer(doc, nameTokens, "name_newline_known_gazatteer_start");
		
	}
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "name_newline_known", "end", false, minTokenLength);
		success = extractNameUsingGazetteer(doc, nameTokens, "name_newline_known_gazatteer_end");
	}
	
	
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_newline_known", "middle", false, minTokenLength);
		success = extractNameUsingGazetteer(doc, nameTokens, "smart_name_newline_known_gazatteer_middle");
		
	}
	if(!success){
		nameTokens = getTempResumeNameTokens(doc, "smart_name_newline_unknown", "middle", false, minTokenLength);
		success = extractNameUsingGazetteer(doc, nameTokens, "smart_name_newline_unknown_gazatteer_middle");
		
	}
	return success;
}
public boolean extractNameUsingLCS(Document doc, LCSToken[] emailTokens, LCSToken[] nameTokens, String ruleName, boolean useSubStringMatch, boolean useStrictLCS){
	if(emailTokens != null && nameTokens != null){
		String lcsFinal = "";
		LCSToken lcsFinalToken = null;
		float currentScore = 0;
		for(LCSToken emailToken : emailTokens){
			for(LCSToken nameToken : nameTokens){
				boolean runLCS = true;;
				if(emailToken.CompactToken.length() <= 2 || nameToken.CompactToken.length() <= 2) {
					runLCS = false;
				}
				if(emailToken.CompactToken.length() > 35) {
					runLCS = false;
				}
				if(nameToken.CompactToken.length() > 35){
					runLCS = false;
				}
				//runLCS = true;
				if(runLCS){
					String lcs = longestSubSequence(emailToken.CompactToken, nameToken.CompactToken);
					String lcs1 = longestSubSequence(emailToken.CompactToken, nameToken.CompactTokenReverse);
					if(lcs1.length() > lcs.length()){
						lcs = lcs1;
					}
					float score = 0;
					if(emailToken.CompactToken.length() < nameToken.CompactToken.length()) {
						score = (float)lcs.length()/(float)emailToken.CompactToken.length();
					}
					else {
						score = (float)lcs.length()/(float)nameToken.CompactToken.length();
					}
					if(useStrictLCS) {
						if(score > 0.80 && score > currentScore) {
							String longestSubString = longestSubString(emailToken.CompactToken, nameToken.CompactToken);
							if(longestSubString.length() > 4) {
								currentScore = score;
								lcsFinal = lcs;
								lcsFinalToken = nameToken;
							}
						}
						
					}
					else {
						if(score > 0.49 && score > currentScore) {
							String longestSubString = longestSubString(emailToken.CompactToken, nameToken.CompactToken);
							if(longestSubString.length() > 3) {
								currentScore = score;
								lcsFinal = lcs;
								lcsFinalToken = nameToken;
							}
						}
					}
				}
			}
		}
		if((useStrictLCS && currentScore > 0.80) || currentScore > 0.49) {
			gate.FeatureMap features = gate.Factory.newFeatureMap();
			features.put("category","basicInfo");
			features.put("kind","name");
			features.put("rule",ruleName);
			features.put("priority","100");
			doc.annotations.add(lcsFinalToken.annotation.getStartNode(), lcsFinalToken.annotation.getEndNode(), "ResumeToken",features);
			return true;
		}
	}
	return false;
}
public boolean extractNameUsingEmail(Document doc, LCSToken[] emailTokens){
	if(emailTokens != null){
		for(LCSToken lcsToken : emailTokens) {
			String temp = lcsToken.FullToken.toLowerCase().split("@")[0];
			String[] parts = temp.split("[_.]")
			String name = "";
			int namePartsCount = 0;
			if(parts != null && parts.length > 1){
				for(String part : parts){
					String aTemp = "";
					for (int j = 0; j < part.length(); j++) {
						Character c = part.charAt(j);
						if (Character.isLetter(c)) {
							aTemp = aTemp + c.toString();
						}
					}
					if(aTemp.length() > 0) {
						name = name + " " + aTemp.charAt(0).toString().toUpperCase()+aTemp.substring(1);
						namePartsCount = namePartsCount + 1;
					}
				}
				if(namePartsCount > 1) {
					gate.FeatureMap features = gate.Factory.newFeatureMap();
					features.put("category","basicInfo");
					features.put("kind","name");
					features.put("rule","name_extracted_from_email");
					features.put("priority","100");
					features.put("name", name.trim());
					features.put("email",  lcsToken.FullToken);
					doc.annotations.add(lcsToken.annotation.getStartNode(), lcsToken.annotation.getEndNode(), "ResumeToken" ,features);
					return true;
				}
			}
		}
	}
	return false;
}
public boolean extractNameUsingGazetteer(Document doc, LCSToken[] nameTokens, String ruleName) {
	if(nameTokens != null && nameTokens.size()>0){
		LCSToken lcsToken = nameTokens[0];
		gate.FeatureMap features = gate.Factory.newFeatureMap();
		features.put("category","basicInfo");
		features.put("kind","name");
		features.put("rule",ruleName);
		features.put("priority","100");
		doc.annotations.add(lcsToken.annotation.getStartNode(), lcsToken.annotation.getEndNode(), "ResumeToken" ,features);
		return true;
	}
	return false;
}
public boolean extractPhoneNo(Document doc){
	AnnotationSet annAll = doc.getAnnotations();
	FeatureMap tempResMap = gate.Factory.newFeatureMap();
	tempResMap.put("position", "start");
	tempResMap.put("majorType", "phone");
	//tempResMap.put("minorType", "land");
	AnnotationSet annSet = annAll.get("TempPhoneToken", tempResMap);
	Hashtable<String, String> ht = new Hashtable<String, String>();
	
	boolean homePhoneDetected = false;
	if(annSet != null && annSet.size() > 0){
		List annList = new ArrayList(annSet);
		Collections.sort(annList, new OffsetComparator());
		for(Annotation ann : annList){
			gate.FeatureMap features = gate.Factory.newFeatureMap();
			long start = ann.getStartNode().getOffset();
			long end = ann.getEndNode().getOffset();
			String annotation = doc.getContent().getContent(start, end);
			if(!ht.containsKey(annotation)){
				ht.put(annotation, annotation);
				FeatureMap annMap = ann.getFeatures();
				String phoneKind = annMap.get("minorType");
				features.put("category","basicInfo");
				features.put("kind","phone");
				features.put("phoneKind", phoneKind);
				features.put("rule", "phone_at_start");
				features.put("priority", "100");
				doc.annotations.add(ann.getStartNode(), ann.getEndNode(), "ResumeToken",features);
			}
		}
	}
	if(ht.isEmpty()){
		tempResMap = gate.Factory.newFeatureMap();
		//tempResMap.put("position", "middle");
		tempResMap.put("majorType", "phone");
		tempResMap.put("minorType", "mobile");
		annSet = annAll.get("TempPhoneToken", tempResMap);
		if(annSet != null && annSet.size() > 0){
			List annList = new ArrayList(annSet);
			Collections.sort(annList, new OffsetComparator());
			Annotation  ann = (Annotation)annList.get(0);
			gate.FeatureMap features = gate.Factory.newFeatureMap();
			features.put("category","basicInfo");
			features.put("kind","phone");
			features.put("phoneKind", "mobile");
			features.put("rule", "phone_at_middle_mobile");
			features.put("priority", "100");
			long start = ann.getStartNode().getOffset();
			long end = ann.getEndNode().getOffset();
			String annotation = doc.getContent().getContent(start, end);
			ht.put(annotation, annotation);
			doc.annotations.add(ann.getStartNode(), ann.getEndNode(), "ResumeToken",features);
		}
	}
	if(ht.isEmpty()){
		tempResMap = gate.Factory.newFeatureMap();
		//tempResMap.put("position", "middle");
		tempResMap.put("majorType", "phone");
		tempResMap.put("minorType", "land");
		annSet = annAll.get("TempPhoneToken", tempResMap);
		if(annSet != null && annSet.size() > 0){
			List annList = new ArrayList(annSet);
			Collections.sort(annList, new OffsetComparator());
			Annotation  ann = (Annotation)annList.get(0);
			gate.FeatureMap features = gate.Factory.newFeatureMap();
			features.put("category","basicInfo");
			features.put("kind","phone");
			features.put("phoneKind", "land");
			features.put("rule", "phone_at_middle_land");
			features.put("priority", "100");
			long start = ann.getStartNode().getOffset();
			long end = ann.getEndNode().getOffset();
			String annotation = doc.getContent().getContent(start, end);
			ht.put(annotation, annotation);
			doc.annotations.add(ann.getStartNode(), ann.getEndNode(), "ResumeToken",features);
		}
	}
}
public boolean extractEmail(Document doc){
	AnnotationSet annAll = doc.getAnnotations();
	FeatureMap tempResMap = gate.Factory.newFeatureMap();
	tempResMap.put("position", "start");
	AnnotationSet annSet = annAll.get("TempEmailToken", tempResMap);
	Hashtable<String, String> ht = new Hashtable<String, String>();	
	if(annSet != null && annSet.size() > 0){
		List annList = new ArrayList(annSet);
		Collections.sort(annList, new OffsetComparator());
		for(Annotation ann : annList){
			gate.FeatureMap features = gate.Factory.newFeatureMap();
			long start = ann.getStartNode().getOffset();
			long end = ann.getEndNode().getOffset();
			String annotation = doc.getContent().getContent(start, end);
			if(!ht.containsKey(annotation)){
				ht.put(annotation, annotation);
				features.put("category","basicInfo");
				features.put("kind","email");
				features.put("rule","email_not_start");
				features.put("priority","100");
				doc.annotations.add(ann.getStartNode(), ann.getEndNode(), "ResumeToken",features);
				
			}
		}
	}
	if(ht.isEmpty()){
		annSet = annAll.get("TempEmailToken");
		if(annSet != null && annSet.size() > 0){
			List annList = new ArrayList(annSet);
			Collections.sort(annList, new OffsetComparator());
			Annotation  ann = (Annotation)annList.get(0);
			gate.FeatureMap features = gate.Factory.newFeatureMap();
			features.put("category","basicInfo");
			features.put("kind","email");
			features.put("rule","email_not_start");
			features.put("priority","100");
			doc.annotations.add(ann.getStartNode(), ann.getEndNode(), "ResumeToken",features);
		}
	}
}
public boolean isHomePhone(String phoneNo){
	String aTemp = "";
	for (int j = 0; j < phoneNo.length(); j++) {
		Character c = phoneNo.charAt(j);
		if (Character.isDigit(c)) {
			aTemp = aTemp + c.toString();
		}
	}
	if(aTemp.length() > 0){
		aTemp = aTemp.substring(aTemp.length()-10,aTemp.length());
		if(!aTemp.startsWith("9") && !aTemp.startsWith("8")){
			return true;
		}
	}
	return false;
}

public LCSToken[] getTempResumeNameTokens(Document doc, String kind, String position, boolean forLCS, int minTokenLength) {
	AnnotationSet annAll = doc.getAnnotations();
	FeatureMap tempResMap = gate.Factory.newFeatureMap();
	tempResMap.put("majorType", "name");
	tempResMap.put("minorType", kind);
	if(!position.equalsIgnoreCase("all")) {
		tempResMap.put("position", position);
	}
	AnnotationSet nameAnns = annAll.get("TempNameToken", tempResMap);
	if(nameAnns != null && nameAnns.size() > 0){
		List nameAnnsList = new ArrayList(nameAnns);
		Collections.sort(nameAnnsList, new OffsetComparator());
		ArrayList<LCSToken> lcsTokensList = new ArrayList<LCSToken>();
		int i = 0;
		for(Annotation ann : nameAnnsList){
			long start = ann.getStartNode().getOffset();
			long end = ann.getEndNode().getOffset();
			String annotation = doc.getContent().getContent(start, end);
			annotation = annotation.toLowerCase();
			String inputName = annotation;
			if(inputName.endsWith(".")){
				inputName = inputName.substring(0, inputName.length()-1);
			}
			String[] splits = inputName.split("[ .]");
			boolean proper = false;
			for(String split : splits){
				if(split.length() > 2){
					proper = true;
					break;
				}
			}
			if(position.equalsIgnoreCase("middle") || position.equalsIgnoreCase("end")){
				if(splits.length == 1){
					proper = false;
				}
			}
			else if(!forLCS && splits.length < minTokenLength)
			{
				proper = false;
			}
			/*
			else if(kind.equalsIgnoreCase("smart_name_newline_unknown")) {
				if(splits.length == 1){
					proper = false;
				}
			}
			*/		
			if(proper){
				LCSToken t = new LCSToken();
				t.FullToken = doc.getContent().getContent(start, end);
				t.annotation = ann;
				String aTemp = "";
				for (int j = 0; j < annotation.length(); j++) {
					Character c = annotation.charAt(j);
					if (Character.isLetter(c)) {
						aTemp = aTemp + c.toString();
					}
				}
				t.CompactToken = aTemp;
				
				annotation = reverseName(annotation);
				String aTemp1 = "";
				for (int j = 0; j < annotation.length(); j++) {
					Character c = annotation.charAt(j);
					if (Character.isLetter(c)) {
						aTemp1 = aTemp1 + c.toString();
					}
				}
				t.CompactTokenReverse = aTemp1;
				lcsTokensList.add(t);
			}
		}
		LCSToken[] lctTokenArray = new LCSToken[lcsTokensList.size()];
		return lcsTokensList.toArray(lctTokenArray);
	}
	return null;
}
public static String reverseName(String inputName){
	if(inputName == null){
		return null;
	}
	if(inputName.endsWith(".")){
		inputName = inputName.substring(0, inputName.length()-1);
	}
	String[] splits = inputName.split("[ .]");
	if(splits.length > 1){
		String temp = splits[0];
		splits[0] = splits[splits.length-1];
		splits[splits.length-1]=temp;
	}
	StringBuilder sb = new StringBuilder();
	for(String k : splits){
		sb.append(k);
	}
	return sb.toString();
}
public LCSToken[] getEmailTokens(Document doc)
throws InvalidOffsetException {
	AnnotationSet annAll = doc.getAnnotations();
	FeatureMap map = gate.Factory.newFeatureMap();
	map.put("category", "basicInfo");
	map.put("kind", "email");
	AnnotationSet emails = annAll.get("ResumeToken", map);
	LCSToken[] emailArr = new LCSToken[emails.size()];
	int i = 0;
	for (Object o : emails) {
		Annotation ann = (Annotation) o;
		long start = ann.getStartNode().getOffset();
		long end = ann.getEndNode().getOffset();
		String annotation = doc.getContent().getContent(start, end);
		//.toString().toLowerCase();
		annotation = annotation.toLowerCase();
		LCSToken t = new LCSToken();
		t.FullToken = annotation;
		t.annotation = ann;
		String temp = annotation.split("@")[0];
		String aTemp = "";
		for (int j = 0; j < temp.length(); j++) {
			Character c = temp.charAt(j);
			if (Character.isLetter(c)) {
				aTemp = aTemp + c.toString();
			}
		}
		t.CompactToken = aTemp;
		emailArr[i] = t;
		i++;
	}
	return emailArr;
	
}
/**
 * @param args
 */
public String longestSubSequence(String X, String Y) {
	int i,j;
	//String X = "ABCABCBA";
	//String Y = "BACBBA";
	/* initialize the n x m matrix B and C for dynamic programming 
	 * B[i][j] stores the directions, C[i][j] stores the length of LCS of
	 * X[0..i-1] and Y[0..j-1]
	 */ 
	int n = X.length();
	int m = Y.length();
	int[][] C = new int[n+1][m+1];
	int[][] B = new int[n+1][m+1];
	
	/* C[i][0] = 0 for 0<=i<=n */
	for (i = 0; i <= n; i++) {
		C[i][0] = 0;
	}
	
	/* C[0][j] = 0 for  0<=j<=m */
	for (j = 0; j <= m; j++) {
		C[0][j] = 0;
	}
	
	/* dynamic programming */
	for (i = 1; i <= n; i++) {
		for (j = 1; j <= m; j++) {
			if (X.charAt(i-1) == Y.charAt(j-1)) {
				C[i][j]=C[i-1][j-1]+1;
				B[i][j]=1;  /* diagonal */
			}
			else if (C[i-1][j]>=C[i][j-1]) {
				C[i][j]=C[i-1][j];
				B[i][j] = 2;  /* down */
			}
			else {
				C[i][j]=C[i][j-1];     
				B[i][j]=3;   /* forword */
			}
		}
	}
	/* Backtracking */
	String lcs = new String();
	i=n;
	j=m;
	while (i!=0 && j!=0) {
		if (B[i][j] ==1) {   /* diagonal */
			lcs =X.charAt(i-1).toString() + lcs;
			i = i - 1;
			j = j - 1;
		}
		if (B[i][j] == 2) {  /* up */
			i = i - 1;
		}
		if (B[i][j] == 3) {  /* backword */
			j = j - 1;
		}
	}
	
	return lcs;
}
/**
 * This method is extracted from
 * http://en.wikibooks.org/wiki/Algorithm_Implementation
 * /Strings/Longest_common_substring#C.23 The java implementation was giving
 * only length of longest substring to also get the longest substring we also used C#
 * implementation
 * 
 * @param str_
 * @param toCompare_
 * @return
 */
public String longestSubString(String str_, String toCompare_) {
	if (str_.isEmpty() || toCompare_.isEmpty())
		return "";
	
	int[][] compareTable = new int[str_.length()][toCompare_.length()];
	int maxLen = 0;
	int lastSubsBegin = 0;
	StringBuilder sequenceBuilder = new StringBuilder();
	
	for (int m = 0; m < str_.length(); m++) {
		for (int n = 0; n < toCompare_.length(); n++) {
			if (str_.charAt(m) != toCompare_.charAt(n)) {
				compareTable[m][n] = 0;
			} else {
				if ((m == 0) || (n == 0)) {
					compareTable[m][n] = 1;
				} else {
					compareTable[m][n] = compareTable[m - 1][n - 1] + 1;
				}
			}
			if (compareTable[m][n] > maxLen) {
				maxLen = compareTable[m][n];
				int thisSubsBegin = m - compareTable[m][n] + 1;
				if (lastSubsBegin == thisSubsBegin) {
					// if the current LCS is the same as the last time this
					// block ran
					sequenceBuilder.append(str_.charAt(m));
				} else {
					// this block resets the string builder if a different
					// LCS is found
					lastSubsBegin = thisSubsBegin;
					sequenceBuilder.delete(0, sequenceBuilder.length());// clear
					// it
					sequenceBuilder.append(str_.substring(lastSubsBegin,
							m + 1));
				}
			}
			/*
			 * maxLen = (compareTable[m][n] > maxLen) ? compareTable[m][n] :
			 * maxLen;
			 */
		}
	}
	return sequenceBuilder.toString();
}




public class LCSToken {
	/*
	public static void main(String[] args){
	}*/
	public LCSToken() {
	}
	public String FullToken;
	public String CompactToken;
	public String CompactTokenReverse;
	public Annotation annotation;
}

