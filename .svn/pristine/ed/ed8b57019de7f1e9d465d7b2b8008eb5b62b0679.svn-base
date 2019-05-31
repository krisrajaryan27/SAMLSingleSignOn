/*
 * The JAPE grammar rules along  with gazetteer lists tries to annotate 
 * (annotations are stored in TempExperienceToken) basic building blocks of experience
 * 1.Company name -> (TempExperienceToken.kind = company_name)
 * 2.Job title -> (TempExperienceToken.kind = job_title)
 * 3.Duration -> (TempExperienceToken.kind = date_range) will also have 
 * properties for fromDate, toDate, isTillDate. All the dates are formatted dd-MM-yyyy 
 * Experience.groovy is run over these annotations to aggregate them into valid experience.
 * The main steps in Experience.groovy are
 * 1. Identify all the sections which contains experience
 * 2. For each section Aggregate experience
 * 3. Remove duplicate experience.
 */

import gate.Annotation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Document;
import gate.util.OffsetComparator;


boolean isExpBackFirstTime;
boolean isBackwardLookSucceded;
processDocument();

/*
 * This is main method which is run in Experience.groovy
 */
public void processDocument(){
	isExpBackFirstTime = true;
	isBackwardLookSucceded = false;
	get_exp_sections(doc);
	grmr_process_exp_sections(doc);
	remove_duplicate_experience(doc);
}

/*
 * Process all the ExpSections considering data ranges which contain month
 * if didn't get experiences process all ExpSections considering data ranges which contain only year like 2004 - 2006
 * if didn't get any experience process entire document.
 */
public void grmr_process_exp_sections(Document doc){
	AnnotationSet annAll = doc.getAnnotations();
	List annAllList = new ArrayList(annAll);
	Collections.sort(annAllList, new OffsetComparator());
	Annotation ann = annAllList.get(annAllList.size()-1);	
	Long docEndOffSet = ann.getEndNode().getOffset();
	AnnotationSet secSet = annAll.get("ExpSection");
	if(secSet != null && !secSet.isEmpty()){
		List secList = new ArrayList(secSet);
		Collections.sort(secList, new OffsetComparator());
		
		for(Annotation secAnn : secList){
			long start = secAnn.getStartNode().getOffset();
			long end = secAnn.getEndNode().getOffset();
			grmr_process_exp_section(doc, start, end, false);
		}
		AnnotationSet expTokenSet = annAll.get("ExperienceToken");
		if(expTokenSet == null || expTokenSet.isEmpty()) {
			for(Annotation secAnn : secList){
				long start = secAnn.getStartNode().getOffset();
				long end = secAnn.getEndNode().getOffset();
				grmr_process_exp_section(doc, start, end, true);
			}
		}
	}
	AnnotationSet expTokenSet = annAll.get("ExperienceToken");
	if(expTokenSet == null || expTokenSet.isEmpty()) {
		grmr_process_exp_section(doc, 0, docEndOffSet, false);
	}
	
	expTokenSet = annAll.get("ExperienceToken");
	if(expTokenSet == null || expTokenSet.isEmpty()) {
		grmr_process_exp_section(doc, 0, docEndOffSet, true);
	}
}
/*
 * Get all the date ranges in given section.
 * process these data ranges.
 */
public void grmr_process_exp_section(Document doc, long start, long end, boolean isYearDateRange){
	ArrayList<Annotation> expSentences = grmr_get_experience_date_ranges(doc, start, end, isYearDateRange);
	isExpBackFirstTime = true;
	isBackwardLookSucceded = false;
	grmr_process_experience_date_ranges(doc, expSentences, start, end);
}

/*
 * If a data range is completed contained within another data range
 * we consider this as duplicate and we will remove it. We will annotate this as DupExperienceToken
 * for debugging purpose
 * if a data range is partially contained in another data range
 * we consider it as invalid and will remove it.
 */
public void remove_duplicate_experience(Document doc){
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet expSet1 = annAll.get("ExperienceToken");
	List expList1 = new ArrayList(expSet1);
	Collections.sort(expList1, new OffsetComparator());
	Hashtable duplicateDetector = new Hashtable();
	for(int i = 0; i < expList1.size(); i++){
		Annotation expAnn = expList1.get(i);
		gate.FeatureMap fm = expAnn.getFeatures();
		SimpleDateFormat sdFormat = new SimpleDateFormat("dd-mm-yyyy");
		Date annFromDate = sdFormat.parse(fm.get("fromDate").toString());
		Date annToDate = sdFormat.parse(fm.get("toDate").toString());
		String dateKey = sdFormat.format(annFromDate)+"-"+ sdFormat.format(annToDate);
		if(duplicateDetector.containsKey(dateKey)){
			Annotation prevAnn = duplicateDetector.get(dateKey);
			gate.FeatureMap prevFM = prevAnn.getFeatures();
			if(fm.size() > prevFM.size()){
				duplicateDetector.put(dateKey, expAnn);
				doc.annotations.remove(prevAnn);
				prevFM.put("duplicate","exact_date_match");
				doc.annotations.add(prevAnn.getStartNode(), prevAnn.getEndNode(), "DupExperienceToken",prevFM);	
			}
			else{
				doc.annotations.remove(expAnn);
				fm.put("duplicate","exact_date_match");
				doc.annotations.add(expAnn.getStartNode(), expAnn.getEndNode(), "DupExperienceToken",fm);
			}
		}else{
			duplicateDetector.put(dateKey, expAnn);
		}
	}
	
	//Check Partial overlaps
	boolean oneMoreRound = true;
	while(oneMoreRound){
		oneMoreRound = false;
		AnnotationSet expSet = annAll.get("ExperienceToken");
		List expList = new ArrayList(expSet);
		Collections.sort(expList, new OffsetComparator());
		for(int i = 0; i < expList.size(); i++){
			Annotation expAnn = expList.get(i);
			if(isContainedPartially(expAnn, expList)){
				doc.annotations.remove(expAnn);
				//expList.remove(expAnn);
				doc.annotations.add(expAnn.getStartNode(), expAnn.getEndNode(), "DupExperienceToken", expAnn.getFeatures());
				oneMoreRound = true;
				break;
			}
		}
	}
	
	//Check for complete overlaps
	oneMoreRound = true;
	while(oneMoreRound){
		oneMoreRound = false;
		AnnotationSet expSet = annAll.get("ExperienceToken");
		List expList = new ArrayList(expSet);
		Collections.sort(expList, new OffsetComparator());
		for(int i = 0; i < expList.size(); i++){
			Annotation expAnn = expList.get(i);
			if(isContained(expAnn, expList)){
				doc.annotations.remove(expAnn);
				doc.annotations.add(expAnn.getStartNode(), expAnn.getEndNode(), "DupExperienceToken", expAnn.getFeatures());
				oneMoreRound = true;
				break;
			}
		}
	}
}

/*
 * Checks if an Experience is partially contained in another experience
 */
public boolean isContainedPartially(Annotation ann, List expList){
	SimpleDateFormat sdFormat2 = new SimpleDateFormat("dd-MM-yyyy");
	Date annFromDate = sdFormat2.parse(ann.getFeatures().get("fromDate").toString());
	Date annToDate = sdFormat2.parse(ann.getFeatures().get("toDate").toString());
	if(expList != null && !expList.isEmpty()){
		for(Annotation expAnn : expList){
			Date fromDate = sdFormat2.parse(expAnn.getFeatures().get("fromDate").toString());
			Date toDate = sdFormat2.parse(expAnn.getFeatures().get("toDate").toString());
			if(ann != expAnn && annFromDate.compareTo(fromDate) > 0 && annFromDate.compareTo(toDate) < 0
			&& annToDate.compareTo(toDate) > 0){
				gate.FeatureMap expFM = ann.getFeatures();
				expFM.put("duped_by_partial", expAnn.getFeatures().get("company_name"));
				return true;
			}
		}
	}	
	return false;
}

/*
 * Checks if an Experience is completely contained in another experience
 */

public boolean isContained(Annotation ann, List expList){
	SimpleDateFormat sdFormat2 = new SimpleDateFormat("dd-MM-yyyy");
	Date annFromDate = sdFormat2.parse(ann.getFeatures().get("fromDate").toString());
	Date annToDate = sdFormat2.parse(ann.getFeatures().get("toDate").toString());
	if(expList != null && !expList.isEmpty()){
		for(Annotation expAnn : expList){
			Date fromDate = sdFormat2.parse(expAnn.getFeatures().get("fromDate").toString());
			Date toDate = sdFormat2.parse(expAnn.getFeatures().get("toDate").toString());
			if(ann != expAnn && annFromDate.compareTo(fromDate) >= 0 && annFromDate.compareTo(toDate) <= 0
			&& annToDate.compareTo(fromDate) >= 0 && annToDate.compareTo(toDate) <= 0){
				gate.FeatureMap expFM = ann.getFeatures();
				expFM.put("duped_by", expAnn.getFeatures().get("company_name"));
				return true;
			}
		}
	}	
	return false;
}

/*
 * Gets all the CustomSentence annotations which contains a date range
 */
public ArrayList<Annotation> grmr_get_experience_date_ranges(Document doc, long start, long end, boolean isYearDateRange){
	gate.FeatureMap features = gate.Factory.newFeatureMap();
	features.put("kind","date_range");
	
	AnnotationSet annAll = doc.getAnnotations().get(start, end);
	AnnotationSet senSet = annAll.get("CustomSentence");
	List senList = new ArrayList(senSet);
	Collections.sort(senList, new OffsetComparator());
	ArrayList<Annotation> myArr = new ArrayList<Annotation>();
	ArrayList<Annotation> alreadyConsideredArr = new ArrayList<Annotation>();
	for(Annotation senAnn : senList){
		AnnotationSet containedAnns = annAll.get(senAnn.getStartNode().getOffset(), senAnn.getEndNode().getOffset());
		AnnotationSet expSet;
		if(isYearDateRange){
			features.put("isYearOnly",true);
			expSet = containedAnns.get("TempExperienceToken", features);			
		}
		else{
			features.put("isYearOnly",false);
			expSet = containedAnns.get("TempExperienceToken", features);			
		}
		if(!expSet.isEmpty()){
			Annotation dateRangeAnn = expSet.iterator().next();
			if(!isAlreadyConsidered(alreadyConsideredArr, dateRangeAnn)){
				alreadyConsideredArr.add(dateRangeAnn);
				myArr.add(senAnn);
			}
		}
	}
	return myArr;
}

/*
 * Checks if given annotation is already in the array list or not.
 */
public boolean isAlreadyConsidered(ArrayList<Annotation> alreadyConsideredArr, Annotation newAnnotaion){
	for(Annotation ann : alreadyConsideredArr){
		if(ann.equals(newAnnotaion)){
			return true;
		}
	}
	return false;
}

/*
 * 1. For each data range sentence we got
 * 2. we will try to get company name and job title
 * 	  by looking backward and looking forward
 * 3. For the first date range we will check if lookup backward is succeeded or not
 * 4. If backward lookup is not successful for the first data range then for the remaining date ranges
 * 	  we will not look backward
 */

public void grmr_process_experience_date_ranges(Document doc, ArrayList<Annotation> expSentences, long start, long end){
	AnnotationSet annAll = doc.getAnnotations().getContained(start, end);
	AnnotationSet senSet = annAll.get("CustomSentence");
	if(senSet == null || senSet.isEmpty()){
		return;
	}
	List senList = new ArrayList(senSet);
	Collections.sort(senList, new OffsetComparator());
	Annotation ann = (Annotation)senList.get(senList.size()-1);
	if(expSentences != null &&  !expSentences.isEmpty()){
		for(Annotation senAnn : expSentences){
			gate.FeatureMap finalFeatures = gate.Factory.newFeatureMap();			
			grmr_fill_exp_tokens(doc, senAnn, finalFeatures, true);
			int numberOfSentences = 25;
			//Look backward
			List back3Sentences;
			List forward3Sentences;
			if(isExpBackFirstTime || isBackwardLookSucceded){
				if((senAnn.getStartNode().getOffset()-1) > start) {
					AnnotationSet backSet = annAll.get(start, senAnn.getStartNode().getOffset()-1);
					AnnotationSet backSenSet = backSet.get("CustomSentence");
					List backSenList = new ArrayList(backSenSet);
					Collections.sort(backSenList, new OffsetComparator());
					Collections.reverse(backSenList);
					if(backSenList.size() >= numberOfSentences){
						back3Sentences = backSenList.subList(0,numberOfSentences);
					}
					else{
						back3Sentences = backSenList.subList(0, backSenList.size());				
					}
					
					for(Annotation backAnn : back3Sentences){
						String content = doc.getContent().getContent(backAnn.getStartNode().getOffset(), backAnn.getEndNode().getOffset());
						if(backAnn.getFeatures().containsKey("processed")){
							break;
						}
						boolean breakLoop = false;
						breakLoop = grmr_fill_exp_tokens(doc, backAnn, finalFeatures, false);
						if(breakLoop){
							break;
						}
					}
				}
				if(isExpBackFirstTime){
					isExpBackFirstTime = false;
					if(finalFeatures.containsKey("company_name")){
						isBackwardLookSucceded = true;
					}
				}
			}
			
			//Look forward
			if(end > (senAnn.getEndNode().getOffset()+1)){
				AnnotationSet forwardSet = annAll.get(senAnn.getEndNode().getOffset()+1, end);
				AnnotationSet forwardSenSet = forwardSet.get("CustomSentence");
				List forwardSenList = new ArrayList(forwardSenSet);
				Collections.sort(forwardSenList, new OffsetComparator());
				if(forwardSenList.size() >= numberOfSentences){
					forward3Sentences = forwardSenList.subList(0,numberOfSentences);
				}
				else{
					forward3Sentences = forwardSenList.subList(0, forwardSenList.size());				
				}
				
				for(Annotation forwardAnn : forward3Sentences){
					if(forwardAnn.getFeatures().containsKey("processed")){
						break;
					}
					boolean breakLoop = false;
					breakLoop = grmr_fill_exp_tokens(doc, forwardAnn, finalFeatures, false);
					if(breakLoop){
						break;
					}
				}
				
			}
			
			if(finalFeatures.size() > 1){
				long annStart = Long.parseLong(finalFeatures.get("start").toString());
				long annEnd = Long.parseLong(finalFeatures.get("end").toString());
				finalFeatures.remove("start");
				finalFeatures.remove("end");
				finalFeatures.put("rule", "grmr");
				doc.annotations.add(annStart, annEnd, "ExperienceToken",finalFeatures);
			}
		}		
	}
}

/*
 *  Extract experience properties (like company name) from the current sentence
 *  and fill the FeatureMap accordingly.
 *  if we encounter an experience property and FeatureMap already contains this,
 *  then we will not fill this property and we will stop looking in that direction (forward or backward)
 */
public boolean grmr_fill_exp_tokens(Document doc, Annotation senAnn, gate.FeatureMap finalFeatures, boolean firstSentence){
	boolean breakLoop = false;
	AnnotationSet annAll = doc.getAnnotations();
	AnnotationSet containedAnns = annAll.get(senAnn.getStartNode().getOffset(), senAnn.getEndNode().getOffset());
	String content = doc.getContent().getContent(senAnn.getStartNode().getOffset(), senAnn.getEndNode().getOffset());

	//Fill Exp Tokens
	gate.FeatureMap tempFeatures = gate.Factory.newFeatureMap();
	AnnotationSet expSet = containedAnns.get("TempExperienceToken");
	List expList = new ArrayList(expSet);
	Collections.sort(expList, new OffsetComparator());
	for(Annotation expAnn : expList){		
		gate.FeatureMap initialFeatures = expAnn.getFeatures();
		String token = initialFeatures.get("kind").toString();
		long start = expAnn.getStartNode().getOffset();
		long end = expAnn.getEndNode().getOffset();
		String annotation = doc.getContent().getContent(start, end);
		String tempToken = token;
		if(token.equals("date_range")){
			tempToken = "fromDate";
		}
		if(!finalFeatures.containsKey(tempToken)){
			if(token.equals("date_range")){
				grmr_add_feature(tempFeatures,"fromDate",initialFeatures.get("fromDate"));
				grmr_add_feature(tempFeatures,"toDate",initialFeatures.get("toDate"));
				if(initialFeatures.containsKey("isTillDate")){
					grmr_add_feature(tempFeatures,"isTillDate","true");					
				}
				else{
					grmr_add_feature(tempFeatures,"isTillDate","false");					
				}
			}else{
				grmr_add_feature(tempFeatures,token,annotation);				
			}
		}
		else {
			if(!token.equals("job_title"))
				breakLoop = true;
		}
	}
	
	if(!breakLoop){
		if(firstSentence){
			finalFeatures.put("start", senAnn.getStartNode().getOffset());	
			finalFeatures.put("end", senAnn.getEndNode().getOffset());	
		}
		else{
			if(tempFeatures.size() > 0){
				long start = Long.parseLong(finalFeatures.get("start").toString());
				long end = Long.parseLong(finalFeatures.get("end").toString());
				if(senAnn.getStartNode().getOffset() < start){
					finalFeatures.put("start", senAnn.getStartNode().getOffset());				
				}
				if(senAnn.getEndNode().getOffset() > end){
					finalFeatures.put("end", senAnn.getEndNode().getOffset());				
				}
			}
		}
		// Copy temp features to final features
		int mapsize = tempFeatures.size();		
		Iterator keyValuePairs1 = tempFeatures.entrySet().iterator();
		for (int i = 0; i < mapsize; i++)
		{
			Map.Entry entry = (Map.Entry) keyValuePairs1.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			finalFeatures.put(key, value);
		}
		
		//
		doc.annotations.remove(senAnn);
		gate.FeatureMap sentenceFeatures = senAnn.getFeatures();
		sentenceFeatures.put("processed", "true");
		doc.annotations.add(senAnn.getStartNode(), senAnn.getEndNode(), "CustomSentence",sentenceFeatures);
		return false;
	}
	return breakLoop;
}
/*
 * 
 */
public void grmr_add_feature(gate.FeatureMap featureMap, String key, String value){
	if(featureMap.containsKey(key)){
		String oldValue = featureMap.get(key).toString();
		if(value.length() > oldValue.length()){
			featureMap.put(key, value);
		}
	}else{
		featureMap.put(key, value);
	}
}

/* The main approach is we will make annotations ExpSectionToken with
 * kind = all means this section header is all (like Education, Contact Info etc..)
 * kind = experience means this section header is experience
 * we make section from a (ExpSectionToke.kind = experience) to next (ExpSectionToken.kind = all)
 * 
 * Steps:
 * 1. Get all the ExpSectionToken annotations with kind experience
 * 2. For each of these annotations expSecAnn
 * 3. Get an annotation (ExpSectionToken.kind = all) expAllAnn after the end of the expSecAnn
 * 4. Make a section from start of expSecAnn to start of expAllAnn
 * 5. After identifying all the sections we will merge experience sections which are continuous.
 */

public void get_exp_sections(Document doc){
	gate.FeatureMap featuresAll = gate.Factory.newFeatureMap();
	featuresAll.put("kind","all");
	gate.FeatureMap featuresExp = gate.Factory.newFeatureMap();
	featuresExp.put("kind","experience");
	
	AnnotationSet annSecAll = doc.getAnnotations();
	List annSecAllList = new ArrayList(annSecAll);
	Collections.sort(annSecAllList, new OffsetComparator());
	Annotation ann = annSecAllList.get(annSecAllList.size()-1);
	
	Long docEndOffSet = ann.getEndNode().getOffset();
	AnnotationSet expSet = annSecAll.get("ExpSectionToken", featuresExp);
		
	List expSecList = new ArrayList(expSet);
	Collections.sort(expSecList, new OffsetComparator());
	
	ArrayList<Annotation> myArr = new ArrayList<Annotation>();
	for(Annotation expSecAnn : expSecList){
		AnnotationSet containedAnns = annSecAll.get(expSecAnn.getEndNode().getOffset(), docEndOffSet);
		AnnotationSet allSet = containedAnns.get("ExpSectionToken", featuresAll);
		if(allSet != null && !allSet.isEmpty()){
			List allList = new ArrayList(allSet);
			Collections.sort(allList, new OffsetComparator());
			//S
			long properEnd = 0;
			for(Annotation sectionAnn : allList){
				AnnotationSet betweenAnns = annSecAll.get(expSecAnn.getEndNode().getOffset(), sectionAnn.getStartNode().getOffset());
				AnnotationSet betweenCustomSentences = betweenAnns.get("CustomSentence");
				if(betweenCustomSentences != null && !betweenCustomSentences.isEmpty()){
					properEnd = sectionAnn.getStartNode().getOffset();
					break;
				}
			}
			if(properEnd == 0){
				doc.annotations.add(expSecAnn.getEndNode().getOffset(), docEndOffSet, "ExpSection",featuresAll);
			}
			else{
				doc.annotations.add(expSecAnn.getEndNode().getOffset(), properEnd, "ExpSection",featuresAll);
				
			}
		}
		else{
			doc.annotations.add(expSecAnn.getEndNode().getOffset(), docEndOffSet, "ExpSection",featuresAll);			
		}
	}
	//Merge continuous expSections
	boolean oneMoreRound = true;
	while(oneMoreRound){
		oneMoreRound = false;
		AnnotationSet expSetionsSet = annSecAll.get("ExpSection");
		if(expSetionsSet != null && !expSetionsSet.isEmpty()){
			List expSectionsList = new ArrayList(expSetionsSet);
			Collections.sort(expSectionsList, new OffsetComparator());
			for(int i = 0; i < (expSectionsList.size()-1) ; i++){
				Annotation currentAnn = expSectionsList.get(i);
				Annotation nextAnn = expSectionsList.get(i+1);
				//Overlapping annotatins
				if(nextAnn.getStartNode().getOffset() <= currentAnn.getEndNode().getOffset()){
					int endOffset = 0;
					if(currentAnn.getEndNode().getOffset() > nextAnn.getEndNode().getOffset()){
						endOffset = currentAnn.getEndNode().getOffset();
					}
					else{
						endOffset = nextAnn.getEndNode().getOffset();
					}
					doc.annotations.remove(currentAnn);
					doc.annotations.remove(nextAnn);
					gate.FeatureMap fm = gate.Factory.newFeatureMap();
					fm.put("kind", "merged_sections");
					doc.annotations.add(currentAnn.getStartNode().getOffset(), endOffset, "ExpSection", fm);
					oneMoreRound = true;
					break;
				}
				AnnotationSet containedAnns = annSecAll.get(currentAnn.getEndNode().getOffset(), nextAnn.getStartNode().getOffset());
				AnnotationSet allSet = containedAnns.get("CustomSentence");
				if(allSet == null || allSet.isEmpty() || allSet.size() <= 2){
					//Merge Two
					doc.annotations.remove(currentAnn);
					doc.annotations.remove(nextAnn);
					gate.FeatureMap fm = gate.Factory.newFeatureMap();
					fm.put("kind", "merged_sections");
					doc.annotations.add(currentAnn.getStartNode(), nextAnn.getEndNode(), "ExpSection", fm);
					oneMoreRound = true;
					break;
				}
			}
		}
	}
	
}